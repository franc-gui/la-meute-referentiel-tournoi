package com.lameute.referentieltournoi.domain.joueur.usecase.port.output;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;

import java.util.List;

public interface CreateJoueurListRepository {

    List<Joueur> saveJoueurList(List<Joueur> joueurs);
}
