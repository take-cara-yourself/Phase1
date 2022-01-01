package Controllers;

// Programmers: Cara McNeil, Allen Kim, Eytan Weinstein
// Description: All the methods that take user input in the Attendee Event Menu
// Date Created: 01/11/2020
// Date Modified: 17/11/2020

import java.util.ArrayList;

import Events.CapacityException;
import Events.EventManager;
import Events.EventPermissions;
import Events.RoomManager;
import Person.AttendeeManager;
import Presenter.AttEventMenu;

import java.util.Scanner;

public class AttEventController implements SubMenu {

    private String currentUserID;
    private int currentRequest;
    private AttendeeManager attendeeManager;

    private RoomManager roomManager;
    private EventManager eventManager;
    private EventPermissions eventPermissions;

    private AttEventMenu presenter;
    Scanner input = new Scanner(System.in);

    public AttEventController(String currentUserID, AttendeeManager attendeeManager, RoomManager roomManager,
                              EventManager eventManager) {
        this.currentUserID = currentUserID;
        this.attendeeManager = attendeeManager;
        this.eventManager = eventManager;
        eventPermissions = new EventPermissions(roomManager, eventManager);
        this.roomManager = roomManager;
        presenter = new AttEventMenu(currentUserID, roomManager, eventManager, attendeeManager);
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
            switch(currentRequest) {
                case 0:
                    // return to main menu
                    break;
                case 1:
                    try {
                        String roomID = this.getRoomChoice();
                        if (roomID.equals("1")) {
                            presenter.printEventList();
                        } else {
                            presenter.printRoomEventList(roomManager.getEventIDs(roomID),
                                    roomManager.getRoomName(roomID));
                        }
                    } catch (InvalidChoiceException e) {
                        presenter.printException(e);
                    }
                    break;
                case 2:
                    presenter.printAddEventPrompt();
                    String addingEventInput = SubMenu.readInput(input);
                    try {
                        signupForEvent(addingEventInput);
                    } catch (InvalidChoiceException e) {
                        presenter.printException(e);
                    }
                    break;
                case 3:
                    presenter.printRemoveEventPrompt();
                    String removingEventInput = SubMenu.readInput(input);
                    try {
                         cancelSpotFromEvent(removingEventInput);
                    } catch (InvalidChoiceException e) {
                        presenter.printException(e);
                    }
                    break;
                case 4:
                    presenter.printAttendeeEventList();
                    break;
            }
        }
        while (currentRequest != 0);

    }

    // TODO change, delete and/or add to the methods below

    /**
     * Takes user input to pick a Room for which that user wishes to view the Events held there
     * @return the ID of the Room chosen
     */
    private String getRoomChoice() throws InvalidChoiceException {
        presenter.printRoomChoicePrompt();
        String room = SubMenu.readInput(input);
        if (room.equals("0")) {
            presenter.printRoomList();
            room = SubMenu.readInput(input);
        }
        if (room.equals("1")) {
            return "1";
        }
        if (roomManager.getRoomID(room) != null) {
            return roomManager.getRoomID(room);
        } else {
            throw new InvalidChoiceException("room");
        }
    }

    /**
     * Tries to sign user up for an Event
     * @param eventName The name of the Event the current user requested to sign up for
     */
    private void signupForEvent (String eventName) throws InvalidChoiceException  {
        String event = eventManager.getEventID(eventName);
        String room = roomManager.getEventRoom(event);
        if (room == null || event == null) {
            throw new InvalidChoiceException("event");
        }
        try {
            eventPermissions.signUpForEvent(currentUserID, event, room);
            boolean eventAddedToPerson = attendeeManager.signUpForEvent(currentUserID, event);
            attendeeManager.addAnChat(currentUserID, eventManager.getEventChat(event));
            if (eventAddedToPerson) {
                presenter.printEventAdded();
            }
        } catch (CapacityException c) {
            presenter.printEventFull();
        }
    }

    /**
     * Remove this user from Event
     * @param eventName The name of the Event the current user requested to cancel
     */
    private void cancelSpotFromEvent(String eventName) throws InvalidChoiceException {
        String event = eventManager.getEventID(eventName);
        if (event == null) {
            throw new InvalidChoiceException("event");
        }

        boolean personRemovedFromEvent = eventPermissions.removeFromEvent(currentUserID, event);
        boolean eventRemovedFromPerson = attendeeManager.removeSpotFromEvents(currentUserID, event);
        attendeeManager.removeAnChat(currentUserID, eventManager.getEventChat(event));
        if(personRemovedFromEvent && eventRemovedFromPerson) {
            presenter.printEventRemoved();
        }
    }
}
