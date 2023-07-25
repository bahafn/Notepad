package Tests;

import UI.NumericalTextArea;
import UI.UICreator;

import javax.swing.JFrame;

public class Tests {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new NumericalTextArea("1", true));
        UICreator.initJFrame(frame, true, false, true, true, null);
    }
}