package com.lameute.referentieltournoi.domain.groupeoptimise.usecase.fetch;

import com.lameute.referentieltournoi.domain.commons.entity.Partie;
import com.lameute.referentieltournoi.domain.groupeoptimise.entity.GroupeOptimise;
import com.lameute.referentieltournoi.domain.groupeoptimise.entity.JoueurShort;
import com.lameute.referentieltournoi.domain.groupeoptimise.usecase.port.input.IFetchAllGroupeOptimise;
import com.lameute.referentieltournoi.domain.groupeoptimise.usecase.port.output.ReadAllJoueurPlayingRepository;
import com.lameute.referentieltournoi.domain.groupeoptimise.usecase.port.output.ReadAllPartieOfPreviousRondeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toCollection;

@RequiredArgsConstructor
@Slf4j
@Service
public class FetchAllGroupeOptimiseUseCase implements IFetchAllGroupeOptimise {

    private final ReadAllJoueurPlayingRepository readAllJoueurPlayingRepository;
    private final ReadAllPartieOfPreviousRondeRepository readAllPartieOfPreviousRondeRepository;

    private List<JoueurShort> joueursPlaying;
    private List<Partie> previousParties;
    private Map<JoueurShort, Map<JoueurShort, Integer>> previousPlaysMatrix;
    private List<JoueurShort> previouslyAbsentJoueurs;

    @Override
    public List<GroupeOptimise> process(Integer nombreGroupe, Integer currentRonde) {
        this.joueursPlaying = readAllJoueurPlayingRepository.readAllJoueurPlaying().stream().map(JoueurShort::new).collect(toCollection(ArrayList::new));
        this.previousParties = readAllPartieOfPreviousRondeRepository.readAllPartieOfPreviousRonde(currentRonde);
        this.previousPlaysMatrix = this.generatePreviousPlaysMatrix();
        this.previouslyAbsentJoueurs = this.generatePreviousAbsentJoueursList(currentRonde);
        var nombreJoueursRestants = new AtomicInteger(this.joueursPlaying.size());

        return IntStream.range(1, nombreGroupe + 1).boxed().map(numeroGroupe -> {
            var nbPlayersInGroup = numeroGroupe.equals(nombreGroupe) ? nombreJoueursRestants.get() : nombreJoueursRestants.get() / (nombreGroupe - (numeroGroupe - 1));
            var nbPreviousPlayersInGroup = this.getNbPreviousPlayersInGroup(nombreGroupe, numeroGroupe);
            nombreJoueursRestants.set(nombreJoueursRestants.get() - nbPlayersInGroup);
            var currentRoundGroupe = this.generateCurrentRoundGroupe(nbPlayersInGroup, currentRonde, nbPreviousPlayersInGroup);
            this.joueursPlaying.removeAll(currentRoundGroupe);
            return GroupeOptimise.builder()
                    .numeroGroupe(numeroGroupe)
                    .nombreJoueurs(nbPlayersInGroup)
                    .joueurs(currentRoundGroupe)
                    .build();
        }).toList();
    }

    private Integer getNbPreviousPlayersInGroup(Integer nombreGroupe, Integer numeroGroupe) {
        if (this.previouslyAbsentJoueurs.isEmpty()) {
            return 0;
        }
        var nbPreviousPlayersInGroupIfNotLastGroup = this.previouslyAbsentJoueurs.size() / (nombreGroupe);
        var correctedNbPreviousPlayersInGroupIfNotLastGroup = nbPreviousPlayersInGroupIfNotLastGroup != 0 ? nbPreviousPlayersInGroupIfNotLastGroup : 1;
        return numeroGroupe.equals(nombreGroupe) ? this.previouslyAbsentJoueurs.size() : correctedNbPreviousPlayersInGroupIfNotLastGroup;
    }

    private Map<JoueurShort, Map<JoueurShort, Integer>> generatePreviousPlaysMatrix() {
        Map<JoueurShort, Map<JoueurShort, Integer>> numberOfPlaysTogetherMatrix = new HashMap<>();
        this.joueursPlaying.forEach(joueur -> {
            Map<JoueurShort, Integer> opponents = new HashMap<>();
            this.joueursPlaying.stream().filter(Predicate.not(joueur::equals)).forEach(autreJoueur -> opponents.put(autreJoueur, 0));
            this.previousParties.forEach(previousPartie -> {
                List<JoueurShort> joueursPartie = previousPartie.joueurs().stream().map(JoueurShort::new).filter(this.joueursPlaying::contains).toList();
                if (joueursPartie.stream().anyMatch(joueur::equals)) {
                    joueursPartie.stream().filter(Predicate.not(joueur::equals)).forEach(joueurPartie -> opponents.merge(joueurPartie, 1, Integer::sum));
                }
            });
            numberOfPlaysTogetherMatrix.put(joueur, opponents);
        });
        return numberOfPlaysTogetherMatrix;
    }

