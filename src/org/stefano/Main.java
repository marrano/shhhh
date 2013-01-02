package org.stefano;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import jcurses.system.Toolkit;

//import jcurses.system.CharColor;
//import jcurses.widgets.DefaultLayoutManager;
//import jcurses.widgets.Label;
//import jcurses.widgets.WidgetsConstants;
//import jcurses.widgets.Window;


public class Main 
{
    public static void main(String[] args)
    {    
        Toolkit.setEncoding("CP850");
        MyConsole w;
		try {
			w = new MyConsole(100, 40);
			w.init();
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        
        Thread.currentThread();
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //w.close();

    }
}

