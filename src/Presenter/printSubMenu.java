package Presenter;

import Controllers.NoDataException;

public interface printSubMenu {

    /**
     * Prints the options for this menu.
     */
    void printMenuOptions();

    /**
     * Prints out an Exception thrown by the program to the user
     * @param e The exception
     */
    default void printException(Exception e) {
        System.out.println("\nSorry! That didn't work.");
        System.out.println(e.getMessage());
        System.out.println("Please try again!\n\n");
    }


    default void printList (String[] list, String type) throws NoDataException {
        System.out.println();
        if (list != null && list.length > 0) {
            System.out.println("\n---");
            System.out.println(list[0]);
            for (int i = 1; i < list.length; i++) {
                System.out.println("\n" + list[i]);
            }
            System.out.println("---\n");
        }
        else {
            throw new NoDataException(type);
        }
    }
}
