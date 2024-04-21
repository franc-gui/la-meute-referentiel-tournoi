package com.lameute.referentieltournoi.domain.groupeoptimise.usecase.port.output;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;

import java.util.List;

public interface ReadAllJoueurPlayingRepository {

    List<Joueur> readAllJoueurPlaying();
}
