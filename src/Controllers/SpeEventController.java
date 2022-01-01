package Controllers;

// Programmer: Karyn Komatsu,
// Description: Event Menu for Speaker users.
// Date Created: 01/11/2020
// Date Modified: 19/11/2020

import Events.EventManager;
import Events.RoomManager;
import Message.ChatManager;
import Message.MessageManager;
import Person.SpeakerManager;
import Presenter.SpeEventMenu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SpeEventController implements SubMenu {

    private String currentUserID;
    private int currentRequest;
    private SpeakerManager speakerManager;
    private MessageManager messageManager;
    private ChatManager chatManager;
    private EventManager eventManager;
    private SpeEventMenu presenter;
    Scanner input = new Scanner(System.in);

    public SpeEventController(String currentUserID, SpeakerManager speakerManager, MessageManager messageManager,
                              ChatManager chatManager, EventManager eventManager, RoomManager roomManager) {
        this.currentUserID = currentUserID;
        this.speakerManager = speakerManager;
        this.messageManager = messageManager;
        this.chatManager = chatManager;
        this.eventManager = eventManager;
        presenter = new SpeEventMenu(roomManager, eventManager, speakerManager);
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
            switch (currentRequest){
                case 0:
                    // return to main menu
                    break;
                case 1:
                    getOwnTalks();
                    break;
                case 2:
                    // Send message to all Attendees in an Event
                    presenter.printEventNamePrompt();
                    String eventNameB = SubMenu.readInput(input);
                    presenter.printContentPrompt();
                    String contentB = this.addSpeUsername(SubMenu.readInput(input));
                    this.eventMessage(eventNameB, contentB);
                    presenter.printMessageSent();
                    break;
                case 3:
                    // Send message to all Attendees in all of your Events
                    presenter.printContentPrompt();
                    String contentC = this.addSpeUsername(SubMenu.readInput(input));
                    this.allSpeakerEventsMessage(contentC);
                    presenter.printMessageSent();
                    break;
                case 4:
                    // Send message to all Attendees in some of your Events
                    presenter.printContentPrompt();
                    String contentD = this.addSpeUsername(SubMenu.readInput(input));
                    presenter.printEventNamesPrompt();
                    String eventNames = SubMenu.readInput(input);
                    String[] someSpeakerEvents = eventNames.split(",");
                    this.multipleEventsAnnouncement(someSpeakerEvents, contentD);
                    presenter.printMessageSent();
                    break;
            }
        }
        while (currentRequest != 0);
    }


    /**
     * Get the list of talk the user is scheduled to speak at
     * @return String chunk of formatted Talks
     */
    private void getOwnTalks() {
        try {
            String[] events = {};
            events = speakerManager.getEventList(currentUserID).toArray(events);
            presenter.printEventList(" you speak at", events);
        } catch (NullPointerException e) {
            presenter.printException(new NoDataException("event"));
        }
    }

    /**
     * Sends a Message to every user signed up for an event
     * @param eventName The name of the Event
     * @param messageContent Content of the Message to be sent
     */
    private void eventMessage(String eventName, String messageContent) {
        String eID = eventManager.getEventID(eventName);
        String messageID = messageManager.createMessage(eID, messageContent);
        String acID = eventManager.getEventChat(eID);
        chatManager.addMessageIds(acID, messageID);
    }

    /**
     * This is to send a message to all attendees of all of Speaker's events. For example, if Speaker wanted to
     * announce, "download so and so application for the our talk today" to all talks, this method proves useful. This
     * uses eventMessageForAttendees from above
     * @param events ArrayList of Event Names hosted by the speaker
     * @param messageContent String representing content of the message
     */

    private void  multipleEventsAnnouncement (String[] events, String messageContent) {

        try {
            for (String event : events) {
                eventMessage(event, messageContent);
            }
        } catch (InputMismatchException ime){
                System.out.println("You have entered an event name incorrectly.");
        }
    }


    /**
     * This is the same method from above, but only using messageContent as a parameter instead of the list of all
     * Speaker talks
     * @param messageContent
     */
    private void allSpeakerEventsMessage (String messageContent){
        String[] allTalks = {};
        allTalks = speakerManager.getSpeakerIdAllTalks(currentUserID).toArray(allTalks);
        multipleEventsAnnouncement(allTalks, messageContent);
    }

    /**
     *
     * @param content Content of the message
     * @return Content following with the sentence: ["Contact me using this username:"]\newline
     *                                              [username of the Speaker]
     */
    private String addSpeUsername (String content){
        return content + "\n" + "Contact me using this username:\n"
                + speakerManager.getCurrentUsername(currentUserID);
    }
}
