package com.lameute.referentieltournoi.infrastructure.adapter.commons.repository;

import com.lameute.referentieltournoi.infrastructure.adapter.commons.entity.PartieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartieRepository extends JpaRepository<PartieEntity, Integer> {
    List<PartieEntity> findAllByRondeIsLessThan(Integer ronde);
}
