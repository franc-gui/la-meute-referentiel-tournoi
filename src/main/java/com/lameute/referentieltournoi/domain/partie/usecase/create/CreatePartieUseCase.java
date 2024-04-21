package com.lameute.referentieltournoi.domain.partie.usecase.create;

import com.lameute.referentieltournoi.domain.commons.entity.Partie;
import com.lameute.referentieltournoi.domain.partie.usecase.port.input.ICreatePartie;
import com.lameute.referentieltournoi.domain.partie.usecase.port.output.CreatePartieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class CreatePartieUseCase implements ICreatePartie {

    private final CreatePartieRepository createPartieRepository;

    @Override
    public Partie process(Partie partie) {
        return createPartieRepository.savePartie(partie);
    }
}
