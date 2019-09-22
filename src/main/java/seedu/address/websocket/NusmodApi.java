package seedu.address.websocket;
import org.json.simple.JSONObject;
import seedu.address.commons.core.LogsCenter;
import seedu.address.websocket.util.ApiQuery;
import seedu.address.websocket.util.ParserUtil;
import seedu.address.websocket.util.QueryResult;

import java.util.logging.Logger;


/**
 * NUSMods API websocket
 * Refer to https://api.nusmods.com/v2/#/ for more detailed information
 */
public class NusmodApi {
    private final String BASEURL = "https://api.nusmods.com/v2";
    private final String BASEURL2 = "http://api.nusmods.com/v2";

    private final String ACAD_YEAR = "/2018-2019";
    private final String MODULES = "/modules";
    private final String SLASH = "/";
    private final String JSON_EXTENTION = ".json";
    private final String MODULE_LIST = "/moduleList";
    private final String MODULE_INFO = "/moduleInfo";
    private final String SEMESTERS = "/semesters";
    private final String VENUES = "/venues";
    private final String VENUE_INFO = "/venueInformation";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public NusmodApi() {

    }

    /**
     * Returns a JSONObject of summaries of all modules in ACAD_YEAR
     *
     * @return JSONObject
     */
    public JSONObject getModuleList() {
        ApiQuery query = new ApiQuery(BASEURL + ACAD_YEAR + MODULE_LIST + JSON_EXTENTION);
        QueryResult queryResult = query.execute();
        if(queryResult.process(logger)){
            JSONObject obj = ParserUtil.parseStringToJSONObject(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Returns a JSONObject of detailed information about all modules in ACED_YEAR
     *
     * @return
     */
    public JSONObject getModuleInfo() {
        ApiQuery query = new ApiQuery(BASEURL + ACAD_YEAR + MODULE_INFO + JSON_EXTENTION);
        QueryResult queryResult = query.execute();
        if(queryResult.process(logger)){
            JSONObject obj = ParserUtil.parseStringToJSONObject(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Returns a JSONObject of all information about moduleCode in ACAD_YEAR
     *
     * @param moduleCode code of the specific module
     * @return JSONObject representing information of the module
     */
    public JSONObject getModules(String moduleCode) {
        ApiQuery query = new ApiQuery(BASEURL + ACAD_YEAR + MODULES + SLASH + moduleCode + JSON_EXTENTION);
        QueryResult queryResult = query.execute();
        if(queryResult.process(logger)){
            JSONObject obj = ParserUtil.parseStringToJSONObject(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Returns a JSONObject of summaries of all venues for the current semester
     *
     * @param semester current semmester: 1 -> sem 1
     *                 2 -> sem 2
     *                 3 -> special term 1
     *                 4 -> special term 2
     * @return JSONObject representing the venues
     */
    public JSONObject getVenues(String semester) {
        ApiQuery query = new ApiQuery(BASEURL + ACAD_YEAR + SEMESTERS + semester + VENUES + JSON_EXTENTION);
        QueryResult queryResult = query.execute();
        if(queryResult.process(logger)){
            JSONObject obj = ParserUtil.parseStringToJSONObject(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Returns a JSONObject of detailed infomation of all venues
     *
     * @param semester current semmester: 1 -> sem 1
     *                 2 -> sem 2
     *                 3 -> special term 1
     *                 4 -> special term 2
     * @return JSONObject representing the venues
     */
    public JSONObject getVenueInformation(String semester) {
        ApiQuery query = new ApiQuery(BASEURL + ACAD_YEAR + SEMESTERS + semester + VENUE_INFO + JSON_EXTENTION);
        QueryResult queryResult = query.execute();
        if(queryResult.process(logger)){
            JSONObject obj = ParserUtil.parseStringToJSONObject(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }
}
