package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.moduleutil.TypicalWeeks;

class WeeksTest {
    private Weeks weeks_weekNumbers_allWeeks;
    private Weeks weeks_startEndWeekNumbers_allWeeks;
    private Weeks weeks_startEndWeekInterval_interval1;
    private Weeks weeks_startEndWeekInterval_interval2;

    @BeforeEach
    void setup() {
        weeks_weekNumbers_allWeeks = TypicalWeeks.generateWeeks_weekNumbers_allWeeks();
        weeks_startEndWeekNumbers_allWeeks = TypicalWeeks.generateWeeks_startEndWeekNumbers_allWeeks();
        weeks_startEndWeekInterval_interval1 = TypicalWeeks.generateWeeks_startEndWeekInterval(1);
        weeks_startEndWeekInterval_interval2 = TypicalWeeks.generateWeeks_startEndWeekInterval(2);
    }

    @Test
    void testGetWeekNumbers() {
        assertEquals(weeks_weekNumbers_allWeeks.getWeekNumbers(), TypicalWeeks.WEEK_NUMBERS_ALL);
    }

    @Test
    void testGetStartDate() {
        assertEquals(weeks_startEndWeekNumbers_allWeeks.getStartDate(), TypicalWeeks.START_DATE_1);
    }

    @Test
    void testGetEndDate() {
        assertEquals(weeks_startEndWeekNumbers_allWeeks.getEndDate(), TypicalWeeks.END_DATE_1);
    }

    @Test
    void testGetWeekInterval() {
        assertEquals(weeks_startEndWeekInterval_interval1.getWeekInterval(), 1);
        assertEquals(weeks_startEndWeekInterval_interval2.getWeekInterval(), 2);
    }

    @Test
    void testGetType() {
        assertEquals(weeks_weekNumbers_allWeeks.getType(), WeeksType.WEEK_NUMBERS);
        assertEquals(weeks_startEndWeekNumbers_allWeeks.getType(), WeeksType.START_END_WEEK_NUMBERS);
        assertEquals(weeks_startEndWeekInterval_interval1.getType(), WeeksType.START_END_WEEK_INTERVAL);
    }

    @Test
    void testToString() {
        assertEquals(weeks_weekNumbers_allWeeks.toString(),
                "Week Numbers: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]");
        assertEquals(weeks_startEndWeekNumbers_allWeeks.toString(),
                "Start Date: 2019-08-12 End Date: 2019-10-28 "
                        + "Week Numbers: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]");
        assertEquals(weeks_startEndWeekInterval_interval1.toString(),
                "Start Date: 2019-08-12 End Date: 2019-10-28 Week Interval: 1");
    }
}