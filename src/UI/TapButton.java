package UI;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import WindowClasses.App;

/**
 * <code>JButtons</code> with added feature to work as tap buttons. Added
 * features include changing background color when clicked and having a refreance
 * to the <code>Tap</code> they represent.
 * 
 * @see JButton
 */
public class TapButton extends JButton {
    private Tap tap;

    public static final Color DEFAULT_BUTTON_COLOR = new Color(240, 240, 240);
    public static final Color SELECTED_BUTTON_COLOR = new Color(10, 10, 200, 100);

    /** Creates <code>TapButton</code>. */
    public TapButton(App app, Tap tap, int tapIndex, String text, Dimension size, Insets margins) {
        setOpaque(false);
        setBackground(DEFAULT_BUTTON_COLOR);

        setBorderPainted(false);

        setText(text);
        setPreferredSize(size);
        setMargin(margins);

        setSelected(true, app);

        addActionListener(e -> {
            setSelected(true, app);
            app.changeTap(tapIndex);
        });

        app.getTapButtons().add(this);

        this.tap = tap;
    }

    public Tap getTap() {
        return tap;
    }

    /**
     * Changes the <code>TapButtons</code>'s background depending on its state
     * (selected or not).
     * 
     * @param selected weather to select the <code>TapButton</code> or unselect it
     * @param app      the parent of the <code>TapButton</code> that has an array
     *                 with all other <code>TapButtons</code>
     */
    public void setSelected(boolean selected, App app) {
        setBackground(selected ? SELECTED_BUTTON_COLOR : DEFAULT_BUTTON_COLOR);

        if (!selected)
            return;

        // Change all other buttons to unselected
        for (TapButton button : app.getTapButtons())
            if (button != this)
                button.setSelected(false, app);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}