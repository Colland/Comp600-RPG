/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**
 *
 * @author tjack
 */
public class Player
{
    private String id;
    private String name;
    private int health;
    private int maxHealth;
    private int strength;
    private int level;
    private int experience;
    private int gold;
    private int xpToNextLevel;
    private Weapon equippedWeapon;
    private Armor equippedHelmet;
    private Armor equippedBreastPlate;
    private Armor equippedPlateLegs;
    private Location currentLocation;
    private ArrayList<Quest> acceptedQuests = new ArrayList<>();
    private ArrayList<Item> inventory;
    
    public Player(String name, int maxHealth, Location currentLocation)
    {
        this.id = this.generateId();
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.currentLocation = currentLocation;
        this.strength = 10;
        this.level = 1;
        this.experience = 0;
        this.xpToNextLevel = 100;
        this.gold = 10;
        this.inventory = new ArrayList<Item>();
    }
    
    //Constructor for loading from a database.
    public Player(String id, String name, int health, int maxHealth, int level, int xp, int gold,
                  int xpToNextLevel, int strength, Item helmetEquip, Item breastPlateEquip, Item plateLegsEquip,
                  Item weaponEquip, ArrayList<Item> inventory)
    {
        this.id = id;
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.level = level;
        this.experience = xp;
        this.gold = gold;
        this.xpToNextLevel = xpToNextLevel;
        this.strength = strength;
        this.currentLocation = World.getLocation(new Coordinate(0, 0));
        this.equippedHelmet = (Armor)helmetEquip;
        this.equippedBreastPlate = (Armor)breastPlateEquip;
        this.equippedPlateLegs = (Armor)plateLegsEquip;
        this.equippedWeapon = (Weapon)weaponEquip;
        this.inventory = inventory;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    private String generateId()
    {
        LocalDateTime timestamp = LocalDateTime.now();
        String uniqueId = timestamp.format(DateTimeFormatter.ofPattern("yyyyHHmmss")) + 1;
        
        return uniqueId;
    }
    
    public String getId()
    {
        return this.id;
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
    
    public boolean healAtInn()
    {
        if(this.gold >= 5)
        {
            this.health = maxHealth;
            this.gold -= 5;
            return true;
        }
        else
        {
            return false;
        }
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
    
    public void equipItem(Item item)
    {
        if(item.getItemType() == ItemType.HELMET)
        {
            this.equippedHelmet = (Armor)item;
        }
        else if(item.getItemType() == ItemType.BREASTPLATE)
        {
            this.equippedBreastPlate = (Armor)item;
        }
        else if(item.getItemType() == ItemType.PLATELEGS)
        {
            this.equippedPlateLegs = (Armor)item;
        }
        else if(item.getItemType() == ItemType.WEAPON)
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
        }
    }
    
    /*Generates a random damage value between a certain range based off of players
    stats and enemy stats.*/
    public int attack(CombatNpc enemy)
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
        
        return totalDmg;
    }
    
    //Rolls a 1 in 3 chance to run away from the combat encounter
    public boolean attemptRun()
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
    
    public void die()
    {
        this.health = this.maxHealth;
        this.currentLocation = World.map.get(new Coordinate(0, 0));
    }
}
