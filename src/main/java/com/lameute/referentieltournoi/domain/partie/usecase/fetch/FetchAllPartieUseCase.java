package com.lameute.referentieltournoi.domain.partie.usecase.fetch;

import com.lameute.referentieltournoi.domain.commons.entity.Partie;
import com.lameute.referentieltournoi.domain.partie.usecase.port.input.IFetchAllPartie;
import com.lameute.referentieltournoi.domain.partie.usecase.port.output.ReadAllPartieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FetchAllPartieUseCase implements IFetchAllPartie {

    private final ReadAllPartieRepository readAllPartieRepository;

    @Override
    public List<Partie> process() {
        return readAllPartieRepository.readAllPartie();
    }
}
