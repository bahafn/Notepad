import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.JFileChooser;

import java.io.File;

public class Tap extends JTextArea {
    private File file;
    private String name = "Untitled";

    public Tap() {
        setPreferredSize(new Dimension(1000, 600));
    }

    public void undo() {
        // TODO: write function
    }

    public void find() {
        // TODO: write function
    }

    public void replace() {
        // TODO: write function
    }

    public void open() {
        file = chooseFile();
        name = file.getName();
    }

    public void save() {
        // TODO: write function
    }

    private File chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        return (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile() : new File(""));
    }

    public String getName() {
        return name;
    }
}
