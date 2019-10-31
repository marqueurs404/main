package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class NusModsShareLinkTest {
    private String validLinkString1 = "https://nusmods.com/timetable/sem-1/share?CS2101=&CS2103T=LEC:G05&CS3230=LEC:1,"
            + "TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";
    private String validLinkString2 = "https://nusmods.com/timetable/sem-1/share?CS2101=&CS2103T=LEC:G05&CS3230=LEC:1,"
            + "TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";
    //Can't check validity of module code within parser as there's no access to model.
    private String validLinkStringInvalidModuleCode = "https://nusmods.com/timetable/sem-1/share?ABCDEF=&"
            + "CS2103T=LEC:G05&CS3230=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";
    //Can't check validity of class number within parser as there's no access to model.
    private String validLinkStringInvalidClassNumber = "https://nusmods.com/timetable/sem-1/share?CS2101=&CS2103T="
            + "LEC:GXX&CS3230=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";
    private String invalidLinkStringNotUrl = "not a url";
    private String invalidLinkStringAnotherUrl = "https://google.com/";
    private String invalidLinkStringTypoUrl = "https://nusbods.com/timetable/sem-1/share?CS2101=&CS2103T=LEC:G05&"
            + "CS3230=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";
    private String invalidLinkStringInvalidSemester = "https://nusmods.com/timetable/sem999/share?CS2101=&"
            + "CS2103T=LEC:G05&CS3230=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";
    private String invalidLinkStringMissingQueryString = "https://nusmods.com/timetable/sem-1/share?";
    private String invalidLinkStringInvalidQueryString = "https://nusmods.com/timetable/sem-1/share?CS2101";
    private String invalidLinkStringInvalidClassType = "https://nusmods.com/timetable/sem-1/share?CS2101=&"
            + "CS2103T=LOL:G05&CS3230=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";

    @Test
    void parseLink_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> NusModsShareLink.parseLink(null));
    }

    @Test
    void parseLink_invalidValues_throwsParseExceptionWithSpecificMessages() {
        try {
            NusModsShareLink.parseLink(invalidLinkStringNotUrl);
            assert false;
        } catch (ParseException e) {
            assertEquals(NusModsShareLink.MESSAGE_CONSTRAINTS, e.getMessage());
        }

        try {
            NusModsShareLink.parseLink(invalidLinkStringAnotherUrl);
            assert false;
        } catch (ParseException e) {
            assertEquals(NusModsShareLink.MESSAGE_CONSTRAINTS, e.getMessage());
        }

        try {
            NusModsShareLink.parseLink(invalidLinkStringTypoUrl);
            assert false;
        } catch (ParseException e) {
            assertEquals(NusModsShareLink.MESSAGE_CONSTRAINTS, e.getMessage());
        }

        try {
            NusModsShareLink.parseLink(invalidLinkStringInvalidSemester);
            assert false;
        } catch (ParseException e) {
            assertEquals(NusModsShareLink.MESSAGE_CONSTRAINTS, e.getMessage());
        }

        try {
            NusModsShareLink.parseLink(invalidLinkStringMissingQueryString);
            assert false;
        } catch (ParseException e) {
            assertEquals(NusModsShareLink.MESSAGE_MISSING_QUERY_STRING, e.getMessage());
        }

        try {
            NusModsShareLink.parseLink(invalidLinkStringInvalidQueryString);
            assert false;
        } catch (ParseException e) {
            assertEquals(NusModsShareLink.MESSAGE_NOTHING_TO_ADD, e.getMessage());
        }

        try {
            NusModsShareLink.parseLink(invalidLinkStringInvalidClassType);
            assert false;
        } catch (ParseException e) {
            assertTrue(e.getMessage().startsWith("Invalid class type"));
        }
    }

    @Test
    void isValidUrl() {
        assertTrue(NusModsShareLink.isValidUrl(validLinkString1));
        assertTrue(NusModsShareLink.isValidUrl(validLinkString2));
        assertTrue(NusModsShareLink.isValidUrl(validLinkStringInvalidModuleCode));
        assertTrue(NusModsShareLink.isValidUrl(validLinkStringInvalidClassNumber));
        assertFalse(NusModsShareLink.isValidUrl(invalidLinkStringNotUrl));
        assertFalse(NusModsShareLink.isValidUrl(invalidLinkStringAnotherUrl));
        assertFalse(NusModsShareLink.isValidUrl(invalidLinkStringTypoUrl));
        assertFalse(NusModsShareLink.isValidUrl(invalidLinkStringMissingQueryString));
        assertFalse(NusModsShareLink.isValidUrl(invalidLinkStringInvalidQueryString));
        assertFalse(NusModsShareLink.isValidUrl(invalidLinkStringInvalidSemester));
        assertFalse(NusModsShareLink.isValidUrl(invalidLinkStringInvalidClassType));
    }
}