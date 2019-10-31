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
        assertEquals(TypicalWeeks.WEEK_NUMBERS_ALL, weeks_weekNumbers_allWeeks.getWeekNumbers());
    }

    @Test
    void testGetStartDate() {
        assertEquals(TypicalWeeks.START_DATE_1, weeks_startEndWeekNumbers_allWeeks.getStartDate());
    }

    @Test
    void testGetEndDate() {
        assertEquals(TypicalWeeks.END_DATE_1, weeks_startEndWeekNumbers_allWeeks.getEndDate());
    }

    @Test
    void testGetWeekInterval() {
        assertEquals(1, weeks_startEndWeekInterval_interval1.getWeekInterval());
        assertEquals(2, weeks_startEndWeekInterval_interval2.getWeekInterval());
    }

    @Test
    void testGetType() {
        assertEquals(WeeksType.WEEK_NUMBERS, weeks_weekNumbers_allWeeks.getType());
        assertEquals(WeeksType.START_END_WEEK_NUMBERS, weeks_startEndWeekNumbers_allWeeks.getType());
        assertEquals(WeeksType.START_END_WEEK_INTERVAL, weeks_startEndWeekInterval_interval1.getType());
    }

    @Test
    void testToString() {
        assertEquals("Week Numbers: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]",
                weeks_weekNumbers_allWeeks.toString());
        assertEquals("Start Date: 2019-08-12 End Date: 2019-10-28 Week Numbers: "
                + "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]", weeks_startEndWeekNumbers_allWeeks.toString());
        assertEquals("Start Date: 2019-08-12 End Date: 2019-10-28 Week Interval: 1",
                weeks_startEndWeekInterval_interval1.toString());
    }
}