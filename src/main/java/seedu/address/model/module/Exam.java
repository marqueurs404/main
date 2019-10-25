package seedu.address.model.module;

import java.time.LocalDateTime;

/**
 * Exam for a Module in a Semester.
 */
public class Exam {
    private final LocalDateTime examDate;
    private final int examDuration;

    public Exam() {
        this.examDate = LocalDateTime.MIN;
        this.examDuration = -1;
    }

    public Exam(LocalDateTime examDate, int examDuration) {
        this.examDate = examDate;
        this.examDuration = examDuration;
    }

    public static Exam emptyExam() {
        return new Exam();
    }

    public LocalDateTime getExamDate() {
        return examDate;
    }

    public int getExamDuration() {
        return examDuration;
    }

    @Override
    public String toString() {
        return "Exam Date: " + examDate.toString() + " " + Integer.toString(examDuration);
    }

    /**
     * Returns true if both exam are the same instance of exam.
     */
    public boolean equals(Exam other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other.examDate.equals(this.examDate) && other.examDuration == this.examDuration) {
            return true;
        } else {
            return false;
        }
    }
}
