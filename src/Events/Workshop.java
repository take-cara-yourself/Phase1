package Events;

import Events.Event;
import java.time.LocalDateTime;
import java.time.Duration;

// Contributors: Eytan Weinstein
// Last edit: Nov 17 2020

// Architecture Level - Entity

public class Workshop extends Event {

    // A Workshop is exactly one half hour long, and can take place in the same Room as other Workshops (but not Talks!)
    // so long as the number of its attendees does not exceed the capacity of that Room.

    /**
     * Constructor for Workshop objects
     * @param name The Workshop's name
     * @param speakerID The ID of the speaker at this Workshop
     * @param startTime The time when the Workshop starts
     * @param description The description of this Talk
     */
    public Workshop (String name, String speakerID, LocalDateTime startTime, String description) {
        super(name, startTime,startTime.plusMinutes(30), description, speakerID);
    }

    /**
     * Checks if the inputted Event conflicts in time with this Workshop (recall that only a Talk can conflict)
     * @param event The event which is being checked for conflicts with this Workshop
     * @return whether or not there is a conflict (true or false)
     */
    public boolean conflictsWith(Event event) {
        if (event instanceof Talk) {
            if (this.getStartTime().isBefore(event.getEndTime())) {
                if (this.getEndTime().isAfter(event.getStartTime()) || this.getStartTime().isAfter(event.getStartTime())) {
                    return true;
                }
            }
        }
        return false;
    }

}
