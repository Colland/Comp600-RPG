/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tristan
 */
public class DatabaseTest {
    
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertPlayerData method, of class Database.
     */
    @Test
    public void testInsertAndLoadPlayerData() {
        System.out.println("insertAndLoadPlayerData");
        Player player = new Player("Tristan", 100, new Location(new Coordinate(0, 0), "Town"));
        Database instance = new Database();
        instance.setupDB();
        instance.insertPlayerData(player);
        
        assert(instance.loadPlayerData(player.getId()) != null);
    }

    /**
     * Test of loadItem method, of class Database.
     */
    @Test
    public void testInsertAndLoadItem() {
        System.out.println("insertAndloadItem");
        Database instance = new Database();
        instance.setupDB();
        
        Weapon item = new Weapon("Copper sword", 10, 1, 3, ItemType.WEAPON);
        instance.insertItem(item, "123");
        assert(instance.loadItem(item.getId()) != null);
    }


    /**
     * Test of loadInventory method, of class Database.
     */
    @Test
    public void testInsertAndLoadInventory() {
        System.out.println("insertAndLoadInventory");
        
        Database instance = new Database();
        instance.setupDB();
        
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(new Weapon("Copper sword", 10, 1, 3, ItemType.WEAPON));
        inventory.add(new Weapon("Copper sword", 10, 1, 3, ItemType.WEAPON));
        inventory.add(new Armor("Iron platebody", 20, 2, 12, ItemType.BREASTPLATE));
        inventory.add(new Armor("Iron platebody", 20, 2, 12, ItemType.BREASTPLATE));
        
        instance.insertInventory(inventory, "123");
        assert(instance.loadInventory("123") != null);
    }
    
}
