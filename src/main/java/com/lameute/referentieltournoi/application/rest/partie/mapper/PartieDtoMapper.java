package com.lameute.referentieltournoi.application.rest.partie.mapper;

import com.lameute.referentieltournoi.application.rest.joueur.dto.JoueurDto;
import com.lameute.referentieltournoi.application.rest.partie.dto.JoueurPartieDto;
import com.lameute.referentieltournoi.application.rest.partie.dto.PartieDto;
import com.lameute.referentieltournoi.domain.commons.entity.Joueur;
import com.lameute.referentieltournoi.domain.commons.entity.JoueurPartie;
import com.lameute.referentieltournoi.domain.commons.entity.Partie;
import org.mapstruct.Mapper;

import java.util.Set;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface PartieDtoMapper {

    PartieDto mapPartieDomainToDto(Partie value);

    Partie mapPartieDtoToDomain(PartieDto dto);

    JoueurDto mapJoueurDomainToDto(Joueur value);

    Joueur mapJoueurDtoToDomain(JoueurDto dto);

    Set<JoueurPartieDto> mapJoueurPartieSetDomainToDto(Set<JoueurPartie> value);

    JoueurPartieDto mapJoueurPartieDomainToDto(JoueurPartie value);

    Set<JoueurPartie> mapJoueurPartieSetDtoToDomain(Set<JoueurPartieDto> dto);

    JoueurPartie mapJoueurPartieDtoToDomain(JoueurPartieDto dto);
}
