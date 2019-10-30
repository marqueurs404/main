package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

/**
 * Weeks of the {@code Lesson}, used to represent the schedule of a lesson.
 */
public class Weeks {
    private WeeksType type;
    private List<Integer> weekNumbers;
    private LocalDate startDate;
    private LocalDate endDate;
    private int weekInterval;

    public Weeks(List<Integer> weekNumbers, LocalDate startDate,
                 LocalDate endDate, int weekInterval, WeeksType type) {
        requireNonNull(weekNumbers);
        requireNonNull(startDate);
        requireNonNull(endDate);
        requireNonNull(weekInterval);
        requireNonNull(type);
        if (startDate.isAfter(endDate)) { //DEFENSIVE CODE
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        this.weekNumbers = weekNumbers;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weekInterval = weekInterval;
        this.type = type;
    }

    public List<Integer> getWeekNumbers() {
        return weekNumbers;
    }

    public void setWeekNumbers(List<Integer> weekNumbers) {
        this.weekNumbers = weekNumbers;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
        case WEEK_NUMBERS:
            output = "Week Numbers: " + weekNumbers.toString();
            break;
        case START_END_WEEK_NUMBERS:
            output = "Start Date: " + startDate.toString() + " End Date: "
                + endDate.toString() + " Week Numbers: " + weekNumbers.toString();
            break;
        case START_END_WEEK_INTERVAL:
            output = "Start Date: " + startDate.toString() + " End Date: "
                + endDate.toString() + " Week Interval: " + weekInterval;
            break;
        default: assert false;
        }
        return output;
    }
}
