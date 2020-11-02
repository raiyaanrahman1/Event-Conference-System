import Entity.*;
import UseCase.AttendeeSignUp;
import org.junit.*;
import static org.junit.Assert.*;

public class AttendeeSignUpTests {

    @Test(timeout = 50)
    public void testAttendeeSignUp(){
        Attendee a = new Attendee("attendee1", "666");
        AttendeeSignUp aa = new AttendeeSignUp(a);

        assertSame(aa.getCurrentUser(), a);

    }


    @Test(timeout = 50)
    public void testSignUpForEvent() {
        // passable conditions + testing for EventList mutation
        Attendee a = new Attendee("attendee1", "666");
        Speaker s = new Speaker("Barack Obama", "1");
        Event e = new Event("EventA", "16:00", "2020.10.29", "100", s, 2);

        AttendeeSignUp aa = new AttendeeSignUp(a);
        aa.signUpForEvent(e);
        assertFalse("This Attendee was not signed up to this event successfully\n",
                a.getEventList().contains(e));
    }


    @Test(timeout = 50)
    public void testSignUpForEventFullCapacity() {
        Attendee a1 = new Attendee("attendee1", "666");
        Attendee a2 = new Attendee("attendee2", "666");
        Attendee a3 = new Attendee("attendee3", "666");
        Speaker s = new Speaker("Barack Obama", "1");
        Event e = new Event("EventA", "16:00", "2020.10.29", "100", s,2);

        AttendeeSignUp aa1 = new AttendeeSignUp(a1);
        aa1.signUpForEvent(e);
        AttendeeSignUp aa2 = new AttendeeSignUp(a2);
        aa2.signUpForEvent(e);

        AttendeeSignUp aa3 = new AttendeeSignUp(a3);
        assertFalse("This event is at full capacity\n", aa3.signUpForEvent(e));
    }

    @Test(timeout = 50)
    public void testSignUpForEventConflictingSchedule() {
        // Checks if signUpForEvent returns true when attendee is already
        // attending a talk scheduled at the same time.
        Attendee a = new Attendee("attendee1", "666");
        Speaker s1 = new Speaker("Barack Obama", "1");
        Speaker s2 = new Speaker("Barack Obama Clone", "1");
        Event e1 = new Event("EventA", "16:00", "2020.10.29", "100", s1,2);
        Event e2 = new Event("EventA", "16:00", "2020.10.29", "100", s2,2);

        AttendeeSignUp aa = new AttendeeSignUp(a);
        aa.signUpForEvent(e1);
        assertFalse("This Attendee is already attending another event at this time! \n",
                aa.signUpForEvent(e2));
    }

    @Test(timeout = 50)
    public void cancelSpot() {
        // passable conditions + testing for EventList mutation
        Attendee a = new Attendee("attendee1", "666");
        Speaker s = new Speaker("Barack Obama", "1");
        Event e = new Event("EventA", "16:00", "2020.10.29", "100", s,2);

        AttendeeSignUp aa = new AttendeeSignUp(a);
        aa.signUpForEvent(e);
        aa.cancelSpot(e);
        assertFalse("Cancellation not successful.\n", a.getEventList().contains(e));

    }

    @Test(timeout = 50)
    public void cancelSpotEventAttendeeNotInEvent() {
        // Tests cancelSpot() when Attendee is not signed up for that event
        Attendee a = new Attendee("attendee1", "666");
        Speaker s = new Speaker("Barack Obama", "1");
        Event e = new Event("EventA", "16:00", "2020.10.29", "100", s,2);

        AttendeeSignUp aa = new AttendeeSignUp(a);
        assertFalse("This Attendee is not attending this event!\n", aa.cancelSpot(e));

    }

    @Test(timeout = 50)
    public void createSpeakerAccount() {
        // passable conditions
//        Organizer a = new Organizer("attendee1", "666");
//        AttendeeSignUp aa = new AttendeeSignUp(a);
//
//        assertFalse("This User is not allowed to perform this action!\n",
//                aa.createSpeakerAccount("Barack Obama", "1"));

    }

}
