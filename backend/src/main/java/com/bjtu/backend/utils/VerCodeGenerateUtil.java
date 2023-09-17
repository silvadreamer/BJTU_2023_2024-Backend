package com.bjtu.backend.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Silva31
 * @version 1.0
 * @date 2023/9/17 21:13
 */
public class VerCodeGenerateUtil
{
    private static final String SYMBOLS = "0123456789ABCDEFGHIGKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new SecureRandom();


    public static String generateVerCode()
    {
        char[] numbers = new char[6];
        for (int i = 0; i < numbers.length; i++)
        {
            numbers[i] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(numbers);
    }

}
