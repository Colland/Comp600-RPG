/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

/**
 *
 * @author Tristan
 */
public abstract class Item
{
    private final String name;
    private final int levelReq;
    private final ItemType itemType;
    
    public Item(String name, int levelReq, ItemType itemType)
    {
        this.name = name;
        this.levelReq = levelReq;
        this.itemType = itemType;
    }
    
    public String getName()
    {
        return this.name;
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
