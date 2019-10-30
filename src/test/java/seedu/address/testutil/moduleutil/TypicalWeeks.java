package seedu.address.testutil.moduleutil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.module.Weeks;
import seedu.address.model.module.WeeksType;

/**
 * Typical Weeks for a Lesson.
 */
public class TypicalWeeks {
    public static final LocalDate START_DATE_1 = LocalDate.parse("2019-08-12");
    public static final LocalDate END_DATE_1 = LocalDate.parse("2019-10-28");

    public static final List<Integer> WEEK_NUMBERS_ALL = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
    public static final List<Integer> WEEK_NUMBERS_EVEN = Arrays.asList(4, 6, 8, 10, 12);
    public static final List<Integer> WEEK_NUMBERS_ODD = Arrays.asList(3, 5, 7, 9, 11, 13);

    /**
     * Generate a Typical Lesson.
     *
     * @return Weeks of WeekType.WEEK_NUMBERS
     */
    public static Weeks generateTypicalWeeks1() {
        return new Weeks(WEEK_NUMBERS_ALL, START_DATE_1, END_DATE_1, 1, WeeksType.WEEK_NUMBERS);
    }
}
