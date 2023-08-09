package WindowClasses;

import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import UI.NumericalTextArea;
import UI.UICreator;

public class FontWindow extends MemorySafeWindow {
    private App app;

    public FontWindow(App app) {
        super("Font");

        this.app = app;

        showGUI();
        UICreator.initJFrame(this, true, true, false, true, app);
    }

    private void showGUI() {
        setLayout(new GridLayout(3, 2));

        add(UICreator.createJLabel("Font:", UICreator.DEFAULT_TEXT_SIZE, 2, UICreator.DEFAULT_FONT));

        // Create change font option
        JComboBox<String> fontFamilyBox = UICreator.createJComboBox(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(),
                app.getFont().getFamily());

        fontFamilyBox.addActionListener(e -> changeFont((String) fontFamilyBox.getSelectedItem()));
        add(fontFamilyBox);

        add(UICreator.createJLabel("Size:", UICreator.DEFAULT_TEXT_SIZE, 2, UICreator.DEFAULT_FONT));

        // Create change size option
        NumericalTextArea textArea = UICreator.createNumericalTextArea(String.valueOf(app.getFont().getSize()),
                UICreator.DEFAULT_SIZE, false, true, false);
        // Document listener so the user sees the changes the moment he changes them
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                changeSize(textArea.getIntValue());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                changeSize(textArea.getIntValue());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeSize(textArea.getIntValue());
            }
        });
        add(textArea);

        add(UICreator.createJLabel("Style:", UICreator.DEFAULT_TEXT_SIZE, 2, UICreator.DEFAULT_FONT));

        // Create change style option
        JComboBox<String> fontStyleBox = UICreator.createJComboBox(new String[] { "Regular", "Bold", "Italic" },
                "Regular");
        fontStyleBox.addActionListener(e -> changeStyle(fontStyleBox.getSelectedIndex()));
        add(fontStyleBox);
    }

    private void changeSize(float size) {
        app.setFont(app.getFont().deriveFont(size));
    }

    private void changeFont(String fontName) {
        app.setFont(new Font(fontName, app.getFont().getStyle(), app.getFont().getSize()));
    }

    private void changeStyle(int style) {
        app.setFont(new Font(app.getFont().getFamily(), style, app.getFont().getSize()));
    }
}
