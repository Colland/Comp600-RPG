/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

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
public class GameControllerTest {
    
    public GameControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of main method, of class GameController.
     */
    @Test
    public void testWorldPopulate() {
        System.out.println("worldPopulate");
        World.populate();
        
        assert(World.map.get(new Coordinate(0, 0)) != null);
    }
    
    @Test
    public void testCreateModel()
    {
        System.out.println("createModel");
        Model model = new Model();
        
        assert(model != null);
    }
    
    @Test
    public void testCreateControllerAndView()
    {
        System.out.println("createControllerAndView");
        Model model = new Model();
        Controller controller = new Controller(model);
        
        assert(controller != null);
    }
    
    @Test
    public void testCreatingNewCharacterAndSaving()
    {
        System.out.println("creatingNewCharacterAndSaving");
        World.populate();
        Model model = new Model();
        Controller controller = new Controller(model);
        
        model.generateNewCharacter();
        model.saveCharacter();
        assert(model.db.loadPlayerData(model.player.getId()) != null);
    }
}
