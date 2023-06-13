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
public class ComboItem
{
    private Item item;
    private String itemName;
    
    public ComboItem(Item item, String itemName)
    {
        this.item = item;
        this.itemName = itemName;
    }
    
    public Item getItem()
    {
        return this.item;
    }
    
    @Override
    public String toString()
    {
        return this.itemName;
    }
}
