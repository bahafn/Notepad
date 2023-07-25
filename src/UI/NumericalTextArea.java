package UI;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Graphics;

public class NumericalTextArea extends JTextArea {
    private String regex;
    private String removeString = null;

    public NumericalTextArea(String text, boolean integer) {
        if (integer)
            regex = "[123456789]";
        else
            regex = "[123456789.]";

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) { handleUserChange(e.getOffset(), e.getLength()); }
            @Override
            public void insertUpdate(DocumentEvent e) { handleUserChange(e.getOffset(), e.getLength()); }
            @Override
            public void removeUpdate(DocumentEvent e) {}
        });

        setText(text);
    }

    private void handleUserChange(int changeIndex, int changeLength) {
        String change = getText().substring(changeIndex, changeIndex + changeLength);
        if (!checkChange(change))
            removeString = change;
    }

    private boolean checkChange(String change) {
        if (change.matches(regex))
            return true;
        else
            return false;
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
}
