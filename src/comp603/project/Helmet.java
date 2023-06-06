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
public class Helmet extends Armor
{
    public Helmet(String name, int levelReq, int armorRating)
    {
        super(name, levelReq, armorRating, ItemType.HELMET);
    }
}
