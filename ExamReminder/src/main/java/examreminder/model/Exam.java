package examreminder.model;

import java.time.LocalDate;

/**
 *
 * @author Jose Muñoz
 */
public class Exam {
    protected String subject;
    protected LocalDate date;
    protected float mark;

    public Exam(String subject, LocalDate date) {
        this.subject = subject;
        this.date = date;
        mark = -1f;
    }

    public Exam(String subject, LocalDate date, float mark) {
        this.subject = subject;
        this.date = date;
        this.mark = mark;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }
    
}
