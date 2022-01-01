README.txt
In order to start the program build the program and go the Class Main in the controller package and run the main method.
Follow the instructions that it will provide.

necessary packages include:
java.util
java.time
java.io
java.lang

This is a program that organizes a tech conference. There are three identities as a User.
Attendee, Organizer and Speaker. Users could create different kind of accounts the login.
Below are the instruction on how to use this program base on different identity.

1.  Attendee
    ---------------------------
    Message and Contact system:
    ---------------------------
    Attendees could add another User as their contact. The basic info will show up in the contact list.
    Attendees could communicate with other Users, either one-to-one or talk in a group.

        Attendees should create a 1-1 chat or a group chat with others. Then the chat's info will be
        stored. When send a message, could only send messages in a particular chat. Input the chatID then the Attendees
        could send a message.

    Attendees could view the messages they sent/received and the chat/announcement-chat they are involved in.

        inbox contains the messages they received.
        sent contains the messages they sent.
        Attendees could view the list of chats/announcement-chat they have and get the chatID.
        Attendees could view the messages in one particular chat/announcement-chat.

    -------------
    Event system:
    -------------
    Attendees could view the Events that are scheduled by room. Sign up in Events and cancel their spot.
    Attendees could view the list of Events they have signed up.


2.  Organizer
    ---------------------------
    Message and Contact system:
    ---------------------------
    Organizers could add another User as their contact. The basic info will show up in the contact list.
    Organizers could communicate with other Users, either one-to-one or talk in a group.

        Organizers should create a 1-1 chat or a group chat with others. Then the chat's info will be
        stored. When send a message, could only send messages in a particular chat. Input the chatID then the Organizers
        could send a message.

    Organizers could view the messages they sent/received and the chat/announcement-chat they are involved in.

        inbox contains the messages they received.
        sent contains the messages they sent.
        Organizers could view the list of chats/announcement-chat they have and get the chatID.
        Organizers could view the messages in one particular chat/announcement-chat.

    -------------
    Event system:
    -------------
    Organizers have the fewest restrictions to this conferences

    Organizers are in charge of creating rooms, in which Events are held.

    Organizers could set up the Events with name, room, and descriptions.

    Organizers could view the Events that are scheduled by room.


    Organizers could also attend to an Event.

    Sign up in Events and cancel their spot.
    Organizers could view the list of Events they have signed up.


3.  Speaker
    ---------------------------
    Message and Contact system:
    ---------------------------
    Speakers could add another User as their contact. The basic info will show up in the contact list.
    Speakers CAN NOT create chat, either one-to-one or talk in a group.

        Speakers could only send a message in a chat/announcement-chat which is already exist, that is,
        Speakers could only response to others.
        When send a message, could only send messages in a particular chat. Input the chatID then the Speakers
        could send a message.

    Speakers could view the messages they sent/received and the chat/announcement-chat they are involved in.

        inbox contains the messages they received.
        sent contains the messages they sent.
        Speakers could view the list of chats/announcement-chat they have and get the chatID.
        Speakers could view the messages in one particular chat/announcement-chat.

    -------------
    Event system:
    -------------
    Speakers are the people who will give a talk in an Event.


    Speakers could also attend to an Event.

    Sign up in Events and cancel their spot.
    Speakers could view the list of Events they have signed up.
