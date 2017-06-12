package com.nizhawan.nitin.scu;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nitin on 12/06/17.
 */
public class ExecuteScriptUtil {
    public static InputStream executeScriptStream(String fileContents,SlingRemote slingRemote,Map<String,Object> params) throws Exception{
        uploadScripts(fileContents,slingRemote);
        return slingRemote.getStream("/tmp/test.html",params);
    }

    private static void uploadScripts(String fileContents,SlingRemote slingRemote) throws Exception {
        String lines[] = fileContents.split("\\n");
        String imports = "";
        String code = "";
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith("import ")) {
                trimmed = trimmed.substring("import ".length(), trimmed.length());
                if (trimmed.indexOf(";") > -1) {
                    trimmed = trimmed.substring(0, trimmed.indexOf(";"));
                }
                imports += trimmed + ",";
            } else {
                code += trimmed + "\n";
            }
        }

        imports = imports.trim();
        if (imports.endsWith(",")) {
            imports = imports.substring(0, imports.length() - 1);
        }
        // prepare jsp code
        String jspCode = "<%@ taglib prefix=\"sling\" uri=\"http://sling.apache.org/taglibs/sling/1.0\" %><sling:defineObjects/>";
        if (imports != null && imports.trim().length() > 0) {
            jspCode += "<%@ page import=\"" + imports + "\" %>";
        }
        jspCode += "<% " + code + " %>";
        //System.out.println(jspCode);
        Map<String, Object> tmpNode = new HashMap<String, Object>();
        tmpNode.put("sling:resourceType", "a");
        tmpNode.put("jcr:primaryType", "nt:unstructured");
        String response = slingRemote.doPost("/tmp/test", tmpNode);
        slingRemote.doPost("/apps/a", new HashMap<String, Object>());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("html.jsp", new ByteArrayInputStream(jspCode.getBytes("UTF-8")));
        slingRemote.doPost("/apps/a", map);
    }
    public static String executeScript(String fileContents,SlingRemote slingRemote) throws Exception {
        uploadScripts(fileContents,slingRemote);
        return slingRemote.doGet("/tmp/test.html", null);
    }
}
