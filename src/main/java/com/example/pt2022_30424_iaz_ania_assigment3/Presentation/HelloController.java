package com.example.pt2022_30424_iaz_ania_assigment3.Presentation;

import com.example.pt2022_30424_iaz_ania_assigment3.BusinessLogic.ClientBLL;
import com.example.pt2022_30424_iaz_ania_assigment3.Model.Client;
import com.example.pt2022_30424_iaz_ania_assigment3.HelloApplication;
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

public class HelloController {


    private static int editAdd=-1;
    private ClientBLL clientBLL=new ClientBLL();
    @FXML
    private Button goToProd;
    @FXML
    private Button goToOrd;
    @FXML
    private Button addClients;
    @FXML
    private Button editClients;
    @FXML
    private TableView clientColumns;
    @FXML
    private Button removeCl;

    /**
     * is like a constructor for the controller; this will display the clients in the tableView
     */
    public void initialize(){
        List<Client> list = clientBLL.getClients();
        List<TableColumn<Client, Integer>> tableColumns = clientBLL.getFieldsName();
        clientColumns.getColumns().addAll(tableColumns);
        clientColumns.getItems().setAll(list);
    }

    public void goToProduct() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Product.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage=(Stage) goToProd.getScene().getWindow();
        stage.setTitle("Product");
        stage.setScene(scene);
        stage.show();
    }

    public void goToOrder() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Orders.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage=(Stage) goToOrd.getScene().getWindow();
        stage.setTitle("Orders");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * go to add client screen
     * @throws IOException
     */
    public void addClient() throws IOException {
        editAdd=0;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addClient.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 400);
        Stage stage=(Stage) addClients.getScene().getWindow();
        stage.setTitle("Add Client");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * return the selected client
     * @return
     */
    public Client getInfoClient(){
        Client newClient = (Client) clientColumns.getSelectionModel().getSelectedItem();
        return newClient;
    }

    /**
     * go to edit client screen
     * @throws IOException
     */
    public void editClient() throws IOException {
        editAdd=1;
        if(getInfoClient() == null){
            nullContent();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addClient.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 400);
            Stage stage = (Stage) editClients.getScene().getWindow();
            AddClientController addClientController = fxmlLoader.getController();
            addClientController.setToEditClient(getInfoClient());
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addClient.fxml"));
            scene = new Scene(fxmlLoader.load(), 320, 400);
            stage = (Stage) editClients.getScene().getWindow();
            stage.setTitle("Edit Client");
            stage.setScene(scene);
            stage.show();
        }
    }

    public int getEditAdd() {
        return editAdd;
    }

    public void nullContent() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection is null");
        alert.setHeaderText("Please select the client that you would like to edit or remove");
        alert.setContentText("Then click the 'Edit' or 'Remove' button");
        alert.showAndWait();
    }

    /**
     * remove the selected client
     * @throws IOException
     */
    public void removeClient() throws IOException {
        Client rmv = new Client() ;
        rmv = getInfoClient() ;
        if(rmv != null)
            clientBLL.removeClient(rmv);
        else{
            nullContent();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) removeCl.getScene().getWindow();
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();
    }
}