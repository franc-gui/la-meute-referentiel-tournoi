package com.lameute.referentieltournoi.domain.joueur.usecase.port.output;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;

import java.util.List;

public interface ReadAllJoueurRepository {

    List<Joueur> readAllJoueur();
}
