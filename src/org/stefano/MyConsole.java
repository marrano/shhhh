package org.stefano;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import jcurses.event.*;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import jcurses.widgets.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class MyConsole extends Window implements ItemListener, ActionListener, ValueChangedListener, WindowListener, WidgetsConstants 
{
  
  static TextField textfield 		= null;
  static Button buttonExit 			= null;
  static Button buttonSend 			= null;
  static TextArea textAreaOutput	= null;
  static TextField textFieldInput 	= null;
  static MyConsole window 			= null;
  static CharColor defColor 		= null;
  static CharColor defInvColor 		= null;
  Cipher cipher						= null;
  Key 	 KS 						= null;

  public MyConsole(int width, int height) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
    super(Toolkit.getScreenWidth()-1, Toolkit.getScreenHeight()-1, false, ""); 
    
    defColor = new CharColor(CharColor.BLACK, CharColor.WHITE);
    defInvColor = new CharColor(CharColor.WHITE, CharColor.BLACK);
    
    Toolkit.clearScreen(defColor); /*All black*/
    
    addListener(this);
    
    this.getRootPanel().setColors(defColor);
    this.getRootPanel().setPanelColors(defColor);
       
    
	cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
	//System.out.println(cipher.getProvider());

    String Key = "wazapp";
    byte[] KeyData = null;
	try {
		KeyData = Key.getBytes("UTF8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    KS = new SecretKeySpec(KeyData, "Blowfish");
  }
 
  public void init() {
	DefaultLayoutManager mgr = new DefaultLayoutManager();
    mgr.bindToContainer(getRootPanel());
    mgr.addWidget(
        new Label("Shhhh!!! ..questa è una chat segreta!", defColor),
        			2, 2, 40, 10,
        			WidgetsConstants.ALIGNMENT_LEFT,
        			WidgetsConstants.ALIGNMENT_TOP);
      
    textAreaOutput = new TextArea(Toolkit.getScreenWidth()-4, Toolkit.getScreenHeight()/2 + 10,"");  
    textAreaOutput.setColors(defColor);
    textAreaOutput.setBorderColors(defInvColor);
    textAreaOutput.setTextComponentColors(defInvColor);	/* focus color*/
    textAreaOutput.removeListener(this);
    mgr.addWidget(textAreaOutput, 0, 4, Toolkit.getScreenWidth()-4, Toolkit.getScreenHeight()/2 + 10,
            WidgetsConstants.ALIGNMENT_TOP,
            WidgetsConstants.ALIGNMENT_LEFT);
    
    textFieldInput = new TextField(Toolkit.getScreenWidth()-4,"");  
    textFieldInput.setColors(defColor);
    textFieldInput.setCursorColors(defColor);
    textFieldInput.setDelimiterColors(defColor);
    textFieldInput.setTextComponentColors(defInvColor);	/* focus color*/
    mgr.addWidget(textFieldInput, 0, Toolkit.getScreenHeight()/2 + 16, Toolkit.getScreenWidth()-24, Toolkit.getScreenHeight()/2 - 20,
            WidgetsConstants.ALIGNMENT_TOP,
            WidgetsConstants.ALIGNMENT_LEFT);
    
    buttonSend = new Button("Manda");
    buttonSend.setColors(defColor);
    buttonSend.setFocusedButtonColors(defInvColor);	/* focus color*/
    buttonSend.setShortCut('s');
    buttonSend.addListener(this);
    mgr.addWidget(buttonSend, Toolkit.getScreenWidth()-14, Toolkit.getScreenHeight()/2 + 16, 20, 10,
            WidgetsConstants.ALIGNMENT_TOP,
            WidgetsConstants.ALIGNMENT_LEFT);
    
    buttonExit = new Button("Smetti");
    buttonExit.setColors(defColor);
    buttonExit.setFocusedButtonColors(defInvColor);	/* focus color*/
    buttonExit.setShortCut('q');
    buttonExit.addListener(this);
    mgr.addWidget(buttonExit, 2, Toolkit.getScreenHeight()-12, 10, 10,
            WidgetsConstants.ALIGNMENT_BOTTOM,
            WidgetsConstants.ALIGNMENT_LEFT);
    
    show();
    
    Toolkit.drawHorizontalLine(1, Toolkit.getScreenHeight()-6, Toolkit.getScreenWidth()-5, new CharColor(CharColor.BLACK, CharColor.WHITE, CharColor.BOLD));
    
    
    textFieldInput.getFocus();	
    textFieldInput.addListener(this);
  }

  private void setTitleColors(short magenta) {
	// TODO Auto-generated method stub
	
}

@Override
public void actionPerformed(ActionEvent event) {
    if (event.getSource() == buttonExit) {
      MessageBox msg = new MessageBox("", "Vuoi smettere davvero?");
      msg.setBorderColors(defInvColor);
      msg.setTitleColors(defColor);
      msg.setTitleColors(defColor);
      
      msg.getRootPanel().setPanelColors(defColor);
      
      msg.show();
      
      if(msg.getExitStatus()==true)
    	  close();
    }
    if (event.getSource() == buttonSend) {

		byte[] encryptedText = null;
		byte[] decryptedText = null;

		try {
			cipher.init(Cipher.ENCRYPT_MODE, KS);
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			encryptedText = cipher.doFinal(textFieldInput.getText().getBytes("UTF8"));
		} catch (IllegalBlockSizeException | BadPaddingException
				| UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			cipher.init(Cipher.DECRYPT_MODE, KS);
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			decryptedText = cipher.doFinal(encryptedText);
		} catch (IllegalBlockSizeException | BadPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			
		try {
			textAreaOutput.setText(textAreaOutput.getText() + "\n" + new String(decryptedText, "UTF8") + " (" + encryptedText.toString() + ")");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		textFieldInput.setText("");
		paint();
		textFieldInput.getFocus();	
	}
  }

  @Override
public void stateChanged(ItemEvent e) {  

  }

  @Override
public void valueChanged(ValueChangedEvent e) {

  }

  @Override
public void windowChanged(WindowEvent event) {
    if (event.getType() == WindowEvent.CLOSING) 
    {
      event.getSourceWindow().close();
      Toolkit.clearScreen(defColor);
    }
    if (event.getType() == WindowEvent.ACTIVATED) 
    {
    	//paint();
    }    
  }
}