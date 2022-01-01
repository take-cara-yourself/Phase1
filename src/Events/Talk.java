package Events;

import Events.Event;
import java.time.LocalDateTime;
import java.time.Duration;

// Contributors: Sarah Kronenfeld, Eytan Weinstein
// Last edit: Nov 17 2020

// Architecture Level - Entity

public class Talk extends Event {

    // A Talk is exactly one hour long, and cannot take place in the same Room as any other Event while that Event is
    // occurring.

    /**
     * Constructor for Talk objects
     * @param name The Talk's name
     * @param speakerID The ID of the speaker at this Talk
     * @param startTime The time when the Talk starts
     * @param description The description of this Talk
     */
    public Talk (String name, String speakerID, LocalDateTime startTime, String description) {
        super(name, startTime,startTime.plusHours(1), description, speakerID);
    }

    /**
     * Checks if the inputted Event conflicts in time with this Talk
     * @param event The event which is being checked for conflicts with this Talk
     * @return whether or not there is a conflict (true or false)
     */
    public boolean conflictsWith(Event event) {
        return this.getStartTime().isBefore(event.getEndTime()) && event.getStartTime().isBefore(this.getEndTime());
    }

}

