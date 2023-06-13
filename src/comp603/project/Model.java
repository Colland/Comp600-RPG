/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Tristan
 */
public class Model extends Observable
{
    public Database db;
    public Player player;
    private NonCombatNpc currentNonCombatNpc;
    private Quest currentQuest;
    private CombatNpc currentEnemy;
    
    public Model()
    {
        this.db = new Database();
        this.db.setupDB();
    }
    
    //Finds if there are any locations in each direction and passes it to the view.
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
    
    public void getCurrentLocationNPCs()
    {
        ArrayList<NonCombatNpc> npcList = player.getCurrentLocation().getNonCombatNpcList();
        Data data = new Data();
        data.setNonCombatNpcList(npcList);
        
        this.setChanged();
        notifyObservers(data);
    }
    
    public void getNpcOptions(int userChoice)
    {
        NonCombatNpc npc = player.getCurrentLocation().getNonCombatNpcList().get(userChoice);
        this.currentNonCombatNpc = npc;
        ArrayList<Option> npcOptionList = npc.getOptionList();
        Data data = new Data();
        data.setNonCombatNpcOptionList(npcOptionList);
        data.setMenuTitle(npc.getName());
        
        this.setChanged();
        notifyObservers(data);
    }
    
    public void getNpcQuests()
    {
        ArrayList<Quest> questList = this.currentNonCombatNpc.getQuestList();
        
        Data data = new Data();
        data.setQuestList(questList);
        data.setMenuTitle("Quests");
        
        this.setChanged();
        notifyObservers(data);
    }
    
    public void getQuest(int userChoice)
    {
        Quest quest = currentNonCombatNpc.getQuestList().get(userChoice);
        this.currentQuest = quest;
        
        Data data = new Data();
        data.setCurrentQuest(quest);
        
        this.setChanged();
        notifyObservers(data);
    }
    
    public void acceptQuest()
    {
        this.currentQuest.acceptQuest();
        this.player.addAcceptedQuest(this.currentQuest);
        
        Data data = new Data();
        data.setCurrentQuest(currentQuest);
        
        this.setChanged();
        notifyObservers(data);
    }
    
    public void displayLocationEnemies()
    {
        ArrayList<CombatNpc> npcList = this.player.getCurrentLocation().combatNpcList;
        
        Data data = new Data();
        data.setCombatNpcList(npcList);
        data.setMenuTitle(this.player.getCurrentLocation().getName());
        
        this.setChanged();
        notifyObservers(data);
    }
    
    public void initiateBattle(int userChoice)
    {
        CombatNpc currentEnemy = this.player.getCurrentLocation().combatNpcList.get(userChoice);
        this.currentEnemy = currentEnemy;
        
        Data data = new Data();
        data.setCurrentEnemy(this.currentEnemy);
        data.setPlayer(this.player);
        
        this.setChanged();
        notifyObservers(data);
    }
    
    public void attackRound()
    {
        int playerDamage = this.player.attack(this.currentEnemy);
        int enemyDamage = this.currentEnemy.attack(this.player);
        
        Data data = new Data();
        data.setCurrentEnemy(currentEnemy);
        data.setPlayer(this.player);
        data.setPlayerDmg(playerDamage);
        data.setEnemyDmg(enemyDamage);
        data.setRunAttempt(false);
        
        if(this.player.isDead())
        {
            this.player.die();
            data.setPlayerDead(true);
        }
        else if(this.currentEnemy.isDead())
        {
            int currentLvl = player.getLevel();
            this.currentEnemy.die(this.player);
            data.setEnemyDead(true);
            
            if(player.getLevel() > currentLvl)
            {
                data.setLevelUp(true);
            }
        }
        
        this.setChanged();
        notifyObservers(data);
    }
    
    public void runRound()
    {
       Data data = new Data();
       
       if(player.attemptRun() == true)
       {
           data.setRunSuccess(true);
       }
       else
       {
           int playerDamage = 0;
           int enemyDamage = this.currentEnemy.attack(this.player);  
           data.setCurrentEnemy(currentEnemy);
           data.setPlayer(this.player);
           data.setEnemyDmg(enemyDamage);
           data.setPlayerDmg(playerDamage);
           
           if(this.player.isDead())
            {
                this.player.die();
                data.setPlayerDead(true);
            }   
            else if(this.currentEnemy.isDead())
            {
                int currentLvl = player.getLevel();
                this.currentEnemy.die(this.player);
                data.setEnemyDead(true);

                if(player.getLevel() > currentLvl)
                {
                    data.setLevelUp(true);
                }
            }
       } 
       data.setRunAttempt(true);
       data.setPlayerLocation(this.player.getCurrentLocation());
        
       this.setChanged();
       notifyObservers(data); 
    }
    
    public void showInventory()
    {
        Data data = new Data();
        data.setPlayer(this.player);
        
        this.setChanged();
        notifyObservers(data); 
    }
    
    //Parses ComboItem's back into their item types and has the player equip them.
    public void equipItems(ComboItem helmet, ComboItem breastplate, ComboItem platelegs, ComboItem weapon)
    {
        if(helmet != null)
        {
            this.player.equipItem(helmet.getItem());
        }
        if(breastplate != null)
        {
            this.player.equipItem(breastplate.getItem());
        }
        if(platelegs != null)
        {
            this.player.equipItem(platelegs.getItem());
        }
        if(weapon != null)
        {
            this.player.equipItem(weapon.getItem());
        }
    }
    
    public void displayShopItems(ItemType itemType)
    {
        Data data = new Data();
        data.setShopItems(this.player.getCurrentLocation().npcShopList.get(0).getItemsOfType(itemType));
        
        this.setChanged();
        notifyObservers(data); 
    }
    
    public void buyItem(Item item)
    {
        Data data = new Data();
        data.setPlayer(this.player);
        
        if(player.getGold() >= item.getGoldCost())
        {
            this.player.reduceGold(item.getGoldCost());
            this.player.addItemToInventory(item);
            this.player.getCurrentLocation().npcShopList.get(0).removeItem(item);
            data.setItemBought(true);
        }
        else
        {
            data.setItemBought(false);
        }
        
        this.setChanged();
        notifyObservers(data); 
    }
    
    public void restorePlayerHealth()
    {
        Data data = new Data();
        data.setInnAttempt(true);
        data.setPlayer(this.player);
        
        if(this.player.healAtInn())
        {
            data.setInnSuccess(true);
        }
        else
        {
            data.setInnSuccess(false);
        }
        
        this.setChanged();
        notifyObservers(data); 
    }
    
    public void saveCharacter()
    {
        this.db.insertPlayerData(this.player);
    }
    
    public void generateNewCharacter()
    {
        Player player = new Player("Tristan", 100, World.getLocation(new Coordinate(0, 0)));
        player.addItemToInventory(new Weapon("Copper shortsword", 5, 1, 5, ItemType.WEAPON));
        player.addItemToInventory(new Armor("Leather helmet", 5, 1, 3, ItemType.HELMET));
        player.addItemToInventory(new Armor("Leather tunic", 5, 1, 3, ItemType.BREASTPLATE));
        player.addItemToInventory(new Armor("Leather chaps", 5, 1, 3, ItemType.PLATELEGS));
        World.setPlayer(player);
        this.player = player;
    }
    
}
