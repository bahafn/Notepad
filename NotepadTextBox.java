import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JTextArea;

public class NotepadTextBox extends JTextArea {
    private boolean statusBar;

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

        g.drawString("Ln: " + getText().substring(0, getSelectionEnd()).split("\n").length + ", Col: " + getSelectionStart(), 10, getHeight() - 5);
        repaint();
    }

    public void changeStatusBar() {
        statusBar = !statusBar;
    }
}
