package com.lameute.referentieltournoi.domain.joueur.usecase.port.input;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;

public interface IPatchJoueur {

    Joueur process(Joueur joueur, Integer id);
}
