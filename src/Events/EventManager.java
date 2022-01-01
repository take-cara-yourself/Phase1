package Events;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.time.LocalDateTime;

// Contributors: Sarah Kronenfeld, Eytan Weinstein
// Last edit: Nov 19 2020

// Architecture Level - Use Class

/**
 * Abstract class representing an object that can find Events by EventID
 */
abstract class EventAccess {
    protected abstract Event getEvent(String eventID);
}

public class EventManager extends EventAccess implements Serializable {

    /** A mapping of Event names to their respective IDs. */
    private Map<String, String> eventsByName;

    /** A mapping of IDs to the respective Events they represent. */
    private Map<String, Event> events;

    /**
     * Creates a new empty EventManager
     */
    public EventManager() {
        events = new TreeMap<String, Event>();
        eventsByName = new TreeMap<String, String>();
    }


    // Protected getters (of Event objects)

    /**
     * Returns a textual representation of an Event
     * @param eventID The ID of the Event
     * @return The Event, as a String
     */
    protected Event getEvent(String eventID) {
        try {
            return events.get(eventID);
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Returns all the Events from a list of IDs
     * @param ids The IDs of the events you want to get
     * @return The Events, as an array of their String representations
     */
    protected Event[] getEventList(String[] ids) {
        try {
            if (ids != null && ids.length > 0) {
                Event[] eventInfoArray = new Event[ids.length];
                for (int i = 0; i < ids.length; i++) {
                    eventInfoArray[i] = events.get(ids[i]);
                }
                return eventInfoArray;
            }
            return null;
        } catch (NullPointerException n) {
            return null;
        }
    }


    // Public getters (of ids)

    /**
     * Returns the ID of an Event given its name
     * @param name The Event's name
     * @return The ID
     */
    public String getEventID(String name) {
        try {
            return eventsByName.get(name);
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Helper getter for the all the Events in this EventManager
     * @return an array of all Events in this EventManager
     */
    public String[] getEventIDs() {
        String[] eventArray = {};
        if (events.size() > 0) {
            return events.keySet().toArray(eventArray);
        }
        return null;
    }

    /**
     * Returns the name of an Event stored in this EventManager, given its ID
     * @param eventID The ID of the Event
     * @return The name of the Event, as a String
     */
    public String getEventName(String eventID) {
        try {
            return events.get(eventID).getName();
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Returns the description of an Event stored in this EventManager, given its ID
     * @param eventID The ID of the Event
     * @return The description of the Event, as a String
     */
    public String getDescription(String eventID){
        try {
            return events.get(eventID).getDescription();
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Returns the type of an Event stored in this EventManager, given its ID
     * @param eventID The ID of the Event
     * @return The type of the Event, as an EventType
     */
    public EventType getEventType(String eventID){
        try {
            Event event = getEvent(eventID);
            if (event.getClass().equals(Talk.class)) {
                return EventType.TALK;
            } else if (event.getClass().equals(Workshop.class)) {
                return EventType.WORKSHOP;
            } else {
                return null;
            }
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Returns an array list of all the Attendees (by ID) signed up to the Event with the inputted eventID
     * @param eventID The Event we are interested in
     * @return an array list of the IDs of all Attendees
     */
    public String getSpeakerID(String eventID){
        try {
            return events.get(eventID).getSpeaker();
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Returns an array list of all the Attendees (by ID) signed up to the Event with the inputted eventID
     * @param eventID The Event we are interested in
     * @return an array list of the IDs of all Attendees
     */
    public String getStartTime(String eventID){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return formatter.format(events.get(eventID).getStartTime());
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Returns an array list of all the Attendees (by ID) signed up to the Event with the inputted eventID
     * @param eventID The Event we are interested in
     * @return an array list of the IDs of all Attendees
     */
    public String getEndTime(String eventID){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return formatter.format(events.get(eventID).getEndTime());
        } catch (NullPointerException n) {
            return null;
        }
    }



    // Chat settings

    /**
     * Getter for the ID of an event's chat
     * @param id The event's ID
     * @return The event's chat ID
     */
    public String getEventChat(String id) {
        try {
            Event event = events.get(id);
            return event.getChatID();
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Sets the chatID for the Event with the inputted eventID
     * @param eventID The ID of the Event for which we want to set the chatID
     * @param chatID The chatID
     */
    public void setEventChat(String eventID, String chatID) {
        try {
            events.get(eventID).setChatID(chatID);
        } catch (NullPointerException n) {
            return;
        }
    }

    /**
     * Getter for this password of the Event with the inputted eventID
     * @param eventID The ID of the Event for which we want the password
     * @return String representing the Event's password
     */
    public String getEventPassword(String eventID) {
        Event ev = events.get(eventID);
        return ev.getPassword();
    }



    // Event-adding settings

    /**
     * Adds an Event to this EventManager
     * @param name The name of the Event to be created
     * @param speakerID The ID of the speaker of the Event to be created
     * @param startTime The start time of the Event to be created, as a LocalDateTime object
     * @param description The description for the Event to be created
     * @param type The Type of the Event to be created, as an EventType
     * @return The new Event's ID
     */
    public String addEvent(String name, String speakerID, LocalDateTime startTime, String description, EventType type) {
        Event event;
        if (type.equals(EventType.TALK)) {
            event = createTalk(name, speakerID, startTime, description);
        }
        else if (type.equals(EventType.WORKSHOP)) {
            event = createWorkshop(name, speakerID, startTime, description);
        }
        else {
            return null;
        }
        events.put(event.getID(), event);
        eventsByName.put(event.getName(), event.getID());
        return event.getID();
    }

    /**
     * Deletes an Event
     * @param ID The Event's ID
     * @return whether the Event has been successfully deleted
     */
    public boolean removeEvent(String ID) {
        if (events.get(ID) != null) {
            eventsByName.remove(events.get(ID).getName());
            events.remove(ID);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Creates a Talk to add to EventManager
     * Helper method for addEvent
     */
    private Event createTalk(String name, String speakerID, LocalDateTime startTime, String description) {
        return new Talk(name, speakerID, startTime, description);
    }

    /**
     * Creates a Workshop to add to EventManager
     * Helper method for addEvent
     */
    private Event createWorkshop(String name, String speakerID, LocalDateTime startTime, String description) {
        return new Workshop(name, speakerID, startTime, description);
    }


    // comparison methods

    /**
     * Checks to see if this EventManager contains an event of a certain name
     * @param name The name
     * @return True if an event with this name exists; false if not
     */
    public boolean contains(String name) {
        if (getEventID(name) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != EventManager.class) {
            return false;
        }
        EventManager events2 = (EventManager) obj;
        try {
            if (events2.getEventList(this.getEventIDs()).length > this.getEventIDs().length) {
                return false;
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
