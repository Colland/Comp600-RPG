/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.util.Observable;

/**
 *
 * @author Tristan
 */
public class Model extends Observable
{
    public Player player;
    
    public Model()
    {
        this.player = World.getPlayer();
    }
    
    public void getAvailableDirections()
    { 
        this.setChanged();
        Data data = new Data();
        data.setTravelDirections(player.getCurrentLocation().getAvailableDirections());
        notifyObservers(data);
    }
    
    public void movePlayer(String direction)
    {
        Coordinate currentCoord = player.getCurrentLocation().getCoordinate();
        Coordinate newCoord;
        
        switch(direction)
        {
            case "North":
                newCoord = new Coordinate(currentCoord.getX(), currentCoord.getY()+1);
                break;
            
            case "East":
                newCoord = new Coordinate(currentCoord.getX()+1, currentCoord.getY());
                break;
                
            case "South":
                newCoord = new Coordinate(currentCoord.getX(), currentCoord.getY()-1);
                break;
                
            case "West":
                newCoord = new Coordinate(currentCoord.getX()-1, currentCoord.getY());
                break;
            
            default:
                newCoord = currentCoord;
        }
        
        player.changeLocation(World.getLocation(newCoord));
        
        this.setChanged();
        Data data = new Data();
        data.setPlayerLocation(player.getCurrentLocation());
        notifyObservers(data);
    }
}
