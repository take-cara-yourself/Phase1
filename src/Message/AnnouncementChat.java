package Message;

// Programmer: Shimon Nauenberg, Ran Yi
// Description: A Chat Entity for announcement sent by Organizer/Speaker, cannot be replied by Attendees.
// Date Modified: 18/11/2020

import java.util.ArrayList;
import java.util.UUID;
public class AnnouncementChat extends Chat{

    private final String password;
    private final String ownerId;

    public AnnouncementChat(String ownerId, ArrayList<String> guestIds){
        super(ownerId, guestIds);
        this.password = UUID.randomUUID().toString();
        this.ownerId = ownerId;
    }

    /**
     *  a getter for the password
     * @return a String representign the annoucement Chat's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * A getter for the User who set up this AnnouncementChat.
     * @return the ID of this User.
     */
    public String getEventID() {
        return this.ownerId;
    }


    /**
     * adds the message id and returns true iff the pass matches the annoucement chat password
     * @param messageId a String representing the message Id
     * @param pass a string representing the password for annoucement chat
     * @return true iff the messageId was added to the list of messageid's
     */
    public boolean addMessageIds(String messageId, String pass){
        if(checkPassword(pass)){
            messageIds.add(messageId);
            return true;
        }
        return false;
    }

    /**
     * Checks if the password entered is correct
     * @param pass inputted password
     * @return true iff the inputted password is correct
     */
    private boolean checkPassword(String pass) {
        return pass.equals(password);
    }

}
