package Tests;

import java.io.IOException;

import Saving.Save;

public class Tests {
    public static void main(String[] args) {
        String text = "Hideo Kojima";

        try {
            Save.savePlainText(text, "C:\\Users\\HP\\Desktop\\text.txt");
            System.out.println("Saved.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem.");
        }
    }
}