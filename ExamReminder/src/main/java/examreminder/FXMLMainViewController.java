/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examreminder;

import examreminder.model.Exam;
import examreminder.utils.FileUtils;
import examreminder.utils.MessageUtils;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author josik
 */
public class FXMLMainViewController implements Initializable {

    private Stage stage = null;

    @FXML
    private TableView<Exam> table;
    @FXML
    private TableColumn<Exam, String> tableSub;
    @FXML
    private TableColumn<Exam, LocalDate> tableDate;
    @FXML
    private TableColumn<Exam, Float> tableMark;
    @FXML
    private TextField textFieldSubj;
    @FXML
    private DatePicker date;
    @FXML
    private TextField textFieldMark;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button delete;
    @FXML
    private Button applyFilter;
    @FXML
    private ChoiceBox<String> cbFilter;

    private List<Exam> list;
    ObservableList<Exam> obsList;
    ObservableList<String> listFilter;
    private String[] options;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        update.setDisable(true);
        delete.setDisable(true);

        list = FileUtils.loadExams();
        initializeTable();
        obsList = FXCollections.observableList(list);

        table.setItems(obsList);
        options = new String[]{"Show all exams", "Show exams from currently selected subject", "Show exams average"};
        
        initializeFilters();
        
        date.setEditable(false);
        
        date.getEditor().setOnMouseClicked(e -> {
            date.show();
        });
    }

    /**
     * Initializes the table of subjects of the program
     */
    public void initializeTable() {
        table.setPlaceholder(new Label("No products in this category"));

        tableSub.setCellValueFactory(new PropertyValueFactory<>("subject"));
        tableDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableMark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        tableDate.setStyle("-fx-alignment: CENTER;");
        tableMark.setStyle("-fx-alignment: CENTER-RIGHT;");

        tableMark.setCellFactory(col -> {
            return new TableCell<Exam, Float>() {
                @Override
                protected void updateItem(Float mark, boolean empty) {
                    super.updateItem(mark, empty);

                    if (mark == null || empty || mark == -1) {
                        setText(null);
                    } else {
                        setText(String.format("%.2f", mark));
                    }
                }
            };
        });

        // A product is selected from the table
        List<String> listOption = new ArrayList<>();
        listOption.add("Show all exams");
        listOption.add("Show exams from currently select subject");
        listOption.add("Show exams average");
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                textFieldSubj.setText(newSelection.getSubject());
                textFieldMark.setText(String.format("%.2f", newSelection.getMark()));
                // split the date and rebuild with "/"
                String[] d = newSelection.getDate().toString().split("-");
                String d2 = d[2] + "/" + d[1] + "/" + d[0];
                LocalDate localDate = LocalDate.parse(d2, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                date.setValue(localDate);
                add.setDisable(true);
                update.setDisable(false);
                delete.setDisable(false);
            } else {
                add.setDisable(false);
                update.setDisable(true);
                delete.setDisable(true);
            }
        });
    }

    /**
     * This function add a exam to table and the exams list
     * @param event 
     */
    @FXML
    private void addExam(ActionEvent event) {

        Exam e = null;
        try {
            float markTemp = Float.parseFloat(textFieldMark.getText());
            e = new Exam(textFieldSubj.getText(), date.getValue(), markTemp);
        } catch (Exception ex) {
            e = new Exam(textFieldSubj.getText(), date.getValue());
        }

        list.add(e);

        initializeTable();
        obsList = FXCollections.observableList(list);

        table.setItems(obsList);

        textFieldSubj.clear();
        textFieldMark.clear();
        date.setValue(null);
    }

    /**
     * Update a exam of exam table and the exams list
     * @param event 
     */
    @FXML
    private void updateExam(ActionEvent event) {
        int index = table.getSelectionModel().getSelectedIndex();
        Exam e = null;
        try {
            float markTemp = Float.parseFloat(textFieldMark.getText());
            e = new Exam(textFieldSubj.getText(), date.getValue(), markTemp);
        } catch (Exception ex) {
            e = new Exam(textFieldSubj.getText(), date.getValue());
        }
        list.set(index, e);

        initializeTable();
        obsList = FXCollections.observableList(list);

        table.setItems(obsList);

        textFieldSubj.clear();
        textFieldMark.clear();
        date.setValue(null);
    }
    
    /**
     * Delete a exam of exam table and the exams list
     * @param event 
     */
    @FXML
    private void deleteExam(ActionEvent event) {
        Exam selectedProd = table.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete product " + selectedProd.getSubject());

        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            list.remove(selectedProd);
            obsList.remove(selectedProd);
            table.getSelectionModel().clearSelection();
        }
    }

    /**
     * This function picks up the close command and calls another function
     * @param stage 
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        setOnWindowClose();
    }

    /**
     * This function is the one in charge of saving the exams in the file
     */
    private void setOnWindowClose() {
        if (stage == null) {
            return;
        }

        stage.setOnCloseRequest(event -> {
            FileUtils.saveExams(list);
        });
    }

    /**
     * Initialize filters to choice box
     */
    private void initializeFilters() {
        listFilter = FXCollections.observableArrayList(options);
        cbFilter.setItems(listFilter);
    }

    /**
     * When you click Apply Filter this function captures the filter and applies it.
     * @param event 
     */
    @FXML
    private void applyFilterExam(ActionEvent event) {
        // "Show exams from currently selected subject"
        if (cbFilter.getValue().equals(options[1])) {
            obsList = FXCollections.observableArrayList(
                    list.stream().filter(p -> p.getSubject().equals(
                            textFieldSubj.getText())).collect(Collectors.toList()));

            table.setItems(obsList);
        } //"Show exams average" 
        else if (cbFilter.getValue().equals(options[2])) {
            Double average = list.stream().filter(p -> p.getMark() > 0)
                    .mapToDouble(Exam::getMark).average().getAsDouble();
            MessageUtils.showMessage("Exams average ## The average of selected exams is " + average);
        } // show all exams
        else {
            initialize(null, null);
        }
    }
}
