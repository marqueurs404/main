package seedu.address.model;

import java.time.LocalDate;
import java.util.Optional;

import seedu.address.model.module.AcadCalendar;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleId;
import seedu.address.model.module.ModuleList;
import seedu.address.model.module.ModuleSummaryList;
import seedu.address.model.module.SemesterNo;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.websocket.Cache;

/**
 * Contains all the information the app needs from NUSMods such as modules, academic calendar, holidays, etc.
 */
public class NusModsData {

    private ModuleSummaryList moduleSummaryList;
    private ModuleList moduleList;
    private AcadCalendar acadCalendar;
    private Holidays holidays;

    public NusModsData() {
        this.moduleSummaryList = new ModuleSummaryList();
        this.moduleList = new ModuleList();
        this.acadCalendar = new AcadCalendar();
        this.holidays = new Holidays();
    }

    public ModuleSummaryList getModuleSummaryList() {
        return moduleSummaryList;
    }

    public void setModuleSummaryList(ModuleSummaryList moduleSummaryList) {
        this.moduleSummaryList = moduleSummaryList;
    }

    public ModuleList getModuleList() {
        return moduleList;
    }

    public void setModuleList(ModuleList moduleList) {
        this.moduleList = moduleList;
    }

    public void addModule(Module module) {
        moduleList.addModule(module);
    }

    /**
     * Returns a module for the given ModuleId (academic year and module code).
     * Tries to find the module from the 4 sources in order:
     * 1. Model.NusModsData.ModuleList (in-memory)
     * 2. tempdir folder
     * 3. resources folder
     * 4. NusModsApi
     *
     * @param moduleId a {@code ModuleId}.
     * @return a {@code Module} if found.
     * @throws ModuleNotFoundException if no {@code Module} is found.
     */
    public Module findModule(ModuleId moduleId) throws ModuleNotFoundException {
        Module module;
        try {
            module = moduleList.findModule(moduleId);
        } catch (ModuleNotFoundException ex1) {
            Optional<Module> moduleOptional = Cache.loadModule(moduleId);
            if (moduleOptional.isEmpty()) {
                throw new ModuleNotFoundException();
            }
            module = moduleOptional.get();
        }
        return module;
    }

    public void setAcadCalendar(AcadCalendar acadCalendar) {
        this.acadCalendar = acadCalendar;
    }

    public LocalDate getStartDate(AcadYear acadYear, SemesterNo semesterNo) {
        return this.acadCalendar.getStartDate(acadYear, semesterNo);
    }

    public Holidays getHolidays() {
        return holidays;
    }

    public void setHolidays(Holidays holidays) {
        this.holidays = holidays;
    }
}
