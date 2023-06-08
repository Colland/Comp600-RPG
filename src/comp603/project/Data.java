/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.util.ArrayList;

/**
 *
 * @author Tristan
 */
public class Data
{
    private ArrayList<Integer> travelDirections;
    private Location playerLocation;
    
    public Data()
    {
        
    }
    
    public void setTravelDirections(ArrayList<Integer> directions)
    {
        this.travelDirections = directions;
    }
    
    public void setPlayerLocation(Location playerLocation)
    {
        this.playerLocation = playerLocation;
    }
    
    public ArrayList<Integer> getTravelDirections()
    {
        return this.travelDirections;
    }
    
    public Location getPlayerLocation()
    {
        return this.playerLocation;
    }
}
