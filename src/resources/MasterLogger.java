package resources;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MasterLogger {
    private static MasterLogger instance;
    public static MasterLogger getInstance(){
        if(instance==null){
            instance = new MasterLogger();
        }
        return instance;
    }
    private MasterLogger(){
    }

    public void log(String data,boolean isError,Class<?> clazz){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime now =LocalDateTime.now();
        String string;
        String type=isError?"error":"info";
        string = dtf.format(now)+"  "+type+"   "+clazz.getName()+"  \""+data+"\"";
        try {
            A.addFile(string);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(string);
    }
    public static class A{
        public static void addFile(String str) throws FileNotFoundException {
            String path="./src/resources/log.txt";
            File file = new File(path);
            FileOutputStream fOut = new FileOutputStream(file,true);
            PrintStream printStream = new PrintStream(fOut);
            printStream.println(str);
            printStream.flush();
            printStream.close();
        }
    }
}
