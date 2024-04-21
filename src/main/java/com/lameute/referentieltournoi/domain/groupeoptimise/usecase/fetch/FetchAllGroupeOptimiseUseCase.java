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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public List<GroupeOptimise> process(Integer nombreGroupe, Integer currentRonde) {
        List<JoueurShort> joueursPlaying = readAllJoueurPlayingRepository.readAllJoueurPlaying().stream().map(JoueurShort::new).collect(toCollection(ArrayList::new));
        List<Partie> previousParties = readAllPartieOfPreviousRondeRepository.readAllPartieOfPreviousRonde(currentRonde);
        Map<JoueurShort, Map<JoueurShort, Integer>> preivousPlaysMatrix = generatePreviousPlaysMatrix(joueursPlaying, previousParties);
        var nombreJoueursRestants = new AtomicInteger(joueursPlaying.size());

        List<GroupeOptimise> groupeOptimises = IntStream.range(1, nombreGroupe + 1).boxed().map(numeroGroupe -> {
            var nbPlayersInGroup = numeroGroupe == nombreGroupe ? Integer.valueOf(nombreJoueursRestants.get()) : nombreJoueursRestants.get() / (nombreGroupe - (numeroGroupe - 1));
            nombreJoueursRestants.set(nombreJoueursRestants.get() - nbPlayersInGroup);
            var currentRoundGroupe = generateCurrentRoundGroupe(joueursPlaying, nbPlayersInGroup, preivousPlaysMatrix, currentRonde);
            joueursPlaying.removeAll(currentRoundGroupe);
            return GroupeOptimise.builder()
                    .numeroGroupe(numeroGroupe)
                    .nombreJoueurs(nbPlayersInGroup)
                    .joueurs(currentRoundGroupe)
                    .build();
        }).toList();

        return groupeOptimises;
    }

    private Map<JoueurShort, Map<JoueurShort, Integer>> generatePreviousPlaysMatrix(List<JoueurShort> joueursPlaying, List<Partie> previousParties) {
        Map<JoueurShort, Map<JoueurShort, Integer>> numberOfPlaysTogetherMatrix = new HashMap<>();
        joueursPlaying.forEach(joueur -> {
            Map<JoueurShort, Integer> opponents = new HashMap<>();
            joueursPlaying.stream().filter(Predicate.not(joueur::equals)).forEach(autreJoueur -> opponents.put(autreJoueur, 0));
            previousParties.forEach(previousPartie -> {
                List<JoueurShort> joueursPartie = previousPartie.joueurs().stream().map(JoueurShort::new).filter(joueursPlaying::contains).toList();
                if (joueursPartie.stream().anyMatch(joueur::equals)) {
                    joueursPartie.stream().filter(Predicate.not(joueur::equals)).forEach(joueurPartie -> opponents.merge(joueurPartie, 1, Integer::sum));
                }
            });
            numberOfPlaysTogetherMatrix.put(joueur, opponents);
        });
        return numberOfPlaysTogetherMatrix;
    }

    private List<JoueurShort> generateCurrentRoundGroupe(List<JoueurShort> joueursPlaying, Integer nbPlayersInGroup, Map<JoueurShort, Map<JoueurShort, Integer>> previousPlaysMatrix, Integer currentRonde) {
        List<JoueurShort> groupeJoueurs = new ArrayList<>(nbPlayersInGroup);
        Collections.shuffle(joueursPlaying);

        IntStream.range(0, nbPlayersInGroup).forEach(
                playerNb -> groupeJoueurs.add(
                        getPlayerWhoLessPlayedAgainstOthersPlayers(groupeJoueurs, joueursPlaying, previousPlaysMatrix, currentRonde)));

        return groupeJoueurs;
    }

    private JoueurShort getPlayerWhoLessPlayedAgainstOthersPlayers(List<JoueurShort> joueursAlreadyInGroup, List<JoueurShort> joueursPlaying, Map<JoueurShort, Map<JoueurShort, Integer>> previousPlaysMatrix, Integer currentRonde) {
        Map<JoueurShort, Integer> totalEncounterForJoueurAgainstJoueursAlreadyInGroup = new HashMap<>();
        if (joueursAlreadyInGroup.isEmpty()) {
            return joueursPlaying.stream().findFirst().orElseThrow();
        }
        joueursPlaying.removeAll(joueursAlreadyInGroup);
        Collections.shuffle(joueursPlaying);
        joueursPlaying.forEach(
                joueurNotAlreadyUsed -> joueursAlreadyInGroup.forEach(
                        joueurAlreadyInGroup -> totalEncounterForJoueurAgainstJoueursAlreadyInGroup.merge(
                                joueurNotAlreadyUsed, previousPlaysMatrix.get(joueurAlreadyInGroup).get(joueurNotAlreadyUsed), Integer::sum)));
        var minimumNbOfPlaysAgainstJoueursAlreadyInGroupFound = totalEncounterForJoueurAgainstJoueursAlreadyInGroup.entrySet().stream().sorted(Map.Entry.comparingByValue()).findFirst().orElseThrow().getValue();
        var playersWithMinimumNbOfPlays = totalEncounterForJoueurAgainstJoueursAlreadyInGroup.entrySet().stream().filter(player -> minimumNbOfPlaysAgainstJoueursAlreadyInGroupFound.equals(player.getValue())).map(player -> player.getKey()).collect(toCollection(ArrayList::new));
        var minPlaysAgainstAnyPlayerAlreadyInGroup = new AtomicInteger(0);
        while (playersWithMinimumNbOfPlays.size() != 1 && minPlaysAgainstAnyPlayerAlreadyInGroup.get() < currentRonde) {
            var nbOfMinPlaysAgainstAnyPlayerAlreadyInGroup = new AtomicInteger(0);
            List<JoueurShort> currentMinPlayers = new ArrayList<>();
            List<JoueurShort> playersToRemove = new ArrayList<>();
            playersWithMinimumNbOfPlays.forEach(joueurWithMinPlays -> {
                var nbMin = new AtomicInteger(0);
                joueursAlreadyInGroup.forEach(
                        joueurAlreadyInGroup -> {
                            if (minPlaysAgainstAnyPlayerAlreadyInGroup.get() == previousPlaysMatrix.get(joueurAlreadyInGroup).get(joueurWithMinPlays)) {
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
        return playersWithMinimumNbOfPlays.get(0);
    }
}
