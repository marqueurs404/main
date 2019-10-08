package seedu.address.model.display.detailwindow;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Schedule;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;
import seedu.address.model.tag.Tag;

/**
 * Schedule of timeslots for the week.
 */
public class WeekSchedule {

    private static final int DAYS_OF_THE_WEEK = 7;
    private static final LocalTime STARTTIME = LocalTime.of(00, 00);
    private static final LocalTime ENDTIME = LocalTime.of(23, 59);

    private String weekScheduleName;

    private Name personName;
    private Phone personPhone;
    private Email personEmail;
    private Address personAddress;
    private Tag personTag;

    private HashMap<DayOfWeek, ArrayList<DayTimeslot>> weekSchedule;

    public WeekSchedule(String weekScheduleName, LocalDateTime now, Person person) {

        this.weekScheduleName = weekScheduleName;
        LocalDate currentDate = now.toLocalDate();
        weekSchedule = new HashMap<>();

        personName = person.getName();
        personPhone = person.getPhone();
        personEmail = person.getEmail();
        personAddress = person.getAddress();

        Schedule personSchedule = person.getSchedule();
        ArrayList<Event> events = personSchedule.getEvents();
        for (int i = 1; i <= DAYS_OF_THE_WEEK; i++) {
            weekSchedule.put(DayOfWeek.of(i), new ArrayList<>());
        }

        for (int e = 0; e < events.size(); e++) {
            Event currentEvent = events.get(e);
            String eventName = currentEvent.getEventName();

            ArrayList<Timeslot> timeslots = currentEvent.getTimeslots();
            for (int t = 0; t < timeslots.size(); t++) {
                Timeslot currentTimeslot = timeslots.get(t);
                LocalDateTime currentStartTime = currentTimeslot.getStartTime();
                LocalDateTime currentEndTime = currentTimeslot.getEndTime();
                Venue currentVenue = currentTimeslot.getVenue();
                if (now.toLocalDate().plusDays(7).isAfter(currentStartTime.toLocalDate())
                        && now.toLocalDate().isBefore(currentStartTime.toLocalDate())) {
                    DayTimeslot timeslot = new DayTimeslot(
                            eventName,
                            currentStartTime.toLocalTime(),
                            currentEndTime.toLocalTime(),
                            currentVenue
                    );
                    weekSchedule.get(currentStartTime.getDayOfWeek()).add(timeslot);
                }
            }
        }

            //currentDate = currentDate.plusDays(1);
    }

    public String getWeekScheduleName() {
        return weekScheduleName;
    }

    public HashMap<DayOfWeek, ArrayList<DayTimeslot>> getWeekSchedule() {
        return weekSchedule;
    }

    // for debugging purposes only
    @Override
    public String toString() {
        String output = "";

        output += "=====" + weekScheduleName + " for " + personName.toString() + "=====" + "\n";
        for (int i = 1; i <= 7; i++) {
            ArrayList<DayTimeslot> dayTimeslots = weekSchedule.get(DayOfWeek.of(i));
            output += DayOfWeek.of(i) + ":\n";
            for (DayTimeslot d : dayTimeslots) {
                String timeSlotDetails = d.getStartTime().toString() + "---" + d.getEndTime().toString();
                output += timeSlotDetails + "\n";
            }
        }
        return output;
    }
}
