package com.lameute.referentieltournoi.application.commons;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BaseControllerUtils {

    private static final String BASE_URI = "/referentiel-tournoi/v1";

    public static final String JOUEURS_PATH = BASE_URI + "/joueurs";
    public static final String BULK_JOUEURS_PATH = BASE_URI + "/bulk/joueurs";

    public static final String PARTIES_PATH = BASE_URI + "/parties";
    public static final String GROUPES_OPTMISES_PATH = BASE_URI + "/groupes-optimises";
}
