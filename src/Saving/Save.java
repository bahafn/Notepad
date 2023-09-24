package Saving;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class has the <code>save</code> and <code>load</code> methods which are
 * used to write and read objects to and from files.
 */
public final class Save {
    private Save() {
    }

    /**
     * This method takes an <code>Object</code> of an type that implements
     * <code>Serializable</code> and writes it in a file in a spcified directory.
     * 
     * @param <type>       the type of the <code>Object</code> we want to write
     *                     (must implement Serializable)
     * @param objectToSave the <code>Object</code> we want to save
     * @param directory    the directory we want to write to
     * @throws IOException if the file wasn't found
     */
    public static <type extends Serializable> void save(type objectToSave, String directory) throws IOException {
        // Create the file if it doesn't exist
        if (!checkDirectory(directory)) {
            File file = new File(directory);
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(directory);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(objectToSave);

        oos.flush();
        oos.close();
    }

    /**
     * This method reads from and a file and writes the infromation to an
     * <code>Object</code>.
     * 
     * @param <type>    the type of the <code>Object</code> we want to read
     * @param directory the directory of the file we want to read from
     * @return an <code>Object</code> of type <code>type</code> with information
     *         stored in the file
     * @throws IOException            if the file wasn't found
     * @throws ClassNotFoundException
     * @throws ClassCastException     if we couldn't cast from <code>Object</code>
     *                                to the wanted type
     */
    public static <type extends Serializable> type load(String directory)
            throws IOException, ClassNotFoundException, ClassCastException {
        if (!checkDirectory(directory))
            throw new FileNotFoundException("Couldn't find a file at " + directory);

        FileInputStream fis = new FileInputStream(directory);
        ObjectInputStream ois = new ObjectInputStream(fis);

        @SuppressWarnings("unchecked")
        type returnValue = (type) ois.readObject();
        ois.close();

        return returnValue;
    }

    /** This method saves a string to a directory. */
    public static void savePlainText(String text, String directory) throws IOException {
        // Create the file if it doesn't exist
        if (!checkDirectory(directory)) {
            File file = new File(directory);
            file.createNewFile();
        }

        FileWriter writer = new FileWriter(directory);
        writer.write(text);
        writer.close();
    }

    /** This method loads a string from a directory. */
    public static String loadPlainText(String directory) throws IOException {
        if (directory == null)
            return null;

        return new String(Files.readString(Path.of(directory)));
    }

    /** @return <code>true</code> if a file exists in the directory. */
    private static boolean checkDirectory(String directory) {
        if (directory == null)
            return false;

        File file = new File(directory);
        return file.exists();
    }
}
