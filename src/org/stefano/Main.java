package org.stefano;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import jcurses.system.Toolkit;

public class Main 
{
    public static void main(String[] args)
    {    
        Toolkit.setEncoding("CP850");
        Shhhh w;

		try {
			w = new Shhhh(100, 40);
			w.init();
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException e1) {
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

