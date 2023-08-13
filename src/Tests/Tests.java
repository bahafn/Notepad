package Tests;

import java.io.IOException;

import Saving.Save;

import UI.Tap;
import UI.UICreator;

public class Tests {
    public static void main(String[] args) {
        Tap tap = new Tap();
        tap.setText("Baha Al-Khateeb");
        tap.setFont(UICreator.DEFAULT_FONT.deriveFont(2f));

        try {
            Save.save(tap, "\\Projects");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Tap tap2 = Save.load("C:\\Users\\HP\\Desktop\\text.txt");
            System.out.println(tap2.getText());
            System.out.println(tap2.getFont());
        } catch (IOException ioe) {
            System.out.println("file not found.");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("class not found.");
        }
    }
}