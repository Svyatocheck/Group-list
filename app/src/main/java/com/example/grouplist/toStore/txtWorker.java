package com.example.grouplist.toStore;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class txtWorker {

    public static void txtWrite(String data, Context context)
    {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("txtData.txt", 0));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

//    public static void txtWrite(String data, Context context)
//    {
//        try {
//
//            File directory = context.getDataDir();
//            File file = new File(directory, "txtData.txt");
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
//            FileWriter writer = new FileWriter(file,true);
//            writer.write(data);
//            writer.flush();
//            writer.close();
//
//        }
//        catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
//    }

    public static String readFromTxtFile(Context context)
    {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("txtData.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return "File not found!";
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            return "Some error";
        }

        return "RETURN FROM TXT:\n" + ret;
    }

}
