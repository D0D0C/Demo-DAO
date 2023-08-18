package org.example.DAO;



import org.example.Model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO extends BaseDAO<Person> {
    protected PersonDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(Person element) throws SQLException {
        request = "INSERT INTO person (first_name, last_name) VALUES (?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, element.getFirstName());
        statement.setString(2, element.getLastName());
        int nbRows = statement.executeUpdate();
        resultSet = statement.getGeneratedKeys();
        if(resultSet.next()){
            element.setId(resultSet.getInt(1));
        }
        return nbRows == 1;
    }


    @Override
    public boolean update(Person element) throws SQLException {
        request = "DELETE person set firts_name = ?, last_name = ?, where id = ?";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS );
        statement.setString(1, element.getFirstName());
        statement.setString(2, element.getLastName());
        statement.setInt(3, element.getId());
        int nbRows = statement.executeUpdate();
        return nbRows == 1;

    }

    @Override
    public boolean delete(Person element) throws SQLException {
            request = "DELETE FROM person where id = ?";
            statement = _connection.prepareStatement(request);
            statement.setInt(1,element.getId());
            int nbRows = statement.executeUpdate();
            return nbRows == 1;
    }
    @Override
    public Person get(int id) throws SQLException {
            Person person = null; //Initialisation de la personne à 0
            request = "SELECT * FROM person where id = ?";
            statement = _connection.prepareStatement(request);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()){      // un simple "if" suffit pas de while car id unique,
                                        // si "resulset" à un id, on rentre dans la boucle
                person = new Person(resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"));
            }
            if(_connection != null){
                try{
                    _connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            return person;
            }

    @Override
    public List<Person> get() throws SQLException {
        List<Person> result = new ArrayList<>(); //initilaisation de la liste de personne
        request ="SELECT * FROM Person";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery(); // Pas de parametre à "Seté", pas de point d'interogation à entrer, on saute
        while (resultSet.next()){ //Parcourir la liste de personne et on crée une personne à chaque fois pour l'ajouter à la list
            Person person = new Person(resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"));
            result.add(person); // A chaque personne, on crée une ligne avec un resultat

            }
        return result;
    }
}

