/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examreminder;

import examreminder.model.Exam;
import examreminder.utils.FileUtils;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author josik
 */
public class FXMLMainViewController implements Initializable {

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
    private ComboBox<?> comboFilter;
    @FXML
    private Button applyFilter;
    
    private List<Exam> list;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list = FileUtils.loadExams();
        initializeTable();
        ObservableList<Exam> obsList = FXCollections.observableList(list);
        
        
        
        table.setItems(obsList);
        
//        for (int i = 0; i < list.size(); i++) {
//            tableSub.setText(list.get(i).getSubject());
//            tableDate.setText(list.get(i).getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//            if (list.get(i).getMark() != -1) {
//                tableMark.setText("" + list.get(i).getMark());
//            }
//        }
    }
    
    public void initializeTable(){
        table.setPlaceholder(new Label("No products in this category"));
        
        tableSub.setCellValueFactory(new PropertyValueFactory<>("subject"));
        tableDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableMark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        tableDate.setStyle( "-fx-alignment: CENTER;");
        tableMark.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        tableMark.setCellFactory(col -> {  // How to show the price column's value
            return new TableCell<Exam, Float>() {
                //@Override
                protected void updateItem(Float mark, boolean empty) {
                    //super.updateItem(price, empty);
                    
                    if (mark == null || empty) {
                        setText(null);
                    } else {
                        setText(String.format("%.2f", mark));
                    }
                }
            };
        });
        
        // A product is selected from the table
//        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                productFieldsContainer.setVisible(true);
//                categoryChoice.getSelectionModel().select(
//                        categoryList.getSelectionModel().selectedIndexProperty().intValue());
//                referenceInput.setText(newSelection.getReference());
//                nameInput.setText(newSelection.getName());
//                priceInput.setText(String.format("%.2f", newSelection.getPrice()));
//                saveProductBt.setDisable(false);
//                deleteProductBt.setDisable(false);
//            } else {
//                productFieldsContainer.setVisible(false);
//                saveProductBt.setDisable(true);
//                deleteProductBt.setDisable(true);
//            }
//        });
    }
    
}
