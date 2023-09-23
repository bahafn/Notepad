package WindowClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JTextArea;
import javax.swing.JPanel;

import UI.UICreator;

/**
 * This class creates the find window UI and selects and replaces the words the
 * user is looking for.
 * <p>
 * This extends <code>JFrame</code> so it the UI is added directly to it.
 * 
 * @see MemorySafeWindow
 */
public class FindWindow extends MemorySafeWindow {
    /** The app object that created this object */
    private App app;

    /**
     * The index that the word was found on.
     * <p>
     * Used so we can know where to start searching from when searching for previous
     * and next.
     */
    private int selectedIndex = 0;

    /** Options variable */
    private boolean wholeWord = false, matchCase = true, replaceUI = false;

    private JTextArea searchText;
    private JPanel replacePanel;

    /** Creates a new <code>FindWindow</code>. */
    public FindWindow(App app, boolean replaceUI) {
        this.app = app;
        this.replaceUI = replaceUI;

        showGUI();
        UICreator.initJFrame(this, false, true, false, true, false, getParent());
    }

    private void showGUI() {
        // Create border
        getRootPane().setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK, 1, true));

        // Create find options UI
        JPanel findPanel = new JPanel();

        // Create "show replace options button"
        findPanel.add(UICreator.createJButton("Replace", e -> showReplaceOptions(), UICreator.DEFAULT_SIZE,
                UICreator.DEFAULT_INSETS));

        // Create search textArea
        searchText = UICreator.createJTextArea("", UICreator.DEFAULT_TEXT_SIZE, false);
        findPanel.add(searchText);

        // Create search button
        findPanel.add(UICreator.createJButton("Find", e -> findAndSelect(0, searchText.getText(), true),
                UICreator.DEFAULT_SIZE, UICreator.DEFAULT_INSETS));

        // Create next and previous buttons
        findPanel.add(UICreator.createJButton("<", e -> findAndSelect(selectedIndex - 1, searchText.getText(), false),
                UICreator.SQUARE_SIZE, UICreator.NO_INSETS));
        findPanel.add(UICreator.createJButton(">", e -> findAndSelect(selectedIndex + 1, searchText.getText(), true),
                UICreator.SQUARE_SIZE, UICreator.NO_INSETS));

        // This is used so the check boxes are on top of each other
        JPanel checkBoxPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        checkBoxPanel.setPreferredSize(new Dimension(100, 30));

        // Create option check boxes
        checkBoxPanel.add(UICreator.createJCheckBox("Whole word", false, e -> {
            wholeWord = !wholeWord;
        }));
        checkBoxPanel.add(UICreator.createJCheckBox("Match case", true, e -> {
            matchCase = !matchCase;
        }));

        findPanel.add(checkBoxPanel);

        // Create exist button
        findPanel.add(UICreator.createJButton("X", e -> dispose(), UICreator.SQUARE_SIZE, UICreator.NO_INSETS));

        add(findPanel, BorderLayout.NORTH);

        // Create replace options UI
        replacePanel = new JPanel();

        JTextArea replaceText = UICreator.createJTextArea("", UICreator.DEFAULT_TEXT_SIZE, false);
        replacePanel.add(replaceText);

        // Create replace and replace all buttons
        replacePanel.add(UICreator.createJButton("Replace", e -> replace(replaceText.getText()), UICreator.DEFAULT_SIZE,
                UICreator.DEFAULT_INSETS));
        replacePanel.add(
                UICreator.createJButton("Replace all", e -> replaceAll(searchText.getText(), replaceText.getText()),
                        new Dimension(115, 25), UICreator.DEFAULT_INSETS));

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

    private void replace(String newText) {
        if (!app.replace(newText))
            findAndSelect(selectedIndex, searchText.getText(), true);
        else
            selectedIndex = 0;
    }

    /** replaces all occurnes of a word */
    private void replaceAll(String oldText, String newText) {
        app.setReplacing(true);

        int beginIndex = 0;

        while (true) {
            int[] indexes = findInText(beginIndex, app.getText(), oldText, matchCase, wholeWord, true);

            if (indexes[0] != -1) {
                app.replace(newText, indexes[0], indexes[1]);
                beginIndex = indexes[1];
            } else
                break;
        }

        app.setReplacing(false);
    }

    /**
     * @return the begin index of the word we are looking for which is used by the
     *         replace all method
     * @param startIndex the index to start searching from
     * @param searchText the text we are searching for
     * @param forward    weather we are searching after or before the startIndex
     */
    private void findAndSelect(int startIndex, String searchText, boolean forward) {
        int[] indexes = findInText(startIndex, app.getText(), searchText, matchCase, wholeWord, forward);

        if (indexes[0] != -1) {
            app.selectText(indexes[0], indexes[1]);
            selectedIndex = indexes[0];
        }
    }

    /**
     * @return an array with two integers, one for the begin index, the other for
     *         the end index
     * @param startIndex the index to start searching from
     * @param text       the text we are searching in
     * @param searchText the text are searching for
     * @param matchCase  weather to care about case when searching
     * @param wholeWord  weather the search text is part of a word or an entire word
     * @param forward    weather to search after or before the begin index
     */
    public static int[] findInText(int startIndex, String text, String searchText, boolean matchCase, boolean wholeWord,
            boolean forward) {
        int[] indexes = new int[2]; // The array that will be returned at the end
        boolean repeat = false; // This is used to repeat the do while loop if we are looking for a whole word
                                // and didn't find it

        // Do while loop used to make sure we found a whole word if we are searching for
        // one
        do {
            repeat = false; // Set it to false so we don't enter an endless loop

            // Make sure the startIndex isn't outOfBounds
            // Used mostly because next and previous buttons can send values bigger
            // than text.length() or smaller than zero
            if (startIndex >= text.length() || startIndex < 0)
                startIndex = 0;

            // Make everything lower case if we don't care about case
            if (!matchCase) {
                text = text.toLowerCase();
                searchText = searchText.toLowerCase();
            }

            // If we are going forward, find the first occurnce of searchText in text
            // (strating from startIndex)
            if (forward) {
                indexes[0] = text.indexOf(searchText, startIndex);
                indexes[1] = indexes[0] + searchText.length();
            }
            // Otherwise, find the last occurnce in the text, start from the first index to
            // the startIndex
            else {
                indexes[0] = text.lastIndexOf(searchText, startIndex);
                indexes[1] = indexes[0] + searchText.length();
            }

            if (wholeWord && indexes[1] < text.length()
                    && (Character.isAlphabetic(text.charAt(indexes[1])) || Character.isAlphabetic(indexes[0] - 1))) {
                repeat = true;
                startIndex = indexes[0] + (forward ? 1 : -1);

                if (startIndex >= text.length() || startIndex < 0)
                    break;
            }
        } while (repeat);

        return indexes;
    }

    @Override
    public void dispose() {
        app.setFindWindow(false);
        super.dispose();
    }
}