package Presenter;

import Events.EventManager;
import Events.RoomManager;
import Person.PersonManager;

public class SpeEventMenu extends EventMenu {

    public SpeEventMenu(RoomManager rooms, EventManager events, PersonManager persons) {
        super(rooms, events, persons);
    }

    /**
     * Prints the options for this menu.
     */
    @Override
    public void printMenuOptions() {
        System.out.println('\n' + "----- Speaker Event Menu -----");
        System.out.println("Return to Main Menu ----------------------------------------- Enter '0'.");
        System.out.println("View your events -------------------------------------------- Enter '1'.");
        System.out.println("Send message to all Attendees in an Event ------------------- Enter '2'.");
        System.out.println("Send message to all Attendees in all of your Events --------- Enter '3'.");
        System.out.println("Send message to all Attendees in a list of of your Events --- Enter '4'.");
        // TODO add print statements for all the other menu options
        
    }

    /**
     * Prints out an Exception thrown by the program to the user
     * @param e The exception
     */
    public void printException(Exception e) {
        System.out.println("\nSorry! That didn't work.");
        System.out.println(e.getMessage());
    }

    /**
     * Print String.
     */
    public void printString(String str) {
        System.out.println("str");
    }

    /**
     * Prompts user to enter eventName of the event want to send message to.
     */
    public void printEventNamePrompt() {
        System.out.println("Enter the NAME of the Event you want to send message to.");
    }

    /**
     * Prompts user to enter an ArrayList of eventName(s) of the one or more events
     * that they want to send the message to.
     */
    public void printEventNamesPrompt(){
        System.out.println("Enter the NAMES of Events that you want to send message to, using comma without space (,) to split.");
    }

    /**
     * Prompts user to enter CONTENT of the message.
     */
    public void printContentPrompt() {
        System.out.println("Enter the CONTENT of the message.");
    }

    /**
     * Tell the User the message is sent.
     */
    public void printMessageSent() {
        System.out.println("Messages are sent!");
    }

    // Adding back into SpeEventMenu what I had from yesterday. Please integrate or discard whatever you wish.
    // I'll be working on this right now too. This is just getting a glimpse.

    /**
     * Prints the options for this menu.
     *
     * @return true iff all menu options were printed
     *//*
    /*@Override
    public boolean printMenuOptions() {
        System.out.println("----- Speaker Event Menu -----");
        System.out.println("To return to Main Menu, Enter '0'.");
        System.out.println(("To see the schedule of your talks, Enter '1'."));
        System.out.println("To send a message to one conference attendee, including an organizer, Enter '2'.");
        System.out.println("To send a message to all attendees of only one of your talks, Enter '3'.");
        System.out.println("To send a message to all attendees of all of your talks, Enter '4'");
        return true;
    }*/


}




