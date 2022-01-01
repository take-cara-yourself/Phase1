package Controllers;

// Programmers: Cara McNeil, Sarah Kronenfeld, Eytan Weinstein
// Description: All the methods that take user input in the Organizer Event Menu
// Date Created: 01/11/2020
// Date Modified: 19/11/2020

import Events.EventManager;
import Events.EventPermissions;
import Events.EventType;
import Events.RoomManager;
import Message.ChatManager;
import Message.MessageManager;
import Person.PersonManager;
import Person.SpeakerManager;
import Presenter.OrgEventMenu;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OrgEventController implements SubMenu {

    private String currentUserID;
    private int currentRequest;
    private PersonManager personManager;
    private SpeakerManager speakerManager;
    private RoomManager roomManager;
    private EventManager eventManager;
    private EventPermissions eventPermissions;
    private MessageManager messageManager;
    private ChatManager chatManager;
    private OrgEventMenu presenter;
    Scanner input = new Scanner(System.in);

    public OrgEventController(String currentUserID, PersonManager personManager, SpeakerManager speakerManager,
                              RoomManager roomManager, EventManager eventManager, MessageManager messageManager,
                              ChatManager chatManager) {
        this.currentUserID = currentUserID;
        this.personManager = personManager;
        this.speakerManager = speakerManager;
        this.roomManager = roomManager;
        this.eventManager = eventManager;
        eventPermissions = new EventPermissions(roomManager, eventManager);
        this.messageManager = messageManager;
        this.chatManager = chatManager;
        presenter = new OrgEventMenu(roomManager, eventManager, personManager);
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
                    break;
                case 1:
                    try {
                        addRoomPrompt();
                    } catch (InvalidChoiceException e) {
                        presenter.printException(e);
                    }
                    break;
                case 2:
                    try {
                        createEventPrompt();
                    } catch (InvalidChoiceException e) {
                        presenter.printException(e);
                    }
                    break;
                case 3:
                    try {
                        addSpeakerPrompt();
                    } catch (InvalidChoiceException e) {
                        presenter.printException(e);
                    }
                    break;
                case 4:
                    eventMessagePrompt();
                    break;
            }
        }
        while (currentRequest != 0);
    }

    // OPTION 1

    /**
     * Prompts the user to input the information for the Room they wish to add
     */
    private void addRoomPrompt() throws InvalidChoiceException {
        presenter.addRoomPrompt();
        presenter.roomNamePrompt();
        String name = SubMenu.readInput(input);
        if (name.equals("1") || roomManager.contains(name)) {
            throw new OverwritingException("room");
        }
        presenter.roomCapacityPrompt();
        int capacity = SubMenu.readInteger(input);
        this.addRoom(name, capacity);
    }

    /**
     * Adds a room to the list of rooms in this convention
     * @param name The name of the new Room in the convention (likely its number)
     * @param capacity The capacity of the new Room in the convention
     * @return true iff Room was added to the convention successfully
     */
    private boolean addRoom(String name, int capacity) {
        return this.roomManager.addRoom(name, capacity) != null;
    }

    // OPTION 2

    /**
     * Prompts the user to input the information for the Event they wish to add
     */
    private void createEventPrompt() throws InvalidChoiceException {
        presenter.printCreateEventPrompt();
        presenter.printEventTypePrompt();
        EventType type = chooseEventType();
        presenter.printRoomNamePrompt();
        String roomName = chooseRoom();
        presenter.printEventNamePrompt();
        String eventName = SubMenu.readInput(input);
        presenter.printDescriptionPrompt();
        String eventDescription = SubMenu.readInput(input);
        presenter.printStartTimePrompt();
        LocalDateTime start = chooseStartTime();
        presenter.printSpeakerUsernamePrompt();
        String speakerUsername = SubMenu.readInput(input);
        boolean created = createEvent(eventName, speakerUsername, start, eventDescription, roomName, type);
        if (!created) {
            presenter.printCapacityError();
        }
    }

    /**
     * Prompts the user to choose a type for the Event they wish to add
     * @return the type of event they have chosen (as an EventType object)
     */
    private EventType chooseEventType() throws InvalidChoiceException {
        String type = SubMenu.readInput(input);
        if (type.equals("0")) {
            presenter.printEventTypes();
            type = SubMenu.readInput(input);
        }
        try {
            return EventType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidChoiceException("event type");
        }
    }

    /**
     * Prompts the user to choose a Room from the list of existing Rooms
     * @return The name of the Room they have chosen
     */
    private String chooseRoom() throws InvalidChoiceException {
        String name = SubMenu.readInput(input);
        if (name.equals("0")) {
            presenter.printRoomList();
            name = SubMenu.readInput(input);
        }
        if (roomManager.getRoomID(name) != null) {
            return name;
        }
        else {
            throw new InvalidChoiceException("room");
        }
    }

    /**
     * Prompts the user to choose a valid start time for the new Event
     * @return The start time as a LocalDateTime object
     */
    private LocalDateTime chooseStartTime() {
        String time = SubMenu.readInput(input);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(time, formatter);
        }
        catch (DateTimeParseException exc) {
            presenter.printDateError();
            presenter.printStartTimePrompt();
            return chooseStartTime();
        }
    }

    /**
     * Creates a new Talk in this convention; also creates a new chat for this Talk and sets the Talk's chatID to the
     * ID of this new chat.
     * @param name The name of the Talk
     * @param speaker The username of the Speaker at the Talk
     * @param start The start time of the Talk
     * @param description A description for the Talk
     * @param room The name of the Room the Talk is in
     * @return true iff the Talk was created successfully
     */
    private boolean createEvent(String name, String speaker, LocalDateTime start, String description, String room,
                                EventType type)
            throws InvalidChoiceException {
        String roomID = roomManager.getRoomID(room);
        if (roomID == null) {
            throw new InvalidChoiceException("room");
        }

        String speakerID = speakerManager.getCurrentUserID(speaker);
        if (speakerID == null) {
            throw new InvalidChoiceException("speaker");
        }

        if (eventManager.contains(name)) {
            throw new OverwritingException("event");
        }

        if (eventPermissions.checkConflicts(start, type, roomID)) {
            String eventID = addEvent(name, speakerID, start, description, type);

            roomManager.addEvent(roomID, eventID);
            return true;
        }
        return false;
    }

    /**
     * Helper method - adds a newly created Event into the system
     * @param speakerID The ID of the speaker holding the event
     */
    private String addEvent(String name, String speakerID, LocalDateTime start, String description,
                            EventType type) {

        String eventID = eventManager.addEvent(name, speakerID, start, description, type);


        String announcementChatID = chatManager.createAnnouncementChat(eventID, new ArrayList<String>());
        eventManager.setEventChat(eventID, announcementChatID);

        this.updateSpeakerChatWithAnnouncement(speakerID, announcementChatID);
        this.updateSpeakerChat(speakerID, announcementChatID);
        speakerManager.addTalk(eventID, speakerID);
        speakerManager.addTalkIdToDictionary(speakerID, eventID, eventManager.getEventName(eventID));


        return eventID;
    }

    /**
     * This is a helper method for the methods above; updates Speaker's chat list
     * @param personID The  id of the person
     * @param chatID
     */
    private void updateSpeakerChat(String personID, String chatID) {
        this.speakerManager.addChat(personID, chatID);
    }

    /**
     * This is helper method for the methods above; updates Speaker's announcementChatIDs
     * @param personID the id of the person
     * @param announcementChatID the id of the annoucnemchat
     */
    private void updateSpeakerChatWithAnnouncement(String personID, String announcementChatID) {
        this.speakerManager.addAnnouncementChats(personID, announcementChatID);
    }

    // OPTION 3

    /**
     * Prompts the user to input the information required to create a new Speaker account
     */
    public void addSpeakerPrompt() throws InvalidChoiceException{
        presenter.printAddSpeakerPrompt();
        presenter.printAddNamePrompt();
        String name = SubMenu.readInput(input);
        presenter.printAddEmailPrompt();
        String email = SubMenu.readInput(input);
        presenter.printAddUsernamePrompt();
        String username = SubMenu.readInput(input);
        presenter.printAddPasswordPrompt();
        String pass = SubMenu.readInput(input);
        this.createSpeaker(name, username, pass, email);
    }

    /**
     * Creates a new Person.Speaker account and adds it to the system
     * @param name The name of the Speaker
     * @param username The username of the Speaker
     * @param password The password of the Speaker
     * @param email The email of the Speaker
     * @return true iff a new Person.Speaker object was created
     */
    public void createSpeaker(String name, String username, String password, String email) throws OverwritingException {
        if (!speakerManager.findPerson(username)) {
            speakerManager.createAccount(name, username, password, email);
        }
        else {
            throw new OverwritingException("account");
        }
    }

    // OPTION 4

    /**
     * Prompts the user to input the information required to create an Event Message
     */
    public void eventMessagePrompt() {
        presenter.printEventMessageIntro();
        presenter.printEventNamePrompt();
        String name = SubMenu.readInput(input);
        presenter.printMessageContentPrompt();
        String content = SubMenu.readInput(input);
        eventMessage(name, content);
    }

    /**
     * Adds a Message with content content the AnnouncementChat contained within the Event with eventName
     * @param eventName The name of the Event
     */
    private void eventMessage(String eventName, String messageContent){
        String eventId =  eventManager.getEventID(eventName); // eventId
        String ev = eventManager.getEventChat(eventId); // chatid
        String m = messageManager.createMessage(eventId, messageContent); // creating message for event
        chatManager.addMessageIds(ev,m);// adding message to event chat
    }


}
