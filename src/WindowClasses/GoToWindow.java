package WindowClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import UI.NumericalTextArea;
import UI.UICreator;

/**
 * This class creates the go to UI and selects the line the user chooses.
 * <p>
 * This extends <code>MemorySafeWindow</code> so the UI is added directly to it.
 * 
 * @see MemorySafeWindow
 */
public class GoToWindow extends MemorySafeWindow {
    /** The <code>App</code> that created this object. */
    private App app;

    /** Creates a new <code>GoToWindow</code>. */
    public GoToWindow(App app) {
        this.app = app;

        showGUI();

        UICreator.initJFrame(this, false, true, false, true, false, getParent());
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
    }

    private void showGUI() {
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setPreferredSize(new Dimension(200, 80));
        labelPanel.add(UICreator.createJLabel("Go to line.", new Dimension(200, 50), UICreator.DEFAULT_FONT, 25),
                BorderLayout.NORTH);
        labelPanel.add(UICreator.createJLabel("Line number: ", UICreator.DEFAULT_SIZE, UICreator.DEFAULT_FONT),
                BorderLayout.SOUTH);
        add(labelPanel, BorderLayout.NORTH);

        NumericalTextArea lineArea = UICreator.createNumericalTextArea("1", UICreator.DEFAULT_TEXT_SIZE, true, true,
                true);
        add(lineArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(UICreator.createJButton("Go to", e -> goTo(lineArea.getIntValue() - 1), UICreator.DEFAULT_SIZE,
                UICreator.DEFAULT_INSETS), BorderLayout.SOUTH);
        buttonPanel.add(UICreator.createJButton("Cancel", e -> {
            dispose();
        }, UICreator.DEFAULT_SIZE, UICreator.DEFAULT_INSETS));

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /** Selects a line depending on the given int */
    public void goTo(int line) {
        String text = app.getText();
        String[] lines = text.split("\r|\n|\r\n", -1);

        if (line >= lines.length) {
            setVisible(false);
            UICreator.showErrorMessage(this, "The line number is beyond the total number of lines.", "Go to.", 1);
            dispose();
            return;
        }

        int selectIndex = 0;

        for (int i = 0; i < line; i++)
            selectIndex += lines[i].length() + 1;

        app.selectText(selectIndex, selectIndex);

        dispose();
    }

    @Override
    public void dispose() {
        app.setGoToWindow(false);
        super.dispose();
    }
}
