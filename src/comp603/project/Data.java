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
    private ArrayList<NonCombatNpc> nonCombatNpcList;
    private ArrayList<Option> nonCombatNpcOptionList;
    private String menuTitle;
    private ArrayList<Quest> questList;
    private Quest currentQuest;
    private ArrayList<CombatNpc> combatNpcList;
    private ArrayList<Item> shopItemList;
    private boolean itemBought;
    private CombatNpc currentEnemy;
    private Player player;
    private int playerDmg;
    private int enemyDmg;
    private boolean runAttempt;
    private boolean runSuccess;
    private boolean playerDead;
    private boolean enemyDead;
    private boolean levelUp;
    private boolean innAttempt;
    private boolean innSuccess;
    
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
    
    public Location getPlayerLocation()
    {
        return this.playerLocation;
    }
    
    public ArrayList<Integer> getTravelDirections()
    {
        return this.travelDirections;
    }
    
    public void setNonCombatNpcList(ArrayList<NonCombatNpc> nonCombatNpcList)
    {
        this.nonCombatNpcList = nonCombatNpcList;
    }
    
    public ArrayList<NonCombatNpc> getNonCombatNpcList()
    {
        return this.nonCombatNpcList;
    }
    
    public void setNonCombatNpcOptionList(ArrayList<Option> optionList)
    {
        this.nonCombatNpcOptionList = optionList;
    }
    
    public ArrayList<Option> getNonCombatNpcOptionList()
    {
        return this.nonCombatNpcOptionList;
    }
    
    public void setMenuTitle(String menuTitle)
    {
        this.menuTitle = menuTitle;
    }
    
    public String getMenuTitle()
    {
        return this.menuTitle;
    }
    
    public void setQuestList(ArrayList<Quest> questList)
    {
        this.questList = questList;
    }
    
    public ArrayList<Quest> getQuestList()
    {
        return this.questList;
    }
    
    public void setCurrentQuest(Quest quest)
    {
        this.currentQuest = quest;
    }
    
    public Quest getCurrentquest()
    {
        return this.currentQuest;
    }
    
    public void setCombatNpcList(ArrayList<CombatNpc> npcList)
    {
        this.combatNpcList = npcList;
    }
    
    public ArrayList<CombatNpc> getCombatNpcList()
    {
        return this.combatNpcList;
    }
    
    public void setCurrentEnemy(CombatNpc enemy)
    {
        this.currentEnemy = enemy;
    }
    
    public CombatNpc getCurrentEnemy()
    {
        return this.currentEnemy;
    }
    
    public void setPlayer(Player player)
    {
        this.player = player;
    }
    
    public Player getPlayer()
    {
        return this.player;
    }
    
    public void setPlayerDmg(int playerDmg)
    {
        this.playerDmg = playerDmg;
    }
    
    public int getPlayerDmg()
    {
        return this.playerDmg;
    }
    
    public void setEnemyDmg(int enemyDmg)
    {
        this.enemyDmg = enemyDmg;
    }
    
    public int getEnemyDmg()
    {
        return this.enemyDmg;
    }
    
    public void setRunAttempt(boolean runAttempt)
    {
        this.runAttempt = runAttempt;
    }
    
    public Boolean isRunAttempt()
    {
        return this.runAttempt;
    }
    
    public void setRunSuccess(boolean runSuccess)
    {
        this.runSuccess = runSuccess;
    }
    
    public boolean getRunSuccess()
    {
        return this.runSuccess;
    }
    
    public void setPlayerDead(boolean isPlayerDead)
    {
        this.playerDead = isPlayerDead;
    }
    
    public boolean getPlayerDead()
    {
        return this.playerDead;
    }
    
    public void setEnemyDead(boolean isEnemyDead)
    {
        this.enemyDead = isEnemyDead;
    }
    
    public boolean getEnemyDead()
    {
        return this.enemyDead;
    }
    
    public void setLevelUp(boolean levelUp)
    {
        this.levelUp = levelUp;
    }
    
    public boolean getLevelUp()
    {
        return this.levelUp;
    }
    
    public void setInnAttempt(boolean innAttempt)
    {
        this.innAttempt = innAttempt;
    }
    
    public boolean getInnAttempt()
    {
        return this.innAttempt;
    }
    
    public void setInnSuccess(boolean innSuccess)
    {
        this.innSuccess = innSuccess;
    }
    
    public boolean getInnSuccess()
    {
        return this.innSuccess;
    }
    
    public void setShopItems(ArrayList<Item> shopItems)
    {
        this.shopItemList = shopItems;
    }
    
    public ArrayList<Item> getShopItems()
    {
        return this.shopItemList;
    }
    
    public void setItemBought(boolean itemBought)
    {
       this.itemBought = itemBought; 
    }
    
    public boolean getItemBought()
    {
        return this.itemBought;
    }
}
