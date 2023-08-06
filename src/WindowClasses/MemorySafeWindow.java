package WindowClasses;

import javax.swing.JFrame;

public abstract class MemorySafeWindow extends JFrame {
    protected MemorySafeWindow() {}

    protected MemorySafeWindow(String text) {
        super(text);
    }

    @Override
    public void dispose() {
        removeAll();
        super.dispose();
        System.gc();
    }
}
