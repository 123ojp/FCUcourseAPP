package com.example.o123ojp.appprojet;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by o123ojp on 2018/6/27.
 */

public class SettingfileRW {

    private static File dir = MainActivity.context.getFilesDir();

    //在該目錄底下開啟或建立檔名為 "test.txt" 的檔案
    File outFile = new File(dir, "test.txt");
    File inFile = outFile;
    public static File Filename(String filename){
        return new File(dir, filename+".conf");
    }
    //將資料寫入檔案中，若 package name 為 com.myapp
//就會產生 /data/data/com.myapp/files/test.txt 檔案
    //writeToFile(outFile, "Hello! 大家好");

    //writeToFile 方法如下
    public static void writeToFile(File fout, String data) {
        FileOutputStream osw = null;
        try {
            osw = new FileOutputStream(fout);
            osw.write(data.getBytes());
            osw.flush();
        } catch (Exception e) {
            ;
        } finally {
            try {
                osw.close();
            } catch (Exception e) {
                ;
            }
        }
    }
    ///
    public static String readFromFile(File fin) {
        StringBuilder data = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fin), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.context,"讀取設定檔失敗，讀取預設值", Toast.LENGTH_SHORT).show();
            return null;
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                ;
            }
        }
        return data.toString();
    }
    public static int testconf() {
        File fin = Filename("sec");
        StringBuilder data = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fin), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (Exception e) {
            return 0;
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                ;
            }
        }
        return 1;
    }
    public static String readconf(String filename){
        return readFromFile(Filename(filename));
    }
    public static void setconf(String filename,String data){
        writeToFile(Filename(filename),data);
    }
}
