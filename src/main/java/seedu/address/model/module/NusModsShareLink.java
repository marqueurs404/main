package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.exceptions.LessonTypeNotFoundException;
import seedu.address.websocket.util.UrlUtil;

/**
 * Represents an NUSMods share link.
 */
public class NusModsShareLink {

    public static final String EXAMPLE = "https://nusmods.com/timetable/sem-1/share?CS2101=&CS2103T=LEC:G05&CS3230"
            + "=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";

    public static final String MESSAGE_CONSTRAINTS = "An NUSMods share link should be of the following format:"
            + "https://nusmods.com/timetable/SEMESTER/share?"
            + "MODULE_CODE_1=[LESSON_TYPE_1:LESSON_NUMBER_1,...][&MODULE_CODE_2=...]\n"
            + "An example link: " + EXAMPLE + "\n";

    public static final Set<String> SHORT_SEMESTER_NAMES = SemesterNo.getShortSemesterNames();

    public static final String SEMESTER_REGEX = "(" + String.join("|", SHORT_SEMESTER_NAMES) + ")";
    public static final String VALIDATION_REGEX = "^https://nusmods.com/timetable/"
            + SEMESTER_REGEX + "/share?(.+)$";

    public static final String MESSAGE_MISSING_QUERY_STRING = "Query string is missing!";
    public static final String MESSAGE_NOTHING_TO_ADD = "Oops, can't find any classes. Something "
            + "might wrong with the query string!";
    public static final String MESSAGE_FORMAT_INVALID_CLASS_TYPE = "Invalid class type %s in: %s";

    public final String value;
    public final SemesterNo semesterNo;
    public final Map<ModuleCode, Map<LessonType, LessonNo>> moduleLessonsMap;

    private NusModsShareLink(String value, SemesterNo semesterNo,
                             Map<ModuleCode, Map<LessonType, LessonNo>> moduleLessonsMap) {
        requireNonNull(value);
        requireNonNull(semesterNo);
        requireNonNull(moduleLessonsMap);
        this.value = value;
        this.semesterNo = semesterNo;
        this.moduleLessonsMap = moduleLessonsMap;
    }

    /**
     * Validates and parses a string into a valid NusModsShareLink.
     * @param link String to parse.
     * @return an NusModsShareLink.
     * @throws ParseException if URL is improperly formatted or values are invalid for semester,
     * module codes or class types.
     */
    public static NusModsShareLink parseLink(String link) throws ParseException {
        requireNonNull(link);
        link = link.trim();
        Pattern p = Pattern.compile(VALIDATION_REGEX);
        Matcher m = p.matcher(link);
        if (!m.matches()) { // not a valid NUSMods url.
            throw new ParseException(MESSAGE_CONSTRAINTS);
        }

        // parse semester number from link
        SemesterNo semesterNo = SemesterNo.findSemesterNo(m.group(1));

        // parse pairs of module code & lessons from query string.
        Map<String, String> queryMap;
        try {
            queryMap = UrlUtil.splitQuery(new URL(link));
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            throw new ParseException(e.getMessage(), e);
        }
        if (queryMap.isEmpty()) { // no key-value pairs found in query string.
            throw new ParseException(MESSAGE_MISSING_QUERY_STRING);
        }
        Map<ModuleCode, Map<LessonType, LessonNo>> moduleLessonsMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : queryMap.entrySet()) {
            ModuleCode moduleCode = ParserUtil.parseModuleCode(entry.getKey());
            if (StringUtil.isNullOrEmpty(entry.getValue())) {
                // Skip if no value in pair, happens for combinations like CS2101/CS2103T, where CS2101
                // has no lessons.
                continue;
            }
            String[] lessonTypeNoPairsArr = entry.getValue().split(",");
            Map<LessonType, LessonNo> lessonTypesNosMap = new LinkedHashMap<>();
            for (int i = 0; i < lessonTypeNoPairsArr.length; i++) {
                String[] typeNoPair = lessonTypeNoPairsArr[i].split(":");
                LessonType lType;
                try {
                    lType = LessonType.findLessonType(typeNoPair[0]);
                } catch (LessonTypeNotFoundException e) {
                    throw new ParseException(String.format(MESSAGE_FORMAT_INVALID_CLASS_TYPE,
                            typeNoPair[0], entry.getValue()));
                }
                LessonNo lNo = new LessonNo(typeNoPair[1]);
                lessonTypesNosMap.put(lType, lNo);
            }

            moduleLessonsMap.put(moduleCode, lessonTypesNosMap);
        }

        if (moduleLessonsMap.isEmpty()) { // no CLASS_TYPE:CLASS_NUMBER pairs to add at all
            throw new ParseException(MESSAGE_NOTHING_TO_ADD);
        }


        return new NusModsShareLink(link, semesterNo, moduleLessonsMap);
    }

    /**
     * Returns if a given string is a valid NUSMods URL.
     */
    public static boolean isValidUrl(String link) {
        try {
            parseLink(link);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
