package com.lameute.referentieltournoi.domain.groupeoptimise.entity;

import lombok.Builder;
import lombok.With;

import java.util.List;

@Builder
@With
public record GroupeOptimise(Integer numeroGroupe, Integer nombreJoueurs, List<JoueurShort> joueurs) {
}
