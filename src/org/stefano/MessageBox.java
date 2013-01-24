package org.stefano;

import java.util.StringTokenizer;

import jcurses.event.ActionEvent;
import jcurses.event.ActionListener;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import jcurses.widgets.Button;
import jcurses.widgets.DefaultLayoutManager;
import jcurses.widgets.Dialog;
import jcurses.widgets.Label;
import jcurses.widgets.TextArea;
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
	TextArea _textArea	= null;
	
	static CharColor defColor 		= null;
	static CharColor defInvColor 	= null;	
	static CharColor noColor		= null;
	
	static String buttonLabelOk = "ok";
	
	boolean exitStatusOk = true;
	
    /**
    *  The constructor
    * 
    * @param title the message's title
    * @param text the message's text
    * @param buttonLabelOk the label on the message's button
    * 
    */
	public MessageBox(String title) {    
		super(Toolkit.getScreenWidth()/4+10, Toolkit.getScreenHeight()/4 + 10,true,title);
		
	    defColor = new CharColor(CharColor.BLACK, CharColor.WHITE);
	    defInvColor = new CharColor(CharColor.WHITE, CharColor.BLACK);	
	    noColor = new CharColor(CharColor.BLACK, CharColor.BLACK);
	    
		DefaultLayoutManager manager = (DefaultLayoutManager)getRootPanel().getLayoutManager();
		
		_title = title;		


		_textArea = new TextArea(Toolkit.getScreenWidth()/3-8, Toolkit.getScreenHeight()/4 ,"");  
		_textArea.setColors(defColor);
		_textArea.setBorderColors(noColor);
		_textArea.setTextComponentColors(defInvColor);	/* focus color*/

		
		_buttonOk = new Button(buttonLabelOk);
		_buttonOk.setColors(defColor);
		_buttonOk.setFocusedButtonColors(defInvColor);	/* focus color*/
		_buttonOk.addListener(this);

		
		manager.addWidget(_textArea, 0, 0, Toolkit.getScreenWidth()/3-8, Toolkit.getScreenHeight()/4 ,
	            WidgetsConstants.ALIGNMENT_CENTER,
	            WidgetsConstants.ALIGNMENT_CENTER);
		
		manager.addWidget(_buttonOk,Toolkit.getScreenWidth()/8,Toolkit.getScreenHeight()/4, getHeight(buttonLabelOk)+8, 5, WidgetsConstants.ALIGNMENT_CENTER, 
						  WidgetsConstants.ALIGNMENT_CENTER);
						  
	}
	
	public void setText(String text) 
	{  
		_textArea.setText(_textArea.getText() + "\n" + text);
		paint();
	}
	
	public void clean() 
	{  
		_textArea.setText("");
		paint();
	}	
	public void paint()
	{
		 super.paint();
		 if(this.isVisible()==true)
			 _buttonOk.getFocus();
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
		return exitStatusOk;
	}
	
	public void setExitStatus(boolean status) 
	{  
		exitStatusOk=status;
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
			this.close();
			if (getExitStatus()==false)
					System.exit(1);
		}

	}
	public void show()
	{
		super.show();
//		_buttonOk.getFocus();
//		paint();
	}

}
