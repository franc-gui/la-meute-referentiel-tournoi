package com.lameute.referentieltournoi.domain.commons.entity;

import lombok.Builder;
import lombok.NonNull;
import lombok.With;

import java.math.BigDecimal;

@Builder
@With
public record JoueurPartie(@NonNull String prenom, @NonNull String nomDeFamille, BigDecimal score) {
}
