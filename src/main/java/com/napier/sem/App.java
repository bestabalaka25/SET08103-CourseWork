package com.napier.sem;

import java.sql.*;

public class App
{
    public static void main(String[] args) {
        App a = new App();

        a.connect();

        City ct = a.getCity(1);
        a.displayCity(ct);

        a.disconnect();
    }
    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public City getCity (int ID)
    {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT ID, Name, CountryCode, District, Population "
                            + "FROM city "
                            + "WHERE ID =" + ID;
            ResultSet rset = stmt.executeQuery(strSelect);

            if(rset.next())
            {
                City ct = new City();
                ct.ID = rset.getInt("ID");
                ct.Name = rset.getString("Name");
                ct.CountryCode = rset.getString("CountryCode");
                ct.District = rset.getString("District");
                ct.Population = rset.getInt("Population");
                return ct;
            }
            else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city");
            return null;
        }
    }

    public void displayCity(City ct)
    {
        if (ct != null)
        {
            System.out.println(
                    ct.ID + " "
                            + ct.Name + " "
                            + ct.CountryCode + " "
                            + ct.District + " "
                            + ct.Population + "\n");
        }
    }
}
