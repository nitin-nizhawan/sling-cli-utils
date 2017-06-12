package com.nizhawan.nitin.scu;
import com.nizhawan.nitin.scu.command.ExecCmd;
import com.nizhawan.nitin.scu.command.LogCmd;
import com.nizhawan.nitin.scu.command.StatusCmd;


public class Main {
    static  String slingHost;
    static  String slingPort;




  public static void statusCmd(String args[],SlingRemote slingRemote) throws Exception {
      StatusCmd cmd = new StatusCmd(args,slingRemote);
      cmd.execute();
  }

  public static void configCmd(String args[]){
     
  }
    public static void execCmd(String args[],SlingRemote slingRemote) throws  Exception{
        ExecCmd cmd = new ExecCmd(args[0],slingRemote);
        cmd.execute();
        //return cmd.getOutput();
    }
  public static void main(String args[]) throws Exception {
     slingHost = System.getenv("sling_host");
     slingPort = System.getenv("sling_port");
      SlingRemote slingRemote = new SlingRemote(slingHost,slingPort);
     if(args.length<1){
        System.out.println("Usage sling status|config");
        System.exit(0);
     }
     if("status".equals(args[0])){
       statusCmd(Util.shift(args), slingRemote);
     } else if("config".equals(args[0])){
       configCmd(Util.shift(args));
     } else if("exec".equals(args[0])){
       execCmd(Util.shift(args),slingRemote);
     }  else if("log".equals(args[0])){
         LogCmd cmd = new LogCmd(Util.shift(args),slingRemote);
         cmd.execute();
     }
       
  }
}
