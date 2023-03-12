package com.example.pt2022_30424_iaz_ania_assigment3.BusinessLogic;

import com.example.pt2022_30424_iaz_ania_assigment3.DataAccess.OrdersDAO;
import com.example.pt2022_30424_iaz_ania_assigment3.Model.Client;
import com.example.pt2022_30424_iaz_ania_assigment3.Model.Orders;
import javafx.scene.control.TableColumn;

import java.util.List;

public class OrderBLL {
    private OrdersDAO ordersDAO;

    public OrderBLL(){
        ordersDAO= new OrdersDAO();
    }

    /**
     * @return ->list with all the orders from the table
     */
    public List<Orders> getOrders(){
        List<Orders> orders;
        orders = ordersDAO.findAll();
        return orders;
    }

    /**
     * @return ->list with all the fields in the table
     */
    public List<TableColumn<Orders, Integer>> getFieldsName(){
        List<TableColumn<Orders, Integer>> list = ordersDAO.getFields();
        return list;
    }

    /**
     * creates a new order and adds it to the table
     * @param clientId
     * @param productId
     * @param quantity
     */
    public void addOrder(int clientId, int productId, int quantity){
        Orders orders = new Orders(clientId,productId,quantity);
        ordersDAO.insert(orders);
    }

}
