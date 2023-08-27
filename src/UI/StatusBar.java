package UI;

import javax.swing.JTextArea;

/**
 * Takes a <code>JTextArea</code> and creates a JLabel that shows the position
 * of the pointer on it.
 */
public class StatusBar extends javax.swing.JLabel {
    private JTextArea textArea;
    private boolean replacing;

    public StatusBar(JTextArea textArea, java.awt.Dimension size, java.awt.Font font) {
        this.textArea = textArea;
        
        setPreferredSize(size);
        setFont(font);
        setText("Ln: 1, Col: 1");

        // Add caret (pointer) change listener to textArea so we can update the status bar
        textArea.getCaret().addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                updateStatusBar();
            }
        });
    }

    /** Calculates the index of the selected line and colume. */
    private void updateStatusBar() {
        // This is used because the JTextArea.replaceSelected and JTextArea.replaceRange
        // methods select the text they are replacing, and we don't need to be updating
        // the status bar while replaing all occurnces of word because the user wouldn't
        // see it
        if (replacing)
            return;

        int ln = 1, col;

        try {
            // Calculate the line number from the selected index.
            // The textArea.getLineEndOffset method gets us the index at which the line ends
            // and while that index is lower than the selected index, it means that we are
            // on a lower line
            while (ln < textArea.getLineCount() && textArea.getLineEndOffset(ln - 1) - 1 < textArea.getSelectionEnd())
                ln++;

            // The selected colume's index is the index of the selected character minus
            // the index of the index of the start of the selected line
            col = textArea.getSelectionEnd() - textArea.getLineStartOffset(ln - 1) + 1;

            setText("Ln: " + ln + ", Col: " + col);
        } catch (javax.swing.text.BadLocationException ble) {
            UICreator.showErrorMessage(this, "This error isn't supposed to happen", "You win.", 0);
        }
    }

    public void setReplacing(boolean replacing) {
        this.replacing = replacing;
    }
}
