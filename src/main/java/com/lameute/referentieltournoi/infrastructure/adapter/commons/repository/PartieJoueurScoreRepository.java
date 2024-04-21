package com.lameute.referentieltournoi.infrastructure.adapter.commons.repository;

import com.lameute.referentieltournoi.infrastructure.adapter.commons.entity.PartieJoueurScoreEntity;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.entity.PartieJoueurScoreKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartieJoueurScoreRepository extends JpaRepository<PartieJoueurScoreEntity, PartieJoueurScoreKeyEntity> {
}
