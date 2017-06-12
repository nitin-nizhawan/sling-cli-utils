package com.nizhawan.nitin.scu.command;

import com.nizhawan.nitin.scu.ExecuteScriptUtil;
import com.nizhawan.nitin.scu.SlingRemote;
import com.nizhawan.nitin.scu.Util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nitin on 09/06/17.
 */
public class ExecCmd {
    private String scriptFile;
    private SlingRemote slingRemote;
    public ExecCmd(String scriptFilePath, SlingRemote slingRemote) {
        this.scriptFile = scriptFilePath;
        this.slingRemote = slingRemote;
    }

    public void execute() throws Exception {
        String filePath = scriptFile;
        File file = new File(filePath);
        String fileContents = new String(Util.readFully(new FileInputStream(file)), "UTF-8");
        System.out.println(ExecuteScriptUtil.executeScript(fileContents,slingRemote));
    }

}
