import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

public class App extends JFrame {
    private ArrayList<Tap> taps = new ArrayList<>();
    private int activeTap = 0;
    private JPanel tapsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    private JTextArea textArea = UICreator.createJTextArea("", true);
    private UndoManager undoManager = new UndoManager();

    public App() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        showGUI();

        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }

    private void showGUI() {
        // Make scroll bar
        add(UICreator.createJScrollPane(new Dimension(1000, 600), textArea), BorderLayout.SOUTH);

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
            UICreator.createJMenuItem("Go to", null),
            UICreator.createJMenuItem("Font", e -> newFontWindow())
        });

        // Create view menu items
        menus[2] = UICreator.createJMenu("View", new JMenuItem[] {
            UICreator.createJMenu("Zoom", new JMenuItem[] {
                UICreator.createJMenuItem("Zoom in", null),
                UICreator.createJMenuItem("Zoom out", null),
                UICreator.createJMenuItem("Reset zoom", null)
            }),
            UICreator.createJCheckBoxMenuItem("Status bar", true, null),
            UICreator.createJCheckBoxMenuItem("Word wrap", true, e -> textArea.setLineWrap(!textArea.getLineWrap()))
        });

        JMenuBar menuBar = UICreator.createJMenuBar(menus); // Create menu bar

        add(menuBar, BorderLayout.NORTH);

        add(tapsPanel);
    }

    public void updateTapsPanel() {
        tapsPanel.removeAll();

        for (int i = 0; i < taps.size(); i++) {
            final int index = i; // Used beccause non-final values can't be used in lambda
            tapsPanel.add(UICreator.createJButton(taps.get(i).getName(), e -> changeTap(index),
                    UICreator.DEFAULT_SIZE, UICreator.NO_INSETS));
        }

        tapsPanel.revalidate();
        tapsPanel.repaint();
    }

    public void selectText(int beginIndex, int endIndex) {
        textArea.requestFocus();
        textArea.select(beginIndex, endIndex);
    }

    public void replace(String newText) {
        textArea.replaceRange(newText, textArea.getSelectionStart(), textArea.getSelectionEnd());
    }

    public String getText() {
        return textArea.getText();
    }

    private void newTap() {
        taps.add(new Tap());
        activeTap = taps.size() - 1;
        changeTap(activeTap);

        updateTapsPanel();
    }

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

    private void changeTap(int newTap) {
        // Update text in tap object
        taps.get(activeTap).setText(textArea.getText());

        // Change active tap
        activeTap = newTap;
        textArea.setText(taps.get(activeTap).getText());
    }

    public static void main(String[] args) {
        new App();
    }
}