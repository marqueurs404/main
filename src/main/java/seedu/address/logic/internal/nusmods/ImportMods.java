package seedu.address.logic.internal.nusmods;

import seedu.address.commons.core.AppSettings;
import seedu.address.websocket.NusModsApi;

/**
 * Internal class to be executed as a standalone program to import all NUSMods detailed module data.
 */
public class ImportMods {
    public static void main(String[] args) {
        //loop through every mod in modlist
        NusModsApi api = new NusModsApi(AppSettings.DEFAULT_ACAD_YEAR);
        return;
    }
}
