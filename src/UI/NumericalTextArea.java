package UI;

import javax.swing.JTextArea;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dimension;

/**
 * Extends <code>JTextArea</code> but can only have numerical value.
 * <p>
 * This class checks every change the user (or program) makes to the text of the
 * <code>JTextArea</code> and stops any changes that are non-numerical.
 * <p>
 * The point of this class is have a way to only take numerical input without
 * needing to use complex classes like <code>JFormattedTextArea</code>
 * 
 * @see JTextArea
 */
public class NumericalTextArea extends JTextArea {
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

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char keyChar = e.getKeyChar();
                boolean backSpace = keyChar == KeyEvent.VK_BACK_SPACE;

                if (!String.valueOf(keyChar).matches(regex) && !backSpace)
                    return;

                if (getSelectionStart() != getSelectionEnd())
                    replaceRange("", getSelectionStart(), getSelectionEnd());

                if (!backSpace) {
                    if (checkChange(String.valueOf(e.getKeyChar())))
                        setText(getText() + e.getKeyChar());
                    return;
                }

                try {
                    setText(getText(0, getCaretPosition() - 1));
                } catch (javax.swing.text.BadLocationException ignored) {
                }
            }
        });

        setText(text);
        super.setPreferredSize(size);
    }

    /**
     * @param change A String containing the change that happened to the text.
     * @return <code>true</code> if the change keeps the text numerical.
     */
    private boolean checkChange(String change) {
        if (getText().length() == 0)
            return true;

        boolean hasDot = getText().charAt(0) == '.';

        for (int i = 1; i < getText().length(); i++) {
            if (getText().charAt(i) == '.') {
                if (hasDot)
                    return false;

                hasDot = true;
            }

            if (getText().charAt(i) == '-')
                return false;
        }

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
