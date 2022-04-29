package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class SavingData {
    public static void addToFile(String str){
        String path="./src/resources/initialData.txt";
        File file = new File(path);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file,true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fOut != null;
        PrintStream printStream = new PrintStream(fOut);
        printStream.println(str);
        printStream.flush();
        printStream.close();
    }
    public static void addToFile(ArrayList<String> data){
        String path="./src/resources/initialData.txt";
        File file = new File(path);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file,true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fOut != null;
        PrintStream printStream = new PrintStream(fOut);
        printStream.println(string(data));
        printStream.flush();
        printStream.close();
    }
    private static String string(ArrayList<String> str){
        StringBuilder result= new StringBuilder();
        for (int i = 0; i < str.size(); i++) {
            if(i==0) result.append(str.get(i)).append("/");
            else result.append("\t}").append(str.get(i));
        }
        return result.toString();
    }
}
