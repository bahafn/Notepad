package Saving;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Save {
    private Save() {
    }

    public static <type> void save(type objectToSave, String directory) throws IOException {
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

    public static <type> type load(String directory) throws IOException, ClassNotFoundException {
        if (!checkDirectory(directory))
            throw new FileNotFoundException("Couldn't find a file at " + directory + " directory.");

        FileInputStream fis = new FileInputStream(directory);
        ObjectInputStream ois = new ObjectInputStream(fis);

        type returnValue = (type) ois.readObject();
        ois.close();

        return returnValue;
    }

    private static boolean checkDirectory(String directory) {
        File file = new File(directory);
        return file.exists();
    }
}
