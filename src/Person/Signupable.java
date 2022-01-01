// This interface is being implemented by Attendee and Organizer, HOWEVER the
// interface's features are not being used. Please ignore this class and SignupableManager
// for Phase 1

package Person;

import java.util.ArrayList;

public interface Signupable {

    void signUp(String eventID);

    void cancelSpot(String eventID);

    ArrayList<String> getEventsSignedUp();
}