package Tests;

import java.io.IOException;

import Saving.Save;

import UI.Tap;

public class Tests {
    public static void main(String[] args) {
        Tap tap = new Tap();
        tap.setText("Hideo Kojima's Death Stranding is the first strand-type game.");

        try {
            Save.save(tap, "C:\\Users\\HP\\Desktop\\text.txt");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}