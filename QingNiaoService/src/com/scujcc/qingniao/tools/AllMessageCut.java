package com.scujcc.qingniao.tools;

public class AllMessageCut {
   public static String[] cutAllMeaa(byte[] allbyte) throws Exception{
	   String str=new String(allbyte,"UTF-8");
	   int first;
	   int second;
	   first=str.indexOf(",");
	   second=str.indexOf(",", first+1);
	   return new String[]{str.substring(0, first),str.substring(first+1, second),str.substring(second+1, str.length())};
   }
}
