package UI;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JTextArea;

/** Has the same functionality as JTextArea but can draw the status bar. */
public class NotepadTextBox extends JTextArea {
    private boolean statusBar;
    private int ln, col; // These are used to sure we need a repaint

    public NotepadTextBox(boolean statusBar, Dimension size, String text, boolean lineWrap) {
        this(statusBar, text, lineWrap);
        setPreferredSize(size);
    }

    public NotepadTextBox(boolean statusBar, String text, boolean lineWrap) {
        setText(text);
        setLineWrap(lineWrap);

        this.statusBar = statusBar;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!statusBar)
            return;

        // Get array of every line before the selected line
        String[] lines = getText().substring(0, getSelectionEnd()).split("\r\n|\n|\r", -1);

        // Get the index of the selected character (this index is for all lines not just the selected one)
        int col = getSelectionEnd() + 1;

        // Remove the characters of all the other lines from the index of the selected character
        for (int i = 0; i < lines.length - 1; i++)
            col -= lines[i].length() + 1;

        int ln = lines.length; // The length of the array is the line we are on

        g.drawString("Ln: " + ln + ", Col: " + col, 10, getHeight() - 10); // Draw status bar
        
        // Check if anything changed to do a repaint (this saves memory)
        if (this.ln != ln || this.col != col) {
            this.ln = ln;
            this.col = col;
            repaint();
        }
    }

    public void changeStatusBar() {
        statusBar = !statusBar;
    }
}