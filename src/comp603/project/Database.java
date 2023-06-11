/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Tristan
 */
public class Database
{
    Connection conn = null;
    String URL = "jdbc:derby:rpgDatabase;create=true";
    String dbUserName = "qwe";
    String dbPassWord = "qwe";
    
    public void setupDB()
    {
       try
       {
            conn = DriverManager.getConnection(URL, dbUserName, dbPassWord);
            Statement statement = conn.createStatement();
            String tableName = "WorldData";

            if (!checkTableExisting(tableName)) {
                statement.executeUpdate("CREATE TABLE " + tableName + " (userid VARCHAR(12), password VARCHAR(12), score INT)");
            }
            //statement.executeUpdate("INSERT INTO " + tableName + " VALUES('Fiction',0),('Non-fiction',10),('Textbook',20)");
            statement.close();

        }
       catch (Throwable e)
       {
            System.out.println("Error");

       }
    }
    
    private boolean checkTableExisting(String newTableName)
    {
        boolean flag = false;
        try 
        {
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);
            
            while (rsDBMeta.next())
            {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0)
                {
                    flag = true;
                }
            }
            if (rsDBMeta != null)
            {
                rsDBMeta.close();
            }
        }
        catch (SQLException ex)
        {
            System.out.println("SQL exception");
        }
        return flag;
    }
}
