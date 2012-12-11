package org.stefano;

import java.io.UnsupportedEncodingException;

import jcurses.event.*;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import jcurses.util.Message;
import jcurses.util.Rectangle;
import jcurses.widgets.*;


public class MyConsole extends Window implements ItemListener, ActionListener, ValueChangedListener, WindowListener, WidgetsConstants 
{
  
  static TextField textfield 	= null;
  static Button button 			= null;
  static TextArea textAreaOutput= null;
  //static TextArea textAreaInput = null;
  static TextField textAreaInput = null;
  static MyConsole window 		= null;
  static CharColor defColor 	= null;
  static CharColor defInvColor 	= null;

  public MyConsole(int width, int height) {
    super(Toolkit.getScreenWidth()-1, Toolkit.getScreenHeight()-1, false, ""); 
    
    defColor = new CharColor(CharColor.BLACK, CharColor.WHITE);
    defInvColor = new CharColor(CharColor.WHITE, CharColor.BLACK);
    
    Toolkit.clearScreen(defColor); /*All black*/
    
    addListener((WindowListener) this);
  }
 
  public void init() {
	DefaultLayoutManager mgr = new DefaultLayoutManager();
    mgr.bindToContainer(getRootPanel());
    mgr.addWidget(
        new Label("Shhhh!!! ..questa è una chat segreta!", defColor),
        			2, 2, 40, 10,
        			WidgetsConstants.ALIGNMENT_LEFT,
        			WidgetsConstants.ALIGNMENT_TOP);
    
    
    textAreaOutput = new TextArea(Toolkit.getScreenWidth()-4, Toolkit.getScreenHeight()/2 + 10,"...qui si legge");  
    textAreaOutput.setColors(defColor);
    textAreaOutput.setTextComponentColors(defInvColor);	/* focus color*/
    mgr.addWidget(textAreaOutput, 0, 4, Toolkit.getScreenWidth()-4, Toolkit.getScreenHeight()/2 + 10,
            WidgetsConstants.ALIGNMENT_LEFT,
            WidgetsConstants.ALIGNMENT_TOP);
    
//    textAreaInput = new TextArea(Toolkit.getScreenWidth()-4, Toolkit.getScreenHeight()/2 - 20,"...qui si scrive");  
//    textAreaInput.setColors(defColor);
//    textAreaInput.setTextComponentColors(defInvColor);	/* focus color*/
//    mgr.addWidget(textAreaInput, 0, Toolkit.getScreenHeight()/2 + 16, Toolkit.getScreenWidth()-4, Toolkit.getScreenHeight()/2 - 20,
//            WidgetsConstants.ALIGNMENT_LEFT,
//            WidgetsConstants.ALIGNMENT_TOP);
    
    textAreaInput = new TextField(Toolkit.getScreenWidth()-4,"...qui si scrive");  
    textAreaInput.setColors(defColor);
    textAreaInput.setTextComponentColors(defInvColor);	/* focus color*/
    mgr.addWidget(textAreaInput, 0, Toolkit.getScreenHeight()/2 + 16, Toolkit.getScreenWidth()-4, Toolkit.getScreenHeight()/2 - 20,
            WidgetsConstants.ALIGNMENT_LEFT,
            WidgetsConstants.ALIGNMENT_TOP);
    

    

//    textfield = new TextField(10);
//    mgr.addWidget(textfield, 0, 0, 20, 20,
//        WidgetsConstants.ALIGNMENT_CENTER,
//        WidgetsConstants.ALIGNMENT_CENTER);

    button = new Button("Esci");
    button.setColors(defColor);
    button.setFocusedButtonColors(defInvColor);	/* focus color*/
    button.setShortCut('q');
    button.addListener(this);
    mgr.addWidget(button, 2, Toolkit.getScreenHeight()-12, 10, 10,
            WidgetsConstants.ALIGNMENT_BOTTOM,
            WidgetsConstants.ALIGNMENT_LEFT);
    textAreaOutput.setText("Siamo pigiati!");
    show();
    //Toolkit.changeColors(this.getRectangle(), defColor);  /* Set background to black */
    textAreaInput.getFocus();	
  }

  private void setTitleColors(short magenta) {
	// TODO Auto-generated method stub
	
}

public void actionPerformed(ActionEvent event) {
	textAreaOutput.setText("Siamo pigiati!");

    if (event.getSource() == button) {
      MessageBox msg = new MessageBox("Esci", "Sei sicuro mimmo?");
      msg.setBorderColors(defColor);
      msg.setTitleColors(defColor);
      
      msg.getRootPanel().setPanelColors(defColor);
      
      msg.show();
      
      if(msg.getExitStatus()==true)
    	  close();
    }
  }

  public void stateChanged(ItemEvent e) {   
  }

  public void valueChanged(ValueChangedEvent e) {
	  textAreaOutput.setText("Siamo dentro!");
	if(e.getSource() == textAreaInput){
		  try {
			String valueISO = new String(textAreaInput.getText().getBytes("CP850"), "ISO-8859-1");
			textAreaInput.setText(valueISO);
		  	} 
		  catch (UnsupportedEncodingException e1) {
			  // TODO Auto-generated catch block
			e1.printStackTrace();
		  }
	  }

  }

  public void windowChanged(WindowEvent event) {
    if (event.getType() == WindowEvent.CLOSING) 
    {
      event.getSourceWindow().close();
      Toolkit.clearScreen(defColor);
    }
    if (event.getType() == WindowEvent.ACTIVATED) 
    {
    	Toolkit.changeColors(this.getRectangle(), defColor);  /* Set background to black */
    }    
  }
}