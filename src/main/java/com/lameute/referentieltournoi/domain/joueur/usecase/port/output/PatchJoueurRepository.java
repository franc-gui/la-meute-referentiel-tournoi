package com.lameute.referentieltournoi.domain.joueur.usecase.port.output;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;

public interface PatchJoueurRepository {

    Joueur patchJoueur(Joueur joueur, Integer id);
}
