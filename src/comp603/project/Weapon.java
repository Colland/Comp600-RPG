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
public class Weapon extends Item
{
    private final int damageRating;
    
    public Weapon(String name, int goldCost, int levelReq, int damageRating, ItemType itemtype)
    {
        super(name, goldCost, levelReq, ItemType.WEAPON);
        this.damageRating = damageRating;
    }
    
    public Weapon(String id, String name, int goldCost, ItemType itemType, int levelReq, int qualityRating)
    {
        super(id, name, goldCost, levelReq, itemType);
        this.damageRating = qualityRating;
    }
    
    public int getDamageRating()
    {
        return this.damageRating;
    }
}
