package com.example.grouplist.toStore;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class csvWorker {
    public static void WriteToCsv(String data, Context context)
    {
        try {
            File file = new File(context.getFilesDir(),"csvData.csv");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(data);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public static void WriteToCsv(String data, Context context)
//    {
//        try {
//            File directory = context.getDataDir();
//            File file = new File(directory, "csvData.csv");
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
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static String readFromCsv(Context context)
    {
        BufferedReader br = null;
        try {
            String sCurrentLine;
            String data = "";
            File file = new File(context.getFilesDir(),"csvData.csv");
            FileReader fileReader = new FileReader(file);

            br = new BufferedReader(fileReader);
            while ((sCurrentLine = br.readLine()) != null) {
                data += sCurrentLine;
            }

            String[] intArray = data.split(";");
            StringBuilder returning_data = new StringBuilder();

            for (int i = 0; i < intArray.length; i ++)
            {
                returning_data.append(intArray[i] + '\n');
            }

            return "RETURN FROM CSV\n\n" + String.valueOf(returning_data);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (br != null) br.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return "error";
    }
}
