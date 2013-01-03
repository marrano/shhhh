package org.stefano;

import java.util.StringTokenizer;

import jcurses.event.ActionEvent;
import jcurses.event.ActionListener;
import jcurses.system.CharColor;
import jcurses.widgets.Button;
import jcurses.widgets.DefaultLayoutManager;
import jcurses.widgets.Dialog;
import jcurses.widgets.Label;
import jcurses.widgets.WidgetsConstants;

/**
*  This is a class to create and show user defined messages.
*  Such message is a dialog with an user defined title, containing
* an user defined text and a button to close the window with an user 
* defined label.
*/

public class MessageBox extends Dialog implements ActionListener  {
	
	String _title = null;
	String _text = null;
	
	Button _buttonOk = null;
	Button _buttonCancel  = null;
	Label _label = null;
	
	static CharColor defColor 		= null;
	static CharColor defInvColor 	= null;	
	
	static String buttonLabelOk = "Si, ne sono sicuro";
	static String buttonLabelCancel = "No, ci ripenso su";
	
	boolean exitStatus = false;
	
    /**
    *  The constructor
    * 
    * @param title the message's title
    * @param text the message's text
    * @param buttonLabelOk the label on the message's button
    * 
    */
	public MessageBox(String title, String text) {    
		super(getWidth(text+buttonLabelOk+buttonLabelCancel, title)+4, getHeight(text)+7,true,title);
		
	    defColor = new CharColor(CharColor.BLACK, CharColor.WHITE);
	    defInvColor = new CharColor(CharColor.WHITE, CharColor.BLACK);	
	    
		DefaultLayoutManager manager = (DefaultLayoutManager)getRootPanel().getLayoutManager();
		
		_title = title;		
		_label = new Label(text);
		_label.setColors(defColor);
		
		_buttonOk = new Button(buttonLabelOk);
		_buttonOk.setColors(defColor);
		_buttonOk.setFocusedButtonColors(defInvColor);	/* focus color*/
		_buttonOk.addListener(this);

		_buttonCancel = new Button(buttonLabelCancel);
		_buttonCancel.setColors(defColor);
		_buttonCancel.setFocusedButtonColors(defInvColor);	/* focus color*/
		_buttonCancel.addListener(this);		
		
		manager.addWidget(_label,0,0,getWidth(text+buttonLabelOk+buttonLabelCancel, _title)+5, getHeight(text)+2, WidgetsConstants.ALIGNMENT_CENTER, 
						  WidgetsConstants.ALIGNMENT_CENTER);
		
		manager.addWidget(_buttonOk,2,getHeight(text)+2,getWidth(buttonLabelOk, _title)+5, 5, WidgetsConstants.ALIGNMENT_CENTER, 
						  WidgetsConstants.ALIGNMENT_CENTER);
						  
		manager.addWidget(_buttonCancel, getWidth(buttonLabelOk, _title)+12, getHeight(text)+2, getWidth(buttonLabelCancel, _title)+5, 5, WidgetsConstants.ALIGNMENT_CENTER, 
				  WidgetsConstants.ALIGNMENT_CENTER);		
		
	}
	
	private static int getWidth(String label, String title) {
		
		StringTokenizer tokenizer = new StringTokenizer(label,"\n");
		int result = 0;
		while (tokenizer.hasMoreElements()) {
			String token = tokenizer.nextToken();
			if (result < token.length()) {
				result = token.length();
			}
		}
		if (title.length() > result) {
			result = title.length();
		}
		//message nust fit on the schreen
		
		if (result>jcurses.system.Toolkit.getScreenWidth()-3) {
			result=jcurses.system.Toolkit.getScreenWidth()-3;
		}
		 
		return result;
	}
	public boolean getExitStatus() 
	{
		return exitStatus;
	}
	
	private static int getHeight(String label) {
		
		StringTokenizer tokenizer = new StringTokenizer(label,"\n");
		int result = 0;
		while (tokenizer.hasMoreElements()) {
			tokenizer.nextElement();
			result++;
		}
		return result;
	}
	
	/**
	*  Required for implementing <code>jcurses.event.ActionListener</code>
	*/
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == _buttonOk)
		{
			exitStatus = true;
			this.close();
		}
		if(event.getSource() == _buttonCancel)
			this.close();
	}

}
