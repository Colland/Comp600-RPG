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
    
    public void addLocationOption(String optionText, OptionType type)
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
    
    public void displayOptions()
    {
        for(int i = 0; i < locationOptions.size(); i++)
        {
            System.out.println((i+1) + ". " + locationOptions.get(i).getOptionText());
        }
    }
    
    /*Displays all non-combat npcs in the location, prompts the user to choose
    one of the npcs to interact with, and then calls the selected npc's
    a function on the npc to display its possible options.*/
    public void displayNonCombatNpcs()
    {
        boolean flag = true;
        do
        {
            System.out.println("Friendly npcs");
            System.out.println("-------------");
            
            int i;
            for(i = 0; i < this.locationNonCombatNpcList.size(); i++)
            {
                System.out.println((i+1) + ". " + this.locationNonCombatNpcList.get(i).getName());
            }

            System.out.println((i+1) + ". " + "Exit menu");

            Scanner scan = new Scanner(System.in);
            try
            {
                int userInput = scan.nextInt();
                
                if(userInput == i+1)
                {
                    flag = false;
                }
                else if((userInput > i+1) || (userInput < 1))
                {
                    System.out.println("Please input a valid number");
                }
                else
                {
                    NonCombatNpc selectedNpc = this.locationNonCombatNpcList.get(userInput-1);
                    selectedNpc.displayOptions();
                }
            }
            catch(InputMismatchException e)
            {
                System.out.println("Please enter a number.");
            }
            
            
        }while(flag);
    }
    
    /*Displays all combat npcs in the location, prompts user to pick one to interact
    with and then returns the selected CombatNpc*/
    public CombatNpc displayCombatNpcs()
    {
        boolean flag = true;
        
        do
        {
            System.out.println("Enemies");
            System.out.println("-------");
            
            int i;
            for(i = 0; i < this.combatNpcList.size(); i++)
            {
                System.out.println((i+1) + ". " + this.combatNpcList.get(i).getName());
            }  
            
            System.out.println((i+1) + ". Exit menu");
            
            Scanner scan = new Scanner(System.in);
            try
            {
                int userInput = scan.nextInt();
                
                if(userInput == i+1)
                {
                    flag = false;
                }
                else if((userInput > i+1) || (userInput < 1))
                {
                    System.out.println("Please input a valid number");
                }
                else
                {
                    return this.combatNpcList.get(userInput-1);
                }
            }
            catch(InputMismatchException e)
            {
                System.out.println("Please enter a number.");
            }
        }while(flag);
        
        return null;
    }
        
    //Checks for any Locations ajadcent to the calling Location.
    //Prints them as travel options if any exist.
    //Prompts user input and returns the selected location.
    public Location displayTravelOptions()
    {
        int x = this.coordinate.getX();
        int y = this.coordinate.getY();
        boolean flag = true;
        
        do
        {
           try
           {
                int counter = 1;
                ArrayList<Location> availableLocs = new ArrayList<>();
                
                System.out.println("Travel");
                System.out.println("------");

                //Check adjacent north location
                if(World.map.get(new Coordinate(x, y+1)) != null)
                {
                    System.out.println(counter + ". Head north");
                    availableLocs.add(World.map.get(new Coordinate(x, y+1)));
                    counter++;
                }
                //Check adjacent east location
                if(World.map.get(new Coordinate(x+1, y)) != null)
                {
                    System.out.println(counter + ". Head east");
                    availableLocs.add(World.map.get(new Coordinate(x+1, y)));
                    counter++;
                }
                //Check adjacent south location
                if(World.map.get(new Coordinate(x, y-1)) != null)
                {
                    System.out.println(counter + ". Head south");
                    availableLocs.add(World.map.get(new Coordinate(x, y-1)));
                    counter++;
                }
                //Check adjacent west location
                if(World.map.get(new Coordinate(x-1, y)) != null)
                {
                    System.out.println(counter + ". Head west");
                    availableLocs.add(World.map.get(new Coordinate(x-1, y)));
                    counter++;
                }

                System.out.println(counter + ". Exit menu");

                Scanner scan = new Scanner(System.in);
                int userInput = scan.nextInt();

                if(userInput == counter)
                {
                    flag = false;
                }
                else if(userInput > availableLocs.size() || userInput <= 0)
                {
                    System.out.println("Please input a valid number.");
                }
                else
                {
                    return availableLocs.get(userInput-1);
                } 
           }
           catch(InputMismatchException e)
           {
               System.out.println("Please input only numbers");
           }
        }while(flag);
        
        return null;
    }
    
    public void displayShops()
    {
        boolean flag = true;
        
        do
        {
           System.out.println("\nShops");
           System.out.println("----");
            
           int i;
           for(i = 0; i < this.npcShopList.size(); i++)
            {
                System.out.println((i+1) + ". " + this.npcShopList.get(i).getName());
            }
           System.out.println((i+1) + ". Exit menu");
           
           Scanner scan = new Scanner(System.in);
           
           try
           {
               int userInput = scan.nextInt();
               
               if(userInput == i+1)
               {
                   flag = false;
               }
               else if(userInput <= 0 || userInput > i+1)
               {
                   System.out.println("Please input a valid number");
               }
               else
               {
                    this.npcShopList.get(userInput-1).displayItems();
                    flag = false;
               }    
           }
           catch(InputMismatchException e)
           {
               System.out.println("Please input only numbers");
           }
        }while(flag);
    }
}
