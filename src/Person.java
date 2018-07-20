
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER this class is only to be extended
 */
public class Person {

    private StringProperty id;
    private String firstName;
    private String middleName;
    private String lastName;
    private AccessLevel accessLevel;
    private PermissionLevel level;

    protected Person(){
    
        id = new SimpleStringProperty();
    }
    
    protected void setId(String _id) {

        id.set(_id);

    }

    public String getId() {

        return id.get();
    }
    
    public StringProperty getIdProperty(){
    
        return id;
    }

    private void setPermission(String type) {

        /**
         * SL: School Level. Those writing Post UTME UG: Under Graduate. LE: Can
         * upload files, set and mark exam questions but no access to create
         * Users AD:Can create users and courses but can not set or mark exam.
         */
        switch (type) {
            case "LE":
                level = PermissionLevel.LECTURER;
                break;
            case "AD":
                level = PermissionLevel.ADMINISTRATOR;
                break;

        }

    }

    public PermissionLevel getPermission() {

        return level;
    }

    //this is called while login in
    public boolean login(String username, String password) {

        try {
            Connection connection = SimpleConnection.getConnection();

            String sqlQuery = "SELECT person.id, first_name, last_name, middle_name, permissions.type "
                    + "FROM person INNER JOIN permissions_assign "
                    + "ON permissions_assign.person_id = person.id "
                    + "INNER JOIN permissions "
                    + "ON permissions.id = permissions_assign.permission_id "
                    + "WHERE person.id = ? and password = sha1(?) LIMIT 1";
            PreparedStatement pStatement = connection.prepareStatement(sqlQuery);
            pStatement.setString(1, username);
            pStatement.setString(2, password);

            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println("found");
                setId(resultSet.getString("id"));
                setFirstName(resultSet.getString("first_name"));
                setLastName(resultSet.getString("last_name"));
                setMiddleName(resultSet.getString("middle_name"));

                setPermission(resultSet.getString("type"));

            }

            connection.close();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    //this is called to see results either by class or by student
    public void viewRecords(String subject) {

    }

    public String getFirstName() {
        return firstName;
    }

    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    protected void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return lastName + " " + firstName + " " + middleName;
    }
    
    public void createStudent(String id,String department, String level){
    
        try {
            Connection connection = SimpleConnection.getConnection();

            String sqlQuery = "INSERT INTO student (person_id, department, level) VALUES (?,?,?)";
            PreparedStatement pStatement = connection.prepareStatement(sqlQuery);
            pStatement.setString(1, id);
            pStatement.setString(2, department);
            pStatement.setString(3, level);
            pStatement.execute();

           
            connection.close();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
           
        }
        
    
    }
    
    public void createUser(String id, String lastName,String firstName,String middleName, String password,String permission){
    
        try {
            Connection connection = SimpleConnection.getConnection();

            String sqlQuery = "INSERT INTO person (last_name, first_name, middle_name, id, password  ) VALUES (?,?,?,?,sha1(?) )";
            PreparedStatement pStatement = connection.prepareStatement(sqlQuery);
            
            pStatement.setString(1, lastName);
            pStatement.setString(2, firstName);
            pStatement.setString(3, middleName);
            pStatement.setString(4, id);
            pStatement.setString(5, password);

            pStatement.execute();

            
            String sqlQuery2 = "INSERT INTO permission_assign (person_id, permission_id) VALUES (?, (SELECT id from permissions where type = ? ) )";
            PreparedStatement pStatement2 = connection.prepareStatement(sqlQuery2);
            pStatement2.setString(1, id);
            pStatement2.setString(1, permission);
           
            connection.close();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
           
        }
    
    }

}
