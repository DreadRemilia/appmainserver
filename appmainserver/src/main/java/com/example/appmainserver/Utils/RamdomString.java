package com.example.appmainserver.Utils;

import java.util.Random;

public class RamdomString {
    private static Random randGen = null;
    private static char[] numbersAndLetters = null;
    private static Object initLock = new Object();


    public static final String random(int length) {

        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            synchronized (initLock) {
                if (randGen == null) {
                    randGen = new Random();
                    numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
                            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
                    //numbersAndLetters = ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
                }
            }
        }
        char [] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
            //randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
        }
        return new String(randBuffer);
    }
}
