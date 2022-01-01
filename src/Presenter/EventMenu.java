package Presenter;

import Controllers.InvalidChoiceException;
import Controllers.NoDataException;
import Events.EventManager;
import Events.RoomManager;
import Person.PersonManager;

public abstract class EventMenu implements printSubMenu {
    RoomManager rooms;
    EventManager events;
    PersonManager persons;

    public EventMenu(RoomManager rooms, EventManager events, PersonManager persons) {
        this.rooms = rooms;
        this.events = events;
        this.persons = persons;
    }

    public void printRoomList(){
        try {
            printList(rooms.getRoomNames(), "room");
        }
        catch (NoDataException e) {
            printException(e);
        }
    }

    public void printEventList() {
        printEventList("", events.getEventIDs());
    }

    public void printEventList(String condition, String[] eventIDList) {
        System.out.println("\n-EVENTS" + condition.toUpperCase() + "-");
        try {
            String[] events = new String[eventIDList.length];
            for (int i  = 0; i < eventIDList.length; i++) {
                events[i] = formatEvent(eventIDList[i]);
            }
            printList(events, "event");
        }
        catch (NullPointerException e) {
            printException(new InvalidChoiceException("event"));
        }
        catch (InvalidChoiceException e) {
            printException(e);
        }
    }

    public String formatEvent(String eventID) throws InvalidChoiceException  {
        try {
            StringBuilder e = new StringBuilder();
            e.append(events.getEventType(eventID).toString());
            e.append(": ");
            e.append(events.getEventName(eventID));
            e.append("\nby ");
            e.append(persons.getName(events.getSpeakerID(eventID)));
            e.append(" in room ");
            e.append(rooms.getEventRoom(eventID));
            e.append("\nfrom ");
            e.append(events.getStartTime(eventID));
            e.append(" to ");
            e.append(events.getEndTime(eventID));
            e.append("\n");
            e.append(events.getDescription(eventID));
            return e.toString();
        } catch (NullPointerException n) {
            throw new InvalidChoiceException("event");
        }
    }

}
