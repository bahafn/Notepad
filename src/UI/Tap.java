package UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import Saving.Save;

/** Stores info about a tap (the text, name, font and directory of the tap) */
public class Tap implements java.io.Serializable {
    private transient String name = "Untitled";
    private String text = "", directory = null;
    private Font font = UICreator.DEFAULT_FONT;

    private transient TapButton tapButton;

    private transient boolean plainText = false;

    public static final transient Tap DEFAULT_TAP = new Tap();

    public Tap() {
    }

    public Tap(String name, WindowClasses.App app, int tapIndex, Dimension buttonSize, Insets buttonsMargins) {
        this.name = name;
        tapButton = new TapButton(app, tapIndex, name, buttonSize, buttonsMargins);
    }

    /** Updates the info of the <code>Tap</code> with info from a file. */
    public void open(File file) throws IOException, ClassNotFoundException {
        // This checks if the user choose a file or not
        if (file == null)
            return;

        setName(file.getName());
        directory = file.getAbsolutePath();

        Tap newTap = Save.load(directory);
        text = newTap.getText();
        font = newTap.getFont();
    }

    public void openPlainText(File file) throws IOException {
        text = Save.loadPlainText(file.getAbsolutePath());
        directory = file.getAbsolutePath();
        plainText = true;
        setName(file.getName());
    }

    /** Saves the info of the <code>Tap</code> to a file. */
    public void save(File file) throws IOException {
        if (file == null)
            return;

        directory = file.getAbsolutePath();
        setName(file.getName());
        plainText = false;
        Save.save(this, directory);
    }

    public void savePlainText(File file) throws IOException {
        if (file == null)
            return;

        Save.savePlainText(text, file.getAbsolutePath());
        directory = file.getAbsolutePath();
        setName(file.getName());
        plainText = true;
    }

    /** @return <code>true</code> if the <code>obj</code> equals the tap. */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Tap))
            return false;

        Tap tap = (Tap) obj;
        return font.equals(tap.font) && text.equals(tap.text);
    }

    private void setName(String name) {
        this.name = name;
        tapButton.setText(name);
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getDirectory() {
        return directory;
    }

    public TapButton getTapButton() {
        return tapButton;
    }

    public boolean getPlainText() {
        return plainText;
    }
}
