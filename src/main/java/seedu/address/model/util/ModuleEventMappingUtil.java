package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.ModuleToEventMappingException;
import seedu.address.model.module.Exam;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.LessonNo;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.SemesterNo;
import seedu.address.model.module.Weeks;
import seedu.address.model.module.WeeksType;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;

/**
 * Add an an NUSMods timetable to a person's schedule.
 */
public class ModuleEventMappingUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-M-d");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-M-d HHmm");
    private static final int WEEK_LENGTH = 7;

    /**
     * Converts a {@code Module} to an {@code Event}.
     * @return an Event based on an NUS module
     */
    public static Event mapModuleToEvent(Module module, String startAcadSemDateString, SemesterNo semesterNo,
                  List<LessonNo> lessonNos, List<String> holidayDateStrings) throws ModuleToEventMappingException {
        requireNonNull(module);
        requireNonNull(startAcadSemDateString);
        requireNonNull(semesterNo);
        requireNonNull(lessonNos);
        requireNonNull(holidayDateStrings);

        Semester semester = module.getSemester(semesterNo);
        ArrayList<Lesson> lessons = new ArrayList<>();
        ArrayList<Timeslot> timeslots = new ArrayList<>();

        for (LessonNo lessonNo : lessonNos) {
            List<Lesson> lessonsFound = semester.findLessons(lessonNo);
            if (lessonsFound.isEmpty()) {
                throw new ModuleToEventMappingException("Lesson number not found!");
            }
            lessons.addAll(lessonsFound);
        }

        // Add timeslots for lessons and exam
        for (Lesson lesson : lessons) {
            timeslots.addAll(generateLessonTimeslots(lesson, startAcadSemDateString, holidayDateStrings));
        }
        if (!semester.getExam().equals(Exam.emptyExam())) {
            timeslots.add(generateExamTimeslot(semester.getExam()));
        }

        return new Event(module.getModuleCode().toString(), timeslots);
    }

    /**
     * Generate a {@code Timeslot} for Exam.
     */
    public static Timeslot generateExamTimeslot(Exam exam) {
        LocalDateTime examDate = exam.getExamDate();
        int examDuration = exam.getExamDuration();
        LocalDateTime timeslotStart = examDate;
        LocalDateTime timeslotEnd = examDate.plusMinutes(examDuration);
        Venue emptyVenue = new Venue(""); //empty cause exam venue is not captured in NUSMods

        return new Timeslot(timeslotStart, timeslotEnd, emptyVenue);
    }

    /**
     * Generate all timeslots for the lesson, taking into account start date of semester and holidays.
     */
    public static List<Timeslot> generateLessonTimeslots(Lesson lesson, String startAcadSemDateString,
                                                         List<String> holidayDateStrings) {
        List<Timeslot> timeslots = new ArrayList<>();

        Venue venue = new Venue(lesson.getVenue().toString());

        List<LocalDate> holidayDates = holidayDateStrings.stream()
                .map(s -> LocalDate.parse(s, DATE_FORMATTER))
                .collect(Collectors.toList());

        Weeks weeks = lesson.getWeeks();
        DayOfWeek day = lesson.getDay();

        if (weeks.getType() == WeeksType.WEEK_NUMBERS) {
            String semStartDateStartTimeString = startAcadSemDateString + " " + lesson.getStartTime().toString();
            String semStartDateEndTimeString = startAcadSemDateString + " " + lesson.getEndTime().toString();
            LocalDateTime semStartDateStartTime = LocalDateTime.parse(
                    semStartDateStartTimeString, DT_FORMATTER).with(day);
            LocalDateTime semStartDateEndTime = LocalDateTime.parse(
                    semStartDateEndTimeString, DT_FORMATTER).with(day);

            for (int weekNo : weeks.getWeekNumbers()) {
                LocalDateTime timeslotStart = semStartDateStartTime.plusDays(WEEK_LENGTH * (weekNo - 1));
                LocalDateTime timeslotEnd = semStartDateEndTime.plusDays(WEEK_LENGTH * (weekNo - 1));

                boolean isHoliday = holidayDates.stream().anyMatch(d -> timeslotStart.toLocalDate().isEqual(d));
                if (isHoliday) {
                    continue;
                }

                Timeslot ts = new Timeslot(timeslotStart, timeslotEnd, venue);
                timeslots.add(ts);
            }
        } else if (weeks.getType() == WeeksType.START_END_WEEK_NUMBERS) {
            String lessonStartDateStartTimeString = weeks.getStartDateString() + " " + lesson.getStartTime().toString();
            String lessonStartDateEndTimeString = weeks.getStartDateString() + " " + lesson.getEndTime().toString();
            LocalDateTime lessonStartDateStartTime = LocalDateTime.parse(
                    lessonStartDateStartTimeString, DT_FORMATTER).with(day);
            LocalDateTime lessonStartDateEndTime = LocalDateTime.parse(
                    lessonStartDateEndTimeString, DT_FORMATTER).with(day);

            for (int weekNo : weeks.getWeekNumbers()) {
                LocalDateTime timeslotStart = lessonStartDateStartTime.plusDays(WEEK_LENGTH * (weekNo - 1));
                LocalDateTime timeslotEnd = lessonStartDateEndTime.plusDays(WEEK_LENGTH * (weekNo - 1));

                boolean isHoliday = holidayDates.stream().anyMatch(d -> timeslotStart.toLocalDate().isEqual(d));
                if (isHoliday) {
                    continue;
                }

                Timeslot ts = new Timeslot(timeslotStart, timeslotEnd, venue);
                timeslots.add(ts);
            }
        } else {
            assert weeks.getType() == WeeksType.START_END_WEEK_INTERVAL;
            LocalDate lessonStartDate = LocalDate.parse(weeks.getStartDateString(), DATE_FORMATTER).with(day);
            LocalDate lessonEndDate = LocalDate.parse(weeks.getEndDateString(), DATE_FORMATTER).with(day);
            LocalDate tempDate = lessonStartDate;
            int weekInterval = weeks.getWeekInterval();

            while (tempDate.isBefore(lessonEndDate) || tempDate.isEqual(lessonEndDate)) {
                LocalTime lessonStartTime = LocalTime.parse(lesson.getStartTime().toString(), TIME_FORMATTER);
                LocalTime lessonEndTime = LocalTime.parse(lesson.getEndTime().toString(), TIME_FORMATTER);

                LocalDateTime timeslotStart = LocalDateTime.of(tempDate, lessonStartTime);
                LocalDateTime timeslotEnd = LocalDateTime.of(tempDate, lessonEndTime);

                tempDate = tempDate.plusDays(WEEK_LENGTH * weekInterval);

                boolean isHoliday = holidayDates.stream().anyMatch(d -> timeslotStart.toLocalDate().isEqual(d));
                if (isHoliday) {
                    continue;
                }

                Timeslot ts = new Timeslot(timeslotStart, timeslotEnd, venue);
                timeslots.add(ts);
            }
        }

        return timeslots;
    }
}
