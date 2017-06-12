package com.nizhawan.nitin.scu;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by nitin on 09/06/17.
 */
public class Util {
    public static String[] shift(String args[]){
        String [] ret = new String[args.length];
        for(int i=1;i<args.length;i++){
            ret[i-1] = args[i];
        }
        return ret;
    }
    public static byte [] readFully(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte [] buff = new byte[4*1024*1024];

        int bytesRead;
        while((bytesRead = is.read(buff,0,buff.length)) > -1){
            bos.write(buff,0,bytesRead);
        }

        return bos.toByteArray();
    }
    public static void copyFile(File file,OutputStream os) throws Exception {
        InputStream is = new FileInputStream(file);
        copyFile(is,os);
        is.close();
    }
    public static void copyFile(InputStream is,OutputStream os)throws Exception {
        byte [] buffer = new byte[1024];
        int numRead = 0;
        while((numRead = is.read(buffer,0,buffer.length))>-1){
            os.write(buffer,0,numRead);
        }
    }
}
