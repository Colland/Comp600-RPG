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
public class Player
{
    private String name;
    private int health;
    private int maxHealth;
    private int strength;
    private int level;
    private int experience;
    private int gold;
    private int xpToNextLevel;
    private Weapon equippedWeapon;
    private Helmet equippedHelmet;
    private Breastplate equippedBreastPlate;
    private Platelegs equippedPlateLegs;
    private Location currentLocation;
    private ArrayList<Quest> acceptedQuests = new ArrayList<>();
    private ArrayList<Item> inventory = new ArrayList<>();
    
    public Player(String name, int maxHealth, Location currentLocation)
    {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.currentLocation = currentLocation;
        this.strength = 10;
        this.level = 1;
        this.experience = 0;
        this.xpToNextLevel = 100;
        this.gold = 10;
    }
    
    //Constructor for loading from a save file.
    public Player(String name, int health, int maxHealth, int level, int xp, int gold,
                  int xpToNextLevel, int strength, Location currentLoc, ArrayList<Item> inventory,
                  Helmet helmetEquip, Breastplate breastPlateEquip, Platelegs plateLegsEquip,
                  Weapon weaponEquip)
    {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.level = level;
        this.experience = xp;
        this.gold = gold;
        this.xpToNextLevel = xpToNextLevel;
        this.strength = strength;
        this.currentLocation = currentLoc;
        this.inventory = inventory;
        this.equippedHelmet = helmetEquip;
        this.equippedBreastPlate = breastPlateEquip;
        this.equippedPlateLegs = plateLegsEquip;
        this.equippedWeapon = weaponEquip;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    //Calculates total armor rating based off of all armor currently equipped.
    public int getArmorPoints()
    {
        int totalArmor = 0;
        Armor helmet = this.equippedHelmet;
        Armor breastPlate = this.equippedBreastPlate;
        Armor plateLegs = this.equippedPlateLegs;
        
        if(helmet != null)
        {
            totalArmor += helmet.getArmorRating();
        }
        if(breastPlate != null)
        {
            totalArmor += breastPlate.getArmorRating();
        }
        if(plateLegs != null)
        {
            totalArmor += plateLegs.getArmorRating();
        }
        
        return(totalArmor);
    }
    
    //Calculates total damage rating off any weapon currently equipped.
    public int getDamageRating()
    {
        int totalDamage = 0;
        if(this.equippedWeapon != null)
        {
            totalDamage += equippedWeapon.getDamageRating();
        }
        
        return totalDamage;
    }
    
    public int getStrength()
    {
        return this.strength;
    }
    
    public int getLevel()
    {
        return this.level;
    }
    
    public int getXp()
    {
        return this.experience;
    }
    
    public int getXpToNextLevel()
    {
        return this.xpToNextLevel;
    }
    
    public int getGold()
    {
        return this.gold;
    }
    
    public int getCurrentHealth()
    {
        return this.health;
    }
    
    public int getMaxHealth()
    {
        return this.maxHealth;
    }
    
    public Item getEquippedHelmet()
    {
        return this.equippedHelmet;
    }
    
    public Item getEquippedBreastPlate()
    {
        return this.equippedBreastPlate;
    }
    
    public Item getEquippedPlateLegs()
    {
        return this.equippedPlateLegs;
    }
    
    public Item getEquippedWeapon()
    {
        return this.equippedWeapon;
    }
    
    public ArrayList<Item> getInventory()
    {
        return this.inventory;
    }
    
    //Adds xp and checks if player has leveled up.
    public void addXp(int xp)
    {
        this.experience += xp;
        this.xpToNextLevel -= xp;
        
        if(this.xpToNextLevel <= 0)
        {
            this.levelUp();
        }
    }
    
    public void addGold(int gold)
    {
        this.gold += gold;
    }
    
    
    public void addItemToInventory(Item item)
    {
        this.inventory.add(item);
    }
    
    public void changeLocation(Location location)
    {
        this.currentLocation = location;
    }
    
    public void displayLocationOptions()
    {
        this.currentLocation.displayOptions();
    }
    
    public Location getCurrentLocation()
    {
        return this.currentLocation;
    }
    
    public void addAcceptedQuest(Quest quest)
    {
        this.acceptedQuests.add(quest);
    }
    
    public void reduceHealth(int damage)
    {
        this.health -= damage;
    }
    
    public void reduceGold(int gold)
    {
        this.gold -= gold;
    }
    
    public void reviveCharacter()
    {
        this.health = maxHealth;
    }
    
    public boolean isDead()
    {
        if(this.health <= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private void equipItem(Item item)
    {
        if(item instanceof Helmet)
        {
            this.equippedHelmet = (Helmet)item;
        }
        else if(item instanceof Breastplate)
        {
            this.equippedBreastPlate = (Breastplate)item;
        }
        else if(item instanceof Platelegs)
        {
            this.equippedPlateLegs = (Platelegs)item;
        }
        else if(item instanceof Weapon)
        {
            this.equippedWeapon = (Weapon)item;
        }
    }
    
    //Levels up the player, increasing their stats, resetting healthy, and displaying
    //a level up message.
    public void levelUp()
    {
        if(this.xpToNextLevel <= 0)
        {
            this.xpToNextLevel = (this.level*50) + 50;
            this.level++;
            
            this.strength += 5;
            this.maxHealth += 50;
            this.health = maxHealth;
            
            System.out.println("\n You leveled up! You are now level " + this.level);
            try
            {
                Thread.sleep(2000);
            }
            catch(InterruptedException e)
            {
                
            }
        }
    }
    
    /*Generates a random damage value between a certain range based off of players
    stats and enemy stats.*/
    public void attack(CombatNpc enemy)
    {
        int damageRange = (int)Math.floor(this.strength /2);
        int damageFloor = (int)(this.strength * 0.75) + this.getDamageRating();
        int baseDmg = (int)Math.floor((Math.random() * damageRange) + damageFloor);
        int totalDmg = baseDmg - enemy.getArmorPoints();
        
        //Make sure damage doesn't go into the negative and increase enemy health
        if(totalDmg < 0)
        {
            totalDmg = 0;
        }
        enemy.reduceHealth(totalDmg);
        System.out.println("You attack the " + enemy.getName() + " and deal " 
                           + totalDmg + " damage.");
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            
        }
    }
    
    //Rolls a 1 in 3 chance to run away from the combat encounter
    public boolean attemptRun(CombatNpc enemy)
    {
        double rng = Math.random();
        
        if(rng <= 0.33)
        {
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
    //The main battle loop
    public void battle(CombatNpc enemy)
    {
        System.out.println("\nBattle");
        System.out.println("------");
        enemy.displayBattleText();
        boolean flag = true;        
        
        do
        {
            //Displays battle status and options.
            System.out.println("\n| " + enemy.getName() + " | HP: " + enemy.getHealth()
                               + " |");
            System.out.println("| " + this.name + " | HP: " + this.health + " |");
            
            System.out.println("\n1. Attack");
            System.out.println("2. Run");
            
            Scanner scan = new Scanner(System.in);
            
            //Gets user input and calls corresponding function to either attack or run
            try
            {
                int userInput = scan.nextInt();
            
                if(userInput <= 0 || userInput > 2)
                {
                    System.out.println("Please input a valid number\n");
                }
                else
                {
                   switch(userInput)
                   {
                       case 1:
                           //Deals damage to enemy
                           this.attack(enemy);
                           enemy.attack(this);
                           break;
                           
                       case 2:
                           //Attempts to run from battle
                           if(this.attemptRun(enemy) == true)
                            {
                               flag = false;
                               System.out.println("You manage to run away.\n");
                            }
                            else
                            {
                                System.out.println("You try to run and fail.");    
                                enemy.attack(this);
                            }
   
                   } 

                   //Check if enemy or player is dead
                   if(enemy.isDead())
                   {
                       enemy.die(this);
                       this.updateQuests(enemy.getEnemyType());
                       flag = false;
                   }
                   else if(this.isDead())
                   {
                       System.out.println("You have died. You lost\n");
                       flag = false;
                   }
                }
            }
            catch(InputMismatchException e)
            {
                System.out.println("Please input only numbers\n"); 
            }
        }while(flag);
    }
    
    /*Checks all accepted player quests and updates them if they have the relevant
    enemy type.*/
    public void updateQuests(EnemyType enemyType)
    {
        for(int i = 0; i < this.acceptedQuests.size(); i++)
        {
            if(acceptedQuests.get(i).getEnemyTarget() == enemyType)
            {
                acceptedQuests.get(i).decrementEnemyAmount();
            }
        }
    }
    
    /*Displays the players inventory menu and prompts the user to choose an option.*/
    public void displayInventoryMenu()
    {
        boolean inventoryFlag1 = true;
        do
        {
            System.out.println("Inventory");
            System.out.println("---------");
            System.out.println("Gold: " + this.gold + "\n");
            System.out.println("1. Change equipment");
            System.out.println("2. Show all items");
            System.out.println("3. Exit menu");

            Scanner scan = new Scanner(System.in);
            
            try
            {
                int userInput = scan.nextInt();

                if(userInput == 3)
                {
                    inventoryFlag1 = false;
                }
                else if(userInput > 2 || userInput <= 0)
                {
                    System.out.println("Please input a valid number.");
                }   
                else
                {
                    if(userInput == 1)
                    {
                        this.displayEquippedItems();
                    }
                    else if(userInput == 2)
                    {
                        this.displayAllItems();
                    }
                } 
            }
            catch(InputMismatchException e)
            {
                System.out.println("Please input only numbers");  
            }
        }while(inventoryFlag1);
    }
    
    /*Displays what items the player is wearing and prompts the user to select an
    equipment slot to change.*/
    public void displayEquippedItems()
    {
        boolean flag = true;
        
        do
        {
            System.out.println("Your equipment");
            System.out.println("--------------");
            
            System.out.print("1. Helmet: ");
            if(this.equippedHelmet == null)
            {
                System.out.println("");
            }
            else
            {
                System.out.println(this.equippedHelmet.getName());
            }

            System.out.print("2. Breastplate: ");
            if(this.equippedBreastPlate == null)
            {
                System.out.println("");
            }
            else
            {
                System.out.println(this.equippedBreastPlate.getName());
            }

            System.out.print("3. Platelegs: ");
            if (this.equippedPlateLegs == null)
            {
                System.out.println("");
            }
            else
            {
                System.out.println(this.equippedPlateLegs.getName());
            }

            System.out.print("4. Weapon: ");
            if (this.equippedWeapon == null)
            {
                System.out.println("");
            }
            else
            {
                System.out.println(this.equippedWeapon.getName());
            }
            
            System.out.println("5. Exit menu");
            System.out.println("\nChoose what equipment to change.");

            Scanner scan = new Scanner(System.in);
            
            try
            {
                int userInput = scan.nextInt(); 
                
                switch(userInput)
                {
                    case 1:
                        this.displayItemsToEquip(ItemType.HELMET);
                        break;
                        
                    case 2:
                        this.displayItemsToEquip(ItemType.BREASTPLATE);
                        break;
                        
                    case 3:
                        this.displayItemsToEquip(ItemType.PLATELEGS);
                        break;
                        
                    case 4:
                        this.displayItemsToEquip(ItemType.WEAPON);
                        break;
                        
                    case 5:
                        flag = false;
                        break;
                        
                    default:
                        System.out.println("Please input a valid number");
                        break;
                }
            }  
            catch(InputMismatchException e)
            {
                System.out.println("Please enter only numbers.");
            }
            
            
        }while(flag);       
    }
    
    /*Displays only items from the inventory with a type corresponding to the input
    type, displays them, and prompts the user to choose one to equip,*/
    public void displayItemsToEquip(ItemType itemType)
    {
        ArrayList<Item> bufferList = new ArrayList<>();
        
        for(int i = 0; i < this.inventory.size(); i++)
        {
            if(this.inventory.get(i).getItemType() == itemType)
            {
                bufferList.add(this.inventory.get(i));  
            }
        }
        
        boolean flag = true;
        do
        {
            switch(itemType)
            {
                case HELMET:
                    System.out.println("\nEquip a helmet");
                    System.out.println("--------------");
                    break;
                  
                case BREASTPLATE:
                    System.out.println("\nEquip a breastplate");
                    System.out.println("-------------------");
                    break;
                    
                case PLATELEGS:
                    System.out.println("\nEquip a plateleg");
                    System.out.println("----------------");
                    break;
                   
                case WEAPON:
                    System.out.println("\nEquip a weapon");
                    System.out.println("--------------");
                    break;
            }
            
            int i;
            for(i = 0; i < bufferList.size(); i++)
            {
                System.out.println((i+1) + ". " + bufferList.get(i).getName());
            }
            System.out.println((i+1) + ". Exit menu");

            Scanner scan = new Scanner(System.in);

            try
            {
                int userInput = scan.nextInt();

                if(userInput > i+1 || userInput <= 0)
                {
                    System.out.println("Please input a valid number");
                }
                else if(userInput == i+1)
                {
                    flag = false;
                }
                else
                {
                    switch(itemType)
                    {
                        case HELMET:
                            this.equippedHelmet = (Helmet)bufferList.get(userInput-1);
                            flag = false;
                            break;

                        case BREASTPLATE:
                            this.equippedBreastPlate = (Breastplate)bufferList.get(userInput-1);
                            flag = false;
                            break;

                        case PLATELEGS:
                            this.equippedPlateLegs = (Platelegs)bufferList.get(userInput-1);
                            flag = false;
                            break;

                        case WEAPON: 
                            this.equippedWeapon = (Weapon)bufferList.get(userInput-1);
                            flag = false;
                            break;
                    } 
                }
            }
            catch(InputMismatchException e)
            {
                System.out.println("Please only input numbers");
            }  
        }while(flag);
    }
    
    //Displays all items in the players inventory.
    public void displayAllItems()
    {
        boolean flag = true;
        do
        {
            System.out.println("\nInventory");
            System.out.println("---------");

            for(int i = 0; i < this.inventory.size(); i++)
            {
                System.out.println(this.inventory.get(i).getName());
            }
            
            System.out.println();
            System.out.println("1. Exit menu");
            
            Scanner scan = new Scanner(System.in);
            
            try
            {
                int userInput = scan.nextInt();   
                
                if(userInput == 1)
                {
                    flag = false;
                }
                else
                {
                    System.out.println("Please input a valid number");
                }
            }
            catch(InputMismatchException e)
            {
                System.out.println("Please input only numbers");
            }
        }while(flag);
    }
    
    public boolean buyItem(Item item, int price)
    {
        boolean successfulTrade = false;
        
        if(this.gold >= price)
        {
            this.inventory.add(item);
            System.out.println("You buy the " + item.getName());
            this.reduceGold(price);
            
            successfulTrade = true;
        }
        else
        {
            System.out.println("You cant afford that");
            successfulTrade = false;
        }
        
        try
        {
            Thread.sleep(1500);
        }
        catch(InterruptedException e)
        {
            
        }
        
        return successfulTrade;
    }
}
