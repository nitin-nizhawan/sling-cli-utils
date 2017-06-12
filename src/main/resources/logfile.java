import java.util.*;
import java.io.*;

File logFile = new File("crx-quickstart/${logFilePath}");
FileInputStream fis = new FileInputStream(logFile);
byte buffer [] = new byte[1024];
int numRead = -1;
ByteArrayOutputStream baos = new ByteArrayOutputStream();

while((numRead = fis.read(buffer,0,buffer.length)) > -1){
     baos.write(buffer,0,numRead);
}

byte data [] = baos.toByteArray();
String str = new String(data,"UTF-8");
fis.close();
out.println(str);