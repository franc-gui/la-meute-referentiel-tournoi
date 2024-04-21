package com.lameute.referentieltournoi.infrastructure.adapter.commons.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = PartieEntity.COLLECTION_NAME, indexes = @Index(columnList = "ronde"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartieEntity {
    public static final String COLLECTION_NAME = "ref_partie";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer ronde;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PartieJoueurScoreEntity> scoresJoueursParties;

    @CreationTimestamp
    private Instant createDate;

    @UpdateTimestamp
    private Instant updateDate;
}
