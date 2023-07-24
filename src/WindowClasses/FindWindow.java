package WindowClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;

import UI.UICreator;

public class FindWindow extends JFrame {
    private App app;

    private int selectedIndex = 0;

    private boolean wholeWord = false, matchCase = true;
    private boolean replaceUI = false;

    private JTextArea searchText;
    private JPanel replacePanel;

    public FindWindow(App app, boolean replaceUI) {
        this.app = app;
        this.replaceUI = replaceUI;

        showGUI();

        UICreator.initJFrame(this, false, true, false, true, getParent());
    }

    private void showGUI() {
        // Create border
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        // Create find options UI
        JPanel findPanel = new JPanel();

        // Create "show replace options button"
        findPanel.add(UICreator.createJButton("Replace", e -> showReplaceOptions(), UICreator.DEFAULT_SIZE, UICreator.DEFAULT_INSETS));

        // Create search textArea
        searchText = UICreator.createJTextArea("", UICreator.DEFAULT_TEXT_SIZE, false);
        findPanel.add(searchText);

        // Create search button
        findPanel.add(UICreator.createJButton("Find", e -> findAndSelect(0, searchText.getText(), true), UICreator.DEFAULT_SIZE, UICreator.DEFAULT_INSETS));

        // Create next and previous buttons
        findPanel.add(UICreator.createJButton("<", e -> findAndSelect(selectedIndex - 1, searchText.getText(), false), UICreator.SQUARE_SIZE, UICreator.NO_INSETS));
        findPanel.add(UICreator.createJButton(">", e -> findAndSelect(selectedIndex + 1, searchText.getText(), true), UICreator.SQUARE_SIZE, UICreator.NO_INSETS));

        // This is used so the check boxes are on top of each other
        JPanel checkBoxPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        checkBoxPanel.setPreferredSize(new Dimension(100, 30));

        // Create option check boxes
        checkBoxPanel.add(UICreator.createJCheckBox("Whole word", false, e -> { wholeWord = !wholeWord; }));
        checkBoxPanel.add(UICreator.createJCheckBox("Match case", true, e -> { matchCase = !matchCase; }));

        findPanel.add(checkBoxPanel);

        // Create exist button
        findPanel.add(UICreator.createJButton("X", e -> dispose(), UICreator.SQUARE_SIZE, UICreator.NO_INSETS));

        add(findPanel, BorderLayout.NORTH);

        // Create replace options UI
        replacePanel = new JPanel();

        JTextArea replaceText = UICreator.createJTextArea("", UICreator.DEFAULT_TEXT_SIZE, false);
        replacePanel.add(replaceText);

        // Create replace and replace all buttons
        replacePanel.add(UICreator.createJButton("Replace", e -> replace(searchText.getText(), replaceText.getText()), UICreator.DEFAULT_SIZE, UICreator.DEFAULT_INSETS));
        replacePanel.add(UICreator.createJButton("Replace all", e -> replaceAll(searchText.getText(), replaceText.getText()), new Dimension(115, 25), UICreator.DEFAULT_INSETS));

        if (replaceUI)
            add(replacePanel);
    }

    private void showReplaceOptions() {
        if (replaceUI)
            remove(replacePanel);
        else
            add(replacePanel);

        replaceUI = !replaceUI;
        pack();
    }

    private void replace(String oldText, String newText) {
        if (findAndSelect(0, oldText, rootPaneCheckingEnabled) != -1)
            app.replace(newText);
    }

    private void replaceAll(String oldText, String newText) {
        int beginIndex = 0;

        while (true) {
            if (findAndSelect(beginIndex, oldText, true) != -1) {
                app.replace(newText);
                beginIndex += newText.length();
            }
            else
                break;
        }
    }

    // Returns the begin index of the word we are looking for which is used by the replace all method
    private int findAndSelect(int startIndex, String searchText, boolean forward) {
        int[] indexes = findInText(startIndex, app.getText(), searchText, matchCase, wholeWord, forward);

        if (indexes[0] != indexes[1]) {
            app.selectText(indexes[0], indexes[1]);
            selectedIndex = indexes[0];
            return indexes[0];
        }
        else
            return -1;
    }

    // Returns an array with two integers, one for the begin index, the other for the end index
    public static int[] findInText(int startIndex, String text, String searchText, boolean matchCase, boolean wholeWord, boolean forward) {
        int[] indexes = new int[2]; // The array that will be returned at the end
        int beginIndex = -1; // The index at which the seatchText starts (-1 means not found)

        // Make sure the startIndex isn't outOfBounds
        // Used mostly because next and previous buttons can make send values bigger than text.length() or
        // smaller than zero
        if (startIndex >= text.length() || startIndex < 0)
            startIndex = 0;

        // Make everything lower case if we don't care about case
        if (!matchCase) {
            text = text.toLowerCase();
            searchText = searchText.toLowerCase();
        }

        // If we are going forward, find the first occurnce of searchText in text (strating from startIndex)
        if (forward) {
            beginIndex = text.indexOf(searchText, startIndex);
            indexes[0] = beginIndex;
            indexes[1] = beginIndex + searchText.length();
        }
        // Otherwise, find the last occurnce in the text, start from the first index to the startIndex
        else {
            beginIndex = text.lastIndexOf(searchText, startIndex);
            indexes[0] = beginIndex;
            indexes[1] = beginIndex + searchText.length();
        }

        // If beginIndex is set to -1 (or is still -1) it means we didn't find the word
        if (beginIndex == -1)
            return new int[2];

        // If we are looking for a wholeWord, check if the we found the index after the word has a letter or
        // not. If so, it means what we found wasn't the a whole word and we need to check again
        if (beginIndex + searchText.length() < text.length() && wholeWord && Character.isAlphabetic(text.charAt(beginIndex + searchText.length())))
            return findInText(beginIndex + (forward ? 1 : -1), text, searchText, matchCase, wholeWord, forward);

        return indexes;
    }
}
