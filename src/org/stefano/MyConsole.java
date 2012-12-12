package org.stefano;

import jcurses.event.*;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import jcurses.widgets.*;


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

  public MyConsole(int width, int height) {
    super(Toolkit.getScreenWidth()-1, Toolkit.getScreenHeight()-1, false, ""); 
    
    defColor = new CharColor(CharColor.BLACK, CharColor.WHITE);
    defInvColor = new CharColor(CharColor.WHITE, CharColor.BLACK);
    
    Toolkit.clearScreen(defColor); /*All black*/
    
    addListener(this);
    
    this.getRootPanel().setColors(defColor);
    this.getRootPanel().setPanelColors(defColor);
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
    textAreaOutput.setBorderColors(defColor);
    textAreaOutput.setTextComponentColors(defInvColor);	/* focus color*/
    mgr.addWidget(textAreaOutput, 0, 4, Toolkit.getScreenWidth()-4, Toolkit.getScreenHeight()/2 + 10,
            WidgetsConstants.ALIGNMENT_TOP,
            WidgetsConstants.ALIGNMENT_LEFT);
    
//    textAreaInput = new TextArea(Toolkit.getScreenWidth()-4, Toolkit.getScreenHeight()/2 - 20,"...qui si scrive");  
//    textAreaInput.setColors(defColor);
//    textAreaInput.setTextComponentColors(defInvColor);	/* focus color*/
//    mgr.addWidget(textAreaInput, 0, Toolkit.getScreenHeight()/2 + 16, Toolkit.getScreenWidth()-4, Toolkit.getScreenHeight()/2 - 20,
//            WidgetsConstants.ALIGNMENT_LEFT,
//            WidgetsConstants.ALIGNMENT_TOP);
    
    textFieldInput = new TextField(Toolkit.getScreenWidth()-4,"...qui si scrive");  
    textFieldInput.setColors(defColor);
    textFieldInput.setCursorColors(defColor);
    textFieldInput.setDelimiterColors(defColor);
    textFieldInput.setCursorLocation(40, 40);
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
    

    

//    textfield = new TextField(10);
//    mgr.addWidget(textfield, 0, 0, 20, 20,
//        WidgetsConstants.ALIGNMENT_CENTER,
//        WidgetsConstants.ALIGNMENT_CENTER);

    buttonExit = new Button("Esci");
    buttonExit.setColors(defColor);
    buttonExit.setFocusedButtonColors(defInvColor);	/* focus color*/
    buttonExit.setShortCut('q');
    buttonExit.addListener(this);
    mgr.addWidget(buttonExit, 2, Toolkit.getScreenHeight()-12, 10, 10,
            WidgetsConstants.ALIGNMENT_BOTTOM,
            WidgetsConstants.ALIGNMENT_LEFT);
    
    show();
    //Toolkit.changeColors(this.getRectangle(), defColor);  /* Set background to black */
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
		textAreaOutput.setText(textAreaOutput.getText() + "\n" + textFieldInput.getText());
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