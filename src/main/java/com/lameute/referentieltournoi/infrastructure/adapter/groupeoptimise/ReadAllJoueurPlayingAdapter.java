package com.lameute.referentieltournoi.infrastructure.adapter.groupeoptimise;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;
import com.lameute.referentieltournoi.domain.groupeoptimise.usecase.port.output.ReadAllJoueurPlayingRepository;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.mapper.JoueurMapper;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.repository.JoueurRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReadAllJoueurPlayingAdapter implements ReadAllJoueurPlayingRepository {

    private final JoueurRepository joueurRepository;
    private final JoueurMapper joueurMapper = Mappers.getMapper(JoueurMapper.class);

    @Override
    public List<Joueur> readAllJoueurPlaying() {
        return joueurRepository.findAllByIsPlayingTrue().stream().map(joueurMapper::map).toList();
    }
}
