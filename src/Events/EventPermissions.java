package Events;


import java.time.LocalDateTime;
import java.util.ArrayList;

// Contributors: Sarah Kronenfeld, Eytan Weinstein
// Last edit: Nov 19 2020

// Architecture Level - Use Class

public class EventPermissions {

    private RoomAccess roomAccess;
    private EventAccess eventAccess;

    /**
     * Constructor for a RoomPermissions object
     */
    public EventPermissions (RoomAccess roomAccess, EventAccess eventAccess) {
        this.roomAccess = roomAccess;
        this.eventAccess = eventAccess;
    }

    /**
     * Signs an individual Attendee up for an Event
     * @param personID The ID of the Attendee
     * @param eventID The ID of the Event
     * @param roomID  The ID of the Room the Event is in
     * @return Whether the Event exists
     */
    public boolean signUpForEvent(String personID, String eventID, String roomID) throws CapacityException {
        try {
            if (checkRoomCapacity(eventID, roomID)) {
                Event event = eventAccess.getEvent(eventID);
                event.addAttendee(personID);
                return true;
            }
            throw new CapacityException();
        } catch (NullPointerException n) {
            return false;
        }
    }

    /**
     * Takes an individual Attendee off an Event's list of attendees
     * @param personID The Attendee
     * @param ID The Event
     * @return whether the Attendee was removed from the Event (true or false)
     */
    public boolean removeFromEvent(String personID, String ID) {
        Event event = eventAccess.getEvent(ID);
        if (event != null) {
            event.removeAttendee(personID);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if an event is full
     * @param eventID The ID of the Event
     * @param roomID The ID of the Room the Event is in
     * @return True if there is still space for more Attendees to register
     */
    private boolean checkRoomCapacity(String eventID, String roomID) {
        Event event = eventAccess.getEvent(eventID);
        Room room = roomAccess.getRoom(roomID);
        return (room.getCapacity() - event.getAttendeeIDs().size()) > 0;
    }

    /**
     * Checks if the inputted Event conflicts with multiple other inputted Events
     * @param startTime The time at which the event will start
     * @param type The type of event, as an EventType
     * @param roomID The the ID of the room the Event will be held in
     * @return True if the event doesn't conflict with any existing events
     */
    public boolean checkConflicts(LocalDateTime startTime, EventType type, String roomID) {
        try {
            String[] eventIDs = roomAccess.getEventIDs(roomID);
            return checkConflicts(startTime, type, eventIDs);
        } catch (NullPointerException n) {
            return true;
        }
    }

    /**
     * Checks if the inputted Event conflicts with multiple other inputted Events
     * @param startTime The time at which the event will start
     * @param type The type of event, as an EventType
     * @param events The the IDs of the events the new Event might conflict with
     * @return True if the event doesn't conflict with any existing events
     */
    public boolean checkConflicts(LocalDateTime startTime, EventType type, String[] events) {
        Event event;
        if (type.equals(EventType.WORKSHOP)) {
            event = new Workshop("", "", startTime, "");
        } else if (type.equals(EventType.TALK)) {
            event = new Talk("", "", startTime, "");
        } else {
            return false;
        }

        try {
            for (String eID : events) {
                if (eventAccess.getEvent(eID).conflictsWith(event)) {
                    return false;
                }
            }
            return true;
        } catch (NullPointerException n) {
            return true;
        }
    }

}
