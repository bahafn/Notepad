package WindowClasses;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.undo.UndoManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;
import java.util.ArrayList;

import UI.Tap;
import UI.UICreator;

/**
 * This class is the main window of the program.
 * <p>
 * It creates the UI for the text area and menu bar and handles many operations
 * like opening other windows (<code>FindWindow</code>, <code>GoToWindow</code>),
 * undo and redo, and changing taps.
 */
public class App extends JFrame {
    private ArrayList<Tap> taps = new ArrayList<>();
    private int activeTap = 0;
    private JPanel tapsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    private JTextArea textArea = UICreator.createJTextArea("", true);
    private JLabel statusBar = UICreator.creatJLabel("Ln: 0, Col: 0", UICreator.DEFAULT_SIZE, UICreator.DEFAULT_FONT, 14);
    private final float DEFAULT_ZOOM = textArea.getFont().getSize();
    private boolean replacing = false;

    private UndoManager undoManager = new UndoManager();

    public App() {
        UICreator.setLookAndFeel(UICreator.SYSTEM_LOOK_AND_FEEL);
        showGUI();
        UICreator.initJFrame(this, true, false, true, true, null);

        textArea.getCaret().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) { updateStatusBar(); };
        });
    }

    private void showGUI() {
        // Add JTextArea and status bar
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(UICreator.createJScrollPane(new Dimension(1000, 600), textArea), BorderLayout.NORTH);
        southPanel.add(statusBar, BorderLayout.CENTER);
        southPanel.setBackground(Color.WHITE);
        add(southPanel, BorderLayout.SOUTH);

        // Add undo manger to textArea
        textArea.getDocument().addUndoableEditListener(undoManager);

        newTap(); // Create first tap

        // Create menuBar items
        JMenu[] menus = new JMenu[3];

        // Create File menu
        menus[0] = UICreator.createJMenu("File", new JMenuItem[] {
            UICreator.createJMenuItem("New tap", e -> newTap()),
            UICreator.createJMenuItem("New window", e -> newWindow()),
            UICreator.createJMenuItem("Open", e -> open()),
            UICreator.createJMenuItem("Save", e -> taps.get(activeTap).save()),
            UICreator.createJMenuItem("Save as", e -> taps.get(activeTap).save()),
            UICreator.createJMenuItem("Save all", e -> saveAll())
        });

        // Create edit menu
        menus[1] = UICreator.createJMenu("Edit", new JMenuItem[] {
            UICreator.createJMenuItem("Undo", e -> undo()),
            UICreator.createJMenuItem("Redo", e -> redo()),
            UICreator.createJMenuItem("Find", e -> { new FindWindow(this, false); }),
            UICreator.createJMenuItem("Replace", e -> { new FindWindow(this, true); }),
            UICreator.createJMenuItem("Go to", e -> { new GoToWindow(this); }),
            UICreator.createJMenuItem("Font", e -> newFontWindow())
        });

        // Create view menu items
        menus[2] = UICreator.createJMenu("View", new JMenuItem[] {
            UICreator.createJMenu("Zoom", new JMenuItem[] {
                UICreator.createJMenuItem("Zoom in", e -> { textArea.setFont(textArea.getFont().deriveFont(textArea.getFont().getSize() * 1.3f)); }),
                UICreator.createJMenuItem("Zoom out", e -> { textArea.setFont(textArea.getFont().deriveFont(textArea.getFont().getSize() / 1.3f)); }),
                UICreator.createJMenuItem("Reset zoom", e -> { textArea.setFont(textArea.getFont().deriveFont(DEFAULT_ZOOM)); })
            }),
            UICreator.createJCheckBoxMenuItem("Status bar", true, e -> {  }),
            UICreator.createJCheckBoxMenuItem("Word wrap", true, e -> textArea.setLineWrap(!textArea.getLineWrap()))
        });

        JMenuBar menuBar = UICreator.createJMenuBar(menus); // Create menu bar

        add(menuBar, BorderLayout.NORTH);

        add(tapsPanel);
    }

    /** Updates <code>taps</code> <code>ArrayList</code> (removing them, adding them, or renaming them). */
    public void updateTapsPanel() {
        tapsPanel.removeAll();

        for (int i = 0; i < taps.size(); i++) {
            final int index = i; // Used beccause non-final values can't be used in lambda
            tapsPanel.add(UICreator.createJButton(taps.get(i).getName(), e -> changeTap(index), UICreator.DEFAULT_SIZE, UICreator.NO_INSETS));
        }

        tapsPanel.revalidate();
        tapsPanel.repaint();
    }

    /** Selects the texts between two indexes from the <code>textArea</code>. */
    public void selectText(int beginIndex, int endIndex) {
        textArea.requestFocus();
        textArea.select(beginIndex, endIndex);
    }

    /**
     * Replaces the text between the two indexes.
     * <p>
     * Used by <code>FindWindow</code>.
    */
    public void replace(String newText, int beginIndex, int endIndex) {
        textArea.replaceRange(newText, beginIndex, endIndex);
    }

    /**
     * Replaces the selected text.
     * <p>
     * Used by <code>FindWindow</code>.
     * @return <code>true</code> if some text was replaced
    */    
    public boolean replace(String newText) {
        if (textArea.getSelectionEnd() == textArea.getSelectionStart())
            return false;

        textArea.replaceSelection(newText);
        return true;
    }

    /** @return the text of the <code>textArea</code>. */
    public String getText() {
        return textArea.getText();
    }

    /** Creates a new <code>Tap</code> and adds it to <code>taps</code>. */
    private void newTap() {
        taps.add(new Tap());
        changeTap(taps.size() - 1);

        updateTapsPanel();
    }

    /** Creates new <code>App</code>. */
    private void newWindow() {
        new App();
    }

    private void open() {
        taps.get(activeTap).open();
        updateTapsPanel();
    }

    private void saveAll() {
        // TODO: write function
    }

    private void undo() {
        if (undoManager.canUndo())
            undoManager.undo();
    }

    private void redo() {
        if (undoManager.canRedo())
            undoManager.redo();
    }

    private void newFontWindow() {
        // TODO: write function
    }

    /** Changes the active tap and updates the <code>textArea</code>'s text. */
    private void changeTap(int newTap) {
        // Update text in tap object
        taps.get(activeTap).setText(textArea.getText());

        // Change active tap
        activeTap = newTap;
        textArea.setText(taps.get(activeTap).getText());
    }

    private void updateStatusBar() {
        if (replacing)
            return;

        // Get array of every line before the selected line
        String[] lines = getText().substring(0, textArea.getSelectionEnd()).split("\r\n|\n|\r", -1);

        // Get the index of the selected character (this index is for all lines not just
        // the selected one)
        int col = textArea.getSelectionEnd() + 1;

        // Remove the characters of all the other lines from the index of the selected
        // character
        for (int i = 0; i < lines.length - 1; i++)
            col -= lines[i].length() + 1;

        int ln = lines.length; // The length of the array is the line we are on

        statusBar.setText("Ln: " + ln + ", Col: " + col);
    }

    public void setReplacing(boolean replacing) {
        this.replacing = replacing;
    }

    public static void main(String[] args) {
        new App();
    }
}