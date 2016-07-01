package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Log {
	
	public static List<String> log=new ArrayList<String>();
	
	public static void I(String message){
    	System.out.println("[LogInfo]  :  " + message );
    	log.add("[LogInfo]  :  " + message + "\r\n" );
    }
	
	public static void writeLog(File f) throws IOException{
		FileWriter fw = new FileWriter(f,true); 
	    for(String info : log){
	    	fw.write(info); 
        }
	    log.clear();
		fw.flush(); 
		fw.close(); 
 }
	
}
