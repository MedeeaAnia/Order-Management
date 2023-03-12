package com.example.pt2022_30424_iaz_ania_assigment3.DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.pt2022_30424_iaz_ania_assigment3.Connection.ConnectionFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * @return all the rows from the table
     */
    public List<T> findAll() {
        // TODO:
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase();
        connection = ConnectionFactory.getConnection();
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param id
     * @return the object if found; searched by the id specified as parameter
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * inserts the object specified as a parameter in the corresponding table
     * @param t
     * @return
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        int length = type.getDeclaredFields().length;
        String query = "INSERT INTO " + type.getSimpleName() + "(";
        for (Field field : type.getDeclaredFields()) {
            //PUT THE FIELDS NAME
            length--;
            if (!field.getName().equals("id")) {
                if (length != 0)
                    query = query + '"' + field.getName() + '"' + ",";
                else
                    query = query + '"' + field.getName() + '"';
            }
        }
        query = query + ") VALUES (";
        length = type.getDeclaredFields().length;
        for (Field field : type.getDeclaredFields()) {
            length--;
            if (!field.getName().equals("id")) {
                if (length != 0)
                    query = query + "? ,";
                else
                    query = query + "?";
            }
        }
        query = query + ");";
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int index = 1;
            for (Field field : type.getDeclaredFields()) {
                if (!field.getName().equals("id")) {
                    try {
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                        Method method = propertyDescriptor.getReadMethod();
                        if (field.getType() == int.class) {
                            statement.setInt(index, (Integer) method.invoke(t));
                            index++;
                        } else if (field.getType() == String.class) {
                            //System.out.println((String) method.invoke(t, field.getName()));
                            statement.setString(index, (String) method.invoke(t));
                            index++;
                        }
                        System.out.println(statement.toString());
                    } catch (IntrospectionException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * updates the object specified as a parameter
     * @param t
     * @return
     */
    public T update(T t) {
        // TODO:
        Connection connection = null;
        PreparedStatement statement = null;
        int length = type.getDeclaredFields().length;
        String query = "UPDATE " + type.getSimpleName() + " SET ";
        for (Field field : type.getDeclaredFields()) {
            length --;
            if (!field.getName().equals("id")) {
                if (length != 0)
                    query = query +'"'+ field.getName() + '"'+" = ?,";
                else
                    query = query +'"'+ field.getName() + '"'+ " = ?";
            }
        }
        for (Field field : type.getDeclaredFields()){
            if (field.getName().equals("id"))
                query = query + " WHERE "+ '"'+ field.getName() + '"'+" = ?";
        }
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int index = 1;
            for (Field field : type.getDeclaredFields()) {
                if (!field.getName().equals("id")) {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getReadMethod();
                    if (field.getType() == int.class) {
                        statement.setInt(index, (Integer) method.invoke(t));
                        index++;
                    }
                    else if (field.getType() == String.class) {
                        statement.setString(index, (String) method.invoke(t));
                        index++;
                    }
                }
                System.out.println(statement.toString());
            }
            for(Field field: type.getDeclaredFields()){
                if(field.getName().equals("id")){
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getReadMethod();
                    statement.setInt(index, (Integer) method.invoke(t));
                }
            }
            System.out.println(statement.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * removes the object specified as a parameter
     * @param t
     */
    public void remove(T t){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM "+type.getSimpleName()+" WHERE ";
        for(Field field: type.getDeclaredFields()){
            if(field.getName().equals("id"))
                query = query + '"'+field.getName()+'"'+" = ?";
        }
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for(Field field: type.getDeclaredFields()){
                if(field.getName().equals("id")){
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getReadMethod();
                    statement.setInt(1, (Integer) method.invoke(t));
                }
            }
            System.out.println(statement.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * @return a list with the attributes of a class
     */
    public List<TableColumn<T, Integer>> getFields() {
        List<TableColumn<T, Integer>> list = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            String s = field.getName();
            TableColumn<T, Integer> tableColumn = new TableColumn<>(s);
            tableColumn.setCellValueFactory(new PropertyValueFactory<>(s));
            list.add(tableColumn);
        }
        return list;
    }
}
