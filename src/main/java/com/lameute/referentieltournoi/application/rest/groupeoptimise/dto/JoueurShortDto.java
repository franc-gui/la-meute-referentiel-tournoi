package com.lameute.referentieltournoi.application.rest.groupeoptimise.dto;

import lombok.Builder;

@Builder
public record JoueurShortDto(String prenom, String pseudo, String nomDeFamille) {
}
