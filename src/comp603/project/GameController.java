/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author tjack
 */
public class GameController 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
       World.populate();
       Player loadedPlayer = loadPlayer();
       
       //Checks to see if a player save file was loaded. If yes, prompts the user 
       //to see if they want to use the save file, or create a new character.
       if(loadedPlayer == null)
       {
           System.out.println("A save file could not be found, creating a new character.");
       }
       else
       {
           System.out.println("Would you like to load from the existing save file,"
                   + " otherwise a new character will be created.");
           System.out.println("(y/n)");
           boolean flag = true;
           Scanner scan = new Scanner(System.in);
           
           do
           {
               String userInput = scan.nextLine().trim();
               
               switch(userInput)
               {
                   case "Y":
                   case "y":
                       World.setPlayer(loadedPlayer);
                       flag = false;
                       break;
                       
                   case "N":
                   case "n":
                       flag = false;
                       break;
                       
                   default:
                       System.out.println("Please enter y or n");
                       break;
               }
           }while(flag);
       }
       
       Player player1 = World.getPlayer();
       Scanner scan1 = new Scanner(System.in);
       
       /*Main game loop. Calls a function on the players current location that
       displays any possible actions that can be done within that location. Then
       asks user for input and validates it.*/     
       boolean flag = true;
       do
       {
           int nOfCurrentLocationOptions = player1.getCurrentLocation().getNOfOptions();
           String currentLocName = player1.getCurrentLocation().getName();
           System.out.println(currentLocName);
           
           //Prints a visual underline that is the same length as the current locations name.
           for(int i = 0; i < currentLocName.length(); i++)
           {
               System.out.print("-");
           } 
           System.out.println("");
           
           player1.displayLocationOptions();
           System.out.println(nOfCurrentLocationOptions+1 + ". Save and exit game");
           
           //Validates that only numbers are input.
           try
           {
               int userInput = scan1.nextInt();

               //Handles user input
               if(userInput == nOfCurrentLocationOptions+1)
               {
                   savePlayer();
                   System.exit(0);
               }
               else if((userInput > nOfCurrentLocationOptions+1) || (userInput <= 0))
               {
                   System.out.println("Please input a valid number");
               }
               else
               {
                    Location playerLocation = player1.getCurrentLocation();
                    OptionType optionType = playerLocation.locationOptions.get(userInput-1).getOptionType();

                    switch(optionType)
                    {
                        case TRAVEL:
                            Location newLocation = playerLocation.displayTravelOptions();
                            
                            if(newLocation != null)
                            {
                                player1.changeLocation(newLocation);   
                            }
                            break;

                        case LOOKFORNPCS:
                            playerLocation.displayNonCombatNpcs();
                            break;
                            
                        case LOOKFORSHOPS:
                            playerLocation.displayShops();
                            break;
                            
                        case LOOKFORENEMIES:
                            CombatNpc selectedEnemy = playerLocation.displayCombatNpcs();
                            
                            if(selectedEnemy != null)
                            {
                                player1.battle(selectedEnemy);
                                
                                if(player1.isDead())
                                {
                                    player1.changeLocation(World.getLocation(new Coordinate(0, 0)));
                                    player1.reviveCharacter();
                                    System.out.println("You wake up back in town.\n");
                                }
                            }
                            break;
                            
                        case CHECKINVENTORY:
                            player1.displayInventoryMenu();
                            break;
                            
                        case RESTOREHEALTH:
                            if(player1.getGold() >= 5)
                            {
                                player1.reviveCharacter();
                                player1.reduceGold(5);
                                System.out.println("Your health is restored. It cost 5 gold.");
                            }
                            else
                            {
                                System.out.println("You need 5 gold. You're broke");
                            }
                            break;
                    }
               }    
           }
           catch(InputMismatchException e)
           {
               System.out.println("Please input numbers only.");
               scan1.nextLine();
           }   
       }while(flag);
    }
    
    //Saves player details to a text file.
    public static void savePlayer()
    {
        try 
        {
            PrintWriter pw = new PrintWriter(new FileOutputStream("saveFiles/gameSave.txt"));
            Player player = World.getPlayer();
            
            pw.println("name:" + player.getName());
            pw.println("health:" + player.getCurrentHealth());
            pw.println("maxhealth:" + player.getMaxHealth());
            pw.println("level:" + player.getLevel());
            pw.println("experience:" + player.getXp());
            pw.println("gold:" + player.getGold());
            pw.println("xptonextlevel:" + player.getXpToNextLevel());
            pw.println("strength:" + player.getStrength());
            
            Coordinate playerCoord = player.getCurrentLocation().getCoordinate();
            pw.println("currentloc:" + playerCoord.getX() + ":" + playerCoord.getY());
            
            ArrayList<Item> inventory = player.getInventory();
            pw.print("inventory");
            for(int i = 0; i < inventory.size(); i++)
            {
                saveSingleItem(pw, inventory.get(i));
            }
            pw.println();
            
            if(player.getEquippedHelmet() != null)
            {
                pw.print("helmetequip");
                saveSingleItem(pw, player.getEquippedHelmet());
                pw.println();
            }
            if(player.getEquippedBreastPlate() != null)
            {
                pw.print("breastplateequip");
                saveSingleItem(pw, player.getEquippedBreastPlate());
                pw.println(); 
            }
            if(player.getEquippedPlateLegs() != null)
            {
                pw.print("platelegsequip");
                saveSingleItem(pw, player.getEquippedPlateLegs());
                pw.println();
            }
            if(player.getEquippedWeapon() != null)
            {
                pw.print("weaponequip");
                saveSingleItem(pw, player.getEquippedWeapon());
                pw.println();
            }
            
            pw.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error saving file.");
        }
    }
    
    //Loads player details from a text file.
    public static Player loadPlayer()
    {
        Player loadedPlayer = null;
        
        try 
        {
            BufferedReader br = new BufferedReader(new FileReader("saveFiles/gameSave.txt"));
            String line = null;
            
            String name = "";
            int health = 100;
            int maxHealth = 100;
            int level = 1;
            int experience = 0;
            int gold = 0;
            int xpToNextLevel = 0;
            int strength = 10;
            Location location = World.getLocation(new Coordinate(0, 0));
            Helmet helmet = null;
            Breastplate breastPlate = null;
            Platelegs plateLegs = null;
            Weapon weapon = null;
            ArrayList<Item> inventory = new ArrayList<>();
            
            while((line = br.readLine()) != null)
            {
                StringTokenizer tokenizedLine = new StringTokenizer(line, ":");
                
                //Expects a tag as the first token of each line. Uses the tag to
                //decide how to process and encapsulate the remaining data.
                switch(tokenizedLine.nextToken())
                {
                    case "name":
                        name = tokenizedLine.nextToken();
                        break;
                        
                    case "health":
                        health = Integer.parseInt(tokenizedLine.nextToken());
                        break;
                        
                    case "maxhealth":
                        maxHealth = Integer.parseInt(tokenizedLine.nextToken());
                        break;
                        
                    case "level":
                        level = Integer.parseInt(tokenizedLine.nextToken());
                        break;
                        
                    case "experience":
                        experience = Integer.parseInt(tokenizedLine.nextToken());
                        break;
                        
                    case "gold":
                        gold = Integer.parseInt(tokenizedLine.nextToken());
                        break;
                        
                    case "xptonextlevel":
                        xpToNextLevel = Integer.parseInt(tokenizedLine.nextToken());
                        break;
                        
                    case "strength":
                        strength = Integer.parseInt(tokenizedLine.nextToken());
                        break;
                        
                    case "currentloc":
                        int x = Integer.parseInt(tokenizedLine.nextToken());
                        int y = Integer.parseInt(tokenizedLine.nextToken());
                        location = World.getLocation(new Coordinate(x, y));
                        break;
                        
                    case "helmetequip":
                        helmet = (Helmet)loadSingleItem(tokenizedLine);
                        break;
                        
                    case "breastplateequip":
                        breastPlate = (Breastplate)loadSingleItem(tokenizedLine);
                        break;
                        
                    case "platelegsequip":
                        plateLegs = (Platelegs)loadSingleItem(tokenizedLine);
                        break;
                       
                    case "weaponequip":
                        weapon = (Weapon)loadSingleItem(tokenizedLine);
                        break;
                        
                    case "inventory":
                        while(tokenizedLine.hasMoreTokens())
                        {
                            inventory.add(loadSingleItem(tokenizedLine));
                        }
                        break;
                }
            }
            
            loadedPlayer = (new Player(name, health, maxHealth, level, experience, gold, 
                            xpToNextLevel, strength, location, inventory, helmet, 
                            breastPlate, plateLegs, weapon));
        } 
        catch(FileNotFoundException e)
        {
            System.out.println("Error finding save file.");
        }
        catch(IOException e)
        {
            System.out.println("Error reading save file.");
        }
        
        return loadedPlayer;
    }
    
    public static void saveSingleItem(PrintWriter pw, Item item)
    {
        if(item.getItemType() == ItemType.WEAPON)
        {
            Weapon weapon = (Weapon)item;
            pw.print(":" + weapon.getItemType() + ":" + weapon.getName()
                     + ":" + weapon.getLevelReq() + ":" + weapon.getDamageRating());
        }
        else
        {
            Armor armor = (Armor)item;
            pw.print(":" + armor.getItemType() + ":" + armor.getName() + ":" +
                           armor.getLevelReq() + ":" + armor.getArmorRating());
        }
    }
    
    public static Item loadSingleItem(StringTokenizer tokenizedLine)
    {
        String itemName;
        int levelReq;
        int damageRating;
        int armorRating;
        Item completedItem = null;
        
        //Expects the first token to be be a tag identifying what type of item is being read.
        //Also expects item attributes to be stored in a specific order.
        switch(tokenizedLine.nextToken())
        {
            case "WEAPON":
                itemName = tokenizedLine.nextToken();
                levelReq = Integer.parseInt(tokenizedLine.nextToken());
                damageRating = Integer.parseInt(tokenizedLine.nextToken());

                completedItem = new Weapon(itemName, levelReq, damageRating);
                break;

            case "HELMET":
                itemName = tokenizedLine.nextToken();
                levelReq = Integer.parseInt(tokenizedLine.nextToken());
                armorRating = Integer.parseInt(tokenizedLine.nextToken());

                completedItem = new Helmet(itemName, levelReq, armorRating);
                break;

            case "BREASTPLATE":
                itemName = tokenizedLine.nextToken();
                levelReq = Integer.parseInt(tokenizedLine.nextToken());
                armorRating = Integer.parseInt(tokenizedLine.nextToken());

                completedItem = new Breastplate(itemName, levelReq, armorRating);
                break;

            case "PLATELEGS":
                itemName = tokenizedLine.nextToken();
                levelReq = Integer.parseInt(tokenizedLine.nextToken());
                armorRating = Integer.parseInt(tokenizedLine.nextToken());

                completedItem = new Platelegs(itemName, levelReq, armorRating);
                break;
        }     
        
        return completedItem;
    }
}