package Tests;

import UI.NumericalTextArea;
import UI.UICreator;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Tests {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        NumericalTextArea area = UICreator.createNumericalTextArea("", UICreator.DEFAULT_TEXT_SIZE, false, false, false);
        frame.add(area, BorderLayout.NORTH);
        area.setText("1..2");
        frame.add(UICreator.createJButton("print", e -> {System.out.println(area.getDoubleValue());}, UICreator.DEFAULT_SIZE, UICreator.DEFAULT_INSETS), BorderLayout.SOUTH);
        UICreator.initJFrame(frame, true, false, true, true, null);
    }
}