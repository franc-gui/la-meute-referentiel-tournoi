package com.lameute.referentieltournoi.domain.partie.usecase.port.output;

import com.lameute.referentieltournoi.domain.commons.entity.Partie;

public interface PatchPartieRepository {

    Partie patchPartie(Partie partie, Integer id);
}
