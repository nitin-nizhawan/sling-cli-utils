import java.util.*;
import java.io.*;

String lineNoParam = slingRequest.getParameter("lineNo");
long currentLineNo = -1;
        if(lineNoParam != null){
            currentLineNo = Integer.parseInt(lineNoParam);
        }
File file = new File("crx-quickstart/${logFilePath}");
long startTime = System.currentTimeMillis();
long delta = 5 * 60 * 1000;
long endTime = startTime + delta;
long totalLines = 0;
// count total lines

    try(BufferedReader br=new BufferedReader(new FileReader(file))){
            for(String line;(line=br.readLine())!=null;){
                 totalLines++;
            }
    }

        if(currentLineNo != -1){
            if(totalLines<currentLineNo){
                 currentLineNo =0;
            }
        } else {
            currentLineNo = totalLines;
        }
while(System.currentTimeMillis() < endTime){

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
             int lineNo = 0;
             for(String line; (line=br.readLine())!=null;){
                    if(lineNo > currentLineNo){
                        out.println(currentLineNo+":"+line);
                         out.flush();
                        currentLineNo++;
                    }
                    lineNo++;
             }
        }

}
