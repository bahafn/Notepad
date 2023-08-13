package UI;

import javax.swing.JFileChooser;

import java.awt.Font;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import Saving.Save;

public class Tap implements Serializable {
    private String name = "Untitled", text = "", directory = null;
    private Font font = UICreator.DEFAULT_FONT;

    public void open() throws IOException, ClassNotFoundException {
        File file = chooseFile();
        if (file == null)
            return;

        name = file.getName();
        directory = file.getAbsolutePath();

        Tap newTap = Save.load(directory);
        text = newTap.getText();
        font = newTap.getFont();
    }

    public void save() throws IOException {
        if (directory == null) {
            File file = chooseFile();
            directory = file.getAbsolutePath();
        }

        Save.save(this, directory);
    }

    private File chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        return (fileChooser.showOpenDialog(fileChooser.getParent()) == JFileChooser.APPROVE_OPTION
                ? fileChooser.getSelectedFile()
                : null);
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
}
