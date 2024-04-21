package com.lameute.referentieltournoi.domain.partie.usecase.port.input;

import com.lameute.referentieltournoi.domain.commons.entity.Partie;

public interface IPatchPartie {

    Partie process(Partie partie, Integer id);
}
