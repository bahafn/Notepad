package WindowClasses;

import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;

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
        setLayout(new GridLayout(2, 2));

        add(UICreator.createJLabel("Font:", UICreator.DEFAULT_TEXT_SIZE, 2, UICreator.DEFAULT_FONT));
        add(UICreator.createJComboBox(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(),
                app.getFont().getFamily()));

        add(UICreator.createJLabel("Size:", UICreator.DEFAULT_TEXT_SIZE, 2, UICreator.DEFAULT_FONT));

        NumericalTextArea textArea = UICreator.createNumericalTextArea(String.valueOf(app.getFont().getSize()),
                UICreator.DEFAULT_SIZE, false, true, false);
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
}
