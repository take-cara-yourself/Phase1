// PLEASE IGNORE THIS CLASS FOR PHASE 1

package Person;

import Person.Signupable;

public class SignupableManager {
    private Signupable pers;

    SignupableManager(Signupable pers){
        this.pers =pers;
    }

    /**
     * adds an event to the pers's events
     * @param eventID a string representing the ID of the event
     */
    public void addEvent(String eventID){
        pers.signUp(eventID);
    }

    /**
     * removes an event from the pers's list of events
     * @param eventID a string representing the ID of the event
     */
    public void removeEvent(String eventID){
        pers.cancelSpot(eventID);
    }

    /**
     * changes the current pers in Person.Signupable manger to the new pers in sighupable manager
     * @param pers a signupable object, which will become this singupbalemanager's new pers
     */

    public void changePerson(Signupable pers){
        this.pers=pers;
    }
}
