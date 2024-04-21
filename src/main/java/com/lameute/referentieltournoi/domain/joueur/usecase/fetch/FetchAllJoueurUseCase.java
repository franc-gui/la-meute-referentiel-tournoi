package com.lameute.referentieltournoi.domain.joueur.usecase.fetch;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;
import com.lameute.referentieltournoi.domain.joueur.usecase.port.input.IFetchAllJoueur;
import com.lameute.referentieltournoi.domain.joueur.usecase.port.output.ReadAllJoueurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FetchAllJoueurUseCase implements IFetchAllJoueur {

    private final ReadAllJoueurRepository readAllJoueurRepository;

    @Override
    public List<Joueur> process() {
        return readAllJoueurRepository.readAllJoueur();
    }
}
