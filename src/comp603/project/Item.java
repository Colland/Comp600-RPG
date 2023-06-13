/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Tristan
 */
public abstract class Item
{
    private final String name;
    private final int levelReq;
    private final ItemType itemType;
    private final String id;
    private static int counter = 1;
    private int goldCost;
    
    public Item(String name, int goldCost, int levelReq, ItemType itemType)
    {
        this.id = this.generateId();
        this.name = name;
        this.goldCost = goldCost;
        this.levelReq = levelReq;
        this.itemType = itemType;
    }
    
    public Item(String id, String name, int goldCost, int levelReq, ItemType itemType)
    {
        this.id = id;
        this.name = name;
        this.goldCost = goldCost;
        this.levelReq = levelReq;
        this.itemType = itemType;
    }
    
    private String generateId()
    {
        LocalDateTime timestamp = LocalDateTime.now();
        String uniqueId = timestamp.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + counter;
        counter++;
        
        return uniqueId;
    }
    
    public String getId()
    {
        return this.id;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public int getGoldCost()
    {
        return this.goldCost;
    }
    
    public ItemType getItemType()
    {
        return this.itemType;
    }
    
    public int getLevelReq()
    {
        return this.levelReq;
    }
    
    //Checks to see if player has the level to equip the current item.
    public boolean checkLevelReq(int playerLvl)
    {
        if(playerLvl >= this.levelReq)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
