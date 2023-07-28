package UI;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Graphics;
import java.awt.Dimension;

/**
 * Extends <code>JTextArea</code> but can only have numerical value.
 * <p>
 * This class checks every change the user (or program) makes to the text of the
 * <code>JTextArea</code> and removes any changes that are non-numerical.
 * <p>
 * The point of this class is have a way to only take numerical input without
 * needing to use complex classes like <code>JFormattedTextArea</code>
 * @see JTextArea
 * @apiNote Even though this extends JTextArea, it takes more space and time to make
 * operations. Make sure this class fits your needs before using it.
 */
public class NumericalTextArea extends JTextArea {
    private String regex; // Saves the the character that are allowed to be used
    private int[] removeStringIndex = null; // The string to remove in the next call to paintComponent

    /**
     * Constructs a <code>NumericalTextArea</code> and sets the text, size, and regex (allowed characters) 
     * of it.
     * @param text the text to be set
     * @param size the size of the component
     * @param integer weather to take double values or not
     * @param positive weather to take neagtive values or not
     */
    public NumericalTextArea(String text, Dimension size, boolean integer, boolean positive) {
        // Set the regex (allowed characters)
        if (integer)
            regex = positive ? "^[1234567890]+$" : "^[-1234567890]+$";
        else
            regex = positive ? "^[1234567890.]+$" : "^[-1234567890.]+$";

        // Add document listener to keep track of all changes that happen to text
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) { handleUserChange(e.getOffset(), e.getLength()); }
            @Override
            public void insertUpdate(DocumentEvent e) { handleUserChange(e.getOffset(), e.getLength()); }
            @Override
            public void removeUpdate(DocumentEvent e) {}
        });

        setText(text);
        super.setPreferredSize(size);
    }

    private void handleUserChange(int changeIndex, int changeLength) {
        if (!checkChange(getText().substring(changeIndex, changeIndex + changeLength)))
            removeStringIndex = new int[] {changeIndex, changeIndex + changeLength};
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

        return change.matches(regex.toString());
    }

    /**
     * Chagnes the text of the <code>JTextArea</code> if <code>checkChagne(text)</code> returns true,
     * throws an IlleagalArgumentException otherwise.
     * <p>
     * Overrides the <code>JTextArea.setText(String)</code> method so you can't set the text
     * before checking if it is numerical.
     * @param text the text to be set.
     */
    @Override
    public void setText(String text) {
        if (!checkChange(text))
            throw new IllegalArgumentException("NumericalTextArea can't have non-numerical string.");

        super.setText(text);
    }

    public void paintComponent(Graphics g) {
        // Remove any unwanted string (string that are in removeString)
        if (removeStringIndex != null) {
            replaceRange(null, removeStringIndex[0], removeStringIndex[1]);
            removeStringIndex = null;
        }

        super.paintComponent(g);
    }

    /** @return the integer value of the text */
    public int getIntValue() {
        return (int)getDoubleValue();
    }

    /** @return the double value of the text */
    public double getDoubleValue() {
        return Double.parseDouble(getText());
    }
}
