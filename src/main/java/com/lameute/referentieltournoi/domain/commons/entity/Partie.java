package com.lameute.referentieltournoi.domain.commons.entity;

import lombok.Builder;
import lombok.With;

import java.util.Set;

@Builder
@With
public record Partie(Integer id, Integer ronde, Set<JoueurPartie> joueurs) {
}
