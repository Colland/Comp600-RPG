/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

            if (!checkTableExisting("Players")) {
                statement.executeUpdate("CREATE TABLE Players (playerid VARCHAR(20), name VARCHAR(20), health INT,"
                                      + " maxhealth INT, level INT, experience INT, gold INT, xptonextlevel INT, strength INT,"
                                      + " equippedhelmetid VARCHAR(20), equippedbreastplateid VARCHAR(20), equippedplatelegsid VARCHAR(20),"
                                      + " equippedweaponid VARCHAR(20))");
            }
            if (!checkTableExisting("Items")) {
                statement.executeUpdate("CREATE TABLE Items (itemid VARCHAR(20), name VARCHAR(20), goldcost INT, itemtype VARCHAR(20),"
                                      + " levelreq INT, qualityrating INT, characterid VARCHAR(20))");
            }
            statement.close();

        }
       catch (Throwable e)
       {
            e.printStackTrace();
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
    
    public void insertPlayerData(Player player)
    {
        String id = player.getId();
        String name = player.getName();
        int health = player.getCurrentHealth();
        int maxHealth = player.getMaxHealth();
        int level = player.getLevel();
        int xp = player.getXp();
        int gold = player.getGold();
        int nextXp = player.getXpToNextLevel();
        int strength = player.getStrength();
        ArrayList<Item> inventory = player.getInventory();
        
        String helmet = null;
        String breastplate = null;
        String platelegs = null;
        String weapon = null;

        if (player.getEquippedHelmet() != null)
        {
            helmet = player.getEquippedHelmet().getId();
        }

        if (player.getEquippedBreastPlate() != null)
        {
            breastplate = player.getEquippedBreastPlate().getId();
        }

        if (player.getEquippedPlateLegs() != null)
        {
            platelegs = player.getEquippedPlateLegs().getId();
        }

        if (player.getEquippedWeapon() != null)
        {
            weapon = player.getEquippedWeapon().getId();
        }
        
        String sql = "INSERT INTO Players (playerid, name, health, maxhealth, level, experience"
                + ", gold, xptonextlevel, strength, equippedhelmetid, equippedbreastplateid,"
                + " equippedplatelegsid ,equippedweaponid)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try 
        {
            PreparedStatement statement = this.conn.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setInt(3, health);
            statement.setInt(4, maxHealth);
            statement.setInt(5, level);
            statement.setInt(6, xp);
            statement.setInt(7, gold);
            statement.setInt(8, nextXp);
            statement.setInt(9, strength);
            if (helmet != null)
            {
                statement.setString(10, helmet);
            } 
            else
            {
                statement.setNull(10, java.sql.Types.VARCHAR);
            }

            if (breastplate != null)
            {
                statement.setString(11, breastplate);
            }
            else
            {
                statement.setNull(11, java.sql.Types.VARCHAR);
            }

            if (platelegs != null)
            {
                statement.setString(12, platelegs);
            }
            else
            {
                statement.setNull(12, java.sql.Types.VARCHAR);
            }

            if (weapon != null)
            {
                statement.setString(13, weapon);
            }
            else
            {
                statement.setNull(13, java.sql.Types.VARCHAR);
            }
            
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Error inserting SQL: " + ex.getMessage());
                                ex.printStackTrace();
        }
        
        this.insertInventory(inventory, player.getId());
    }
    
    public Player loadPlayerData(String id)
    {
        Player player = null;

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT playerid, name, health, maxhealth, level, experience, gold, xptonextlevel,"
                + " strength, equippedhelmetid, equippedbreastplateid, equippedplatelegsid, equippedweaponid FROM Players WHERE playerid = '" + id + "'");

            if (resultSet.next())
            {
                String playerid = resultSet.getString("playerid");
                String name = resultSet.getString("name");
                int health = resultSet.getInt("health");
                int maxHealth = resultSet.getInt("maxhealth");
                int level = resultSet.getInt("level");
                int xp = resultSet.getInt("experience");
                int gold = resultSet.getInt("gold");
                int nextXp = resultSet.getInt("xptonextlevel");
                int strength = resultSet.getInt("strength");
                String helmet = resultSet.getString("equippedhelmetid");
                String breastplate = resultSet.getString("equippedbreastplateid");
                String platelegs = resultSet.getString("equippedplatelegsid");
                String weapon = resultSet.getString("equippedweaponid");
                ArrayList<Item> inventory = this.loadInventory(playerid);

                // Create the Player object with retrieved data
                player = new Player(id, name, health, maxHealth, level, xp, gold, nextXp, strength,
                                    helmet != null ? this.loadItem(helmet) : null,
                                    breastplate != null ? this.loadItem(breastplate) : null,
                                    platelegs != null ? this.loadItem(platelegs) : null,
                                    weapon != null ? this.loadItem(weapon) : null,
                                    inventory);
            }

            resultSet.close();
            statement.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return null;
        }

        return player;
    }
    
    public Item loadItem(String itemId)
    {
        String sql = "SELECT itemid, name, goldcost, itemtype, levelreq, qualityrating, characterid FROM Items WHERE itemid = ?";
        Item item = null;

        try 
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, itemId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                String id = resultSet.getString("itemid");
                String name = resultSet.getString("name");
                int goldCost = resultSet.getInt("goldcost");
                String itemType = resultSet.getString("itemtype");
                int levelReq = resultSet.getInt("levelreq");
                int qualityRating = resultSet.getInt("qualityrating");
                String characterId = resultSet.getString("characterid");

                // Create the Item object with retrieved data
                
                if(itemType.equals("WEAPON"))
                {
                    Weapon weapon = new Weapon(id, name, goldCost, ItemType.valueOf(itemType), levelReq, qualityRating);
                    item = (Item)weapon;
                }
                else
                {
                    Armor armor = new Armor(id, name, goldCost, ItemType.valueOf(itemType), levelReq, qualityRating);
                    item = (Item)armor;
                }
            }

            resultSet.close();
            statement.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Error loading item data: " + ex.getMessage());
            ex.printStackTrace();
        }

        return item;
    }
    
    public void insertItem(Item item, String characterid)
    {
        String sql = "INSERT INTO Items (itemid, name, goldcost, itemtype, levelreq, qualityrating, characterid) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = this.conn.prepareStatement(sql);
            statement.setString(1, item.getId());
            statement.setString(2, item.getName());
            statement.setInt(3, item.getGoldCost());
            statement.setString(4, item.getItemType().name());
            statement.setInt(5, item.getLevelReq());
            
            if(item.getItemType() == ItemType.WEAPON)
            {
               Weapon weapon = (Weapon)item;
               statement.setInt(6, weapon.getDamageRating()); 
            }
            else
            {
               Armor armor = (Armor)item;
               statement.setInt(6, armor.getArmorRating()); 
            }
            
            if(characterid != null)
            {
                statement.setString(7, characterid);  
            }
            else
            {
                statement.setNull(7, java.sql.Types.VARCHAR);
            }

            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Error inserting item data: " + ex.getMessage());
            ex.printStackTrace();
        } 
    }
    
        
    public ArrayList<Item> loadInventory(String characterId)
    {
        String sql = "SELECT itemid, name, goldcost, itemtype, levelreq, qualityrating FROM Items WHERE characterid = ?";
        ArrayList<Item> inventory = new ArrayList<Item>();
        
        try 
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, characterId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                String id = resultSet.getString("itemid");
                String name = resultSet.getString("name");
                int gold = resultSet.getInt("goldcost");
                String itemType = resultSet.getString("itemtype");
                int levelReq = resultSet.getInt("levelreq");
                int qualityRating = resultSet.getInt("qualityrating");

                // Create the Item object with retrieved data
                
                if(itemType.equals("WEAPON"))
                {
                    Weapon weapon = new Weapon(id, name, gold, ItemType.valueOf(itemType), levelReq, qualityRating);
                    inventory.add((Item)weapon);
                }
                else
                {
                    Armor armor = new Armor(id, name, gold, ItemType.valueOf(itemType), levelReq, qualityRating);
                    inventory.add((Item)armor);
                }
            }

            resultSet.close();
            statement.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Error loading item data: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        return inventory;
    }
    
    public void insertInventory(ArrayList<Item> inventory, String characterId)
    {
        for(int i = 0; i < inventory.size(); i++)
        {
            insertItem(inventory.get(i), characterId);
        }
    }   
}
