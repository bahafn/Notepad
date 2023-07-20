import javax.swing.JMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Insets;

public class UICreator {
    public static final Dimension DEFAULT_SIZE = new Dimension(100, 25);
    public static final Dimension SQUARE_SIZE = new Dimension(25, 25);
    public static final Insets DEFAULT_INSETS = new JButton().getInsets();
    public static final Insets NO_INSETS = new Insets(0, 0, 0, 0);

    private UICreator() {}

    public static JTextArea createJTextArea(String text, Dimension size, boolean lineWarp) {
        JTextArea textArea = new JTextArea(text);
        textArea.setPreferredSize(size);
        textArea.setLineWrap(lineWarp);

        return textArea;
    }

    public static JButton createJButton(String text, ActionListener actionListener, Dimension size, Insets margins) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setPreferredSize(size);
        button.setMargin(margins);

        return button;
    }

    public static JCheckBox createJCheckBox(String text, boolean state, ActionListener actionListener) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setSelected(state);
        checkBox.addActionListener(actionListener);

        return checkBox;
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

    private static void addItems(JComponent parent, JComponent[] items) {
        for (JComponent item : items)
            parent.add(item);
    }
}
