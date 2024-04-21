package com.lameute.referentieltournoi.application.rest.joueur.controller;

import com.lameute.referentieltournoi.application.rest.joueur.dto.JoueurDto;
import com.lameute.referentieltournoi.application.rest.joueur.mapper.JoueurDtoMapper;
import com.lameute.referentieltournoi.domain.joueur.usecase.port.input.ICreateAllJoueur;
import com.lameute.referentieltournoi.domain.joueur.usecase.port.input.IFetchAllJoueur;
import com.lameute.referentieltournoi.domain.joueur.usecase.port.input.IPatchJoueur;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.lameute.referentieltournoi.application.commons.BaseControllerUtils.BULK_JOUEURS_PATH;
import static com.lameute.referentieltournoi.application.commons.BaseControllerUtils.JOUEURS_PATH;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JoueurController {

    private final IFetchAllJoueur iFetchAllJoueur;
    private final IPatchJoueur iPatchJoueur;
    private final ICreateAllJoueur iCreateAllJoueur;
    private final JoueurDtoMapper dtoMapper = Mappers.getMapper(JoueurDtoMapper.class);

    @GetMapping(JOUEURS_PATH)
    public List<JoueurDto> getAll() {
        return iFetchAllJoueur.process().stream().map(dtoMapper::map).toList();
    }

    @PatchMapping(JOUEURS_PATH + "/{id}")
    public JoueurDto patch(@Valid @RequestBody JoueurDto joueurDto, @PathVariable("id") Integer id) {
        return dtoMapper.map(iPatchJoueur.process(dtoMapper.map(joueurDto), id));
    }

    @PostMapping(BULK_JOUEURS_PATH)
    public List<JoueurDto> saveAll(@Valid @RequestBody List<JoueurDto> joueurs) {
        return iCreateAllJoueur.process(joueurs.stream().map(dtoMapper::map).toList()).stream().map(dtoMapper::map).toList();
    }
}
