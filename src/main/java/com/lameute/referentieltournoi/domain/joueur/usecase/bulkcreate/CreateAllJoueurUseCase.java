package com.lameute.referentieltournoi.domain.joueur.usecase.bulkcreate;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;
import com.lameute.referentieltournoi.domain.joueur.usecase.port.input.ICreateAllJoueur;
import com.lameute.referentieltournoi.domain.joueur.usecase.port.output.CreateJoueurListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CreateAllJoueurUseCase implements ICreateAllJoueur {

    private final CreateJoueurListRepository createJoueurListRepository;

    @Override
    public List<Joueur> process(List<Joueur> joueurs) {
        return createJoueurListRepository.saveJoueurList(joueurs);
    }
}
