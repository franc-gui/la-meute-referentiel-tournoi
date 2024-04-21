package com.lameute.referentieltournoi.domain.partie.usecase.patch;

import com.lameute.referentieltournoi.domain.commons.entity.Partie;
import com.lameute.referentieltournoi.domain.partie.usecase.port.input.IPatchPartie;
import com.lameute.referentieltournoi.domain.partie.usecase.port.output.PatchPartieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class PatchPartieUseCase implements IPatchPartie {

    private final PatchPartieRepository patchPartieRepository;

    @Override
    public Partie process(Partie partie, Integer id) {
        return patchPartieRepository.patchPartie(partie, id);
    }
}
