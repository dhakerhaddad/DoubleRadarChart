package com.haddad.dhaker.radarchartexample.global;

import java.util.Random;

public class Utils {
    public static int[] randomValues(int length) {
        Random random = new Random();
        int[] array = new int[5];
        for (int i =0 ; i<length; i++) {
            array[i] = random.nextInt(100);
        }
        return array;
    }
}
