package org.stefano;

//import jcurses.system.CharColor;
//import jcurses.widgets.DefaultLayoutManager;
//import jcurses.widgets.Label;
//import jcurses.widgets.WidgetsConstants;
//import jcurses.widgets.Window;


public class Main 
{
    public static void main(String[] args)
    {    
        MyConsole w = new MyConsole(100, 40);
        w.init();
        
        Thread.currentThread();
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        w.close();

    }
}

