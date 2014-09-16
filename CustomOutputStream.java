package IBMS;
import IBMS.*;

// import java.io.IOException;
// import java.io.OutputStream;
 
// import javax.swing.JTextArea;

import java.util.*;
import java.text.*;
import IBMS.database.*;

import java.awt.*; 
import java.awt.event.*;
import javax.swing.*; 
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
 
/**
 * This class extends from OutputStream to redirect output to a JTextArrea
 * @author www.codejava.net
 *
 */
public class CustomOutputStream extends OutputStream {
    private JTextArea textArea;
     
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
        textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}