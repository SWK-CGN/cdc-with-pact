package org.softwerkskammer.cdc.swapi.controllers;

class ControllerUtils {

    static String toUrl(final String baseUrl, final long id) {
        return String.format("%s/%d", baseUrl, id);
    }


}
