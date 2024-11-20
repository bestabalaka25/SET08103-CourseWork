package com.napier.sem;

import com.napier.sem.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
public class AppIntegrationTest
{
    static App app;
    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 30000);
    }
    @Test
    void testGetCity()
    {
        City ct = app.getCity(1);
        assertEquals(ct.ID, 1);
        assertEquals(ct.Name, "Kabul");
        assertEquals(ct.CountryCode, "AFG");
        assertEquals(ct.District, "Kabol");
        assertEquals(ct.Population, 1780000);
    }


}
/** 13/11/24 I have created a java file for app integration testing
 * I have added a test to compare actual values against expected values, if they match the test will pass.
 */