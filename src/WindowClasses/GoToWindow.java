package WindowClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import UI.NumericalTextArea;
import UI.UICreator;

public class GoToWindow extends JFrame {
    public GoToWindow() {
        showGUI();
        
        UICreator.initJFrame(this, false, true, false, true, getParent());
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
    }

    private void showGUI() {
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setPreferredSize(new Dimension(200, 80));
        labelPanel.add(UICreator.creatJLabel("Go to line.", new Dimension(200, 50), UICreator.DEFAULT_FONT, 25), BorderLayout.NORTH);
        labelPanel.add(UICreator.creatJLabel("Line number: ", UICreator.DEFAULT_SIZE, UICreator.DEFAULT_FONT), BorderLayout.SOUTH);
        add(labelPanel, BorderLayout.NORTH);

        NumericalTextArea lineArea = UICreator.createNumericalTextArea("1", UICreator.DEFAULT_TEXT_SIZE, true, false);
        add(lineArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(UICreator.createJButton("Go to", null, UICreator.DEFAULT_SIZE, UICreator.DEFAULT_INSETS), BorderLayout.SOUTH);
        buttonPanel.add(UICreator.createJButton("Cancel", null, UICreator.DEFAULT_SIZE, UICreator.DEFAULT_INSETS));
    
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
