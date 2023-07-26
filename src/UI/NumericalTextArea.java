package UI;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Graphics;
import java.awt.Dimension;

public class NumericalTextArea extends JTextArea {
    private String regex; // Saves the the character that are allowed to be used
    private String removeString = null; // The string to remove in the next call to paintComponent

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
        String change = getText().substring(changeIndex, changeIndex + changeLength);
        if (!checkChange(change))
            removeString = change;
    }

    // Return true if the keeps the text numerical
    // TODO: Add all contions to this function like not begin able to put two . or -
    private boolean checkChange(String change) {
        return change.matches(regex);
    }

    // Overrides JTextArea's setText method so you can't use it for non-numerical values
    public void setText(String text) {
        if (!checkChange(text))
            throw new IllegalArgumentException("NumericalTextArea can't have non-numerical string.");

        super.setText(text);
    }

    public void paintComponent(Graphics g) {
        // Remove any unwanted string (string that are in removeString)
        if (removeString != null) {
            super.setText(getText().substring(0, getText().indexOf(removeString)));
            removeString = null;
        }

        super.paintComponent(g);
    }

    public int getIntValue() {
        return (int)getDoubleValue();
    }

    public double getDoubleValue() {
        return Double.parseDouble(getText());
    }
}
