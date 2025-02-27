package tables;

import db.IDBConnect;
import db.MySQLConnect;
import objects.Animal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbsTable implements ITable{
    protected IDBConnect dbConnector = null;
    private String tableName = "";

    public AbsTable(String tableName) {
        dbConnector = new MySQLConnect();
        this.tableName = tableName;
    }

    //CREATE TABLE tableName (column1 INT PRIMARY KEY , column2 VARCHAR(255), column3, ...);
    @Override
    public void create(List<String> columns) {
        //delete();
        // Проверяем существование таблицы
        if (!isTableExists()) {

            dbConnector.execute(String.format("CREATE TABLE %s (%s);", tableName, String.join(",", columns)));
        }
    }

    @Override
    public void delete() {
        dbConnector.execute(String.format("drop table if exists %s;",this.tableName));
    }

    public abstract ArrayList<Animal> read() throws SQLException;
    public abstract void update(Animal data);
    public abstract void write(Animal data);

    public ResultSet selectAll() {
        return dbConnector.executeQuery(String.format("SELECT * FROM  %s;",this.tableName));
    }

    // Метод для проверки существования таблицы
    private boolean isTableExists() {
        String query = String.format("SHOW TABLES LIKE '%s';", this.tableName);
        try (ResultSet rs = dbConnector.executeQuery(query)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
