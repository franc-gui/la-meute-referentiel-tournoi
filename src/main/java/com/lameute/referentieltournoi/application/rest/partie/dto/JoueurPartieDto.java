package com.lameute.referentieltournoi.application.rest.partie.dto;

import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;

@Builder
public record JoueurPartieDto(@NonNull String prenom, @NonNull String pseudo, @NonNull String nomDeFamille, BigDecimal score) {
}
