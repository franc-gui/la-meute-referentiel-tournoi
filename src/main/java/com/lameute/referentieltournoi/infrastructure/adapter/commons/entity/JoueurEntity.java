package com.lameute.referentieltournoi.infrastructure.adapter.commons.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = JoueurEntity.COLLECTION_NAME,
        uniqueConstraints = { @UniqueConstraint(name = "UniqueNom", columnNames = { "prenom", "nomDeFamille" }) })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoueurEntity {
    public static final String COLLECTION_NAME = "ref_joueur";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "joueur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PartieJoueurScoreEntity> scoresJoueursParties;

    private String prenom;

    private String nomDeFamille;

    private Boolean isPlaying;

    @CreationTimestamp
    private Instant createDate;

    @UpdateTimestamp
    private Instant updateDate;
}
