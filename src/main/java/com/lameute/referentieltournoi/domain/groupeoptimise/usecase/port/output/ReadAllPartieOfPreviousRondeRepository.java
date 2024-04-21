package com.lameute.referentieltournoi.domain.groupeoptimise.usecase.port.output;

import com.lameute.referentieltournoi.domain.commons.entity.Partie;

import java.util.List;

public interface ReadAllPartieOfPreviousRondeRepository {

    List<Partie> readAllPartieOfPreviousRonde(Integer currentRonde);
}
