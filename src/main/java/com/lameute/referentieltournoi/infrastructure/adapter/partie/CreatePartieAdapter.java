package com.lameute.referentieltournoi.infrastructure.adapter.partie;

import com.lameute.referentieltournoi.domain.commons.entity.JoueurPartie;
import com.lameute.referentieltournoi.domain.commons.entity.Partie;
import com.lameute.referentieltournoi.domain.partie.usecase.port.output.CreatePartieRepository;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.entity.JoueurEntity;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.entity.PartieEntity;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.entity.PartieJoueurScoreEntity;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.entity.PartieJoueurScoreKeyEntity;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.mapper.PartieMapper;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.repository.JoueurRepository;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.repository.PartieJoueurScoreRepository;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.repository.PartieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CreatePartieAdapter implements CreatePartieRepository {

    private final PartieRepository partieRepository;
    private final JoueurRepository joueurRepository;
    private final PartieJoueurScoreRepository partieJoueurScoreRepository;
    private final PartieMapper partieMapper = Mappers.getMapper(PartieMapper.class);

    @Override
    public Partie savePartie(Partie partie) {
        List<JoueurEntity> joueurs = joueurRepository.findAll();
        PartieEntity partieSaved = partieRepository.save(partieMapper.mapPartieDomainToEntity(partie));
        Set<PartieJoueurScoreEntity> partieJoueurScoreEntities = partie.joueurs().stream()
                .map(joueurPartie -> {
                    JoueurEntity joueur = findJoueur(joueurPartie, joueurs);
                    return PartieJoueurScoreEntity.builder().id(PartieJoueurScoreKeyEntity.builder()
                                    .partieId(partieSaved.getId()).joueurId(joueur.getId())
                                    .build())
                            .partie(partieSaved)
                            .joueur(joueur)
                            .score(joueurPartie.score()).build();
                }).collect(Collectors.toSet());
        partieJoueurScoreRepository.saveAll(partieJoueurScoreEntities);
        partieSaved.setScoresJoueursParties(partieJoueurScoreEntities);
        return partieMapper.mapPartieEntityToDomain(partieRepository.save(partieSaved));
    }

    private JoueurEntity findJoueur(JoueurPartie joueurPartie, List<JoueurEntity> joueursExisting) {
        return joueursExisting.stream()
                .filter(joueur -> joueur.getPrenom().equals(joueurPartie.prenom()) && joueur.getNomDeFamille().equals(joueurPartie.nomDeFamille()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Pas de joueur pour ce nom : %s et prénom :  %s", joueurPartie.prenom(), joueurPartie.nomDeFamille())));
    }
}
