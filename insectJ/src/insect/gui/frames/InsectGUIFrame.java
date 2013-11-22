package insect.gui.frames;

import insect.gui.InsectGUI;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 * InsectGUIFrame.java
 *
 *
 * Created: Tue Mar 18 16:55:04 2003
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 * @version
 */

public abstract class InsectGUIFrame extends JInternalFrame
  implements InternalFrameListener {
  
  /* Reference to containing frame */
  protected InsectGUI parent;

  public InsectGUIFrame() {}

  
  /**
   * Constructor to initialize and create gui.
   *
   * @param parent an <code>InsectGUI</code> value
   */
  public InsectGUIFrame(InsectGUI parent) {    
    this.parent = parent;

    setupElements();
    setupFrame();

    addInternalFrameListener(this);
  }

  
  /**
   * Override for gui initialization.
   *
   */
  protected abstract void setupElements();


  /**
   * Override for gui creation.
   *
   */
  protected abstract void setupFrame();  

  
  /**
   * Handles the event of this frame receiving focus.
   *
   * @param e an <code>InternalFrameEvent</code> value
   */
  protected void gotFocus(InternalFrameEvent e){}
  

  /**
   * Override gotFocus() to handle this event.
   *
   * @param e an <code>InternalFrameEvent</code> value
   */
  public void internalFrameActivated(InternalFrameEvent e) {
    gotFocus(e);
  }


  public void internalFrameClosed(InternalFrameEvent e) {}
  public void internalFrameClosing(InternalFrameEvent e) {}
  public void internalFrameDeactivated(InternalFrameEvent e) {}
  public void internalFrameDeiconified(InternalFrameEvent e) {}
  public void internalFrameIconified(InternalFrameEvent e) {}
  public void internalFrameOpened(InternalFrameEvent e) {}

  
}// InsectGUIFrame
