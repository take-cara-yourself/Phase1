package Presenter;

// Programmers: Cara McNeil, Eytan Weinstein
// Description: Prints information pertaining to an Organizer's Event planning
// Date Created: 13/11/2020
// Date Modified: 19/11/2020

import Events.EventManager;
import Events.EventType;
import Events.RoomManager;
import Person.PersonManager;

public class OrgEventMenu extends EventMenu {

    public OrgEventMenu(RoomManager rooms, EventManager events, PersonManager persons) {
        super(rooms, events, persons);
    }

    /**
     * Prints the options for this menu.
     */
    @Override
    public void printMenuOptions() {
        System.out.println("\n----- Organizer Event Menu -----");
        System.out.println("To return to Main Menu, Enter '0'.");
        System.out.println("To create a new room, Enter 1");
        System.out.println("To create a new event, Enter 2");
        System.out.println("To create a speaker account, Enter 3");
        System.out.println("To make an announcement to the attendees of an event, Enter 4\n");
    }

    // OPTION 1

    /**
     * Prompts user to input the name of the Room they wish to add
     */
    public void addRoomPrompt() {
        System.out.println("\nTo create a new room, please fill in the following information:");
    }

    /**
     * Prompts user to input the name of the Room they wish to add
     */
    public void roomNamePrompt() {
        System.out.println("\nWhat is the name of the room you want to create?");
    }

    /**
     * Prompts user to input the capacity of the Room they wish to add
     */
    public void roomCapacityPrompt() {
        System.out.println("\nWhat is the capacity of the room you want to create?");
    }

    // OPTION 2

    /**
     * Prompts the user to create an Event
     */
    public void printCreateEventPrompt(){
        System.out.println("\nTo create a new event, please fill in the following information:");
    }

    /**
     * Prompts the user to choose the type of Event they wish to create
     */
    public void printEventTypePrompt(){
        System.out.println("\nWhat kind of event do you want to create? (Enter 0 for options)");
    }

    /**
     * Presents to the user the list of possible Event types to choose from
     */
    public void printEventTypes() {
        System.out.println();
        for(EventType t: EventType.values()) {
            System.out.println(t.toString());
        }
        System.out.println();
    }

    /**
     * Prompts the user to choose a Room for an Event (by name)
     */
    public void printRoomNamePrompt(){
        System.out.println("\nWhich room is this event held in? (Enter 0 for options)");
    }

    /**
     * Prompts the user to name the new Event
     */
    public void printEventNamePrompt(){
        System.out.println("\nWhat is the name of this event?");
    }

    /**
     * Prompts the user to enter a description for the new Event
     */
    public void printDescriptionPrompt(){
        System.out.println("\nPlease type a brief description for this new event:");
    }

    /**
     * Prompts the user to enter a start time for the new Event
     */
    public void printStartTimePrompt(){
        System.out.println("\nAt what time will the event start?");
        System.out.println("\n(Please enter the exact date and time of this event in 24-hour clock notation. \nFor " +
                "example, to schedule an event at 11:30 p.m. on March 4, 2020, you would type the date, followed by " +
                "a space, \nfollowed by the 24-hour time, as follows: '2020-03-04 23:30'. \nBe slow and careful!");
    }

    /**
     * Notifies the user that their start time entry was invalid
     */
    public void printDateError(){
        System.out.println("\n(This is not a valid date. Try again. Be slow and careful!");
    }

    /**
     * Prompts the user to add the username of the Speaker at this new Event
     */
    public void printSpeakerUsernamePrompt(){
        System.out.println("\nWhat is the username of the speaker in charge of the event?");
    }

    /**
     * Notifies the user that a Talk was not created because another is already scheduled
     */
    public void printCapacityError(){
        System.out.println("\nThis talk could not be added. Another talk is scheduled in this room at that time.");
    }

    // OPTION 3

    /**
     * Prompts the user to add a Speaker account
     */
    public void printAddSpeakerPrompt(){
        System.out.println("\nTo create a new speaker account, please fill in the following information:");
    }

    /**
     * Prompts the user to enter a name for the Speaker
     */
    public void printAddNamePrompt(){
        System.out.println("\nWhat is the speaker's full name?");
    }

    /**
     * Prompts the user to add a password for the Speaker
     */
    public void printAddPasswordPrompt(){
        System.out.println("\nPlease enter a password for the speaker:");
    }

    /**
     * Prompts the user to add a username for the Speaker
     */
    public void printAddUsernamePrompt(){
        System.out.println("\nPlease enter a username for the speaker:");
    }

    /**
     * Prompts the user to add an email for the Speaker
     */
    public void printAddEmailPrompt(){
        System.out.println("\nWhat is the speaker's e-mail address?");
    }

    // OPTION 4

    /**
     * Prompts the user to make an announcement about an Event
     */
    public void printEventMessageIntro(){
        System.out.println("\nTo make an announcement about an event, please fill in the following information:");
    }

    /**
     * Prompts the user to enter the content of the announcement
     */
    public void printMessageContentPrompt(){
        System.out.println("\nPlease enter your announcement below");
    }

}
