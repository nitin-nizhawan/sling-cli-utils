package com.nizhawan.nitin.scu;


import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by nitin on 09/06/17.
 */
public class SlingRemote {
    private String host;
    private String port;
    public SlingRemote(String host,String port){
        this.host = host;
        this.port = port;
    }
    private   String getURL(String path){
        return "http://"+host+":"+port+path;
    }

    public InputStream getStream(String path,Map<String,Object> params) throws Exception {
        String urlString = getURL(path);
        String sep = "";
        if(params != null && params.size() > 0){
            urlString+="?";
            for(Map.Entry<String,Object> e:params.entrySet()){
                urlString+=sep+e.getKey()+"="+e.getValue();
                sep="&";
            }
        }
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.addRequestProperty("Authorization","Basic YWRtaW46YWRtaW4=");
        return conn.getInputStream();
    }

    public String doGet(String path,Map<String,Object> params) throws Exception {
        InputStream response = getStream(path,params);
        return new String(Util.readFully(response),"UTF-8");
    }
    public String doPost(String path,Map<String,Object> params) throws Exception {
        String boundary = Long.toHexString(System.currentTimeMillis());
        String CRLF = "\r\n";
        String charset="UTF-8";
//          Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 4504));
        URL url = new URL(getURL(path));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.addRequestProperty("Authorization","Basic YWRtaW46YWRtaW4=");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        OutputStream output = conn.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
        for(Map.Entry<String,Object> e : params.entrySet()){
            String key = e.getKey();
            Object value = e.getValue();
            writer.append("--" + boundary).append(CRLF);
            if(value instanceof InputStream){
                writer.append("Content-Disposition: form-data; name=\""+key+"\";filename=\""+key+"\"").append(CRLF);
                writer.append("Content-Type: " + "application/octet-stream" ).append(CRLF);
                writer.append("Content-Transfer-Encoding: binary").append(CRLF);
                writer.append(CRLF).flush();
                Util.copyFile((InputStream) value, output);
                output.flush();
                writer.append(CRLF).flush();
            } else if(value instanceof File){
                writer.append("Content-Disposition: form-data; name=\""+key+"\";filename=\""+key+"\"").append(CRLF);
                writer.append("Content-Type: " + "application/octet-stream").append(CRLF);
                writer.append("Content-Transfer-Encoding: binary").append(CRLF);
                writer.append(CRLF).flush();
                Util.copyFile((File) value, output);
                output.flush();
                writer.append(CRLF).flush();
            } else {
                writer.append("Content-Disposition: form-data; name=\""+key+"\"").append(CRLF);
               // System.out.println("Added param value "+value);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(value+"").append(CRLF).flush();
            }
        }
        writer.append("--"+boundary+"--").append(CRLF).flush();
        InputStream response = conn.getInputStream();
        return new String(Util.readFully(response),"UTF-8");
    }

}
