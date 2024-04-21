package com.lameute.referentieltournoi.infrastructure.adapter.joueur;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;
import com.lameute.referentieltournoi.domain.joueur.usecase.port.output.CreateJoueurListRepository;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.mapper.JoueurMapper;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.repository.JoueurRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CreateJoueurListAdapter implements CreateJoueurListRepository {

    private final JoueurRepository joueurRepository;
    private final JoueurMapper joueurMapper = Mappers.getMapper(JoueurMapper.class);

    @Override
    public List<Joueur> saveJoueurList(List<Joueur> joueurs) {
        return joueurRepository.saveAll(joueurs.stream().map(joueurMapper::map).toList())
                .stream().map(joueurMapper::map).toList();
    }
}
