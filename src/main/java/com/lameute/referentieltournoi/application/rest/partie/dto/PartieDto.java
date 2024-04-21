package com.lameute.referentieltournoi.application.rest.partie.dto;

import lombok.Builder;
import lombok.With;

import java.util.Set;

@Builder
@With
public record PartieDto(Integer id, Integer ronde, Set<JoueurPartieDto> joueurs) {
}
