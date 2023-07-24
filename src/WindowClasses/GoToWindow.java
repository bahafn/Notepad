package WindowClasses;

import javax.swing.JFrame;

import UI.UICreator;

public class GoToWindow extends JFrame {
    public GoToWindow() {
        showGUI();
        
        UICreator.initJFrame(this, false, true, false, true, getParent());
    }

    private void showGUI() {

    }
}
