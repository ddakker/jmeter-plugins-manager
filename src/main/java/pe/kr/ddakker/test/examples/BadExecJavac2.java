package pe.kr.ddakker.test.examples;

import java.util.*;
import java.io.*;
public class BadExecJavac2
{
    public static void main(String args[])
    {
        try
        {            
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("java -version");
            int exitVal = proc.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable t)
          {
            t.printStackTrace();
          }
    }
}