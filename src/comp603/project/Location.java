/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author tjack
 */
public class Location
{
    public Coordinate coordinate;
    public String name;
    public ArrayList<Option> locationOptions = new ArrayList<>();
    public ArrayList<NonCombatNpc> locationNonCombatNpcList = new ArrayList<>();
    public ArrayList<NonCombatNpc> npcShopList = new ArrayList<>();
    public ArrayList<CombatNpc> combatNpcList = new ArrayList<>();
    
    public Location(Coordinate locationCoord, String name)
    {
        this.coordinate = locationCoord;
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public Coordinate getCoordinate()
    {
       return this.coordinate; 
    }
    
    public void addLocationOption(String optionText, ButtonType type)
    {
        locationOptions.add(new Option(optionText, type));
    }
    
    public void addNpcShop(NonCombatNpc npc)
    {
        this.npcShopList.add(npc);
    }
    
    public void addNonCombatNpc(NonCombatNpc npc)
    {
        this.locationNonCombatNpcList.add(npc);
    }
    
    public void addCombatNpc(CombatNpc npc)
    {
        this.combatNpcList.add(npc);
    }
    
    public void removeCombatNpc(CombatNpc npc)
    {
        this.combatNpcList.remove(npc);
    }
    
    public int getNOfOptions()
    {
        return this.locationOptions.size();
    }
    
    public ArrayList<Option> getOptions()
    {
        return this.locationOptions;
    }
    
    public ArrayList<NonCombatNpc> getNonCombatNpcList()
    {
        return this.locationNonCombatNpcList;
    }
    
        
    //Checks for any Locations ajadcent to the calling Location.
    public ArrayList<Integer> getAvailableDirections()
    {
        int x = this.coordinate.getX();
        int y = this.coordinate.getY();
        
        ArrayList<Integer> availableDirections = new ArrayList<>();

        //Check adjacent north location
        if(World.map.get(new Coordinate(x, y+1)) != null)
        {
            availableDirections.add(0);
        }
        //Check adjacent east location
        if(World.map.get(new Coordinate(x+1, y)) != null)
        {
            availableDirections.add(1);
        }
        //Check adjacent south location
        if(World.map.get(new Coordinate(x, y-1)) != null)
        {
            availableDirections.add(2);
        }
        //Check adjacent west location
        if(World.map.get(new Coordinate(x-1, y)) != null)
        {
            availableDirections.add(3);
        }
        
        return availableDirections;
    }
}
