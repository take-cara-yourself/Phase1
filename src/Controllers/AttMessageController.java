package Controllers;

// Programmer: Ran Yi, Sarah Kronenfeld, Karyn Komatsu
// Description: For current Attendee (or Organizer) to view chat and message, create chat and send message.
// Date Modified: 19/11/2020


import Events.EventManager;
import Message.ChatManager;
import Message.MessageManager;
import Person.AttendeeManager;
import Presenter.AttMessageMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttMessageController extends MessageController {
    private AttendeeManager attendeeManager;
    private AttMessageMenu presenter;

    public AttMessageController(String currentUserID, AttendeeManager attendeeManager, MessageManager mManager,
                                ChatManager cManager, EventManager eventManager) {
        super(currentUserID, attendeeManager, mManager, cManager, eventManager);
        this.attendeeManager = attendeeManager;
        presenter = new AttMessageMenu(attendeeManager, mManager, cManager, eventManager);
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
                    // return to main menu
                    break;
                case 1: //Check your inbox
                    inBox();
                    break;
                case 2: //Check your sent box
                    sentBox();
                    break;
                case 3: //View the chat list
                    viewChats(attendeeManager.getChats(currentUserID));
                    break;
                case 4: //View the messages in a chat
                    displayChat();
                    break;
                case 5: //Send a message
                    sendMessageChoice();
                    break;
                case 6: //View the announcement chat list
                    viewChats(attendeeManager.getAnChats(currentUserID));
                    break;
                case 7:
                    //View the announcements in an announcement chat
                    displayChat("Announcement");
                    break;
                case 8: //Create a chat
                    createChatChoice();
                    break;
                case 9: //Create a group chat
                    createGroupchatChoice();
                    break;
            }
        }
        while (currentRequest != 0);
    }

    // Option 8

    /**
     * Creates a new Chat
     */
    private void createChatChoice() {
        presenter.printContactUsernamePrompt();
        try {
            String chatID = createChat(SubMenu.readInput(input));
            if(chatID!=null){presenter.printID(chatID);}
        } catch (InvalidChoiceException e) {
            presenter.printException(e);
        }
    }


    /**
     * Creates new Chat if contact is on contactList
     * @param contactUsername the username of the contact the current user wants create a Chat with
     * @return chatID iff new Chat was created and added to user's Chat list and contact's contactList
     */

    private String createChat(String contactUsername) throws InvalidChoiceException {
        String contactID = attendeeManager.getCurrentUserID(contactUsername);
        if (contactID == null) {
            throw new InvalidChoiceException("user");
        }
        if (chatManager.existChat(currentUserID, contactID)){
            String chatID = chatManager.findChat(currentUserID, contactID);
            presenter.printChatNotCreated();
            presenter.printChatExists();
            return chatID;
        }
        else if (currentUserID.equals(contactID)){
            presenter.printChatNotCreated();
            presenter.printSoloChatNotAllowed();
            return null;
        }else {
            String chatID = chatManager.createChat(currentUserID, contactID);
            attendeeManager.addChat(contactID, chatID);
            attendeeManager.addChat(currentUserID, chatID);
            presenter.printJobDone();
            presenter.printChatCreated();
            return chatID;
        }
    }

    // Option 9

    /**
     * Creates a new GroupChat
     */
    private void createGroupchatChoice() {
        presenter.printContactUsernamesPrompt();
        String contacts = SubMenu.readInput(input);
        String[] a = contacts.split(",");
        ArrayList<String> contactlist = new ArrayList<>(Arrays.asList(a));
        if (contactlist.isEmpty()) {
            presenter.printException(new InvalidChoiceException("user"));
            return;
        }
        try {
            ArrayList<String> cs = new ArrayList<String>();
            for (String contact: contactlist) {
                cs.add(attendeeManager.getCurrentUserID(contact));
            }
            String groupChatID = createGroupChat(cs);
            if  (groupChatID != null){
                for (String contact: cs) {
                    attendeeManager.addChat(contact, groupChatID);
                }
                presenter.printJobDone();
                presenter.printChatCreated();
                presenter.printID(groupChatID);
            }
        } catch (NoDataException e) {
            presenter.printChatNotCreated();
            presenter.printSoloChatNotAllowed();
        } catch (NullPointerException e) {
            presenter.printException(new InvalidChoiceException("user"));
        } catch (InvalidChoiceException e) {
            presenter.printChatNotCreated();
            presenter.printException(e);
        }
    }

    /**
     * Create a new group chat if contacts are in this user's contactlist.
     * @param contactIDs the ArrayList of contacts' IDs.
     * @return the chatID.
     */
    private String createGroupChat(ArrayList<String> contactIDs) throws InvalidChoiceException {
        if (this.chatManager.existChat(currentUserID, contactIDs)) { //if there already exist a desired Chat
            String chatID = chatManager.findChat(currentUserID, contactIDs);
            presenter.printID(chatID);
            throw new OverwritingException("chat");
        }
        else if ((contactIDs.size()==1)&&(contactIDs.contains(currentUserID))){ //if it's trying to create Chat by itself
            throw new NoDataException("user");
        }
        else {
            String chatID = chatManager.createChat(currentUserID, contactIDs);
            personManager.addChat(currentUserID, chatID);
            for (String contact: contactIDs){personManager.addChat(contact, chatID);}
            return chatID;
        }
    }

}
