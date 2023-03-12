package com.example.pt2022_30424_iaz_ania_assigment3.Presentation;

import com.example.pt2022_30424_iaz_ania_assigment3.BusinessLogic.ClientBLL;
import com.example.pt2022_30424_iaz_ania_assigment3.BusinessLogic.OrderBLL;
import com.example.pt2022_30424_iaz_ania_assigment3.BusinessLogic.ProductBLL;
import com.example.pt2022_30424_iaz_ania_assigment3.HelloApplication;
import com.example.pt2022_30424_iaz_ania_assigment3.Model.Client;
import com.example.pt2022_30424_iaz_ania_assigment3.Model.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class AddOrderController {
    private ProductBLL productBLL = new ProductBLL();
    private OrderBLL orderBLL = new OrderBLL();
    private ClientBLL clientBLL = new ClientBLL();
    FileWriter file = null;
    @FXML
    private Button backToOrder;
    @FXML
    private Button addOrder;
    @FXML
    private TextField clientId;
    @FXML
    private TextField productId;
    @FXML
    private TextField quantity;

    public void goBackToOrder() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Orders.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) backToOrder.getScene().getWindow();
        stage.setTitle("Orders");
        stage.setScene(scene);
        stage.show();
    }

    public boolean findProduct(int id) {
        List<Product> list = productBLL.getProducts();
        for (Product product : list) {
            if (product.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns true if the id specified was found in the table
     * @param id
     * @return
     */
    public boolean findClient(int id) {
        List<Client> list = clientBLL.getClients();
        for (Client client : list) {
            if (client.getId() == id) {
                return true;
            }
        }
        return false;
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
     * after every order added->writes in a file.txt customer name, product bought and total value to pay
     * @throws IOException
     */
    public void writeBill() throws IOException {
        file.write("CUSTOMER: ");
        file.write(clientBLL.findClient(Integer.valueOf(clientId.getText())).getName());
        file.write("\n");
        file.write("PRODUCT: ");
        file.write(productBLL.findPrd(Integer.valueOf(productId.getText())).getNameProduct());
        file.write("\n");
        file.write("TOTAL VALUE TO PAY: ");
        int total = Integer.valueOf(quantity.getText())*productBLL.findPrd(Integer.valueOf(productId.getText())).getPrice();
        file.write(String.valueOf(total));
    }


    /**
     * validates the data introduced and adds an order
     * @throws IOException
     */
    public void addOrder() throws IOException {
        if (productId.getText().isEmpty() || clientId.getText().isEmpty() || quantity.getText().isEmpty()) {
            emptyFields();
        } else if(validateNumber(productId.getText()) == false || validateNumber(clientId.getText()) == false || validateNumber(quantity.getText()) == false){
            notNumber();
        }
        else if (Integer.valueOf(quantity.getText()) <= 0 || Integer.valueOf(productId.getText()) < 0 || Integer.valueOf(clientId.getText()) < 0) {
            negativeQuantity();
        } else if (findProduct(Integer.valueOf(productId.getText())) == false) {
            noPrd();
        } else if (productBLL.findPrd(Integer.valueOf(productId.getText())).getQuantity() < Integer.valueOf(quantity.getText())) {
            if (productBLL.findPrd(Integer.valueOf(productId.getText())).getQuantity() == 0) {
                noProduct();
            } else {
                outOfStock();
            }
        } else if (findClient(Integer.valueOf(clientId.getText())) == false) {
            noClient();
        }else{
                orderBLL.addOrder(Integer.valueOf(clientId.getText()), Integer.valueOf(productId.getText()), Integer.valueOf(quantity.getText()));
                Product prd = productBLL.findPrd(Integer.valueOf(productId.getText()));
                productBLL.editProduct(prd.getId(), prd.getNameProduct(), prd.getPrice(), prd.getQuantity() - Integer.valueOf(quantity.getText()));
                try {
                    file = new FileWriter("bill.txt");
                    writeBill();
                    file.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Orders.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage stage = (Stage) addOrder.getScene().getWindow();
                stage.setTitle("Orders");
                stage.setScene(scene);
                stage.show();
            }
        }


        public void emptyFields () {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("One or more fields are empty");
            alert.setHeaderText("Please insert all the fields then press 'Add'");
            alert.setContentText("");
            alert.showAndWait();
        }

        public void negativeQuantity () {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Negative values");
            alert.setHeaderText("Please insert only positive numbers");
            alert.setContentText("Then press 'Add'");
            alert.showAndWait();
        }

        public void outOfStock () {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("OUT OF STOCK");
            alert.setHeaderText("The quantity you inserted is higher than the stock of the product");
            alert.setContentText("Please reinsert the quantity");
            alert.showAndWait();
        }

        public void noProduct () {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("OUT OF STOCK");
            alert.setHeaderText("The product you selected is out of stock");
            alert.setContentText("Please reinsert product Id");
            alert.showAndWait();
        }

        public void noClient () {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NO CLIENT");
            alert.setHeaderText("The id of the client does not exist");
            alert.setContentText("Please reinsert client Id");
            alert.showAndWait();
        }

        public void noPrd () {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NO PRODUCT");
            alert.setHeaderText("The id of the product does not exist");
            alert.setContentText("Please reinsert product Id");
            alert.showAndWait();
        }

        public void notNumber(){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect data");
            alert.setHeaderText("Please introduce numbers only");
            alert.setContentText("");
            alert.showAndWait();
        }

    }
