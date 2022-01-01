package Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// There are not events that Speaker than can sign for in the capacity of an attendee. Nor can Speaker register
// himself or herself for a Speaking event. This is by the organizers of the conference.

public class Speaker extends Person { //implements Person.Signupable

    protected ArrayList<String> allTalksID = new ArrayList<>();

    protected Map<String, String> allTalksDictionary = new HashMap<String, String>(); // this is a a dictionary of eventIDs to eventNames

    protected ArrayList<String> announcementChatIds = new ArrayList<>();

    Speaker(String fullName, String username, String password, String email){
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.id = UUID.randomUUID().toString();
        this.typePerson = 3;
    }


    /**
     * adds an event ID to the Person.Person.Speaker's list of signed up Talks
     * @param eventID takes in the ID of the event
     */
    public void signUp(String eventID) {
        this.allTalksID.add(eventID);
    }

    /**
     * returns the events/talks that the Person.Speaker has been signed up for by Organizer
     * @return ArrayList<String> getAllTalks the events' ids that the person has signed up for
     */
    public ArrayList<String> getAllTalks(){
        return allTalksID;
    }

    /**
     * return the events/talks that the Person.Speaker has been signed up for by Organizer
     * @return Map of eventIds to getAllTalksDictionary that the person has signed up for; key is eventID, value is eventName
     */
    public Map getAllTalksDictionary() {
        return allTalksDictionary;
    }


    /**
     *
     * This is a list of strings representing chatIds pertaining to announcements about events
     * @return ArrayList<String> of chatID of messages received by the User
     */

    public ArrayList<String> getAnnouncementChats() {
        return this.announcementChatIds;

    }
}



