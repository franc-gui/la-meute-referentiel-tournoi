package com.lameute.referentieltournoi.infrastructure.adapter.joueur;

import com.lameute.referentieltournoi.domain.commons.entity.Joueur;
import com.lameute.referentieltournoi.domain.joueur.usecase.port.output.PatchJoueurRepository;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.entity.JoueurEntity;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.mapper.JoueurMapper;
import com.lameute.referentieltournoi.infrastructure.adapter.commons.repository.JoueurRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PatchJoueurAdapter implements PatchJoueurRepository {

    private final JoueurRepository joueurRepository;
    private final JoueurMapper joueurMapper = Mappers.getMapper(JoueurMapper.class);

    @Override
    public Joueur patchJoueur(Joueur joueur, Integer id) {
        JoueurEntity myJoueur = joueurRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Pas de joueur pour cet id : %s", id)));
        joueurMapper.updateJoueurFromDto(joueur, myJoueur);
        return joueurMapper.map(joueurRepository.save(myJoueur));
    }
}
