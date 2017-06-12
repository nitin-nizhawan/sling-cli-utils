package com.nizhawan.nitin.scu.command;

import com.nizhawan.nitin.scu.SlingRemote;

/**
 * Created by nitin on 09/06/17.
 */
public class StatusCmd {
    private SlingRemote slingRemote;
    private String[] args;
    public StatusCmd(String args[],SlingRemote slingRemote){
        this.slingRemote = slingRemote;
        this.args = args;
    }
    public void execute() throws Exception {
        if("bundles".equals(args[0])) {
            String s = slingRemote.doGet("/system/console/bundles.json", null);
            int startIndex = s.indexOf(":\"");
            int endIndex = s.indexOf("\",\"");
            System.out.println(s.substring(startIndex+2,endIndex));
        } else if("components".equals(args[0])){
            String s = slingRemote.doGet("/system/console/components.json", null);
            int startIndex = s.indexOf(":\"");
            int endIndex = s.indexOf("\",\"");
            System.out.println(s.substring(0,endIndex));
        }

    }
}
