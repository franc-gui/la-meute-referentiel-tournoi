package com.lameute.referentieltournoi.infrastructure.adapter.commons.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = JoueurEntity.COLLECTION_NAME,
        uniqueConstraints = {@UniqueConstraint(name = "UniqueNom", columnNames = {"prenom", "pseudo", "nomDeFamille"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoueurEntity {
    public static final String COLLECTION_NAME = "ref_joueur";

    @Id
    private Integer id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "joueur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PartieJoueurScoreEntity> scoresJoueursParties;

    private String prenom;

    private String pseudo;

    private String nomDeFamille;

    private Boolean isPlaying;

    @CreationTimestamp
    private Instant createDate;

    @UpdateTimestamp
    private Instant updateDate;
}
