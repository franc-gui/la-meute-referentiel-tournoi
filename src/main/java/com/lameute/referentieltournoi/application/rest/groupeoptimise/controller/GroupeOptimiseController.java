package com.lameute.referentieltournoi.application.rest.groupeoptimise.controller;

import com.lameute.referentieltournoi.application.rest.groupeoptimise.dto.GroupeOptimiseDto;
import com.lameute.referentieltournoi.application.rest.groupeoptimise.mapper.GroupeOptimiseDtoMapper;
import com.lameute.referentieltournoi.domain.groupeoptimise.usecase.port.input.IFetchAllGroupeOptimise;
import com.lameute.referentieltournoi.domain.joueur.usecase.port.input.IFetchAllJoueur;
import com.lameute.referentieltournoi.domain.joueur.usecase.port.input.IPatchJoueur;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.lameute.referentieltournoi.application.commons.BaseControllerUtils.GROUPES_OPTMISES_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(GROUPES_OPTMISES_PATH)
@Slf4j
public class GroupeOptimiseController {

    private final IFetchAllGroupeOptimise iFetchAllGroupeOptimise;
    private final GroupeOptimiseDtoMapper dtoMapper = Mappers.getMapper(GroupeOptimiseDtoMapper.class);

    @GetMapping
    public List<GroupeOptimiseDto> getGroupesOptimises(@RequestParam(value = "ronde") Integer ronde, @RequestParam(value = "nombreGroupe") Integer nombreGroupe) {
        return iFetchAllGroupeOptimise.process(nombreGroupe, ronde).stream().map(dtoMapper::map).toList();
    }
}
