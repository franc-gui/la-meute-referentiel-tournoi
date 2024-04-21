package com.lameute.referentieltournoi.infrastructure.adapter.commons.mapper;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.entity.JoueurEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface JoueurMapper {

    Joueur map(JoueurEntity entity);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateJoueurFromDto(Joueur dto, @MappingTarget JoueurEntity entity);

    JoueurEntity map(Joueur reference);
}
