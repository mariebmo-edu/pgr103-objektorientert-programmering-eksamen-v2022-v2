package Repo;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public abstract class AbstractRepo<T> {

    protected DataSource dataSource;
    protected String repo;

    public AbstractRepo(String repo) throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader("src/main/resources/environment.properties");
        properties.load(fileReader);

        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(properties.getProperty("REPO_URL"));
        mysqlDataSource.setUser(properties.getProperty("REPO_USER"));
        mysqlDataSource.setPassword(properties.getProperty("REPO_PASSWORD"));

        dataSource = mysqlDataSource;
        this.repo = repo;
    }


    public boolean Insert(String query) {

        try (Connection con = dataSource.getConnection()) {
            Statement s = con.createStatement();
            s.executeUpdate(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean Exists(String query) {

        try (Connection con = dataSource.getConnection()) {
            Statement s = con.createStatement();
            ResultSet resultSet = s.executeQuery(query);

            if (!resultSet.next()) {
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean Update(String query) {

        try (Connection con = dataSource.getConnection()) {
            Statement s = con.createStatement();
            s.executeUpdate(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<T> RetrieveAll() {

        ArrayList<T> arrayList = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            Statement s = con.createStatement();

            String query = "SELECT * FROM " + repo;

            ResultSet resultSet = s.executeQuery(query);

            while (resultSet.next()) {
                T result = resultMapper(resultSet);
                arrayList.add(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }

    public T RetrieveOneById(int id) {

        try (Connection con = dataSource.getConnection()) {
            Statement s = con.createStatement();

            String query = "SELECT * FROM " + repo + " WHERE id='" + id + "'";
            ResultSet resultSet = s.executeQuery(query);

            if (resultSet.next()) {
                return resultMapper(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public T GenericRetrieveOne(String query) {

        try (Connection con = dataSource.getConnection()) {
            Statement s = con.createStatement();

            ResultSet resultSet = s.executeQuery(query);

            if (resultSet.next()) {
                return resultMapper(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ArrayList<T> GenericRetrieveAll(String query) {

        ArrayList<T> arrayList = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            Statement s = con.createStatement();

            ResultSet resultSet = s.executeQuery(query);

            while (resultSet.next()) {
                T result = resultMapper(resultSet);
                arrayList.add(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<T> RetrieveAllById(int id, String idName) {

        ArrayList<T> arrayList = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            Statement s = con.createStatement();

            String query = "SELECT * FROM " + repo + " WHERE " + idName + "='" + id + "'";
            ResultSet resultSet = s.executeQuery(query);

            while (resultSet.next()) {
                T result = resultMapper(resultSet);
                arrayList.add(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }

    public boolean Delete(int id) {

        try (Connection con = dataSource.getConnection()) {
            Statement s = con.createStatement();

            String query = "DELETE FROM " + repo + " WHERE id='" + id + "'";
            s.executeQuery(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public abstract T resultMapper(ResultSet resultSet) throws SQLException;
}
