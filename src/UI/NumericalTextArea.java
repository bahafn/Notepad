package UI;

import java.awt.event.KeyEvent;
import java.awt.Dimension;

/**
 * Extends <code>JTextArea</code> but can only have numerical value.
 * <p>
 * This class checks every change the user (or program) makes to the text of the
 * <code>JTextArea</code> and stops any changes that are non-numerical.
 * <p>
 * The point of this class is have a way to only take numerical input without
 * needing to use complex classes like <code>JFormattedTextArea</code>.
 * 
 * @see JTextArea
 */
public class NumericalTextArea extends javax.swing.JTextArea {
    /** Saves the the character that are allowed to be used. */
    private String regex;

    /**
     * Constructs a <code>NumericalTextArea</code> and sets the text, size, and
     * regex (allowed characters) of it.
     * 
     * @param text     the text to be set
     * @param size     the size of the component
     * @param integer  weather to take double values or not
     * @param positive weather to take neagtive values or not
     */
    public NumericalTextArea(String text, Dimension size, boolean integer, boolean positive) {
        setEditable(false);

        // Set the regex (allowed characters)
        if (integer)
            regex = positive ? "^[1234567890]+$" : "^[-1234567890]+$";
        else
            regex = positive ? "^[1234567890.]+$" : "^[-1234567890.]+$";

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleInput(e.getKeyChar());
            }
        });

        setText(text);
        super.setPreferredSize(size);
    }

    private void handleInput(char keyChar) {
        boolean backSpace = keyChar == KeyEvent.VK_BACK_SPACE;

        if (!String.valueOf(keyChar).matches(regex) && !backSpace)
            return;

        // If there are selected text, remove it
        if (getSelectionStart() != getSelectionEnd())
            replaceRange("", getSelectionStart(), getSelectionEnd());

        // If the character we entered wasn't a backspace, we check if the change keeps
        // the value numerical and add the change if it does
        if (!backSpace) {
            if (checkChange(String.valueOf(keyChar)))
                super.setText(getText() + keyChar);
            return; // Return so we don't get to the backspace case below
        }

        // If backspace was pressed, remove the character before the caret
        try {
            super.setText(getText(0, getCaretPosition() - 1));
        } catch (javax.swing.text.BadLocationException ignored) {
        }
    }

    /**
     * @param change A String containing the change that happened to the text.
     * @return <code>true</code> if the change keeps the text numerical.
     */
    private boolean checkChange(String change) {
        if (getText().length() == 0)
            return true;

        // Check if the change puts a minus sign anywhere but at the start
        if (change.contains("-") && getCaretPosition() != 0)
            return false;

        // Check if the text has more than one dot sign
        boolean hasDot = getText().charAt(0) == '.' || change.contains(".");

        for (int i = 1; i < getText().length(); i++) {
            if (getText().charAt(i) == '.') {
                if (hasDot)
                    return false;

                hasDot = true;
            }
        }

        // If none of the other returns were made, we just need to check if the change
        // has any characters that are not in the regex
        return change.matches(regex);
    }

    /**
     * Chagnes the text of the <code>JTextArea</code> if
     * <code>checkChagne(text)</code> returns true, throws an
     * IlleagalArgumentException otherwise.
     * <p>
     * Overrides the <code>JTextArea.setText(String)</code> method so you can't set
     * the text before checking if it is numerical.
     * 
     * @param text the text to be set.
     */
    @Override
    public void setText(String text) {
        if (!checkChange(text) && !text.equals(""))
            throw new IllegalArgumentException("NumericalTextArea can't have non-numerical string.");

        super.setText(text);
    }

    /** @return the integer value of the text */
    public int getIntValue() {
        try {
            return Integer.parseInt(getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /** @return the double value of the text */
    public double getDoubleValue() {
        try {
            return Double.parseDouble(getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
