package UI;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

/**
 * This class has functions and constants to make it easier to create
 * <code>javax.swing</code> components. While it has some overloaded methods, it
 * is build in a way that you'll need to use the constants if you don't want to
 * set some values.
 */
public final class UICreator {
    public static final Dimension DEFAULT_SIZE = new Dimension(100, 25);
    public static final Dimension DEFAULT_TEXT_SIZE = new Dimension(200, 25);
    public static final Dimension SQUARE_SIZE = new Dimension(25, 25);

    public static final Insets DEFAULT_INSETS = new JButton().getInsets();
    public static final Insets NO_INSETS = new Insets(0, 0, 0, 0);

    public static final String SYSTEM_LOOK_AND_FEEL = UIManager.getSystemLookAndFeelClassName();

    public static final Font DEFAULT_FONT = new JTextArea().getFont();
    public static final float DEFAULT_FONT_SIZE = 12;

    private UICreator() {
    }

    // TODO: Remove System.exist from here and make it throw an exception instead
    public static void setLookAndFeel(String lookAndFeel) {
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            showErrorMessage(null, "Couldn't find look and feel", "Warning", 0);
            System.exit(0);
        }
    }

    public static void showErrorMessage(Component parent, String message, String title, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

    /**
     * Creates new <code>JTextArea</code>.
     * 
     * @param text     the text to be set
     * @param lineWarp weather to wrap the line at the end of the screen
     * @return new <code>JTextArea</code> with specified text and lineWrap
     * @see JTextArea
     */
    public static JTextArea createJTextArea(String text, boolean lineWarp) {
        JTextArea textArea = new JTextArea(text);
        textArea.setLineWrap(lineWarp);

        return textArea;
    }

    /**
     * Creates new <code>JTextArea</code>.
     * 
     * @param size the size of the component
     * @return new <code>JTextArea</code> with specified values
     * @throws IllegalArgumentException
     * @see <code>createJTextArea(String , boolean)</code>
     * @see <code>checkDimension</code>
     */
    public static JTextArea createJTextArea(String text, Dimension size, boolean lineWarp) {
        checkDimension(size);

        JTextArea textArea = createJTextArea(text, lineWarp);
        textArea.setPreferredSize(size);

        return textArea;
    }

    /**
     * Creates new <code>NumericalTextArea</code>.
     * 
     * @param text     the text to be set
     * @param size     the size of the component
     * @param integer  weather to take double values or not
     * @param positive weather to take neagtive values or not
     * @param lineWarp weather to wrap the line at the end of the screen
     * @return new <code>NumericalTextArea</code> with specified values
     * @throws IllegalArgumentException
     * @see NumericalTextArea
     */
    public static NumericalTextArea createNumericalTextArea(String text, Dimension size, boolean integer,
            boolean positive, boolean lineWarp) {
        checkDimension(size);

        NumericalTextArea textArea = new NumericalTextArea(text, size, integer, positive);
        textArea.setLineWrap(lineWarp);

        return textArea;
    }

    /**
     * Creates new <code>JLabel</code>.
     * 
     * @param text the text to be set
     * @param size the size of the component
     * @param font the font to use
     * @return new <code>JLabel</code> with specified values
     * @throws IllegalArgumentException
     * @see JLabel
     * @see <code>checkDimension</code>
     */
    public static JLabel createJLabel(String text, Dimension size, Font font) {
        checkDimension(size);

        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setPreferredSize(size);

        return label;
    }

    /**
     * Creates new <code>JLabel</code>.
     * 
     * @param fontSize the size of the font to be used (overwrites the size of the
     *                 font parameter)
     * @return new <code>JLabel</code> with specified values
     * @throws IllegalArgumentException
     * @see JLabel
     * @see <code>createJLabel(String , Dimension , Font)</code>
     */
    public static JLabel createJLabel(String text, Dimension size, Font font, float fontSize) {
        checkDimension(size);

        JLabel label = UICreator.createJLabel(text, size, font);
        label.setFont(font.deriveFont(fontSize));

        return label;
    }

    /**
     * Creates new <code>JLabel</code>
     * 
     * @param alignment where the text starts from (right, left, or middle)
     * @return new <code>JLabel</code> with specified values
     * @throws IllegalArgumentException
     * @see JLabel
     * @see <code>createJLabel(String , Dimension , Font)</code>
     */
    public static JLabel createJLabel(String text, Dimension size, int alignment, Font font) {
        checkDimension(size);

        JLabel label = UICreator.createJLabel(text, size, font);
        label.setHorizontalAlignment(alignment);

        return label;
    }

    /**
     * Creates new <code>JButton</code>.
     * 
     * @param text           the text to be set
     * @param actionListener an <code>ActionListener</code> to be added to the
     *                       <code>JButton</code>
     * @param size           the size of the component
     * @param margins        the margins at the which the text of the
     *                       <code>JButton</code> can't continue
     * @return new <code>JButton</code> with specified values
     * @throws IllegalArgumentException
     * @see <code>checkDimension</code>
     * @see JButton
     */
    public static JButton createJButton(String text, ActionListener actionListener, Dimension size, Insets margins) {
        checkDimension(size);

        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setPreferredSize(size);
        button.setMargin(margins);

        return button;
    }

    /**
     * Creates new <code>JCheckBox</code>.
     * 
     * @param text           the text to be set
     * @param state          the initial state of the <code>JCheckBox</code>
     * @param actionListener an <code>ActionListener</code> to be added to the
     *                       <code>JCheckBox</code>
     * @return new <code>JCheckBox</code> with specified values
     * @see JCheckBox
     */
    public static JCheckBox createJCheckBox(String text, boolean state, ActionListener actionListener) {
        JCheckBox checkBox = new JCheckBox(text, state);
        checkBox.addActionListener(actionListener);

        return checkBox;
    }

    /**
     * Creates new <code>JCheckBoxMenuItem</code>.
     * 
     * @param text           the text to be set
     * @param state          the initial state of the <code>JCheckBoxMenuItem</code>
     * @param actionListener an <code>ActionListener</code> to be added to the
     *                       <code>JCheckBoxMenuItem</code>
     * @return new <code>JCheckBoxMenuItem</code> with specified values
     * @see JCheckBoxMenuItem
     */
    public static JCheckBoxMenuItem createJCheckBoxMenuItem(String text, boolean state, ActionListener actionListener) {
        JCheckBoxMenuItem checkBox = new JCheckBoxMenuItem(text, state);
        checkBox.addActionListener(actionListener);

        return checkBox;
    }

    /**
     * Creates new <code>JScrollPane</code>.
     * 
     * @param size      the size of the component
     * @param component the component to be added to the <code>JScrollPane</code>
     * @return new <code>JScrollPane</code> with specified values
     * @throws IllegalArgumentException
     * @see JScrollPane
     * @see <code>checkDimension</code>
     */
    public static JScrollPane createJScrollPane(Dimension size, JTextArea component) {
        checkDimension(size);

        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setPreferredSize(size);

        return scrollPane;
    }

    /**
     * Creates new <code>JMenuItem</code>.
     * 
     * @param text           the text to be set
     * @param actionListener an <code>ActionListner</code> to be added to the
     *                       <code>JMenuItem</code>
     * @return new <code>JMenuItem</code> with specified values
     * @see JMenuItem
     */
    public static JMenuItem createJMenuItem(String text, ActionListener actionListener) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(actionListener);

        return item;
    }

    /**
     * Creates new <code>JMenu</code>.
     * 
     * @param text  the text to be set
     * @param items the <code>JMenuItem</code>s to be added to the
     *              <code>JMenu</code>
     * @return new <code>JMenu</code> with specified values
     * @see JMenu
     */
    public static JMenu createJMenu(String text, JComponent[] items) {
        JMenu menu = new JMenu(text);
        addItems(menu, items);

        return menu;
    }

    /**
     * Create new <code>JMenuBar</code>.
     * 
     * @param items the <code>JMenuItem</code>s to be added to the
     *              <code>JMenuBar</code>
     * @return new <code>JMenuBar</code> with specified values
     * @see JMenuBar
     */
    public static JMenuBar createJMenuBar(JMenuItem[] items) {
        JMenuBar menuBar = new JMenuBar();
        addItems(menuBar, items);

        return menuBar;
    }

    public static JSeparator createJSeparator() {
        return new JSeparator(0);
    }

    /**
     * Creates new <code>JComboBox</code>.
     * 
     * @param <type>         the type of each element of the <code>JComboBox</code>
     * @param elements       the elements of the <code>JComboBox</codE>
     * @param selectedOption the selected option (this is an <code>type</code>
     *                       <code>Object</code> and not the index of the selected
     *                       <code>Object</code>)
     * @return new <code>JComboBox</code> with specified values
     */
    public static <type> JComboBox<type> createJComboBox(type[] elements, type selectedOption) {
        JComboBox<type> comboBox = new JComboBox<>(elements);
        comboBox.setSelectedItem(selectedOption);

        return comboBox;
    }

    /**
     * Creates new <code>JComboBox</code>.
     * 
     * @param <type>         the type of each element of the <code>JComboBox</code>
     * @param elements       the elements of the <code>JComboBox</codE>
     * @param selectedOption the selected option (this is an <code>type</code>
     *                       <code>Object</code> and not the index of the selected
     *                       <code>Object</code>)
     * @return new <code>JComboBox</code> with specified values
     */
    public static <type> JComboBox<type> createJComboBox(type[] elements, int selectedIndex) {
        JComboBox<type> comboBox = new JComboBox<>(elements);
        comboBox.setSelectedIndex(selectedIndex);

        return comboBox;
    }

    public static File chooseFile(String openButtonText) {
        UIManager.put("FileChooser.openButtonText", openButtonText);
        JFileChooser fileChooser = new JFileChooser();

        return (fileChooser.showOpenDialog(fileChooser.getParent()) == JFileChooser.APPROVE_OPTION
                ? fileChooser.getSelectedFile()
                : null);
    }

    /**
     * Adds an array of <code>JComponent</code>s to the another
     * <code>JComponent</code>.
     * 
     * @param parent the <code>JComponent</code> to be added to
     * @param items  the array of <code>JComponent</code>s to be added
     * @see JComponent
     */
    private static void addItems(JComponent parent, JComponent[] items) {
        for (JComponent item : items)
            parent.add(item);
    }

    /**
     * Initializes a <code>JFrame</code> with specified values.
     * 
     * @param frame       the frame to be initialized
     * @param decorated   weather the <code>JFrame</code> is decorated (have edges
     *                    and top) or not
     * @param alwaysOnTop weather the <code>JFrame</code> is always on top of other
     *                    windows
     * @param mainFrame   weather to set the
     *                    <code>JFrame.defaultCloseOperation</code> to
     *                    <code>JFrame.EXIT_ON_CLOSE</code> or not
     * @param pack        weather to call <code>JFrame.pack()</code> or not (NOTE:
     *                    if you want to call <code>JFrame.pack()</code>, make sure
     *                    to add the components before calling this function
     * @param parent      the parent of the <code>JFrame</code>. This is also passed
     *                    to <code>JFrame.setLocationRelativeTo()</code> method
     */
    public static void initJFrame(JFrame frame, boolean decorated, boolean alwaysOnTop, boolean mainFrame, boolean pack,
            boolean resizeable, Container parent) {
        frame.setUndecorated(!decorated);
        frame.setAlwaysOnTop(alwaysOnTop);
        frame.setVisible(true);
        frame.setResizable(resizeable);
        if (pack)
            frame.pack();
        frame.setLocationRelativeTo(parent);
        frame.setDefaultCloseOperation(mainFrame ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE);
        frame.requestFocus();
    }

    /**
     * Checks the given <code>Dimension</code>'s width and height to make sure
     * they don't go under one.
     * <p>
     * This is used because the <code>Dimension</code>'s constructer allows you to
     * set width and height under 1 but creates problems if you do so.
     * 
     * @throws IllegalArgumentException
     * @see Dimension
     */
    private static void checkDimension(Dimension dimension) {
        if (dimension.getWidth() <= 0 || dimension.getHeight() <= 0)
            throw new IllegalArgumentException("Dimensions' width and height can't be zero or lower.");
    }
}
