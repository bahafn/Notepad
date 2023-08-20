package Tests;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.plaf.DimensionUIResource;

import UI.NumericalTextArea;
import UI.UICreator;

public class Tests {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        NumericalTextArea textArea = new NumericalTextArea("5.5", new DimensionUIResource(125, 25), false, false);
        frame.add(textArea, BorderLayout.NORTH);
        frame.add(UICreator.createJButton("Print", e -> System.out.println(textArea.getIntValue()),
                UICreator.DEFAULT_SIZE, UICreator.DEFAULT_INSETS), BorderLayout.SOUTH);
        UICreator.initJFrame(frame, true, false, true, true, false, null);
    }
}