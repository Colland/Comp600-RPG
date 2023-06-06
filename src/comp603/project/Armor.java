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
public abstract class Armor extends Item
{
    private final int armorRating;
    
    public Armor(String name, int levelReq, int armorRating, ItemType itemType)
    {
        super(name, levelReq, itemType);
        this.armorRating = armorRating;
    }
    
    public int getArmorRating()
    {
        return this.armorRating;
    }
}
