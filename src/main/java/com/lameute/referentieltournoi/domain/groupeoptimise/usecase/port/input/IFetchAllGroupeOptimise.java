package com.lameute.referentieltournoi.domain.groupeoptimise.usecase.port.input;

import com.lameute.referentieltournoi.domain.groupeoptimise.entity.GroupeOptimise;

import java.util.List;

public interface IFetchAllGroupeOptimise {

    List<GroupeOptimise> process(Integer nombreGroupe, Integer currentRonde);
}
