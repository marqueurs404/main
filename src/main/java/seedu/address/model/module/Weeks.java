package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;

/**
 * Weeks of the {@code Lesson}, used to represent the schedule of a lesson.
 */
public class Weeks {
    private List<Integer> weekNumbers = new ArrayList<>();
    private String startDateString;
    private String endDateString;
    private int weekInterval;
    private WeeksType type;

    public Weeks(List<Integer> weekNumbers, String startDateString,
                 String endDateString, int weekInterval, WeeksType type) {
        this.weekNumbers = weekNumbers;
        this.startDateString = startDateString;
        this.endDateString = endDateString;
        this.weekInterval = weekInterval;
        this.type = type;
    }

    public Weeks() {
        this.startDateString = "";
        this.endDateString = "";
        this.weekInterval = -1;
        this.type = null;
    }

    /**
     * Static method to get an empty week.
     */
    public static Weeks emptyWeeks() {
        return new Weeks();
    }

    public List<Integer> getWeekNumbers() {
        return weekNumbers;
    }

    public void setWeekNumbers(List<Integer> weekNumbers) {
        this.weekNumbers = weekNumbers;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public int getWeekInterval() {
        return weekInterval;
    }

    public void setWeekInterval(int weekInterval) {
        this.weekInterval = weekInterval;
    }

    public WeeksType getType() {
        return type;
    }

    public void setType(WeeksType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String output = "";
        switch(type) {
        case WEEK_NUMBERS: output = "Week Numbers: " + weekNumbers.toString();
                break;
        case START_END_WEEK_NUMBERS: output = "Start Date: " + startDateString + " End Date: "
                + endDateString + " Week Numbers: " + weekNumbers.toString();
                break;
        case START_END_WEEK_INTERVAL: output = "Start Date: " + startDateString + " End Date: "
                + endDateString + " Week Interval: " + weekInterval;
                break;
        default: assert false;
        }
        return output;
    }
}
