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
}
