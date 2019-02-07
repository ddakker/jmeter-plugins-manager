package pe.kr.ddakker.test.examples;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler extends Thread {
    InputStream is;
    String type;
    JTextArea ta;

    public StreamGobbler(InputStream is, String type, JTextArea ta) {
        this.is = is;
        this.type = type;
        this.ta = ta;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(type + ">" + line);

//                ta.append("\n" + type + ">" + line);
                ta.append(type + ">" + line + "\n");
//                ta.append(type + ">" + line);
                ta.setCaretPosition(ta.getDocument().getLength());
//                DefaultCaret caret = (DefaultCaret) ta.getCaret();
//                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}