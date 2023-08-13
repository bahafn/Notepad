package Saving;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class has the <code>save</code> and <code>load</code> method which are
 * used to write and read objects to and from files.
 */
public final class Save {
    private Save() {
    }

    /**
     * This method takes an <code>Object</code> of an type that implements
     * <code>Serializable</code> and writes on a file in a spcified directory.
     * 
     * @param <type>       the type of the <code>Object</code> we want to write
     *                     (must implement Serializable)
     * @param objectToSave the <code>Object</code> we want to save
     * @param directory    the directory we want to write to
     * @throws IOException if the file wasn't found
     */
    public static <type extends Serializable> void save(type objectToSave, String directory) throws IOException {
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
            throw new FileNotFoundException("Couldn't find a file at " + directory + " directory.");

        FileInputStream fis = new FileInputStream(directory);
        ObjectInputStream ois = new ObjectInputStream(fis);

        type returnValue = (type) ois.readObject();
        ois.close();

        return returnValue;
    }

    public static void savePlainText(String text, String directory) throws IOException {
        FileWriter writer = new FileWriter(directory);
        writer.write(text);
        writer.close();
    }

    private static boolean checkDirectory(String directory) {
        File file = new File(directory);
        return file.exists();
    }
}
