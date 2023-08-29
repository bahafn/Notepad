package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.SpringLayout;

import WindowClasses.App;

/**
 * <code>JButtons</code> with added feature to work as tap buttons. Added
 * features include changing background color when clicked and having a
 * refreance
 * to the <code>Tap</code> they represent.
 * 
 * @see JButton
 */
public class TapButton extends JButton {
    private App app;
    private int tapIndex;
    private JButton closeButton = UICreator.createJButton("X", e -> removeTap(), new Dimension(10, 10), UICreator.NO_INSETS); 

    public static final Color DEFAULT_BUTTON_COLOR = new Color(240, 240, 240);
    public static final Color SELECTED_BUTTON_COLOR = new Color(110, 110, 110, 100);

    /** Creates <code>TapButton</code>. */
    public TapButton(App app, int tapIndex, String text, Dimension size, Insets margins) {
        this.app = app;
        this.tapIndex = tapIndex;

        setOpaque(false);
        setBackground(DEFAULT_BUTTON_COLOR);
        setBorderPainted(false);

        setText(text);
        setPreferredSize(size);
        setMargin(margins);

        setSelected(true);

        addActionListener(e -> {
            setSelected(true);
            app.changeTap(this.tapIndex);
        });

        // Create close button
        SpringLayout layout = new SpringLayout();

        closeButton.setOpaque(false);
        closeButton.setBorderPainted(false);
        closeButton.setBackground(getBackground());

        layout.putConstraint(SpringLayout.WEST, closeButton, 5, SpringLayout.EAST, this);
        setLayout(layout);
        add(closeButton);
    }

    /**
     * Changes the <code>TapButtons</code>'s background depending on its state
     * (selected or not).
     * 
     * @param selected weather to select the <code>TapButton</code> or unselect it
     */
    public void setSelected(boolean selected) {
        setBackground(selected ? SELECTED_BUTTON_COLOR : DEFAULT_BUTTON_COLOR);
        closeButton.setBackground(getBackground());

        if (!selected)
            return;

        // Change all other buttons to unselected
        for (TapButton button : app.getTapButtons())
            if (button != this)
                button.setSelected(false);
    }

    private void removeTap() {
        for (int i = tapIndex + 1; i < app.getTapButtons().size(); i++)
            app.getTapButtons().get(i).tapIndex -= 1;

        app.removeTap(tapIndex);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