    private List<JoueurShort> generateCurrentRoundGroupe(Integer nbPlayersInGroup, Integer currentRonde, Integer nbPreviousPlayersInGroup) {
        List<JoueurShort> groupeJoueurs = new ArrayList<>(nbPlayersInGroup);
        Collections.shuffle(this.joueursPlaying);
        AtomicInteger nbPreviousPlayersInGroupLeft = new AtomicInteger(nbPreviousPlayersInGroup);
        List<JoueurShort> joueursAbleToBePutInGroup = new ArrayList<>(this.joueursPlaying);

        IntStream.range(0, nbPlayersInGroup).forEach(
                playerNb -> {
                    this.joueursPlaying.removeAll(groupeJoueurs);
                    joueursAbleToBePutInGroup.removeAll(groupeJoueurs);
                    if (nbPreviousPlayersInGroupLeft.get() == 0) {
                        joueursAbleToBePutInGroup.removeAll(this.previouslyAbsentJoueurs);
                    }
                    var newJoueur = getPlayerWhoLessPlayedAgainstOthersPlayers(groupeJoueurs, joueursAbleToBePutInGroup, currentRonde);
                    groupeJoueurs.add(newJoueur);
                    if (this.previouslyAbsentJoueurs.contains(newJoueur)) {
                        nbPreviousPlayersInGroupLeft.decrementAndGet();
                        this.previouslyAbsentJoueurs.remove(newJoueur);
                    }
                }
        );

        return groupeJoueurs;
    }

    private JoueurShort getPlayerWhoLessPlayedAgainstOthersPlayers(List<JoueurShort> joueursAlreadyInGroup, List<JoueurShort> joueursAbleToBePutInGroup, Integer currentRonde) {
        Map<JoueurShort, Integer> totalEncounterForJoueurAgainstJoueursAlreadyInGroup = new HashMap<>();
        if (joueursAlreadyInGroup.isEmpty()) {
            return this.joueursPlaying.stream().findFirst().orElseThrow();
        }

        Collections.shuffle(joueursAbleToBePutInGroup);
        joueursAbleToBePutInGroup.forEach(
                joueurNotAlreadyUsed -> joueursAlreadyInGroup.forEach(
                        joueurAlreadyInGroup -> totalEncounterForJoueurAgainstJoueursAlreadyInGroup.merge(
                                joueurNotAlreadyUsed, this.previousPlaysMatrix.get(joueurAlreadyInGroup).get(joueurNotAlreadyUsed), Integer::sum)));

        var minimumNbOfPlaysAgainstJoueursAlreadyInGroupFound = totalEncounterForJoueurAgainstJoueursAlreadyInGroup.entrySet().stream().min(Map.Entry.comparingByValue()).orElseThrow().getValue();
        var playersWithMinimumNbOfPlays = totalEncounterForJoueurAgainstJoueursAlreadyInGroup.entrySet().stream().filter(player -> minimumNbOfPlaysAgainstJoueursAlreadyInGroupFound.equals(player.getValue())).map(Map.Entry::getKey).collect(toCollection(ArrayList::new));
        var minPlaysAgainstAnyPlayerAlreadyInGroup = new AtomicInteger(0);
        while (playersWithMinimumNbOfPlays.size() != 1 && minPlaysAgainstAnyPlayerAlreadyInGroup.get() < currentRonde) {
            var nbOfMinPlaysAgainstAnyPlayerAlreadyInGroup = new AtomicInteger(0);
            List<JoueurShort> currentMinPlayers = new ArrayList<>();
            List<JoueurShort> playersToRemove = new ArrayList<>();
            playersWithMinimumNbOfPlays.forEach(joueurWithMinPlays -> {
                var nbMin = new AtomicInteger(0);
                joueursAlreadyInGroup.forEach(
                        joueurAlreadyInGroup -> {
                            if (minPlaysAgainstAnyPlayerAlreadyInGroup.get() == this.previousPlaysMatrix.get(joueurAlreadyInGroup).get(joueurWithMinPlays)) {
                                nbMin.addAndGet(1);
                            }
                        });
                if (nbMin.get() < nbOfMinPlaysAgainstAnyPlayerAlreadyInGroup.get()) {
                    playersToRemove.add(joueurWithMinPlays);
                } else if (nbMin.get() == nbOfMinPlaysAgainstAnyPlayerAlreadyInGroup.get()) {
                    currentMinPlayers.add(joueurWithMinPlays);
                } else if (nbMin.get() > nbOfMinPlaysAgainstAnyPlayerAlreadyInGroup.get()) {
                    playersToRemove.addAll(currentMinPlayers);
                    currentMinPlayers.clear();
                    currentMinPlayers.add(joueurWithMinPlays);
                    nbOfMinPlaysAgainstAnyPlayerAlreadyInGroup.set(nbMin.get());
                }
            });
            playersWithMinimumNbOfPlays.removeAll(playersToRemove);
            minPlaysAgainstAnyPlayerAlreadyInGroup.addAndGet(1);
        }
        Collections.shuffle(playersWithMinimumNbOfPlays);
        return playersWithMinimumNbOfPlays.getFirst();
    }

    private List<JoueurShort> generatePreviousAbsentJoueursList(Integer currentRonde) {
        List<JoueurShort> previousAbsentPlayers = new ArrayList<>();
        if (currentRonde > 1) {
            this.joueursPlaying.forEach(joueur -> {
                var nbParties = new AtomicInteger(0);
                this.previousParties.forEach(previousPartie -> {
                    if (previousPartie.joueurs().stream().map(JoueurShort::new).anyMatch(joueurShort -> joueurShort.equals(joueur))) {
                        nbParties.addAndGet(1);
                    }
                });
                if (nbParties.get() < (currentRonde - 1)) {
                    previousAbsentPlayers.add(joueur);
                }
            });
        }
        return previousAbsentPlayers;
    }
}
