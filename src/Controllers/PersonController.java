package Controllers;

// Programmer: Cara McNeil, Sarah Kronenfeld
// Description: abstract main account page for other controllers to inherit from
// Date Created: 01/11/2020
// Date Modified: 19/11/2020

import Events.EventManager;
import Events.RoomManager;
import Message.ChatManager;
import Message.MessageManager;
import Person.PersonManager;

import java.util.Scanner;

abstract public class PersonController {
    Scanner input = new Scanner(System.in);
    public String currentUserID;
    //public int typePerson;
    private PersonManager manager;
    protected RoomManager roomManager;
    protected EventManager eventManager;
    protected MessageManager messageManager;
    protected ChatManager chatManager;
    private LoginController loginController;
    private int accountChoice;
    public boolean loggedIn =  false;


    public PersonController(PersonManager manager, RoomManager roomManager, EventManager eventManager,
                            MessageManager messageManager, ChatManager chatManager, int accountChoice) {
        this.manager = manager;
        this.roomManager = roomManager;
        this.eventManager = eventManager;
        this.messageManager = messageManager;
        this.chatManager = chatManager;
        this.accountChoice = accountChoice;
    }

    /**
     * Allows user to login and see their account. Terminates if the user chooses to logout.
     */
    public void run() {
        loginController = new LoginController(manager, accountChoice);
        loginController.menuChoice();
        if (loginController.loggedIn) {
            loggedIn = true;
            currentUserID = manager.getCurrentUserID(loginController.username);
        }
    }
}
