package UI;

import java.awt.Font;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import Saving.Save;

public class Tap implements Serializable {
    private transient String name = "Untitled"; 
    private String text = "", directory = null;
    private Font font = UICreator.DEFAULT_FONT;

    public static final transient Tap DEFAULT_TAP = new Tap();

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
        Save.save(this, directory);
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

    public boolean equals(Object obj) {
        if (!(obj instanceof Tap))
            return false;

        Tap tap = (Tap) obj;
        return font.equals(tap.font) && text.equals(tap.text);
    }
}
