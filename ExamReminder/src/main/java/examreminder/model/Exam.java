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

    /**
     * Constructor
     * @param subject
     * @param date 
     */
    public Exam(String subject, LocalDate date) {
        this.subject = subject;
        this.date = date;
        mark = -1f;
    }

    /** 
     * Other constructor
     * @param subject
     * @param date
     * @param mark 
     */
    public Exam(String subject, LocalDate date, float mark) {
        this.subject = subject;
        this.date = date;
        this.mark = mark;
    }

    /**
     * 
     * @return exam subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Change the subject
     * @param subject 
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 
     * @return exam date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Change exam date
     * @param date 
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * 
     * @return exam mark
     */
    public float getMark() {
        return mark;
    }

    /**
     * Change exam mark
     * @param mark 
     */
    public void setMark(float mark) {
        this.mark = mark;
    }
    
}
