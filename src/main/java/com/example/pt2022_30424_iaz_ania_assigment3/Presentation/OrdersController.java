package com.example.pt2022_30424_iaz_ania_assigment3.Presentation;
import com.example.pt2022_30424_iaz_ania_assigment3.BusinessLogic.OrderBLL;
import com.example.pt2022_30424_iaz_ania_assigment3.HelloApplication;
import com.example.pt2022_30424_iaz_ania_assigment3.Model.Orders;
import com.example.pt2022_30424_iaz_ania_assigment3.Model.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class OrdersController {
    @FXML
    private Button goToProd;
    @FXML
    private Button goToCl;
    @FXML
    private Button addOrd;
    @FXML
    private TableView ordersTable;

    private OrderBLL orderBLL = new OrderBLL();

    /**
     * display the content of the orders table
     */
    public void initialize(){
        List<Orders> list = orderBLL.getOrders();
        List<TableColumn<Orders, Integer>> fields = orderBLL.getFieldsName();
        ordersTable.getColumns().addAll(fields);
        ordersTable.getItems().setAll(list);
    }

    /**
     * switch to product screen
     * @throws IOException
     */
    public void goToProduct() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Product.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage=(Stage) goToProd.getScene().getWindow();
        stage.setTitle("Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * switch to client screen
     * @throws IOException
     */
    public void goToClient() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage=(Stage) goToCl.getScene().getWindow();
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * switch to add order screen
     * @throws IOException
     */
    public void addOrder() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addOrder.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 400);
        Stage stage=(Stage) addOrd.getScene().getWindow();
        stage.setTitle("Add Order");
        stage.setScene(scene);
        stage.show();
    }

}
