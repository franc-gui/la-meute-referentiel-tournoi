package com.lameute.referentieltournoi.domain.partie.usecase.port.input;

import com.lameute.referentieltournoi.domain.commons.entity.Partie;

import java.util.List;

public interface ICreatePartie {

    Partie process(Partie partie);
}
