package com.horosoft.ymq.utils;

import java.util.Random;

/**
 * Created by eugen on 7/13/2017.
 */
public class IsbnGenerator implements NumberGenerator {

    // ======================================
    // =          Business methods          =
    // ======================================

    @Override
    public String generateNumber() {
        return "13-84356-" + Math.abs(new Random().nextInt());
    }
}
