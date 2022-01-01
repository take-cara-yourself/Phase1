package Person;

public class Organizer extends Attendee {
    public Organizer(String fullName, String username, String password, String email){
        super(fullName, username, password, email);
        this.typePerson = 2;
    }


}
