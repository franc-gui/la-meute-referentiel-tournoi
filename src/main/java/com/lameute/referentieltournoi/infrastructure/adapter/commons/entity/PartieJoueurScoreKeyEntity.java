package com.lameute.referentieltournoi.infrastructure.adapter.commons.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartieJoueurScoreKeyEntity implements Serializable {
    @Column(name = "partie_id")
    private Integer partieId;

    @Column(name = "joueur_id")
    private Integer joueurId;
}
