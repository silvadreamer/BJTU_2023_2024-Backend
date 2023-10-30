package com.bjtu.backend.utils;


import java.util.Date;
import java.text.SimpleDateFormat;

public class TimeGenerateUtil
{
    public static String getTime()
    {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(currentDate);
    }
}
