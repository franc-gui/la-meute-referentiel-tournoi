package com.lameute.referentieltournoi.application.rest.joueur.mapper;

import com.lameute.referentieltournoi.application.rest.joueur.dto.JoueurDto;
import com.lameute.referentieltournoi.domain.commons.entity.Joueur;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface JoueurDtoMapper {

    JoueurDto map(Joueur value);

    Joueur map(JoueurDto dto);
}
