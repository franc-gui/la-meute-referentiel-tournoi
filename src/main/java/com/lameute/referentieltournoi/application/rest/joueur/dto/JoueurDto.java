package com.lameute.referentieltournoi.application.rest.joueur.dto;

import lombok.Builder;

import java.util.Objects;

@Builder
public record JoueurDto(Integer id, String prenom, String pseudo, String nomDeFamille, Boolean isPlaying) {

    public JoueurDto {
        isPlaying = Objects.requireNonNullElse(isPlaying, Boolean.TRUE);
    }
}
