package Presenter;

// Programmers: Cara McNeil,
// Description: Prints information pertaining to a user's login information
// Date Created: 11/11/2020
// Date Modified: 13/11/2020

public class LoginMenu implements printSubMenu {

    /**
     * Prints the options for this menu.
     */
    @Override
    public void printMenuOptions() {
        System.out.println("\n----- Login Menu -----");
        System.out.println("To return to start page, Enter '0'.");
        System.out.println("To Login, Enter '1'.");
        System.out.println("To Create a new account, Enter '2'.");
    }

    /**
     * Prompts user to enter username
     */
    public void printUsernamePrompt() {
        System.out.print("Enter username: ");
    }

    /**
     * Prompts user to enter password
     */
    public void printPasswordPrompt() {
        System.out.print("Enter password: ");
    }

    /**
     * Prompts user to enter name
     */
    public void printNamePrompt() {
        System.out.print("Enter full name: ");
    }

    /**
     * Prompts user to enter email
     */
    public void printEmailPrompt() {
        System.out.print("Enter email address: ");
    }

    /**
     * Prints instructions for user to create account
     */
    public void printCreateAccountPrompt() {
        System.out.println("\nTo create an account, select a username and password, then enter your full name and email " +
                "address. ");
    }

    /**
     * Prints confirmation that an account was created.
     */
    public void printAccountCreationSuccessful() {
        System.out.println("Account creation successful! Redirecting to Login Menu..." + '\n');
    }

    /**
     * Prints instructions for user to login
     */
    public void printLoginPrompt() {
        System.out.println("\nTo login, enter the following: ");
    }


    /**
     * Prints confirmation that Login was successful
     */
    public void printLoginSuccessful() {
        System.out.println("Login Successful! Redirecting back to account Main Menu..." + '\n');
    }
}
