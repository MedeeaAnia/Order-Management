package com.example.pt2022_30424_iaz_ania_assigment3.Presentation;

import com.example.pt2022_30424_iaz_ania_assigment3.BusinessLogic.ProductBLL;
import com.example.pt2022_30424_iaz_ania_assigment3.HelloApplication;
import com.example.pt2022_30424_iaz_ania_assigment3.Model.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class AddProdController {
    public static Product newProduct;
    @FXML
    private Button backToProduct;
    @FXML
    private Button addProd;
    @FXML
    private TextField productName;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productQuantity;


    /**
     * displays the content of the selected product in able to edit it easier
     */
    public void initialize() {
        if (newProduct != null) {
            productName.setText(newProduct.getNameProduct());
            productPrice.setText(String.valueOf(newProduct.getPrice()));
            productQuantity.setText(String.valueOf(newProduct.getQuantity()));
        }
    }

    public void goBackToProduct() throws IOException {
        newProduct = null;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Product.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) backToProduct.getScene().getWindow();
        stage.setTitle("Product");
        stage.setScene(scene);
        stage.show();
    }


    public boolean validateNumber(String s){
        final String VERIFY_NUMBERS = "([0-9]+)";
        Pattern pattern = Pattern.compile(VERIFY_NUMBERS);
        if(pattern.matcher(s).matches()){
            return true;
        }
        return false;
    }

    /**
     * depending on the button pressed it adds or edits a selected product
     * @throws IOException
     */
    public void addOrEditProduct() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Product.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) addProd.getScene().getWindow();
        ProductController productController = fxmlLoader.getController();
        if (productController.getAddOrEdit() == 1) {
            //when the variable is 1 it means that the user clicked the edit button
            if (productName.getText().isEmpty() || productPrice.getText().isEmpty() || productQuantity.getText().isEmpty()) {
                emptyFields();
            }else if(validateNumber(productPrice.getText()) == false || validateNumber(productQuantity.getText()) == false){
                notNumber();
            }else if (Integer.valueOf(productPrice.getText()) <= 0 || Integer.valueOf(productQuantity.getText()) <= 0) {
                negativeValues();
            } else {
                ProductBLL productBLL = new ProductBLL();
                productBLL.editProduct(newProduct.getId(), productName.getText(), Integer.valueOf(productPrice.getText()), Integer.valueOf(productQuantity.getText()));
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Product.fxml"));
                scene = new Scene(fxmlLoader.load(), 600, 400);
                stage = (Stage) addProd.getScene().getWindow();
                stage.setTitle("Product");
                stage.setScene(scene);
                stage.show();
            }
            newProduct = null;
        } else if (productController.getAddOrEdit() == 0) {
            //like in the case of the client when the variable addOrEdit is 0 we want to add
            if (productName.getText().isEmpty() || productPrice.getText().isEmpty() || productQuantity.getText().isEmpty()) {
                emptyFields();
            }else if(validateNumber(productPrice.getText()) == false || validateNumber(productQuantity.getText()) == false){
                notNumber();
            } else if (Integer.valueOf(productPrice.getText()) <= 0 || Integer.valueOf(productQuantity.getText()) <= 0) {
                negativeValues();
            } else {
                ProductBLL productBLL = new ProductBLL();
                productBLL.addProduct(productName.getText(), Integer.valueOf(productPrice.getText()), Integer.valueOf(productQuantity.getText()));
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Product.fxml"));
                scene = new Scene(fxmlLoader.load(), 600, 400);
                stage = (Stage) addProd.getScene().getWindow();
                stage.setTitle("Product");
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public void setNewProduct(Product newProduct) {
        AddProdController.newProduct = newProduct;
    }

    public void emptyFields() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("One or more fields are empty");
        alert.setHeaderText("Please insert all the fields then press 'Add'");
        alert.setContentText("");
        alert.showAndWait();
    }


    public void negativeValues() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Negative values");
        alert.setHeaderText("Please insert positive numbers only then 'Add'");
        alert.setContentText("Price and quantity needs to be higher than 0");
        alert.showAndWait();
    }

    public void notNumber(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect data");
        alert.setHeaderText("Please introduce numbers only for price and quantity");
        alert.setContentText("");
        alert.showAndWait();
    }
}
