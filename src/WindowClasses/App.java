package WindowClasses;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.undo.UndoManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import UI.StatusBar;
import UI.Tap;
import UI.UICreator;
import UI.TapButton;

import Saving.Save;

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
    private ArrayList<Tap> taps = new ArrayList<>();
    private ArrayList<TapButton> tapButtons = new ArrayList<>();
    private JPanel tapsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    /**
     * The index of the <code>Tap</code> the user is on.
     * <p>
     * NOTE: Don't change this value if you want to change the tap, use the
     * changeTap method
     */
    private int activeTap = 0;

    private javax.swing.JTextArea textArea = UICreator.createJTextArea("", true);
    private StatusBar statusBar = new StatusBar(textArea, UICreator.DEFAULT_SIZE, UICreator.DEFAULT_FONT);

    private float zoom = 1;
    /** The size of the textArea's font without zoom */
    private float defaultFontSize = textArea.getFont().getSize();

    /**
     * <code>boolean</code> used to make sure we don't open two of the same window.
     */
    private boolean findWindow, goToWindow, fontWindow;

    private UndoManager undoManager = new UndoManager();

    private static int NumberOfWindows = 0;

    public App() {
        super("Notepad");

        UICreator.setLookAndFeel(UICreator.SYSTEM_LOOK_AND_FEEL);
        showGUI();
        UICreator.initJFrame(this, true, false, false, true, true, null);

        NumberOfWindows++;
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
                UICreator.createJMenuItem("New window", e -> new App()),
                UICreator.createJSeparator(),
                UICreator.createJMenuItem("Open", e -> open()),
                UICreator.createJMenuItem("Save", e -> save(false, activeTap)),
                UICreator.createJMenuItem("Save as", e -> save(true, activeTap)),
                UICreator.createJMenuItem("Save plain text", e -> savePlainText(activeTap)),
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
                            zoom += 0.5;
                            textArea.setFont(textArea.getFont().deriveFont(defaultFontSize * zoom));
                        }),
                        UICreator.createJMenuItem("Zoom out", e -> {
                            zoom -= 0.5;
                            textArea.setFont(textArea.getFont().deriveFont(defaultFontSize / zoom));
                        }),
                        UICreator.createJMenuItem("Reset zoom", e -> {
                            zoom = 1;
                            textArea.setFont(textArea.getFont().deriveFont(defaultFontSize));
                        })
                }),
                UICreator.createJCheckBoxMenuItem("Status bar", true, e -> {
                    statusBar.setVisible(!statusBar.isVisible());
                }),
                UICreator.createJCheckBoxMenuItem("Word wrap", true, e -> textArea.setLineWrap(!textArea.getLineWrap()))
        });

        javax.swing.JMenuBar menuBar = UICreator.createJMenuBar(menus); // Create menu bar

        add(menuBar, BorderLayout.NORTH);

        add(tapsPanel);
    }

    /** Selects the texts between two indexes from the <code>textArea</code>. */
    public void selectText(int beginIndex, int endIndex) {
        textArea.requestFocus();
        textArea.select(beginIndex, endIndex);
    }

    /** Replaces the text between the two indexes */
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

    /** @return the font of the <code>textArea</code> */
    public Font getFont() {
        return textArea.getFont();
    }

    /** Changes the font of the <code>textArea</code> */
    public void setFont(Font font) {
        textArea.setFont(font.deriveFont(font.getSize() * zoom));
        defaultFontSize = font.getSize();
    }

    /** Updates tapsPanel so it repaints correctly. */
    public void updateTapsPanel() {
        System.out.println(true);
        tapsPanel.revalidate();
        tapsPanel.repaint();
    }

    /** Creates a new <code>Tap</code> and adds it to <code>taps</code>. */
    private void newTap() {
        Tap tap = new Tap("Untitled " + (taps.size() + 1), this, taps.size(), UICreator.DEFAULT_SIZE,
                UICreator.DEFAULT_INSETS);
        taps.add(tap);
        tapButtons.add(tap.getTapButton());
        tapsPanel.add(tap.getTapButton());
        changeTap(taps.size() - 1);

        updateTapsPanel();
    }

    /** @return the index of the active tap */
    public int getActiveTap() {
        return activeTap;
    }

    /** Changes the active tap and updates the <code>textArea</code>'s text. */
    public void changeTap(int newTap) {
        updateTap();

        // Change active tap to new tap
        activeTap = newTap;
        Tap currentTap = taps.get(activeTap);
        textArea.setText(currentTap.getText());
        textArea.setFont(currentTap.getFont());
        defaultFontSize = currentTap.getFont().getSize();
    }

    /** Removes a tap depending on its index */
    public void removeTap(int tapIndex) {
        // End the program if the user removes all taps
        if (taps.size() == 1)
            dispose();

        try {
            checkUnsavedChanges(tapIndex);
        } catch (IOException | ClassCastException | ClassNotFoundException ignored) {
        }

        int changeOnTap = activeTap == 0 ? 1 : -1; // Saves the chagne from the current tap index to the new tap index
        changeTap(activeTap + changeOnTap);
        tapButtons.get(activeTap).setSelected(true);

        taps.remove(tapIndex);
        tapButtons.remove(tapIndex);
        tapsPanel.remove(tapIndex);

        updateTapsPanel();
    }

    /** Updates the information of the active tap to it's object. */
    private void updateTap() {
        Tap currentTap = taps.get(activeTap);
        currentTap.setText(textArea.getText());
        currentTap.setFont(textArea.getFont());
    }

    /** Opens a file and creates a <code>Tap</code> with the opened info. */
    private void open() {
        newTap();
        Tap currentTap = taps.get(activeTap);
        java.io.File file = UICreator.chooseFile("Open");

        try {
            currentTap.open(file);
            defaultFontSize = currentTap.getFont().getSize();
        } catch (FileNotFoundException fnfe) {
            UICreator.showErrorMessage(this, "File not found.", "File error", 0);
        } catch (IOException | ClassNotFoundException e) {
            try {
                currentTap.openPlainText(file);
            } catch (IOException ioe) {
                UICreator.showErrorMessage(this,
                        "Make sure the file you are trying to open is compatiable with this software.",
                        "Couldn't open file", 0);
            }
        }

        textArea.setText(currentTap.getText());
        textArea.setFont(currentTap.getFont());
        updateTapsPanel();
    }

    /**
     * Saves a file with info from an opened <code>Tap</code>
     * 
     * @param saveAs    whether to ask the user to choose a file
     * @param tapToSave the index of the <code>tap</code> we want to save
     */
    private void save(boolean saveAs, int tapToSave) {
        updateTap();

        try {
            Tap tap = taps.get(tapToSave);
            tap.save(saveAs || tap.getDirectory() == null ? UICreator.chooseFile("Save")
                    : new java.io.File(tap.getDirectory()));
        } catch (IOException e) {
            UICreator.showErrorMessage(this, "Make sure you aren't losing any data before closing the program.",
                    "Problem while saving", 0);
        }

        updateTapsPanel();
    }

    /** Saves the text of the <code>activeTap</code> */
    private void savePlainText(int tapToSave) {
        updateTap();
        Tap tap = taps.get(tapToSave);

        try {
            String directory = tap.getDirectory() == null ? UICreator.chooseFile("Save").getAbsolutePath()
                    : tap.getDirectory();

            tap.savePlainText(new java.io.File(directory));
        } catch (IOException e) {
            UICreator.showErrorMessage(this, "Make sure you aren't losing any data before closing the program.",
                    "Problem while saving", 0);
        }
    }

    private void saveAll() {
        for (int i = 0; i < taps.size(); i++)
            save(false, i);
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

    /**
     * This is used so we can stop calculating the selected line and colume when the
     * <code>FindWindow.replaceAll()</code> is called.
     */
    public void setReplacing(boolean replacing) {
        statusBar.setReplacing(replacing);
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

    public int getDefualtFontSize() {
        return (int) defaultFontSize;
    }

    public ArrayList<TapButton> getTapButtons() {
        return tapButtons;
    }

    /**
     * Compares the <code>Tap</code> to the one saved in the file and saves if it's
     * not the same.
     */
    private void checkUnsavedChanges(int tapIndex) throws IOException, ClassNotFoundException, ClassCastException {
        updateTap();

        Tap currentTap = taps.get(tapIndex);

        // If the current tap has only text, check if it is the same as the saved text
        // and save it if it's not
        if (currentTap.getPlainText()) {
            if (!currentTap.getText().equals(Save.loadPlainText(currentTap.getDirectory())) && saveUnsavedChanges(currentTap))
                savePlainText(tapIndex);
            return; // Return so we don't go through the checks for the not plain text tap
        }

        Tap savedTap = currentTap.getDirectory() == null ? Tap.DEFAULT_TAP : Save.load(currentTap.getDirectory());

        // If the current tap doesn't have a directory, check if it is the same as the
        // default tap
        if (((currentTap.getDirectory() == null && !currentTap.equals(Tap.DEFAULT_TAP))
                // Otherwise, check if it's different from the tap saved in the directory
                || !currentTap.equals(savedTap))
                // And if any of the above cases are true, ask the user if they want to save
                // changes
                && saveUnsavedChanges(currentTap))
            save(false, tapIndex);
    }

    /**
     * Shows a dialog box asking the user if he wants to save unsaved changes and
     * returns true if the user chooses yes.
     */
    private boolean saveUnsavedChanges(Tap tap) {
        return javax.swing.JOptionPane.showConfirmDialog(this,
                "Do you want to save changes to " + tap.getName() + "?",
                "Unsaved chagnes.", 0) == 0;
    }

    @Override
    public void dispose() {
        // Check if any changes were made to the file
        try {
            for (int i = taps.size() - 1; i >= 0; i--)
                checkUnsavedChanges(i);
        } catch (ClassNotFoundException | ClassCastException | IOException ignored) {
        } finally {
            // If no other App window exists, stop the program
            // This is used because the dispose method isn't called if we change the
            // JFrame's defaultCloseOperation to JFrame.EXIST_ON_CLOSE
            if (NumberOfWindows == 1)
                System.exit(0);

            NumberOfWindows--;
            super.dispose();
        }
    }

    public static void main(String[] args) {
        new App();
    }
}