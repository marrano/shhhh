package org.stefano;

import java.util.StringTokenizer;

import jcurses.event.ActionEvent;
import jcurses.event.ActionListener;
import jcurses.system.CharColor;
import jcurses.widgets.Button;
import jcurses.widgets.DefaultLayoutManager;
import jcurses.widgets.Dialog;
import jcurses.widgets.Label;
import jcurses.widgets.TextField;
import jcurses.widgets.WidgetsConstants;

/**
*  This is a class to create and show user defined messages.
*  Such message is a dialog with an user defined title, containing
* an user defined text and a button to close the window with an user 
* defined label.
*/

public class InitMessageBox extends Dialog implements ActionListener  {
	
	static String _title 					= null;
	static String _text 					= null;
	static Button _buttonOk 				= null;
	static Label _label 					= null;	
	static TextField _textFieldMyName 		= null;
	static TextField _textFieldRemoteName	= null;
	static TextField _key					= null;
	static String _buttonLabelOk 			= "Vai";
	
	boolean exitStatus 						= false;
	DefaultLayoutManager manager 			= null;
	
	static CharColor defColor 				= null;
	static CharColor defInvColor 			= null;	
	
	String myName							= null;	
	String remoteName						= null;	
	String key								= null;	
	
    /**
    *  The constructor
    * 
    * @param title the message's title
    * @param text the message's text
    * @param _buttonLabelOk the label on the message's button
    * 
    */
	public InitMessageBox(String title) {    
		super(30,20,true, title);
		
	    defColor = new CharColor(CharColor.BLACK, CharColor.WHITE);
	    defInvColor = new CharColor(CharColor.WHITE, CharColor.BLACK);	
	    
	    manager = (DefaultLayoutManager)getRootPanel().getLayoutManager();
		
		_title = title;		
		
		_textFieldMyName = new TextField(20,"nome tuo");  
		_textFieldMyName.setColors(defColor);
		_textFieldMyName.setCursorColors(defColor);
		_textFieldMyName.setDelimiterColors(defColor);
		_textFieldMyName.setTextComponentColors(defInvColor);	/* focus color*/
		manager.addWidget(_textFieldMyName, 2, 2, 20, 20,
	            WidgetsConstants.ALIGNMENT_TOP,
	            WidgetsConstants.ALIGNMENT_LEFT);
		
		_key = new TextField(20,"chiave");  
		_key.setColors(defColor);
		_key.setCursorColors(defColor); 
		_key.setDelimiterColors(defColor);
		_key.setTextComponentColors(defInvColor);	/* focus color*/
		manager.addWidget(_key, 2, 6, 20, 20,
	            WidgetsConstants.ALIGNMENT_TOP,
	            WidgetsConstants.ALIGNMENT_LEFT);
		
		_buttonOk = new Button(_buttonLabelOk);
		_buttonOk.setColors(defColor);
		_buttonOk.setFocusedButtonColors(defInvColor);	/* focus color*/
		_buttonOk.addListener(this);	
		manager.addWidget(_buttonOk, 2, 14, getWidth(_buttonLabelOk, _title)+5, 5, WidgetsConstants.ALIGNMENT_CENTER, 
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
	
	public String getMyName() 
	{
		return myName!=null?myName:"";
	}
	
	public String getRemoteName() 
	{
		return remoteName!=null?remoteName:"";
	}
	
	public String getKey() 
	{
		return key!=null?key:"";
	}	
	
	/**
	*  Required for implementing <code>jcurses.event.ActionListener</code>
	*/
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == _buttonOk)
		{
		
			myName = new String(_textFieldMyName.getText());
			remoteName = new String(_textFieldMyName.getText());
			key = new String(_key.getText());
			
			exitStatus = true;
			this.close();
		}
	}

}
