package com.example.pt2022_30424_iaz_ania_assigment3.BusinessLogic;

import com.example.pt2022_30424_iaz_ania_assigment3.DataAccess.ClientDAO;
import com.example.pt2022_30424_iaz_ania_assigment3.Model.Client;
import javafx.scene.control.TableColumn;

import java.util.List;

public class ClientBLL {
    private ClientDAO clientDAO;

    public ClientBLL(){
        clientDAO = new ClientDAO();
    }

    /**
     * @return -> list of all the clients in the table
     */
    public List<Client> getClients(){
        List<Client> clients;
        clients = clientDAO.findAll();
        return clients;
    }

    /**
     * @return ->list with all fields name
     */
    public List<TableColumn<Client, Integer>> getFieldsName(){
        List<TableColumn<Client, Integer>> list = clientDAO.getFields();
        return list;
    }

    /**
     * creates and adds a client in the table
     * @param name
     * @param address
     * @param email
     * @param age
     */
    public void addClient(String name, String address, String email, int age){
        Client client = new Client(name, address, email, age);
        clientDAO.insert(client);
    }

    /**
     * creates and updates the client with the new fields
     * @param id
     * @param name
     * @param address
     * @param email
     * @param age
     */
    public void editClient(int id, String name, String address, String email, int age){
        Client client = new Client(id,name, address, email, age);
        clientDAO.update(client);
    }

    /**
     * removes from the table the client specified
     * @param cl ->client that we want to remove
     */
    public void removeClient(Client cl){
        clientDAO.remove(cl);
    }

    /**
     * @param id
     * @return -> client found with the parameter id
     */
    public Client findClient(int id){
        return clientDAO.findById(id);
    }
}
