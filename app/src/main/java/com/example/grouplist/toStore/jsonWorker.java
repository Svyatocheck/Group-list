package com.example.grouplist.toStore;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class jsonWorker {

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public static String getStringFromFile(String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public static void writeJson(JSONObject jsonObject, Context context)
    {
        // Convert JsonObject to String Format
        String userString = jsonObject.toString();

        // Define the File Path and its Name
        File file = new File(context.getFilesDir(), "jsonData.json");
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        try {
            bufferedWriter.write(userString);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String readJson(Context context)
    {
        File file = new File(context.getFilesDir(),"jsonData.json");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null){
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }

            bufferedReader.close();

            // This responce will have Json Format String
            String responce = stringBuilder.toString();
            JSONObject jsonObject  = new JSONObject(responce);

            String data =
                    "Student Name: "+ jsonObject.get("Name").toString() + "\n" +
                    "Sex: " + jsonObject.get("Sex").toString() + "\n" +
                    "IDE: " + jsonObject.get("IDE").toString() + "\n" +
                    "Lang: " +  jsonObject.get("Lang").toString();

            return "RETURN FROM JSON\n\n" + data;

        } catch (JSONException | IOException e) {
            e.printStackTrace();

        }

        return "Nothing";
    }
}
