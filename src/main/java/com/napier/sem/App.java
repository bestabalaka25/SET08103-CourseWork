package com.napier.sem;

import java.sql.*;

public class App {
    public static void main(String[] args) {
        App a = new App();
        if (args.length == 1) {
            a.connect("localhost:33060", 10000);
        } else {
            a.connect("db:3306", 10000);
        }


        City ct = a.getCity(1);
        a.displayCity(ct);

        a.disconnect();
    }

    /**
     * 11/11/24 modified Main to default to localhost
     * Connect to the MySQL database.
     * 11/11/24 updated connect method to add location and delay parameters
     */
    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        boolean shouldWait = false;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                if (shouldWait) {
                    Thread.sleep(delay);
                }
                // Connect to database
                System.out.println(String.format("jdbc:mysql://%s/world?useSSL=false", location));
                con = DriverManager.getConnection(String.format("jdbc:mysql://%s/world?useSSL=false", location), "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
                shouldWait = true;
            } catch (InterruptedException ie) {
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
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public City getCity(int ID) {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT ID, Name, CountryCode, District, Population "
                            + "FROM city "
                            + "WHERE ID =" + ID;
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next()) {
                City ct = new City();
                ct.ID = rset.getInt("ID");
                ct.Name = rset.getString("Name");
                ct.CountryCode = rset.getString("CountryCode");
                ct.District = rset.getString("District");
                ct.Population = rset.getInt("Population");
                return ct;
            } else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city");
            return null;
        }
    }

    public void displayCity(City ct) {
        if (ct != null) {
            System.out.println(
                    ct.ID + " "
                            + ct.Name + " "
                            + ct.CountryCode + " "
                            + ct.District + " "
                            + ct.Population + "\n");
        }
    }



    public Country getCountry(String Code) {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT Code, Name, Continent, Region, Population "
                            + "FROM country "
                            + "WHERE Code =" + Code;
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next()) {
                Country cn = new Country();
                cn.Code = rset.getString("Code");
                cn.Name = rset.getString("Name");
                cn.Continent = rset.getString("Continent");
                cn.Region = rset.getString("Region");
                cn.Population = rset.getInt("Population");
                return cn;
            } else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country");
            return null;
        }
    }

    public void displayCountry(Country cn) {
        if (cn != null) {
            System.out.println(
                    cn.Code + " "
                            + cn.Name + " "
                            + cn.Continent + " "
                            + cn.Region + " "
                            + cn.Population + "\n");
        }
    }

    public CountryLanguage getCountryLanguage(String CountryCode) {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT CountryCode, Language, IsOfficial, Percentage"
                            + "FROM countrylanguage "
                            + "WHERE CountryCode =" + CountryCode;
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next()) {
                CountryLanguage cl = new CountryLanguage();
                cl.CountryCode = rset.getString("CountryCode");
                cl.Language = rset.getString("Language");
                cl. IsOfficial = rset.getString("IsOfficial");
                cl.Percentage = rset.getFloat("Percentage");
                return cl;
            } else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country language");
            return null;
        }
    }

    public void displayCountryLanguage(CountryLanguage cl) {
        if (cl != null) {
            System.out.println(
                    cl.CountryCode + " "
                            + cl.Language + " "
                            + cl.IsOfficial + " "
                            + cl.Percentage + " ");
        }
    }
}
