package UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import Saving.Save;

public class Tap implements java.io.Serializable {
    private transient String name = "Untitled";
    private String text = "", directory = null;
    private Font font = UICreator.DEFAULT_FONT;

    private transient TapButton tapButton;

    public static final transient Tap DEFAULT_TAP = new Tap();

    public Tap() {}

    public Tap(String name, WindowClasses.App app, int tapIndex, Dimension buttonSize, Insets buttonsMargins)  {
        this.name = name;
        tapButton = new TapButton(app, tapIndex, name, buttonSize, buttonsMargins);
    }

    public void open(File file) throws IOException, ClassNotFoundException {
        // This checks if the user choose a file or not
        if (file == null)
            return;

        name = file.getName();
        directory = file.getAbsolutePath();

        Tap newTap = Save.load(directory);
        text = newTap.getText();
        font = newTap.getFont();
    }

    public void save(File file) throws IOException {
        if (file == null)
            return;

        directory = file.getAbsolutePath();
        name = file.getName();
        tapButton.setText(name);
        Save.save(this, directory);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Tap))
            return false;

        Tap tap = (Tap) obj;
        return font.equals(tap.font) && text.equals(tap.text);
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
}
