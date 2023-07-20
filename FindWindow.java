import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;

public class FindWindow extends JFrame {
    private App app;
    private String text;
    private boolean wholeWord = false, matchCase = true;
    private int selectedIndex = 0;

    public FindWindow(String text, App app) {
        this.text = text;
        this.app = app;

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
        panel.add(UICreator.createJButton("Find", e -> findAndSelect(0, textArea.getText(), true), UICreator.DEFAULT_SIZE, UICreator.DEFAULT_INSETS));

        // Create next and previous buttons
        panel.add(UICreator.createJButton("<", e -> findAndSelect(selectedIndex - 1, textArea.getText(), false), UICreator.SQUARE_SIZE, UICreator.NO_INSETS));
        panel.add(UICreator.createJButton(">", e -> findAndSelect(selectedIndex + 1, textArea.getText(), true), UICreator.SQUARE_SIZE, UICreator.NO_INSETS));

        // This is used so the check boxes are on top of each other
        JPanel checkBoxPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        checkBoxPanel.setPreferredSize(new Dimension(100, 30));

        // Create option check boxes
        checkBoxPanel.add(UICreator.createJCheckBox("Whole word", false, e -> { wholeWord = !wholeWord; }));
        checkBoxPanel.add(UICreator.createJCheckBox("Match case", true, e -> { matchCase = !matchCase; }));

        panel.add(checkBoxPanel);

        // Create exist button
        panel.add(UICreator.createJButton("X", e -> dispose(), UICreator.SQUARE_SIZE, UICreator.NO_INSETS));

        add(panel);
    }

    private void findAndSelect(int startIndex, String searchText, boolean forward) {
        int[] indexes = findInText(startIndex, text, searchText, matchCase, wholeWord, forward);

        if (indexes[0] != indexes[1]) {
            app.selectText(indexes[0], indexes[1]);
            selectedIndex = indexes[0];
        }
    }

    // Returns an array with two integers, one for the begin index, the other for the end index
    public static int[] findInText(int startIndex, String text, String searchText, boolean matchCase, boolean wholeWord, boolean forward) {
        int[] indexes = new int[2];
        int beginIndex = -1;

        if (!matchCase) {
            text = text.toLowerCase();
            searchText = searchText.toLowerCase();
        }

        if (forward) {
            beginIndex = text.indexOf(searchText, startIndex);
            indexes[0] = beginIndex;
            indexes[1] = beginIndex + searchText.length();
        }
        else {
            beginIndex = text.substring(0, startIndex).lastIndexOf(searchText);
            indexes[0] = beginIndex;
            indexes[1] = beginIndex + searchText.length();
        }

        if (wholeWord && Character.isAlphabetic(text.charAt(beginIndex + searchText.length())))
            return findInText(beginIndex + 1, text, searchText, matchCase, wholeWord, forward);

        if (beginIndex == -1)
            return new int[2];

        return indexes;
    }
}
