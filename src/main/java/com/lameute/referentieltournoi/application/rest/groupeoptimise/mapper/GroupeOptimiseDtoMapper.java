package com.lameute.referentieltournoi.application.rest.groupeoptimise.mapper;

import com.lameute.referentieltournoi.application.rest.groupeoptimise.dto.GroupeOptimiseDto;
import com.lameute.referentieltournoi.domain.groupeoptimise.entity.GroupeOptimise;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface GroupeOptimiseDtoMapper {

    GroupeOptimiseDto map(GroupeOptimise value);

    GroupeOptimise map(GroupeOptimiseDto dto);
}
