/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

/**
 *
 * @author tjack
 */
public class CombatNpc extends Npc
{
    private int health;
    private int armor;
    private int strength;
    private int xpReward;
    private int goldReward;
    private EnemyType enemyType;
    private String battleText;
    private String deathText;
    
    public CombatNpc(String name, int health, int armor, int strength, int xpReward, int goldReward,
                     EnemyType enemyType)
    {
        super(name);
        this.health = health;
        this.armor = armor;
        this.strength = strength;
        this.xpReward = xpReward;
        this.goldReward = goldReward;
        this.enemyType = enemyType;
    }
    
    public int getArmorPoints()
    {
        return this.armor;
    }
    
    public int getHealth()
    {
        return this.health;
    }
    
    public EnemyType getEnemyType()
    {
        return this.enemyType;
    }
    
    public void reduceHealth(int damage)
    {
        this.health -= damage;
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
    
    public void addBattleText(String text)
    {
        this.battleText = text;
    }
    
    public void addDeathText(String text)
    {
        this.deathText = text;
    }
    
    public void displayBattleText()
    {
        System.out.println(this.battleText);
    }
    
    //Calculates a random amount of damage between a set range using CombatNpc and
    //player stat values.
    public int attack(Player player)
    {
        int damageRange = (int)Math.floor(this.strength /2);
        int damageFloor = (int)(this.strength * 0.75);
        int baseDmg = (int)Math.floor((Math.random() * damageRange) + damageFloor);
        int totalDmg = baseDmg - player.getArmorPoints();
        
        if(totalDmg < 0)
        {
            totalDmg = 0;
        }
        player.reduceHealth(totalDmg);
        
        return totalDmg;
    }
    
    public void die(Player player)
    {
        player.addXp(this.xpReward);
        player.addGold(this.goldReward);
        
        System.out.println("\n" + this.deathText);
        System.out.println("You have gained " + this.xpReward + " xp and " + this.goldReward
                           + " gold!\n");
        try
        {
            Thread.sleep(1500);
        }
        catch(InterruptedException e)
        {
            
        }
        
        //Beating the balrog is the end game condition.
        if(this.enemyType == EnemyType.BALROG)
        {
            System.out.println("Congratulations you beat the balrog and saved the town!");
            try
            {
                Thread.sleep(5000);
                System.out.println("Ending game...");
                Thread.sleep(3000);
                System.exit(0);
            }
            catch (InterruptedException e)
            {
                
            }
        }
        
        player.getCurrentLocation().removeCombatNpc(this);
    }
}
