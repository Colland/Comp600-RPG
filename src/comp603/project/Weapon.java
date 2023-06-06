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
    
    public Weapon(String name, int levelReq, int damageRating)
    {
        super(name, levelReq, ItemType.WEAPON);
        this.damageRating = damageRating;
    }
    
    public int getDamageRating()
    {
        return this.damageRating;
    }
}
