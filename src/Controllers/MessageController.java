package Controllers;

import Events.EventManager;
import Message.ChatManager;
import Message.MessageManager;
import Person.PersonManager;
import Presenter.MessageMenu;

import java.util.ArrayList;
import java.util.Scanner;

// Programmer: Ran Yi, Sarah Kronenfeld
// Description: For current User to view chat and message, create chat and send message.
// Date Modified: 19/11/2020

public class MessageController implements SubMenu {
    protected String currentUserID;
    protected PersonManager personManager;
    protected ChatManager chatManager;
    protected MessageManager messageManager;
    protected EventManager eventManager;
    private MessageMenu presenter;
    Scanner input = new Scanner(System.in);
    protected int currentRequest;

    public MessageController (String currentUserID, PersonManager personManager, MessageManager mManager,
                              ChatManager cManager, EventManager eventManager) {
        this.currentUserID = currentUserID;
        this.personManager = personManager;
        this.messageManager = mManager;
        this.chatManager = cManager;
        this.eventManager = eventManager;
        presenter = new MessageMenu(personManager, messageManager, chatManager, eventManager);
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
                    try {
                        viewChats(personManager.getChats(currentUserID));
                    } catch (NullPointerException e) {
                        System.out.println("huh?");
                    }
                    break;
                case 4: //View the messages in a chat
                    displayChat();
                    break;
                case 5: //Send a message
                    sendMessageChoice();
                    break;
            }
        }
        while (currentRequest != 0);
    }

    // Option 1

    /**
     * Show all the messages this user received in presenter, **sorted by datetime.
     */
    protected void inBox(){
        try {
            ArrayList<String> receivedMessages = new ArrayList<>();
            for (String message: messageManager.getMessageIDs()){
                if (messageManager.getRecipientId(message).equals(currentUserID)){
                    receivedMessages.add(message);
                }
            }

            presenter.printList(presenter.formatMessages(receivedMessages), "message");
        } catch (NullPointerException e) {
            presenter.printException(new NoDataException("message"));
        } catch (NoDataException e) {
            presenter.printException(e);
        }
    }

    // Option 2

    /**
     * Show all the messages this user sent in presenter, **sorted by datetime.
     */
    protected void sentBox(){
        try {
            ArrayList<String> sentMessages = new ArrayList<>();
            for (String message: messageManager.getMessageIDs()){
                if (messageManager.getSenderID(message).equals(currentUserID)){
                    sentMessages.add(message);
                }
            }

            presenter.printList(presenter.formatMessages(sentMessages), "message");
        } catch (NullPointerException e) {
            presenter.printException(new NoDataException("message"));
        } catch (NoDataException e) {
            presenter.printException(e);
        }
    }

    // Option 3

    /**
     * Show the chatList with this User inside with its ID and participants' IDs.
     * Chats formatted like so:
     *              [ID]: [ID of the chat]\new line
     *              [Participants]: [ID of the Participants]\newline
     *              [ID]: [ID of the chat]\new line
     *              [Participants]: [ID of the Participants]\newline
     *              ...
     *
     */
    protected void viewChats(ArrayList<String> chatIDs) {
        try {
            presenter.printFormattedChatList(chatIDs);
        } catch (InvalidChoiceException e) {
            presenter.printException(e);
        }
    }

    // Option 4

    /**
     * Displays a chat
     * @param type The type of chat it is (e.g. "" for a normal chat, "Announcement" for an AnnouncementChat)
     */
    protected void displayChat(String type) {
        presenter.printChatIdPrompt(type);
        try {
            presenter.printChat(SubMenu.readInput(input));
        } catch (InvalidChoiceException e) {
            presenter.printException(e);
        }
    }

    /**
     * Displays a chat
     */
    protected void displayChat() {
        displayChat("");
    }


    /**
     * Sends a new Message
     */
    protected void sendMessageChoice() {
        presenter.printChatIdMessagePrompt();
        String chatId = SubMenu.readInput(input);
        presenter.printContentPrompt();
        String content = SubMenu.readInput(input);
        try {
            sendMessage(chatId, content);
            presenter.printJobDone();
        } catch (InvalidChoiceException e) {
            presenter.printException(e);
        }
    }


    // Option 5

    /**
     * Creates new Message for existing Chat (1 to 1 chat or group chat both use this.)
     * @param chatID The chatID of the Chat the current user want's to send a Message to
     * @param messageContent The contents of the message the current user wants to send
     */
    protected void sendMessage(String chatID, String messageContent) throws InvalidChoiceException {
        if (chatManager.isEmpty()) {
            throw new NoDataException("chat");
        }
        if (chatManager.isChatIDNull(chatID)) {
            throw new InvalidChoiceException("chat");
        }
        for (String receiverID : chatManager.getPersonIds(chatID)){
            if (!receiverID.equals(currentUserID)){
                String messageID = messageManager.createMessage(currentUserID, receiverID, messageContent);
                chatManager.addMessageIds(chatID,messageID);
            }
        }
    }

}
