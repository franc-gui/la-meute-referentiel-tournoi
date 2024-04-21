package com.lameute.referentieltournoi.application.rest.groupeoptimise.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record GroupeOptimiseDto(Integer numeroGroupe, Integer nombreJoueurs, List<JoueurShortDto> joueurs) {
}
