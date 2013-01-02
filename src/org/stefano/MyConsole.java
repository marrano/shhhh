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
import javax.crypto.KeyGenerator;
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
  Cipher encoder					= null;
  Cipher decoder					= null;

  public MyConsole(int width, int height) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
    super(Toolkit.getScreenWidth()-1, Toolkit.getScreenHeight()-1, false, ""); 
    
    defColor = new CharColor(CharColor.BLACK, CharColor.WHITE);
    defInvColor = new CharColor(CharColor.WHITE, CharColor.BLACK);
    
    Toolkit.clearScreen(defColor); /*All black*/
    
    addListener(this);
    
    this.getRootPanel().setColors(defColor);
    this.getRootPanel().setPanelColors(defColor);
    
    
	encoder = Cipher.getInstance("Blowfish/CFB/NoPadding");
	System.out.println(encoder.getProvider());
    
	KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
	keyGenerator.init(128);
	Key KS = keyGenerator.generateKey();	
	
    
//    String Key = "wazapp";
//    byte[] KeyData;
//	  KeyData = Key.getBytes("UTF8");
//    SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
	

	encoder.init(Cipher.ENCRYPT_MODE, KS);
	decoder.init(Cipher.DECRYPT_MODE, KS);


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
    textAreaOutput.setBorderColors(defColor);
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
    
    buttonSend = new Button("Invia");
    buttonSend.setColors(defColor);
    buttonSend.setFocusedButtonColors(defInvColor);	/* focus color*/
    buttonSend.setShortCut('s');
    buttonSend.addListener(this);
    mgr.addWidget(buttonSend, Toolkit.getScreenWidth()-20, Toolkit.getScreenHeight()/2 + 16, 20, 10,
            WidgetsConstants.ALIGNMENT_TOP,
            WidgetsConstants.ALIGNMENT_LEFT);
    
    buttonExit = new Button("Esci");
    buttonExit.setColors(defColor);
    buttonExit.setFocusedButtonColors(defInvColor);	/* focus color*/
    buttonExit.setShortCut('q');
    buttonExit.addListener(this);
    mgr.addWidget(buttonExit, 2, Toolkit.getScreenHeight()-12, 10, 10,
            WidgetsConstants.ALIGNMENT_BOTTOM,
            WidgetsConstants.ALIGNMENT_LEFT);
    
    show();
    
    Toolkit.drawHorizontalLine(0, Toolkit.getScreenHeight()-6, Toolkit.getScreenWidth()-2, new CharColor(CharColor.BLACK, CharColor.WHITE, CharColor.BOLD));
    
    
    textFieldInput.getFocus();	
    textFieldInput.addListener(this);
  }

  private void setTitleColors(short magenta) {
	// TODO Auto-generated method stub
	
}

@Override
public void actionPerformed(ActionEvent event) {
    if (event.getSource() == buttonExit) {
      MessageBox msg = new MessageBox("Esci", "Sei sicuro mimmo?");
      msg.setBorderColors(defInvColor);
      msg.setTitleColors(defColor);
      msg.setTitleColors(defColor);
      
      msg.getRootPanel().setPanelColors(defColor);
      
      msg.show();
      
      if(msg.getExitStatus()==true)
    	  close();
    }
    if (event.getSource() == buttonSend) {
		try {
			byte[] encryptedText;
			byte[] decryptedText;
			try {
				encrypted = encoder.doFinal(textFieldInput.getText().getBytes("UTF8"));
				decryptedText = decoder.doFinal(encrypted);
				
				textAreaOutput.setText(textAreaOutput.getText() + "\n" + encrypted.toString());
				
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IllegalBlockSizeException | BadPaddingException e) {
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
    }    
  }
}