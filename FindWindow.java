import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;

public class FindWindow extends JFrame {
    public FindWindow() {
        showGUI();

        setAlwaysOnTop(true);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        requestFocus();
    }

    private void showGUI() {
        setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        // Create search textArea
        JTextArea textArea = UICreator.createJTextArea("", new Dimension(200, 25), false);
        panel.add(textArea);

        // Create search button
        panel.add(UICreator.createJButton("Find", e -> findInText(), UICreator.DEFAULT_SIZE, UICreator.DEFAULT_INSETS));

        // Create exist button
        panel.add(UICreator.createJButton("X", e -> dispose(), UICreator.SQUARE_SIZE, UICreator.NO_INSETS));

        add(panel);
    }

    // Returns an array with two integers, one for the begin index, the other for the end index
    public static void findInText() {
        // TODO: write function
    }
}

