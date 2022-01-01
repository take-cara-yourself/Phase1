package Message;

// Programmer: Karyn Komatsu, Ran Yi
// Description: Chat Entity, has ID, the messageIDs of the Messages sent in this Chat. and the Users' ID.
// Date Modified: 18/11/2020

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class Chat implements Serializable {
    protected String Id;
    protected ArrayList<String> messageIds;
    protected ArrayList<String> personIds;

    //Chat constructor. Input is arraylist of ID(s) of guest(s) that you (owner) want to form group chat with.
    public Chat(String ownerId, String guestId){
        messageIds = new ArrayList<>();
        personIds = new ArrayList<>();
        personIds.add(ownerId);
        personIds.add(guestId);
        this.Id = UUID.randomUUID().toString();
    }
    public Chat(String ownerId, ArrayList <String> guestIds){
        messageIds = new ArrayList<>();
        personIds = new ArrayList<>();
        personIds.add(ownerId);
        personIds.addAll(guestIds);
        this.Id = UUID.randomUUID().toString();
    }

    public Chat(String ownerId, String[] guestIds) {
        messageIds = new ArrayList<>();
        personIds = new ArrayList<>();
        personIds.add(ownerId);
        Collections.addAll(personIds,guestIds);
        this.Id = UUID.randomUUID().toString();
    }

    /**
     * Add the Id of the Message to messageIds list.
     * @param messageId - the Id of the Message object that we want to add.
     * @return True iff messageId was successfully added to messageIds.
     */
    public boolean addMessageIds(String messageId){
        this.messageIds.add(messageId);
        return true;
    }

    /**
     * Add the Id of the Message to messageIds list.
     * @param personId - the Id of the Message object that we want to add.
     * @return True iff messageId was successfully added to messageIds.
     *
     */
    public boolean addPersonIds(String personId) {
        this.personIds.add(personId);
        return true;
    }

    /**
     * Gets ID of Chat.
     * @return the Id of this Chat.
     */
    public String getId(){
        return this.Id;
    }

    /**
     * Gets all Message IDs that are stored in this Chat.
     * @return ArrayList of strings (Message IDs) stored in this Chat.
     */
    public ArrayList<String> getMessageIds() {
        return this.messageIds;
    }

    /**
     * Gets all Person IDs that are stored in this Chat.
     * @return ArrayList of strings (Person IDs) stored in this Chat.
     */
    public ArrayList<String> getPersonIds(){return this.personIds;}
}
