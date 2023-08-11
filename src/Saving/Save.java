package Saving;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;

public class Save {
    private Save() {}

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

    private static boolean checkDirectory(String directory) {
        File file = new File(directory);
        return file.exists();
    }
}
