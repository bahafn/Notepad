package WindowClasses;

import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import UI.NumericalTextArea;
import UI.UICreator;

/**
 * This class creates the change font UI and changes the font depending on the
 * user changes.
 * <p>
 * This class extends <code>MemorySafeWindow</code> so the UI is added directly
 * to it.
 * 
 * @see MemorySafeWindow
 */
public class FontWindow extends MemorySafeWindow {
    private App app;

    /** Creates <code>FontWindow</code> */
    public FontWindow(App app) {
        super("Font");

        this.app = app;

        showGUI();
        UICreator.initJFrame(this, true, true, false, true, false, app);
    }

    private void showGUI() {
        setLayout(new GridLayout(3, 2));

        add(UICreator.createJLabel("Font:", UICreator.DEFAULT_TEXT_SIZE, 2, UICreator.DEFAULT_FONT));

        // Create change font option
        JComboBox<String> fontFamilyBox = UICreator.createJComboBox(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(),
                app.getFont().getFamily());

        fontFamilyBox.addActionListener(e -> app.setFont(
                new Font((String) fontFamilyBox.getSelectedItem(), app.getFont().getStyle(), app.getFont().getSize())));
        add(fontFamilyBox);

        add(UICreator.createJLabel("Style:", UICreator.DEFAULT_TEXT_SIZE, 2, UICreator.DEFAULT_FONT));

        // Create change style option
        JComboBox<String> fontStyleBox = UICreator.createJComboBox(new String[] { "Regular", "Bold", "Italic" },
                app.getFont().getStyle());
        fontStyleBox.addActionListener(e -> app.setFont(app.getFont().deriveFont(fontStyleBox.getSelectedIndex())));
        add(fontStyleBox);

        add(UICreator.createJLabel("Size:", UICreator.DEFAULT_TEXT_SIZE, 2, UICreator.DEFAULT_FONT));

        // Create change size option
        NumericalTextArea textArea = UICreator.createNumericalTextArea(String.valueOf(app.getDefualtFontSize()),
                UICreator.DEFAULT_SIZE, true, true, false);
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
    }

    private void changeSize(float size) {
        app.setFont(app.getFont().deriveFont(size));
    }

    @Override
    public void dispose() {
        app.setFontWindow(false);
        super.dispose();
    }
}
