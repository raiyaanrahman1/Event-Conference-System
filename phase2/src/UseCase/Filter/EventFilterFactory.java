package UseCase.Filter;

import Exceptions.NoSuchFilterException;

public class EventFilterFactory {
    private final static String BY_ATTENDEE = "attendee";
    private final static String BY_DATE = "date";
    private final static String BY_ORGANIZER = "organizer";
    private final static String BY_SPEAKER = "speaker";

    public EventFilter getEventFilter(String type) throws NoSuchFilterException {
        if (type == null) {
            return new NullFilter();
        }

        switch (type) {
            case EventFilterFactory.BY_ATTENDEE:
                return new AttendeeFilter();
            case EventFilterFactory.BY_DATE:
                return new DateFilter();
            case EventFilterFactory.BY_ORGANIZER:
                return new OrganizerFilter();
            case EventFilterFactory.BY_SPEAKER:
                return new SpeakerFilter();
            default:
                throw  new NoSuchFilterException();
        }
    }
}
