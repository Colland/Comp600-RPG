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
    private CombatNpc currentEnemy;
    private Player player;
    private int playerDmg;
    private int enemyDmg;
    private boolean runAttempt;
    private boolean runSuccess;
    
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
}
