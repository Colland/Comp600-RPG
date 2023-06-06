/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

/**
 *
 * @author Tristan
 */
public class Quest
{
    private String name;
    private int goldReward;
    private int xpReward;
    private String description;
    private EnemyType enemyTarget;
    private int enemyAmount;
    private boolean accepted;
    private boolean completed;
    
    public Quest(String questName, int goldReward, int xpReward, EnemyType enemyTarget, int enemyAmount)
    {
        this.name = questName;
        this.goldReward = goldReward;
        this.xpReward = xpReward;
        this.enemyTarget = enemyTarget;
        this.enemyAmount = enemyAmount;
        this.accepted = false;
        this.completed = false;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public EnemyType getEnemyTarget()
    {
        return this.enemyTarget;
    }
    
    public int getGoldReward()
    {
        return this.goldReward;
    }
    
    public int getXpReward()
    {
        return this.xpReward;
    }
    
    //Decreases the amount of enemies needed to kill and checks if quest is completed
    public void decrementEnemyAmount()
    {
        this.enemyAmount -= 1;
        
        if(this.enemyAmount == 0)
        {
            this.completed = true;
            System.out.println("You have completed " + this.getName() + "! Time to hand it in.");
            
            try
            {
                Thread.sleep(2000);
            }
            catch(InterruptedException e)
            {
                
            }
            System.out.println();
        }
    }
    
    public void addDescription(String description)
    {
        this.description = description;
    }
    
    public void acceptQuest()
    {
        this.accepted = true;
    }
    
    public boolean isAccepted()
    {
        return this.accepted;
    }
    
    public boolean isCompleted()
    {
        return this.completed;
    }
    
    public void displayQuest()
    {
        System.out.println(this.name);
        System.out.println("--------------------------------------");
        System.out.println(this.description);
    }
    
    public void complete()
    {
        System.out.println("Congratulations you have completed " + this.name + " and gained "
                + this.xpReward + " xp and " + this.goldReward + " gold.");
        
        Player player = World.getPlayer();
        player.addXp(xpReward);
        player.addGold(goldReward);
    }
}
