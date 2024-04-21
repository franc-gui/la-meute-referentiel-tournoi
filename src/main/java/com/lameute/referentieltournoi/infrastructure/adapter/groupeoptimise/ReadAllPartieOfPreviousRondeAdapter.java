package com.lameute.referentieltournoi.infrastructure.adapter.groupeoptimise;

import com.lameute.referentieltournoi.domain.commons.entity.Partie;
import com.lameute.referentieltournoi.domain.groupeoptimise.usecase.port.output.ReadAllPartieOfPreviousRondeRepository;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.mapper.PartieMapper;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.repository.PartieRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReadAllPartieOfPreviousRondeAdapter implements ReadAllPartieOfPreviousRondeRepository {

    private final PartieRepository partieRepository;
    private final PartieMapper partieMapper = Mappers.getMapper(PartieMapper.class);

    @Override
    public List<Partie> readAllPartieOfPreviousRonde(Integer currentRonde) {
        return partieRepository.findAllByRondeIsLessThan(currentRonde).stream().map(partieMapper::mapPartieEntityToDomain).toList();
    }
}
