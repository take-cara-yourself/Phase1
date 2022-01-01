package Controllers;

// Programmer: Cara McNeil, Sarah Kronenfeld
// Description: Classes that the Main Menu refers to
// Date Created: 01/11/2020
// Date Modified: 16/11/2020

import java.util.Scanner;

public interface SubMenu {

    /**
     * Prompts user to choose a menu option, takes the input and calls the corresponding method
     */
    void menuOptions();

    /**
     * Takes user input and calls appropriate methods, until user wants to return to Main Menu
     */
    void menuChoice();

    /**
     * Reads in lines of the input until it recieves an integer
     * @param in A scanner used to read in input
     * @return The integer it eventually receives
     */
    static int readInteger(Scanner in) {
        String i = in.nextLine();
        try {
            int returnValue = Integer.valueOf(i);
            return returnValue;
        } catch (NumberFormatException f) {
            return readInteger(in);
        }
    }

    /**
     * Reads in lines of the input until it recieves input
     * @param in A scanner used to read in input
     * @return The input it eventually receives
     */
    static String readInput(Scanner in) {
        String i = in.nextLine();
        while (i == null || i.equals("")) {
            i = in.nextLine();
        }
        return i;
    }
}
