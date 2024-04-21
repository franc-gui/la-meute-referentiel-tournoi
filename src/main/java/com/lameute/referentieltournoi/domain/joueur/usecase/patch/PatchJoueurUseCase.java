package com.lameute.referentieltournoi.domain.joueur.usecase.patch;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;
import com.lameute.referentieltournoi.domain.joueur.usecase.port.input.IPatchJoueur;
import com.lameute.referentieltournoi.domain.joueur.usecase.port.output.PatchJoueurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class PatchJoueurUseCase implements IPatchJoueur {

    private final PatchJoueurRepository patchJoueurRepository;

    @Override
    public Joueur process(Joueur joueur, Integer id) {
        return patchJoueurRepository.patchJoueur(joueur, id);
    }
}
