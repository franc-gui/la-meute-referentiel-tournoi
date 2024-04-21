package com.lameute.referentieltournoi.domain.commons.entity;

import lombok.Builder;
import lombok.With;

@Builder
@With
public record Joueur(Integer id, String prenom, String nomDeFamille, Boolean isPlaying) {
}
