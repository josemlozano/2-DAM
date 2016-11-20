package examreminder.utils;

import examreminder.model.Exam;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose Muñoz
 */
public class FileUtils {
    
    private static final String EXAMS = "exams.txt";
    private static final String PASSWORD = "password.txt";
    
    /**
     * Load exams of a file
     * @return listExam
     */
    public static List<Exam> loadExams() {
        List<Exam> listExam = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(
                new FileReader(new File(EXAMS)))) {
            String line;
            Exam e = null;
            do {
                line = reader.readLine();
                if (!line.equals("")) {
                    String[] data = line.split(":");
                    if (data.length == 2) {
                        e = new Exam(data[0], LocalDate.parse(data[1], 
                                DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        listExam.add(e);
                    }
                    else {
                        e = new Exam(data[0], LocalDate.parse(data[1], 
                                DateTimeFormatter.ofPattern("dd/MM/yyyy")), 
                                Float.parseFloat(data[2]));
                        listExam.add(e);
                    }
                }                
            } while (!line.equals(""));
            
        } catch (Exception e){
            System.err.println("Error load: " + e.getMessage());
        }
        return listExam;
    }
    
    /**
     * Save exams in its file
     * @param exams 
     */
    public static void saveExams (List<Exam> exams) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(EXAMS)))){
            exams.stream().forEach(p -> {
                if (p.getMark() != -1) {
                    printWriter.write(p.getSubject() + ":" + p.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) 
                            + ":" + p.getMark() + "\n");
                }
                else {
                    printWriter.write(p.getSubject() + ":" + p.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) 
                            + "\n");
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Verify that the user and password are correct
     * @param login
     * @param pass
     * @return true if the user and password are correct, and false if user and
     * password aren't correct
     */
    public static boolean read(String login, String pass) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(new File(PASSWORD)))) {
            String line = reader.readLine();
            String[] lineArray = line.split(":");
            
            if (lineArray[0].equals(login) && lineArray[1].equals(pass)) {
                return true;
            }
            
        } catch (Exception e){
            System.err.println("Error read: " + e.getMessage());
        }
        return false;
    }
}
