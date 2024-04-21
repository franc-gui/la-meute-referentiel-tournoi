package com.lameute.referentieltournoi.domain.joueur.usecase.port.input;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;

import java.util.List;

public interface IFetchAllJoueur {

    List<Joueur> process();
}
