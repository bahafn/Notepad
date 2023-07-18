import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

public class App extends JFrame {
    private ArrayList<Tap> taps = new ArrayList<>();
    private int activeTap = 0;
    private JPanel tapsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    private JTextArea textArea = new JTextArea();

    public App() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        showGUI();

        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }

    private void showGUI() {
        newTap(); // Create first tap

        // Add main text area
        textArea.setPreferredSize(new Dimension(1000, 600));
        add(textArea, BorderLayout.SOUTH);

        // Create menuBar items
        JMenu[] menus = new JMenu[2];

        // Create File menu items
        JMenuItem[] fileItems = new JMenuItem[6];

        fileItems[0] = UICreator.createJMenuItem("New tap", e -> newTap());
        fileItems[1] = UICreator.createJMenuItem("New window", e -> newWindow());
        fileItems[2] = UICreator.createJMenuItem("Open", e -> open());
        fileItems[3] = UICreator.createJMenuItem("Save", e -> taps.get(activeTap).save());
        fileItems[4] = UICreator.createJMenuItem("Save as", e -> taps.get(activeTap).save());
        fileItems[5] = UICreator.createJMenuItem("Save all", e -> saveAll());

        menus[0] = UICreator.createJMenu("File", fileItems); // Create File menu

        // Create Edit menu items
        JMenuItem[] editItems = new JMenuItem[5];

        editItems[0] = UICreator.createJMenuItem("Undo", e -> taps.get(activeTap).undo());
        editItems[1] = UICreator.createJMenuItem("Find", e -> taps.get(activeTap).find());
        editItems[2] = UICreator.createJMenuItem("Replace", e -> taps.get(activeTap).replace());
        editItems[3] = UICreator.createJMenuItem("Go to", null);
        editItems[4] = UICreator.createJMenuItem("Font", e -> newFontWindow());

        menus[1] = UICreator.createJMenu("Edit", editItems);

        JMenuBar menuBar = UICreator.createJMenuBar(menus); // Create menu bar

        add(menuBar, BorderLayout.NORTH);

        add(tapsPanel);
    }

    public void updateTapsPanel() {
        tapsPanel.removeAll();

        for (int i = 0; i < taps.size(); i++) {
            final int index = i; // Used beccause non-final values can't be used in lambda
            tapsPanel.add(UICreator.createJButton(taps.get(i).getName(), e -> changeTap(index),
                    UICreator.DEFAULT_SIZE, UICreator.NO_INSETS));
        }

        tapsPanel.revalidate();
        tapsPanel.repaint();
    }

    private void newTap() {
        taps.add(new Tap());
        activeTap = taps.size() - 1;
        changeTap(activeTap);

        updateTapsPanel();
    }

    private void newWindow() {
        new App();
    }

    private void open() {
        taps.get(activeTap).open();
        updateTapsPanel();
    }

    private void saveAll() {
        // TODO: write function
    }

    private void newFontWindow() {
        // TODO: write function
    }

    public static void main(String[] args) {
        new App();
    }

    private void changeTap(int newTap) {
        // Update text in tap object
        taps.get(activeTap).setText(textArea.getText());

        // Change active tap
        activeTap = newTap;
        textArea.setText(taps.get(activeTap).getText());
    }
}