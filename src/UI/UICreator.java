package UI;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;

public class UICreator {
    public static final Dimension DEFAULT_SIZE = new Dimension(100, 25);
    public static final Dimension SQUARE_SIZE = new Dimension(25, 25);
    public static final Insets DEFAULT_INSETS = new JButton().getInsets();
    public static final Insets NO_INSETS = new Insets(0, 0, 0, 0);

    public static final String SYSTEM_LOOK_AND_FEEL = UIManager.getSystemLookAndFeelClassName();

    private UICreator() {}

    public static void setLookAndFeel(String lookAndFeel) {
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Couldn't find look and feel", "Warning", 0);
            System.exit(0);
        }
    }

    public static JTextArea createJTextArea(String text, boolean lineWarp) {
        JTextArea textArea = new JTextArea(text);
        textArea.setLineWrap(lineWarp);

        return textArea;
    }

    public static JTextArea createJTextArea(String text, Dimension size, boolean lineWarp) {
        checkDimension(size);

        JTextArea textArea = createJTextArea(text, lineWarp);
        textArea.setPreferredSize(size);

        return textArea;
    }

    public static JButton createJButton(String text, ActionListener actionListener, Dimension size, Insets margins) {
        checkDimension(size);
        
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setPreferredSize(size);
        button.setMargin(margins);

        return button;
    }

    public static JCheckBox createJCheckBox(String text, boolean state, ActionListener actionListener) {
        JCheckBox checkBox = new JCheckBox(text, state);
        checkBox.addActionListener(actionListener);

        return checkBox;
    }

    public static JCheckBoxMenuItem createJCheckBoxMenuItem(String text, boolean state, ActionListener actionListener) {
        JCheckBoxMenuItem checkBox = new JCheckBoxMenuItem(text, state);
        checkBox.addActionListener(actionListener);

        return checkBox;
    }

    public static JScrollPane createJScrollPane(Dimension size, JTextArea component) {
        checkDimension(size);

        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setPreferredSize(size);

        return scrollPane;
    }

    public static JMenuItem createJMenuItem(String text, ActionListener actionListener) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(actionListener);

        return item;
    }

    public static JMenu createJMenu(String text, JMenuItem[] items) {
        JMenu menu = new JMenu(text);
        addItems(menu, items);

        return menu;
    }

    public static JMenuBar createJMenuBar(JMenuItem[] items) {
        JMenuBar menuBar = new JMenuBar();
        addItems(menuBar, items);

        return menuBar;
    }

    // This is used so we don't repeat the same code in every class that extends JFrame
    public static void initJFrame(JFrame frame, boolean decorated, boolean alwaysOnTop, boolean mainFrame, boolean pack, Container parent) {
        frame.setUndecorated(!decorated);
        frame.setAlwaysOnTop(alwaysOnTop);
        frame.setVisible(true);
        if (pack)
            frame.pack();
        frame.setLocationRelativeTo(parent);
        if (mainFrame)
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void addItems(JComponent parent, JComponent[] items) {
        for (JComponent item : items)
            parent.add(item);
    }

    private static void checkDimension(Dimension size) {
        if (size.getWidth() <= 0 || size.getHeight() <= 0)
            throw new IllegalArgumentException("Dimensions width and height can't be zero or lower.");
    }
}
