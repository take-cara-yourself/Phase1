package Events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

// Contributors: Sarah Kronenfeld, Eytan Weinstein
// Last edit: Nov 19 2020

// Architecture Level - Use Class

/**
 * Abstract class representing an object that can find Rooms by RoomID
 */
abstract class RoomAccess {
    protected abstract Room getRoom(String roomID);
    abstract String[] getEventIDs(String roomID);
}

public class RoomManager extends RoomAccess implements Serializable {

    /** A mapping of Room IDs to their lists of EventIDs. */
    private Map<String, ArrayList<String>> roomEventList;

    /** A mapping of Room IDs to their lists of EventIDs. */
    private Map<String, Room> roomList;

    /** A mapping of Room names to their respective IDs. */
    private Map<String, String> roomsByName;

    /**
     * Constructor for RoomManager objects
     */
    public RoomManager() {
        roomEventList = new TreeMap<String, ArrayList<String>>();
        roomsByName = new TreeMap<String, String>();
        roomList = new TreeMap<String, Room>();
    }


    // Protected getters

    /**
     * Returns the Room associated with this RoomID
     * @param roomID The Room's ID
     * @return The Room
     */
    protected Room getRoom(String roomID) {
        try {
            return roomList.get(roomID);
        } catch (NullPointerException e) {
            return null;
        }
    }

    // Public getters

    /**
     * Finds the ID of a specific Room (by name)
     * @param name The Room's name
     * @return The Room's ID
     */
    public String getRoomID (String name) {
        try {
            return roomsByName.get(name);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Finds the name of a specific Room (by ID)
     * @param ID The Room's ID
     * @return The Room's name
     */
    public String getRoomName (String ID) {
        try {
            return roomList.get(ID).getName();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Returns a list of the names of all Rooms currently established at this conference
     * @return The Room's EventManager
     */
    public String[] getRoomNames() {
        String[] names = {};
        try {
            return roomsByName.keySet().toArray(names);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Gets the Room an Event is held in
     * @param ID the ID of the event
     * @return the ID of the room
     */
    public String getEventRoom(String ID) {
        try {
            for (String room : getRoomNames()) {
                ArrayList<String> events = roomEventList.get(roomsByName.get(room));
                if (events.contains(ID)) {
                    return room;
                }
            }
            return null;
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Finds the EventManager object for a specific Room's Events (by ID)
     * @param roomID The ID of the Room
     * @return The Room's EventManager
     */
    public String[] getEventIDs(String roomID) {
        try {
            String[] evList = {};
            return roomEventList.get(roomID).toArray(evList);
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Returns the number of Rooms this RoomManager contains
     * @return ^
     */
    public int numRooms() {
        return roomList.size();
    }

    // Adding methods

    /**
     * Creates a new Room with the inputted capacity
     * @param name The name of the new room
     * @param capacity The capacity of the new Room
     * @return The ID of the new Room
     */
    public String addRoom(String name, int capacity) {
        Room thisRoom = new Room(name, capacity);
        roomList.put(thisRoom.getID(), thisRoom);
        roomsByName.put(thisRoom.getName(), thisRoom.getID());
        roomEventList.put(thisRoom.getID(), new ArrayList<String>());
        return thisRoom.getID();
    }

    /**
     * Adds an event in a certain Room to that Room's EventIDs list
     * @param roomID The Room's ID
     * @param eventID The Event's ID
     * @return True if successful
     */
    public boolean addEvent(String roomID, String eventID) {
        try {
            ArrayList<String> room = roomEventList.get(roomID);
            room.add(eventID);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    // comparison methods

    /**
     * Checks to see if this RoomManager contains a room of a certain name
     * @param name The name
     * @return True if an event with this name exists; false if not
     */
    public boolean contains(String name) {
        if (getRoomID(name)!= null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (obj.getClass() != RoomManager.class){
            System.out.println("Wrong class!");
            return false;
        }
        RoomManager rooms2 = (RoomManager) obj;
        if (rooms2.numRooms() == this.numRooms()) {
            if(rooms2.getRoomNames().equals(this.getRoomNames())) {
                for (String room : this.getRoomNames()) {
                    String id = this.getRoomID(room);
                    if (rooms2.getEventIDs(id).equals(this.getEventIDs(id))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
