package seedu.address.model.person;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Schedule;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;

public class ScheduleStub {
    private ArrayList<ArrayList<Pair<Integer, Integer>>> schedule;
    public ScheduleStub(int version) {
        schedule = new ArrayList<ArrayList<Pair<Integer, Integer>>>();
        if (version == 1) {
            for (int i = 0; i < 7; i++) {
                ArrayList<Pair<Integer, Integer>> daySchedule = new ArrayList<Pair<Integer, Integer>>();
                if (i % 2 == 1) {
                    daySchedule.add(new Pair(1100, 1300));
                    daySchedule.add(new Pair(1500, 1600));
                    daySchedule.add(new Pair(1600, 1700));
                } else {
                    daySchedule.add(new Pair(800, 900));
                    daySchedule.add(new Pair(1330, 1430));
                }
                schedule.add(daySchedule);
            }
        } else if (version == 2) {
            for (int i = 0; i < 7; i++) {
                ArrayList<Pair<Integer, Integer>> daySchedule = new ArrayList<Pair<Integer, Integer>>();
                if (i % 2 == 1) {
                    daySchedule.add(new Pair(900, 1000));
                    daySchedule.add(new Pair(1100, 1300));
                } else {
                    daySchedule.add(new Pair(1200, 1400));
                    daySchedule.add(new Pair(1800, 1900));
                }
                schedule.add(daySchedule);
            }
        } else {
            for (int i = 0; i < 7; i++) {
                ArrayList<Pair<Integer, Integer>> daySchedule = new ArrayList<Pair<Integer, Integer>>();
                schedule.add(daySchedule);
            }
        }
    }

    public Schedule getSchedule() {
        Schedule schedule = new Schedule(new PersonId(12345));
        Venue venue = new Venue("Central Library");
        LocalDateTime startTime1 = LocalDateTime.of(2019,9, 23, 9, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2019, 9, 23, 11, 0);

        LocalDateTime startTime2 = LocalDateTime.of(2019,9, 25, 13, 0);
        LocalDateTime endTime2 = LocalDateTime.of(2019, 9, 25, 14, 0);
        Timeslot timeslot1 = new Timeslot(startTime1, endTime1, venue);
        Timeslot timeslot2 = new Timeslot(startTime2, endTime2, venue);
        Event monday1pmTo3pm = new Event("Test", new ArrayList<>(List.of(timeslot1, timeslot2)));
        schedule.addEvent(monday1pmTo3pm);
        return schedule;
    }

    public ArrayList<Pair<Integer, Integer>> getDaySchedule(int dayNumber) {
        return schedule.get(dayNumber - 1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof ScheduleStub) {
            boolean result = true;
            for (int i = 0; i < 7; i++) {
                ArrayList<Pair<Integer, Integer>> temp = schedule.get(i);
                for (int j = 0; j < temp.size(); j++) {
                    Pair<Integer, Integer> p = temp.get(j);
                    result = result && (p.getKey() == ((ScheduleStub) other).getDaySchedule(i + 1).get(j).getKey())
                            && (p.getValue() == ((ScheduleStub) other).getDaySchedule(i + 1).get(j).getValue());
                }
            }
            return result;
        } else {
            return false;
        }
    }
}
