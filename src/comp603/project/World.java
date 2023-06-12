/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.util.HashMap;

/**
 *
 * @author Tristan
 */
public final class World
{
    public static HashMap<Coordinate, Location> map = new HashMap<Coordinate, Location>();
    private static Player player;
    
    //Private so the class cannot be instantiated, mimicking a static class.
    private World(){}
    
    public static void addLocation(Location loc)
    {
        map.put(loc.coordinate, loc);
    }
    
    public static Location getLocation(Coordinate coord)
    {
        return map.get(coord);
    }
    
    public static void setPlayer(Player player1)
    {
        player = player1;
    }
    
    public static Player getPlayer()
    {
        return player;
    }
    
    //Fills the world with game objects and creates the player character.
    public static void populate()
    {
        Location mainTown = new Location(new Coordinate(0, 0), "Town");
        mainTown.addLocationOption("Travel somewhere else", ButtonType.TRAVEL);
        mainTown.addLocationOption("Look around for people", ButtonType.LOOKFORNPCS);       
        mainTown.addLocationOption("Look around for shops", ButtonType.LOOKFORSHOPS);
        mainTown.addLocationOption("Check your inventory", ButtonType.CHECKINVENTORY);
        mainTown.addLocationOption("Rest at an inn", ButtonType.RESTOREHEALTH);

        NonCombatNpc townMayor = new NonCombatNpc("Town mayor");
        townMayor.addOption(new Option("Ask for a quest", ButtonType.REQUESTQUEST));
        mainTown.addNonCombatNpc(townMayor);

        Quest slayGoblinQuest = new Quest("Slay the goblins", 100, 100, EnemyType.GOBLIN, 10);
        slayGoblinQuest.addDescription("Ever since the warriors guild disbanded we've been inundated with those pesky goblins. \n"
                + "If you can slay some of the goblins in the forest to the north, you will be rewarded handsomely.");
        townMayor.addQuest(slayGoblinQuest);
        
        Quest slaySlimesQuest = new Quest("Slay the slimes", 150, 100, EnemyType.SLIME, 10);
        slaySlimesQuest.addDescription("There are too many slimes around pestering the villagers, could you take care of some of them?"
                                        + "\nYou can find them in the mountains to the east.");
        townMayor.addQuest(slaySlimesQuest);
        
        Quest slaySkeletonsQuest = new Quest("Slay the skeletons", 500, 600, EnemyType.SKELETON, 5);
        slaySkeletonsQuest.addDescription("There are some skeletons deeper into the forest to the north. \n"
                                        + "Please slay 5 of them.");
        townMayor.addQuest(slaySkeletonsQuest);

        Quest slayBalrogQuest = new Quest("Slay the Balrog", 9500, 4600, EnemyType.BALROG, 1);
        slayBalrogQuest.addDescription("A great balrog slumbers deep in a cave to the far north.\nAdventurer if you "
                            + "can defeat it, you will be known as the town here forever.");
        townMayor.addQuest(slayBalrogQuest);
        
        NonCombatNpc blackSmith = new NonCombatNpc("Blacksmith");
        blackSmith.addOption(new Option("Trade with", ButtonType.TRADEWITH));
        blackSmith.addItem(new Weapon("Iron shortsword", 1, 8));
        blackSmith.addItem(new Weapon("Steel shortsword", 2, 14));
        blackSmith.addItem(new Weapon("Mithril shortsword", 4, 20));
        blackSmith.addItem(new Weapon("Adamantite shortsword", 6, 35));
        blackSmith.addItem(new Weapon("Rune shortsword", 10, 60));
        
        blackSmith.addItem(new Armor("Iron helmet", 1, 6, ItemType.HELMET));
        blackSmith.addItem(new Armor("Steel helmet", 2, 12, ItemType.HELMET));
        blackSmith.addItem(new Armor("Mithril helmet", 4, 25, ItemType.HELMET));
        blackSmith.addItem(new Armor("Adamantite helmet", 6, 30, ItemType.HELMET));
        blackSmith.addItem(new Armor("Rune helmet", 10, 50, ItemType.HELMET));
        
        blackSmith.addItem(new Armor("Iron platebody", 1, 8, ItemType.BREASTPLATE));
        blackSmith.addItem(new Armor("Steel platebody", 2, 14, ItemType.BREASTPLATE));
        blackSmith.addItem(new Armor("Mithril platebody", 4, 20, ItemType.BREASTPLATE));
        blackSmith.addItem(new Armor("Adamantite platebody", 6, 35, ItemType.BREASTPLATE));
        blackSmith.addItem(new Armor("Rune platebody", 10, 60, ItemType.BREASTPLATE));
        
        blackSmith.addItem(new Armor("Iron platelegs", 1, 7, ItemType.PLATELEGS));
        blackSmith.addItem(new Armor("Steel platelegs", 2, 13, ItemType.PLATELEGS));
        blackSmith.addItem(new Armor("Mithril platelegs", 4, 18, ItemType.PLATELEGS));
        blackSmith.addItem(new Armor("Adamantite platelegs", 6, 32, ItemType.PLATELEGS));
        blackSmith.addItem(new Armor("Rune platelegs", 10, 55, ItemType.PLATELEGS));
        mainTown.addNpcShop(blackSmith);

        Location forest1 = new Location(new Coordinate(0, 1), "Forest");
        forest1.addLocationOption("Travel somewhere else", ButtonType.TRAVEL);
        forest1.addLocationOption("Look around for some monsters", ButtonType.LOOKFORENEMIES);
        forest1.addLocationOption("Check your inventory", ButtonType.CHECKINVENTORY);

        for(int i = 0; i < 11; i++)
        {
            CombatNpc goblin = new CombatNpc("Goblin", 100, 2, 10, 15, 10, EnemyType.GOBLIN);
            goblin.addBattleText("The goblin shrieks as it sees you approach.");
            goblin.addDeathText("The goblin crumples to the ground.");
            forest1.addCombatNpc(goblin);
        }
        
        Location forest2 = new Location(new Coordinate(-1, 1), "Forest");
        forest2.addLocationOption("Travel somewhere else", ButtonType.TRAVEL);
        forest2.addLocationOption("Look around for some monsters", ButtonType.LOOKFORENEMIES);
        forest2.addLocationOption("Check your inventory", ButtonType.CHECKINVENTORY);
        
        for(int i = 0; i < 13; i++)
        {
            CombatNpc goblin = new CombatNpc("Goblin", 100, 2, 10, 15, 10, EnemyType.GOBLIN);
            goblin.addBattleText("The goblin shrieks as it sees you approach.");
            goblin.addDeathText("The goblin crumples to the ground.");
            forest2.addCombatNpc(goblin);
        }
        
        Location forest3 = new Location(new Coordinate(1, 1), "Forest");
        forest3.addLocationOption("Travel somewhere else", ButtonType.TRAVEL);
        forest3.addLocationOption("Look around for some monsters", ButtonType.LOOKFORENEMIES);
        forest3.addLocationOption("Check your inventory", ButtonType.CHECKINVENTORY);
        
        for(int i = 0; i < 11; i++)
        {
            CombatNpc goblin = new CombatNpc("Goblin", 100, 2, 10, 15, 10, EnemyType.GOBLIN);
            goblin.addBattleText("The goblin shrieks as it sees you approach.");
            goblin.addDeathText("The goblin crumples to the ground.");
            forest3.addCombatNpc(goblin);
        }
        
        Location deeperForest1 = new Location(new Coordinate(0, 2), "Deeper forest");
        deeperForest1.addLocationOption("Travel somewhere else", ButtonType.TRAVEL);
        deeperForest1.addLocationOption("Look around for some monsters", ButtonType.LOOKFORENEMIES);
        deeperForest1.addLocationOption("Check your inventory", ButtonType.CHECKINVENTORY);
        
        for(int i = 0; i < 5; i++)
        {
            CombatNpc goblin = new CombatNpc("Goblin", 100, 2, 10, 15, 10, EnemyType.GOBLIN);
            goblin.addBattleText("The goblin shrieks as it sees you approach.");
            goblin.addDeathText("The goblin crumples to the ground.");
            deeperForest1.addCombatNpc(goblin);
        }
        
        for(int i = 0; i < 3; i++)
        {
            CombatNpc skeleton = new CombatNpc("Skeleton", 300, 5, 30, 30, 15, EnemyType.SKELETON);
            skeleton.addBattleText("The skeleton charges at you.");
            skeleton.addDeathText("The skeleton dissipates into ashes.");
            deeperForest1.addCombatNpc(skeleton);
        }
        
        Location deeperForest2 = new Location(new Coordinate(-1, 2), "Deeper forest");
        deeperForest2.addLocationOption("Travel somewhere else", ButtonType.TRAVEL);
        deeperForest2.addLocationOption("Look around for some monsters", ButtonType.LOOKFORENEMIES);
        deeperForest2.addLocationOption("Check your inventory", ButtonType.CHECKINVENTORY);
        
        for(int i = 0; i < 8; i++)
        {
            CombatNpc goblin = new CombatNpc("Goblin", 100, 2, 10, 15, 10, EnemyType.GOBLIN);
            goblin.addBattleText("The goblin shrieks as it sees you approach.");
            goblin.addDeathText("The goblin crumples to the ground.");
            deeperForest2.addCombatNpc(goblin);
        }
        
        for(int i = 0; i < 6; i++)
        {
            CombatNpc skeleton = new CombatNpc("Skeleton", 300, 5, 30, 30, 15, EnemyType.SKELETON);
            skeleton.addBattleText("The skeleton charges at you.");
            skeleton.addDeathText("The skeleton dissipates into ashes.");
            deeperForest2.addCombatNpc(skeleton);
        }
        
        Location deeperForest3 = new Location(new Coordinate(1, 2), "Deeper forest");
        deeperForest3.addLocationOption("Travel somewhere else", ButtonType.TRAVEL);
        deeperForest3.addLocationOption("Look around for some monsters", ButtonType.LOOKFORENEMIES);
        deeperForest3.addLocationOption("Check your inventory", ButtonType.CHECKINVENTORY);
 
        for(int i = 0; i < 4; i++)
        {
            CombatNpc goblin = new CombatNpc("Goblin", 100, 2, 10, 15, 10, EnemyType.GOBLIN);
            goblin.addBattleText("The goblin shrieks as it sees you approach.");
            goblin.addDeathText("The goblin crumples to the ground.");
            deeperForest3.addCombatNpc(goblin);
        }
        
        for(int i = 0; i < 7; i++)
        {
            CombatNpc skeleton = new CombatNpc("Skeleton", 300, 5, 30, 30, 15, EnemyType.SKELETON);
            skeleton.addBattleText("The skeleton charges at you.");
            skeleton.addDeathText("The skeleton dissipates into ashes.");
            deeperForest3.addCombatNpc(skeleton);
        }
        
        Location cave1 = new Location(new Coordinate(0, 3), "Cave");
        cave1.addLocationOption("Travel somewhere else", ButtonType.TRAVEL);
        cave1.addLocationOption("Look around for some monsters", ButtonType.LOOKFORENEMIES);
        cave1.addLocationOption("Check your inventory", ButtonType.CHECKINVENTORY);
        
        CombatNpc balrog = new CombatNpc("Balrog", 1000, 30, 50, 1000, 1000, EnemyType.BALROG);
        balrog.addBattleText("The great balrog awakens.");
        balrog.addDeathText("The cave rumbles as the great balrog falls to the ground, dead.");
        cave1.addCombatNpc(balrog);

        Location mountains1 = new Location(new Coordinate(-1, -1), "Mountains");
        mountains1.addLocationOption("Travel somewhere else", ButtonType.TRAVEL);
        mountains1.addLocationOption("Look around for some monsters", ButtonType.LOOKFORENEMIES);
        mountains1.addLocationOption("Check your inventory", ButtonType.CHECKINVENTORY);
        
        for (int i = 0; i < 10; i++)
        {
            CombatNpc slime = new CombatNpc("Slime", 120, 0, 8, 20, 5, EnemyType.SLIME);
            slime.addBattleText("The slime jiggles menacingly.");
            slime.addDeathText("The slime disssolves into the ground.");
            mountains1.addCombatNpc(slime); 
        }
        
        Location mountains2 = new Location(new Coordinate(0, -1), "Mountains");
        mountains2.addLocationOption("Travel somewhere else", ButtonType.TRAVEL);
        mountains2.addLocationOption("Look around for some monsters", ButtonType.LOOKFORENEMIES);
        mountains2.addLocationOption("Check your inventory", ButtonType.CHECKINVENTORY);
        
        for (int i = 0; i < 11; i++)
        {
            CombatNpc slime = new CombatNpc("Slime", 120, 0, 8, 20, 5, EnemyType.SLIME);
            slime.addBattleText("The slime jiggles menacingly.");
            slime.addDeathText("The slime disssolves into the ground.");
            mountains2.addCombatNpc(slime); 
        }
        
        Location mountains3 = new Location(new Coordinate(1, -1), "Mountains");
        mountains3.addLocationOption("Travel somewhere else", ButtonType.TRAVEL);
        mountains3.addLocationOption("Look around for some monsters", ButtonType.LOOKFORENEMIES);
        mountains3.addLocationOption("Check your inventory", ButtonType.CHECKINVENTORY);
        
        for (int i = 0; i < 13; i++)
        {
            CombatNpc slime = new CombatNpc("Slime", 110, 0, 8, 20, 5, EnemyType.SLIME);
            slime.addBattleText("The slime jiggles menacingly.");
            slime.addDeathText("The slime disssolves into the ground.");
            mountains3.addCombatNpc(slime); 
        }

        Location loc5 = new Location(new Coordinate(-1, 0), "Mountains");
        loc5.addLocationOption("Travel somewhere else", ButtonType.TRAVEL);
        loc5.addLocationOption("Check your inventory", ButtonType.CHECKINVENTORY);
        
        Location loc6 = new Location(new Coordinate(1, 0), "Mountains");
        loc6.addLocationOption("Travel somewhere else", ButtonType.TRAVEL);
        loc6.addLocationOption("Travel somewhere else", ButtonType.LOOKFORENEMIES);
        loc6.addLocationOption("Check your inventory", ButtonType.CHECKINVENTORY);
        
        for (int i = 0; i < 13; i++)
        {
            CombatNpc slime = new CombatNpc("Slime", 110, 0, 8, 20, 5, EnemyType.SLIME);
            slime.addBattleText("The slime jiggles menacingly.");
            slime.addDeathText("The slime disssolves into the ground.");
            loc6.addCombatNpc(slime); 
        }

        World.addLocation(mainTown);
        World.addLocation(forest1);
        World.addLocation(forest2);
        World.addLocation(forest3);
        World.addLocation(deeperForest1);
        World.addLocation(deeperForest2);
        World.addLocation(deeperForest3);
        World.addLocation(cave1);
        World.addLocation(mountains1);
        World.addLocation(mountains2);
        World.addLocation(mountains3);
        World.addLocation(loc5);
        World.addLocation(loc6);
    }
}
