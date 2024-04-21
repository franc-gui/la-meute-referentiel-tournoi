package com.lameute.referentieltournoi.application.rest.partie.controller;

import com.lameute.referentieltournoi.application.rest.partie.dto.PartieDto;
import com.lameute.referentieltournoi.application.rest.partie.mapper.PartieDtoMapper;
import com.lameute.referentieltournoi.domain.partie.usecase.port.input.ICreatePartie;
import com.lameute.referentieltournoi.domain.partie.usecase.port.input.IFetchAllPartie;
import com.lameute.referentieltournoi.domain.partie.usecase.port.input.IPatchPartie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.lameute.referentieltournoi.application.commons.BaseControllerUtils.PARTIES_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(PARTIES_PATH)
@Slf4j
public class PartieController {

    private final IFetchAllPartie iFetchAllPartie;
    private final IPatchPartie iPatchPartie;
    private final ICreatePartie iCreatePartie;
    private final PartieDtoMapper dtoMapper = Mappers.getMapper(PartieDtoMapper.class);

    @GetMapping
    public List<PartieDto> getAll() {
        return iFetchAllPartie.process().stream().map(dtoMapper::mapPartieDomainToDto).toList();
    }

    @PatchMapping("/{id}")
    public PartieDto patch(@Valid @RequestBody PartieDto partieDto, @PathVariable("id") Integer id) {
        return dtoMapper.mapPartieDomainToDto(iPatchPartie.process(dtoMapper.mapPartieDtoToDomain(partieDto), id));
    }

    @PostMapping
    public PartieDto save(@Valid @RequestBody PartieDto partie) {
        return dtoMapper.mapPartieDomainToDto(iCreatePartie.process(dtoMapper.mapPartieDtoToDomain(partie)));
    }
}
