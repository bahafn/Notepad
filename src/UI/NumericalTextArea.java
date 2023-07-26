package UI;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Graphics;
import java.awt.Dimension;

public class NumericalTextArea extends JTextArea {
    private String regex;
    private String removeString = null;

    public NumericalTextArea(String text, Dimension size, boolean integer, boolean positive) {
        if (integer)
            regex = positive ? "^[1234567890]+$" : "^[-1234567890]+$";
        else
            regex = positive ? "^[1234567890.]+$" : "^[-1234567890.]+$";

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

    private boolean checkChange(String change) {
        return change.matches(regex);
    }

    public void setText(String text) {
        if (!checkChange(text))
            throw new IllegalArgumentException("NumericalTextArea can't have non-numerical string.");

        super.setText(text);
    }

    public void paintComponent(Graphics g) {
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
