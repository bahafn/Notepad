package Tests;

import UI.NumericalTextArea;
import UI.UICreator;

import javax.swing.JFrame;

public class Tests {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        NumericalTextArea area = new NumericalTextArea("1.1", false);
        frame.add(area);
        System.out.println(area.getDoubleValue());
        UICreator.initJFrame(frame, true, false, true, true, null);
    }
}