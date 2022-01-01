package Presenter;

// Programmers: Cara McNeil, Karyn Komatsu, Ran Yi, Sarah Kronenfeld
// Description: Prints information pertaining to a user's message information
// Date Created: 11/11/2020
// Date Modified: 19/11/2020


import Events.EventManager;
import Message.*;
import Person.PersonManager;


public class AttMessageMenu extends MessageMenu {


    public AttMessageMenu(PersonManager personManager, MessageManager messageManager, ChatManager chatManager,
                          EventManager eventManager) {
        super(personManager, messageManager, chatManager, eventManager);
    }

    /**
     * Prints the options for this menu.
     */
    @Override
    public void printMenuOptions() {
        super.printMenuOptions();
        System.out.println("View the announcement chat list -------------------- Enter '6'.");
        System.out.println("View the announcements in an announcement chat ----- Enter '7'.");
        System.out.println("Create a chat -------------------------------------- Enter '8'.");
        System.out.println("Create a group chat -------------------------------- Enter '9'.");
    }

    /**
     * Prompts user to enter username of the contact want to have chat with.
     */
    public void printContactUsernamePrompt(){
        System.out.println("Who do you want to chat with? Enter their username.");
    }

    /**
     * Prompts user to enter usernames of the contacts want to have chat with.
     */
    public void printContactUsernamesPrompt(){
        System.out.println("Who do you want to have a group chat with? Enter their usernames in a comma-separated list." +
                "\n(E.g. 'user1,user2,user3' without apostrophes (''). Please do not add spaces after commas)");
    }
    //Method below is left here in case we use it in Phase 2
    /**
     * Tell the User the chat is created and the ID.
     */
    public void printChatCreated(String chatID) {
        System.out.println("Chat created! The ID is: " + chatID);
    }

    /**
     * Only tell the User that the chat is created.
     */
    public void printChatCreated(){System.out.println("Chat created!"); }

    /**
     * Tells the User the ID.
     */
    public void printID(String chatID){System.out.println("The ID is: " + chatID);}

    /**
     * Tell the User that the chat was NOT created.
     */
    public void printChatNotCreated(){System.out.println("Whoops! The Chat was NOT created!");}

    /**
     * Tell the User that there already exists a Chat with same members as the input.
     */
    public void printChatExists(){System.out.println("That Chat already exists."); }

    /**
     * Tell the User that a Chat cannot be created by yourself.
     */
    public void printSoloChatNotAllowed(){System.out.println("You cannot create a Chat by yourself.");}
}
