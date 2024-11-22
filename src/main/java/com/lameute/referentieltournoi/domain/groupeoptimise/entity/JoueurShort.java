package com.lameute.referentieltournoi.domain.groupeoptimise.entity;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;
import com.lameute.referentieltournoi.domain.commons.entity.JoueurPartie;
import lombok.Builder;
import lombok.With;

@Builder
@With
public record JoueurShort(String prenom, String pseudo, String nomDeFamille) {
    public JoueurShort(Joueur joueur) {
        this(joueur.prenom(), joueur.pseudo(), joueur.nomDeFamille());
    }

    public JoueurShort(JoueurPartie joueur) {
        this(joueur.prenom(), joueur.pseudo(), joueur.nomDeFamille());
    }
}
