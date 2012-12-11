package org.stefano;

import java.io.UnsupportedEncodingException;

import jcurses.event.*;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
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
    
    //Toolkit.setEncoding("ISO-8859-1");
    
    addListener(this);
  }
 
  public void init() {
	DefaultLayoutManager mgr = new DefaultLayoutManager();
    mgr.bindToContainer(getRootPanel());
    mgr.addWidget(
        new Label("Shhhh!!! ..questa � una chat segreta!", defColor),
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
    
    show();
    //Toolkit.changeColors(this.getRectangle(), defColor);  /* Set background to black */
    textAreaInput.getFocus();	
    textAreaInput.addListener(this);
  }

  private void setTitleColors(short magenta) {
	// TODO Auto-generated method stub
	
}

@Override
public void actionPerformed(ActionEvent event) {
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

  @Override
public void stateChanged(ItemEvent e) {   
  }

  @Override
public void valueChanged(ValueChangedEvent e) {
	if(e.getSource() == textAreaInput){
		
			textAreaInput.removeListener(this);
			textAreaInput.setText("");
			repaintMe();

		  textAreaInput.addListener(this);
	  }

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
    	Toolkit.changeColors(this.getRectangle(), defColor);  /* Set background to black */
    }    
  }
  private void repaintMe()
  {
	  this.paint();
	  Toolkit.changeColors(this.getRectangle(), defColor);  /* Set background to black */
  }
}