package com.example.iut.finalproject.client.utils;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class FileManipulation {

    public String read(Context context, String fileName) {
        try {
            Scanner scanner = new Scanner(context.openFileInput(fileName));
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine()).append("\n");
            }
            return builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void write(Context context, String fileName, int mode, String data) {
        try {
            PrintStream stream = new PrintStream(context.openFileOutput(fileName, mode));
            stream.println(data);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
