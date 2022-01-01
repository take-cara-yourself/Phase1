package Controllers;

// Programmer: Cara McNeil
// Description: All the methods that take user input in the Login Menu
// Date Created: 01/11/2020
// Date Modified: 16/11/2020

import Person.PersonManager;
import Presenter.LoginMenu;

import java.util.Scanner;

public class LoginController implements SubMenu {
    private int currentRequest;
    private int accountChoice;
    private PersonManager manager;
    private LoginMenu presenter = new LoginMenu();
    public String username;
    public boolean loggedIn = false;
    Scanner input = new Scanner(System.in);

    public LoginController(PersonManager manager, int accountChoice) {
        this.manager = manager;
        this.accountChoice = accountChoice;
    }

    /**
     * Prompts user to choose a menu option, takes the input and calls the corresponding method
     */
    @Override
    public void menuOptions() {
        presenter.printMenuOptions();
        currentRequest = SubMenu.readInteger(input);
    }

    /**
     * Takes user input and calls appropriate methods, until user wants to return to Main Menu
     */
    @Override
    public void menuChoice() {
        do {
            menuOptions();
            switch (currentRequest) {
                case 0:
                    break; // The user has inputted 0
                case 1:
                    try {
                        login();
                        loggedIn = true;
                        currentRequest = 0;
                    } catch (InvalidChoiceException e) {
                        presenter.printException(e);
                    }
                    break;
                case 2:
                    try {
                        createAccount();
                    } catch (InvalidChoiceException e) {
                        presenter.printException(e);
                    }
                    break;
            }
        }
        while (currentRequest != 0);
    }

    /**
     * Prompts user to input username and password.
     */
    private void login() throws InvalidChoiceException {
        //int typePresenter = PersonController;
        presenter.printLoginPrompt();
        presenter.printUsernamePrompt();
        username = SubMenu.readInput(input);
        if(this.accountChoice != manager.typePerson(username)) {
            throw new InvalidChoiceException("account");
        }
        presenter.printPasswordPrompt();
        String password = SubMenu.readInput(input);
        if (manager.getCurrentUserID(username) != null)  {
            if(manager.confirmPassword(username, password)) {
                return;
            }
        }
        throw new InvalidChoiceException("account");
    }

    /**
     * Prompts user for relevant information and uses it to create a new account.
     */
    private void createAccount() throws InvalidChoiceException {
        presenter.printCreateAccountPrompt();
        presenter.printUsernamePrompt();
        username = SubMenu.readInput(input);
        if(username.contains(",")) {
            throw new InvalidChoiceException("username possible. Try again with NO comma(s)");
        }
        if (manager.getCurrentUserID(username) != null) {
            throw new OverwritingException("username");
        }
        presenter.printPasswordPrompt();
        String password = SubMenu.readInput(input);
        presenter.printNamePrompt();
        String name = SubMenu.readInput(input);
        presenter.printEmailPrompt();
        String email = SubMenu.readInput(input);
        if (manager.createAccount(name, username, password, email)) {
            presenter.printAccountCreationSuccessful();
        }
    }
}
