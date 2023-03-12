package com.example.pt2022_30424_iaz_ania_assigment3.BusinessLogic;

import com.example.pt2022_30424_iaz_ania_assigment3.DataAccess.ProductDAO;
import com.example.pt2022_30424_iaz_ania_assigment3.Model.Product;
import javafx.scene.control.TableColumn;

import java.util.List;

public class ProductBLL {

    private ProductDAO productDAO;

    public ProductBLL(){
        productDAO = new ProductDAO();
    }

    /**
     * @return -> list with all the product from the table
     */
    public List<Product> getProducts(){
        List<Product> products;
        products = productDAO.findAll();
        return products;
    }

    /**
     * @return -> list with all the fields of the table
     */
    public List<TableColumn<Product, Integer>> getFieldsName(){
        List<TableColumn<Product, Integer>> list = productDAO.getFields();
        return list;
    }

    /**
     * creates and adds to the table the product
     * @param nameProduct
     * @param price
     * @param quantity
     */
    public void addProduct(String nameProduct, int price, int quantity){
        Product product = new Product(nameProduct, price, quantity);
        productDAO.insert(product);
    }

    /**
     * creates a new product and updates the one with the same id
     * @param id
     * @param nameProduct
     * @param price
     * @param quantity
     */
    public void editProduct(int id, String nameProduct, int price, int quantity){
        Product product = new Product(id, nameProduct, price, quantity);
        productDAO.update(product);
    }

    /**
     * removes the product specified as parameter
     * @param remove
     */
    public void removeProduct(Product remove){
        productDAO.remove(remove);
    }

    /**
     * @param id
     * @return ->product with the id specified as parameter
     */
    public Product findPrd(int id){
        return productDAO.findById(id);
    }
}
