package com.example.pt2022_30424_iaz_ania_assigment3.Presentation;

import com.example.pt2022_30424_iaz_ania_assigment3.BusinessLogic.ProductBLL;
import com.example.pt2022_30424_iaz_ania_assigment3.HelloApplication;
import com.example.pt2022_30424_iaz_ania_assigment3.Model.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class ProductController {
    private static int addOrEdit = -1;
    @FXML
    private Button goToOrd;
    @FXML
    private Button goToCl;
    @FXML
    private Button addPrd;
    @FXML
    private Button editPrd;
    @FXML
    private TableView productTable;
    @FXML
    private Button removePrd;

    private ProductBLL productBLL = new ProductBLL();

    /**
     * display the content of the product table
     */
    public void initialize() {
        List<Product> list = productBLL.getProducts();
        List<TableColumn<Product, Integer>> fields = productBLL.getFieldsName();
        productTable.getColumns().addAll(fields);
        productTable.getItems().setAll(list);
    }

    public void goToOrder() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Orders.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) goToOrd.getScene().getWindow();
        stage.setTitle("Orders");
        stage.setScene(scene);
        stage.show();
    }

    public void goToClient() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) goToCl.getScene().getWindow();
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * go to add product screen
     * @throws IOException
     */
    public void addProduct() throws IOException {
        addOrEdit = 0;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 400);
        Stage stage = (Stage) addPrd.getScene().getWindow();
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * return the selected product
     * @return
     */
    public Product getProductInfo() {
        Product pr = (Product) productTable.getSelectionModel().getSelectedItem();
        return pr;
    }

    /**
     * go to edit product screen
     * @throws IOException
     */
    public void editProduct() throws IOException {
        addOrEdit = 1;
        if (getProductInfo() == null) {
           nullContent();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addProduct.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 400);
            Stage stage = (Stage) editPrd.getScene().getWindow();
            AddProdController addProdController = fxmlLoader.getController();
            addProdController.setNewProduct(getProductInfo());
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addProduct.fxml"));
            scene = new Scene(fxmlLoader.load(), 320, 400);
            stage = (Stage) editPrd.getScene().getWindow();
            stage.setTitle("Edit Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    public int getAddOrEdit() {
        return addOrEdit;
    }

    /**
     * remove the selected product
     * @throws IOException
     */
    public void removeProduct() throws IOException {
        if(getProductInfo() == null){
            nullSelection();
        }
        else{
            productBLL.removeProduct(getProductInfo());
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Product.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = (Stage) removePrd.getScene().getWindow();
            stage.setTitle("Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void nullContent() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection is null");
        alert.setHeaderText("Please select the product that you would like to edit");
        alert.setContentText("Then click the 'Edit' button");
        alert.showAndWait();
    }

    public void nullSelection() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection is null");
        alert.setHeaderText("Please select the product that you would like to remove");
        alert.setContentText("Then click the 'Remove' button");
        alert.showAndWait();
    }

}
