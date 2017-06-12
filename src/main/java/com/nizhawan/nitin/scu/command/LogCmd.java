package com.nizhawan.nitin.scu.command;

import com.nizhawan.nitin.scu.ExecuteScriptUtil;
import com.nizhawan.nitin.scu.SlingRemote;
import com.nizhawan.nitin.scu.Util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nitin on 12/06/17.
 */
public class LogCmd {
    private SlingRemote slingRemote;
    private String [] args;
    private String logFileName;
    private boolean tail;
    public LogCmd(String args[], SlingRemote slingRemote){
        this.slingRemote = slingRemote;
        this.args = args;
        parse(args);
    }

    private void setTail(boolean enable){
        this.tail = enable;
    }

    private boolean getTail(){
        return this.tail;
    }

    private void setLogFile(String logFile){
        this.logFileName = logFile;
    }

    private void parse(String [] args){
        for(int i=0;i<args.length;i++){
            if("--tail".equals(args[i]) || "-t".equals(args[i])){
                setTail(true);
            } else {
                setLogFile(args[i]);
            }
        }

    }
    private void doTail(String logFilePath) throws Exception {
        String scriptData = new String(Util.readFully(this.getClass().getResourceAsStream("/tailfile.java")), "UTF-8");
        scriptData = scriptData.replace("${logFilePath}", logFilePath);
        String lastLine = null;
        Map<String,Object> params = new HashMap<String,Object>();
        while(true) {
            InputStream is = ExecuteScriptUtil.executeScriptStream(scriptData, slingRemote,params);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line=br.readLine()) != null){
                int index = line.indexOf(":");
                System.out.println(line.substring(index+1,line.length()));
                lastLine = line.substring(0,index);
            }
            if(lastLine != null){
                params.put("lineNo",lastLine);
            }
        }
    }
    public void execute() throws Exception {
        if(logFileName == null){
            logFileName = "error.log";
        }

        if(logFileName != null){
            String logFilePath = "logs/"+logFileName;
            if(!logFileName.endsWith(".log")){
                logFilePath+=".log";
            }
            if(tail == true){
              doTail(logFilePath);
            } else {
                String scriptData = new String(Util.readFully(this.getClass().getResourceAsStream("/logfile.java")), "UTF-8");
                scriptData = scriptData.replace("${logFilePath}", logFilePath);
                System.out.println(ExecuteScriptUtil.executeScript(scriptData, slingRemote));
            }

        } else {
            System.out.println("Usage: \n sling log <logfile>");
        }
    }
}
