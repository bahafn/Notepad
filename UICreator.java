import javax.swing.JMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.awt.event.ActionListener;

public abstract class UICreator {
    private UICreator() {}

    public static JMenuItem createJMenuItem(String name, ActionListener actionListener) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(actionListener);

        return item;
    }

    public static JMenu createJMenu(String name, JMenuItem[] items) {
        JMenu menu = new JMenu(name);
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
