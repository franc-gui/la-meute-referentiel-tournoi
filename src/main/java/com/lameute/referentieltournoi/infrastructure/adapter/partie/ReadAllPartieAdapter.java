package com.lameute.referentieltournoi.infrastructure.adapter.partie;

import com.lameute.referentieltournoi.domain.commons.entity.Partie;
import com.lameute.referentieltournoi.domain.partie.usecase.port.output.ReadAllPartieRepository;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.mapper.PartieMapper;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.repository.PartieRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReadAllPartieAdapter implements ReadAllPartieRepository {

    private final PartieRepository partieRepository;
    private final PartieMapper partieMapper = Mappers.getMapper(PartieMapper.class);

    @Override
    public List<Partie> readAllPartie() {
        return partieRepository.findAll().stream().map(partieMapper::mapPartieEntityToDomain).toList();
    }
}
