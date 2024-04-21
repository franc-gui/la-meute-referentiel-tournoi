package com.lameute.referentieltournoi.domain.joueur.usecase.port.input;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;

import java.util.List;

public interface ICreateAllJoueur {

    List<Joueur> process(List<Joueur> joueurs);
}
