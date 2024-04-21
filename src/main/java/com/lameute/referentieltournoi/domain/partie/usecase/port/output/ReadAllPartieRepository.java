package com.lameute.referentieltournoi.domain.partie.usecase.port.output;

import com.lameute.referentieltournoi.domain.commons.entity.Partie;

import java.util.List;

public interface ReadAllPartieRepository {

    List<Partie> readAllPartie();
}
