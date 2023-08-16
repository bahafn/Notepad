package WindowClasses;

/**
 * This class extends <code>JFrame</code> class but changes the dispose method
 * so we are sure the <code>Garbage Collector</code> removes the
 * <code>JFrame</code>
 * object from memory.
 * 
 * @see JFrame
 */
public class MemorySafeWindow extends javax.swing.JFrame {
    /** Defualt constructor mostly used by sub classes. */
    protected MemorySafeWindow() {
    }

    /** Creates a <code>MemorySafeWindow</code> and changes its title. */
    protected MemorySafeWindow(String text) {
        super(text);
    }

    /**
     * Overrides <code>JFrame</code>'s origanl <code>dispose()</code> method and
     * makes sure the object is removed form memory.
     */
    @Override
    public void dispose() {
        removeAll();
        super.dispose();
        System.gc();
    }
}
