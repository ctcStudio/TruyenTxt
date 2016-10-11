package com.hiepkhach9x.baseTruyenHK.utils;

import android.content.res.AssetManager;

import com.hiepkhach9x.baseTruyenHK.BookApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by HungHN on 2/20/2016.
 */
public class FileUtils {

    public static StringBuilder readFileFromDisk(String filePath) {
        File file = new File(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            char[] chars = new char[10];
            int line;
            while ((line = bufferedReader.read(chars)) > 0) {
                stringBuilder.append(chars,0,line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    public static StringBuilder readFileFromAsset(String filePath) {
        AssetManager assetManager = BookApplication.get().getAssets();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(assetManager.open(filePath));
            BufferedReader bufferedReader = new BufferedReader(reader);
            char[] chars = new char[24];
            int offset = 0;
            int line;
            while ((line = bufferedReader.read(chars)) > 0) {
                stringBuilder.append(chars, offset, line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder;
    }
}
