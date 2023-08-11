package UI;

import javax.swing.JFileChooser;

import java.awt.Font;

import java.io.File;
import java.io.Serializable;

public class Tap implements Serializable {
    private File file;
    private String name = "Untitled";
    private String text = "";
    private Font font = UICreator.DEFAULT_FONT;

    public void open() {
        file = chooseFile();
        name = file.getName();
    }

    public void save() {
        // TODO: write function
    }

    private File chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        return (fileChooser.showOpenDialog(fileChooser.getParent()) == JFileChooser.APPROVE_OPTION
                ? fileChooser.getSelectedFile()
                : new File(""));
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
