package com.lameute.referentieltournoi.infrastructure.adapter.commons.repository;

import com.lameute.referentieltournoi.infrastructure.adapter.commons.entity.JoueurEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoueurRepository extends JpaRepository<JoueurEntity, Integer> {
    List<JoueurEntity> findAllByIsPlayingTrue();
}
