package seedu.address.model.module;

import java.util.Objects;

/**
 * Semester number of the Semester.
 */
public class SemesterNo {
    private String semesterNo;

    public SemesterNo(String semesterNo) {
        this.semesterNo = semesterNo;
    }

    public String getSemesterNo() {
        return semesterNo;
    }

    public void setSemesterNo(String semesterNo) {
        this.semesterNo = semesterNo;
    }

    @Override
    public String toString() {
        return semesterNo;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SemesterNo)) {
            return false;
        }
        SemesterNo sNo = (SemesterNo) other;
        if (sNo == this) {
            return true;
        } else if (sNo.semesterNo.equals(this.semesterNo)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(semesterNo);
    }
}
