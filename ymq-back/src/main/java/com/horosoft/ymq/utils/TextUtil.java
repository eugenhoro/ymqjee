package com.horosoft.ymq.utils;

/**
 * Created by eugen on 7/13/2017.
 */
public class TextUtil {

    // ======================================
    // =          Business methods          =
    // ======================================

    public String sanitize(String textToSanitize) {
        return textToSanitize.replaceAll("\\s+", " ");
    }
}
