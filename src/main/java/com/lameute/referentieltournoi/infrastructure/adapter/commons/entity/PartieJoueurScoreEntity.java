package com.lameute.referentieltournoi.infrastructure.adapter.commons.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = PartieJoueurScoreEntity.COLLECTION_NAME)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"partie", "joueur"})
@ToString(exclude = {"partie", "joueur"})
public class PartieJoueurScoreEntity {
    public static final String COLLECTION_NAME = "partie_joueur_score";

    @EmbeddedId
    private PartieJoueurScoreKeyEntity id;

    @ManyToOne
    @MapsId("partieId")
    @JoinColumn(name = "partie_id")
    private PartieEntity partie;

    @ManyToOne
    @MapsId("joueurId")
    @JoinColumn(name = "joueur_id")
    private JoueurEntity joueur;

    private BigDecimal score;

    @CreationTimestamp
    private Instant createDate;

    @UpdateTimestamp
    private Instant updateDate;
}
