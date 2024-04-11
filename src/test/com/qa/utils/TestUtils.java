package com.qa.utils;


import com.qa.BaseTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {
    public static final long Wait = 10;

    public String realTimeDateAndTime()
    {
        DateFormat dateFormatObj = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        System.out.println(dateFormatObj.format(date));
        return dateFormatObj.format(date);
    }

    //to get logs

    public void log(String txt)
    {
        BaseTest base = new BaseTest();
        String msg = Thread.currentThread().threadId() + ":" + base.getPlatform() + ":" +Thread.currentThread().getStackTrace()[2].getClassName() +":" + txt;

        String strFile = "logs" + File.separator+base.getPlatform()+"_"+File.separator+ base.getDateandTime();

        File logFile = new File(strFile);

        if(!logFile.exists())
        {
            logFile.mkdirs();
        }

        FileWriter fw = null;
        try{
            fw = new FileWriter(logFile+File.separator+"log.txt", true);

        }catch (IOException e)
        {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(msg);
        pw.close();
     }
}
