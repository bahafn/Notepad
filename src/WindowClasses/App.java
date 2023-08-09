package WindowClasses;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import UI.Tap;
import UI.UICreator;
import UI.TapButton;

/**
 * This class is the main window of the program.
 * <p>
 * It creates the UI for the text area and menu bar and handles many operations
 * like opening other windows (<code>FindWindow</code>,
 * <code>GoToWindow</code>), undo and redo, and changing taps.
 * 
 * @see MemorySafeWindow
 */
public class App extends MemorySafeWindow {
    private ArrayList<TapButton> tapButtons = new ArrayList<>();
    private int activeTap = 0;
    private JPanel tapsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    private JTextArea textArea = UICreator.createJTextArea("", true);
    private JLabel statusBar = UICreator.createJLabel("Ln: 1, Col: 1", UICreator.DEFAULT_SIZE, UICreator.DEFAULT_FONT,
            14);
    private final float DEFAULT_ZOOM = textArea.getFont().getSize();
    private boolean replacing = false;

    /**
     * <code>boolean</code> used to make sure we don't open two of the same window.
     */
    private boolean findWindow, goToWindow, fontWindow;

    private UndoManager undoManager = new UndoManager();

    public App(boolean mainFrame) {
        super("Notepad");

        UICreator.setLookAndFeel(UICreator.SYSTEM_LOOK_AND_FEEL);
        showGUI();
        UICreator.initJFrame(this, true, false, mainFrame, true, null);

        textArea.getCaret().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateStatusBar();
            }
        });
    }

    private void showGUI() {
        // Add JTextArea and status bar
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(UICreator.createJScrollPane(new Dimension(1000, 600), textArea), BorderLayout.NORTH);
        southPanel.add(statusBar, BorderLayout.CENTER);
        southPanel.setBackground(textArea.getBackground());
        add(southPanel, BorderLayout.SOUTH);

        // Add undo manger to textArea
        textArea.getDocument().addUndoableEditListener(undoManager);

        newTap(); // Create first tap

        // Create menuBar items
        JMenu[] menus = new JMenu[3];

        // Create File menu
        menus[0] = UICreator.createJMenu("File", new JComponent[] {
                UICreator.createJMenuItem("New tap", e -> newTap()),
                UICreator.createJMenuItem("New window", e -> newWindow()),
                UICreator.createJSeparator(),
                UICreator.createJMenuItem("Open", e -> open()),
                UICreator.createJMenuItem("Save", e -> tapButtons.get(activeTap).getTap().save()),
                UICreator.createJMenuItem("Save as", e -> tapButtons.get(activeTap).getTap().save()),
                UICreator.createJMenuItem("Save all", e -> saveAll())
        });

        // Create edit menu
        menus[1] = UICreator.createJMenu("Edit", new JComponent[] {
                UICreator.createJMenuItem("Undo", e -> undo()),
                UICreator.createJMenuItem("Redo", e -> redo()),
                UICreator.createJSeparator(),
                UICreator.createJMenuItem("Find", e -> newFindWindow(false)),
                UICreator.createJMenuItem("Replace", e -> newFindWindow(true)),
                UICreator.createJMenuItem("Go to", e -> newGoToWindow()),
                UICreator.createJSeparator(),
                UICreator.createJMenuItem("Font", e -> newFontWindow())
        });

        // Create view menu items
        menus[2] = UICreator.createJMenu("View", new JComponent[] {
                UICreator.createJMenu("Zoom", new JComponent[] {
                        UICreator.createJMenuItem("Zoom in", e -> {
                            textArea.setFont(textArea.getFont().deriveFont(textArea.getFont().getSize() * 1.3f));
                        }),
                        UICreator.createJMenuItem("Zoom out", e -> {
                            textArea.setFont(textArea.getFont().deriveFont(textArea.getFont().getSize() / 1.3f));
                        }),
                        UICreator.createJMenuItem("Reset zoom", e -> {
                            textArea.setFont(textArea.getFont().deriveFont(DEFAULT_ZOOM));
                        })
                }),
                UICreator.createJCheckBoxMenuItem("Status bar", true, e -> {
                    statusBar.setVisible(!statusBar.isVisible());
                }),
                UICreator.createJCheckBoxMenuItem("Word wrap", true, e -> textArea.setLineWrap(!textArea.getLineWrap()))
        });

        JMenuBar menuBar = UICreator.createJMenuBar(menus); // Create menu bar

        add(menuBar, BorderLayout.NORTH);

        add(tapsPanel);
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
     * 
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

    public Font getFont() {
        return textArea.getFont();
    }

    public void setFont(Font font) {
        textArea.setFont(font);
    }

    /**
     * Updates <code>taps</code> <code>ArrayList</code> (removing them, adding them,
     * or renaming them).
     */
    public void updateTapsPanel() {
        tapsPanel.revalidate();
        tapsPanel.repaint();
    }

    /** Creates a new <code>Tap</code> and adds it to <code>taps</code>. */
    private void newTap() {
        TapButton tapButton = new TapButton(this, new Tap(), tapButtons.size(), "Untitled", UICreator.DEFAULT_SIZE,
                UICreator.DEFAULT_INSETS);
        tapButtons.add(tapButton);
        tapsPanel.add(tapButton);
        changeTap(tapButtons.size() - 1);

        updateTapsPanel();
    }

    /** @return the index of the active tap */
    public int getActiveTap() {
        return activeTap;
    }

    /** Changes the active tap and updates the <code>textArea</code>'s text. */
    public void changeTap(int newTap) {
        // Update text in tap object
        Tap currentTap = tapButtons.get(activeTap).getTap();
        currentTap.setText(textArea.getText());
        currentTap.setFont(textArea.getFont());

        // Change active tap
        activeTap = newTap;
        currentTap = tapButtons.get(activeTap).getTap();
        textArea.setText(currentTap.getText());
        textArea.setFont(currentTap.getFont());
    }

    public ArrayList<TapButton> getTapButtons() {
        return tapButtons;
    }

    /** Creates new <code>App</code>. */
    private void newWindow() {
        new App(false);
        setDefaultCloseOperation(MemorySafeWindow.DISPOSE_ON_CLOSE);
    }

    private void open() {
        tapButtons.get(activeTap).getTap().open();
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

    private void newFindWindow(boolean replace) {
        if (findWindow)
            return;

        findWindow = true;
        new FindWindow(this, replace);
    }

    private void newFontWindow() {
        if (fontWindow)
            return;

        fontWindow = true;
        new FontWindow(this);
    }

    private void newGoToWindow() {
        if (goToWindow)
            return;

        goToWindow = true;
        new GoToWindow(this);
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

            statusBar.setText("Ln: " + ln + ", Col: " + col);
        } catch (BadLocationException ble) {
            UICreator.showErrorMessage(this, "This error isn't supposed to happen", "You win.", 0);
        }
    }

    /**
     * This is used so we can stop calculating the selected line and colume when the
     * <code>FindWindow.replaceAll()</code> is called.
     */
    public void setReplacing(boolean replacing) {
        this.replacing = replacing;
    }

    public void setFindWindow(boolean findWindow) {
        this.findWindow = findWindow;
    }

    public void setGoToWindow(boolean goToWindow) {
        this.goToWindow = goToWindow;
    }

    public void setFontWindow(boolean fontWindow) {
        this.fontWindow = fontWindow;
    }

    public static void main(String[] args) {
        new App(true);
    }
}