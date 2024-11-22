package com.lameute.referentieltournoi.infrastructure.adapter.commons.mapper;

import com.lameute.referentieltournoi.domain.commons.entity.JoueurPartie;
import com.lameute.referentieltournoi.domain.commons.entity.Partie;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.entity.PartieEntity;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.entity.PartieJoueurScoreEntity;
import org.mapstruct.*;

import java.util.Set;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface PartieMapper {

    @Mapping(target = "joueurs", source = "scoresJoueursParties")
    Partie mapPartieEntityToDomain(PartieEntity entity);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePartieFromDto(Partie dto, @MappingTarget PartieEntity entity);

    PartieEntity mapPartieDomainToEntity(Partie reference);

    Set<JoueurPartie> mapPartieJoueurScoreSetEntityToDomain(Set<PartieJoueurScoreEntity> entities);

    @Mapping(target = "prenom", source = "joueur.prenom")
    @Mapping(target = "nomDeFamille", source = "joueur.nomDeFamille")
    @Mapping(target = "pseudo", source = "joueur.pseudo")
    JoueurPartie mapPartieJoueurScoreEntityToDomain(PartieJoueurScoreEntity entity);
}
