/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JFrame;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Tristan
 */
public class View extends JFrame implements Observer
{
    private Controller controller;
    private JPanel locationPanel = new JPanel();
    private JPanel directionsPanel = new JPanel();
    private JPanel guiLayoutPanel = new JPanel();
    
    private String currentMenu;
            
    public View(Controller controller)
    {
        this.controller = controller;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);
        guiLayoutPanel.setLayout(new BoxLayout(guiLayoutPanel, BoxLayout.X_AXIS));
        locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.Y_AXIS));
        locationPanel.setMaximumSize(new Dimension(600, 300));
        
        this.displayLocationGUI();
        this.currentMenu = "LocationMain";
        this.setVisible(true);
    }
    
    public void displayLocationGUI()
    {
        this.guiLayoutPanel.removeAll();
        this.locationPanel.removeAll();
        //Model updates and sends a data packet, containing info on what screen, what location etc.
        Location location = World.getPlayer().getCurrentLocation();
        ArrayList<Option> optionList = location.getOptions();
        
        JLabel locName = new JLabel(location.getName());
        locName.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        locName.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        locationPanel.add(locName);
        
        for(int i = 0; i < optionList.size(); i++)
        {
            Option option = optionList.get(i);
            CustomButton optionBtn = new CustomButton(option.getOptionText(), option.getOptionType());
            optionBtn.setMaximumSize(new Dimension(170, 30));
            optionBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            optionBtn.addActionListener(controller);
            locationPanel.add(optionBtn);
        }
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(locationPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.add(guiLayoutPanel);
        this.revalidate();
        this.repaint();
        
        this.currentMenu = "LocationMain";
    }
    
      public void displayLocationGUI(Location location)
      {
          this.guiLayoutPanel.removeAll();
          this.locationPanel.removeAll();
          //Model updates and sends a data packet, containing info on what screen, what location etc.
          ArrayList<Option> optionList = location.getOptions();

          JLabel locName = new JLabel(location.getName());
          locName.setFont(new Font("Century Gothic", Font.PLAIN, 32));
          locName.setAlignmentX(JLabel.CENTER_ALIGNMENT);
          locationPanel.add(locName);

          for(int i = 0; i < optionList.size(); i++)
          {
              Option option = optionList.get(i);
              CustomButton optionBtn = new CustomButton(option.getOptionText(), option.getOptionType());
              optionBtn.setMaximumSize(new Dimension(170, 30));
              optionBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
              optionBtn.addActionListener(controller);
              
              if(option.getOptionType() == ButtonType.LOOKFORENEMIES)
              {
                  optionBtn.setMaximumSize(new Dimension(170, 40));
                  optionBtn.setText("<html>Look around for <br /> some enemies<html>");
              }
              
              locationPanel.add(optionBtn);
          }

          guiLayoutPanel.add(Box.createHorizontalGlue());
          guiLayoutPanel.add(locationPanel);
          guiLayoutPanel.add(Box.createHorizontalGlue());
          
          this.add(guiLayoutPanel);
          this.revalidate();
          this.repaint();
          
          this.currentMenu = "LocationMain";
        }
    
    public void displayDirectionsGUI(ArrayList<Integer> availableDirections)
    {
        this.guiLayoutPanel.removeAll();
        this.directionsPanel.removeAll();
        this.directionsPanel.setLayout(new GridLayout(3, 3));
        this.directionsPanel.setMaximumSize(new Dimension(200, 200));
        
        CustomButton northBtn = new CustomButton("North");
        CustomButton westBtn = new CustomButton("West");
        CustomButton eastBtn = new CustomButton("East");
        CustomButton southBtn = new CustomButton("South");
        CustomButton exitBtn = new CustomButton("Exit menu");
        
        northBtn.buttonType = ButtonType.DIRECTIONAL;
        westBtn.buttonType = ButtonType.DIRECTIONAL;
        eastBtn.buttonType = ButtonType.DIRECTIONAL;
        southBtn.buttonType = ButtonType.DIRECTIONAL;
        exitBtn.buttonType = ButtonType.EXITMENU;
        
        this.directionsPanel.add(Box.createHorizontalGlue());
        if(availableDirections.contains(0))
        {
            northBtn.addActionListener(controller);
            this.directionsPanel.add(northBtn);   
        }
        else
        {
            this.directionsPanel.add(Box.createHorizontalGlue());  
        }
        
        this.directionsPanel.add(Box.createHorizontalGlue());
        if(availableDirections.contains(3))
        {
            westBtn.addActionListener(controller);
            this.directionsPanel.add(westBtn);   
        }
        else
        {
            this.directionsPanel.add(Box.createHorizontalGlue());  
        }
        
        this.directionsPanel.add(Box.createHorizontalGlue());
        if(availableDirections.contains(1))
        {
            eastBtn.addActionListener(controller);
            this.directionsPanel.add(eastBtn);   
        }
        else
        {
            this.directionsPanel.add(Box.createHorizontalGlue());  
        }
        
        this.directionsPanel.add(Box.createHorizontalGlue());
        if(availableDirections.contains(2))
        {
            southBtn.addActionListener(controller);
            this.directionsPanel.add(southBtn);   
        }
        else
        {
            this.directionsPanel.add(Box.createHorizontalGlue());  
        }
        
        exitBtn.addActionListener(controller);
        this.directionsPanel.add(exitBtn); 
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(directionsPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.add(guiLayoutPanel);
        this.revalidate();
        this.repaint();
        
        this.currentMenu = "Directions";
    }
    
    public void displayLocationNpcs(ArrayList<NonCombatNpc> npcList)
    {
        this.guiLayoutPanel.removeAll();
        JPanel locationNpcPanel = new JPanel();
        locationNpcPanel.setLayout(new BoxLayout(locationNpcPanel, BoxLayout.Y_AXIS));
        locationNpcPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel npcTitle = new JLabel("Npcs");
        npcTitle.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        npcTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        locationNpcPanel.add(npcTitle);
        
        for(int i = 0; i < npcList.size(); i++)
          {
              Npc npc = npcList.get(i);
              CustomButton npcBtn = new CustomButton(npc.getName(), ButtonType.TALKTONPC, i);
              npcBtn.setMaximumSize(new Dimension(170, 30));
              npcBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
              npcBtn.addActionListener(controller);
              
              locationNpcPanel.add(npcBtn);
          }
        
        CustomButton exitBtn = new CustomButton("Exit menu");
        exitBtn.buttonType = ButtonType.EXITMENU;
        exitBtn.setMaximumSize(new Dimension(170, 30));
        exitBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        exitBtn.addActionListener(controller);
        locationNpcPanel.add(exitBtn);
        
        this.guiLayoutPanel.add(locationNpcPanel);
        this.revalidate();
        this.repaint();
        
        this.currentMenu = "LocationNpcs";
    }
    
    public void displayNpcOptions(ArrayList<Option> optionList, String menuTitle)
    {
        this.guiLayoutPanel.removeAll();
        JPanel npcOptionsPanel = new JPanel();
        npcOptionsPanel.setLayout(new BoxLayout(npcOptionsPanel, BoxLayout.Y_AXIS));
        npcOptionsPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel npcName = new JLabel(menuTitle);
        npcName.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        npcName.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        npcOptionsPanel.add(npcName);
        
        for(int i = 0; i < optionList.size(); i++)
           {
              Option option = optionList.get(i);
              CustomButton optionBtn;
                      
              switch(option.getOptionType())
              {
                  case REQUESTQUEST:
                      optionBtn = new CustomButton(option.getOptionText(), ButtonType.REQUESTQUEST, i);
                      break;
                      
                  default:
                      optionBtn = new CustomButton(option.getOptionText());
                      break;
              }
              
              optionBtn.setMaximumSize(new Dimension(170, 30));
              optionBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
              optionBtn.addActionListener(controller);
              
              npcOptionsPanel.add(optionBtn);
           }
        
        CustomButton exitBtn = new CustomButton("Exit menu");
        exitBtn.buttonType = ButtonType.EXITMENU;
        exitBtn.setMaximumSize(new Dimension(170, 30));
        exitBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        exitBtn.addActionListener(controller);
        npcOptionsPanel.add(exitBtn);
        
         guiLayoutPanel.add(Box.createHorizontalGlue());
         guiLayoutPanel.add(npcOptionsPanel);
         guiLayoutPanel.add(Box.createHorizontalGlue());
          
         this.add(guiLayoutPanel);
         this.revalidate();
         this.repaint();
          
         this.currentMenu = "NpcOptions";
    }
    
    public void displayNpcQuests(ArrayList<Quest> questList, String menuTitle)
    {
        this.guiLayoutPanel.removeAll();
        JPanel questsPanel = new JPanel();
        questsPanel.setLayout(new BoxLayout(questsPanel, BoxLayout.Y_AXIS));
        questsPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel questsTitle = new JLabel(menuTitle);
        questsTitle.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        questsTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        questsPanel.add(questsTitle);
        
        for(int i = 0; i < questList.size(); i++)
           {
              Quest quest = questList.get(i);
              CustomButton questBtn = new CustomButton(quest.getName(), ButtonType.SHOWQUEST, i);
              
              questBtn.setMaximumSize(new Dimension(170, 30));
              questBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
              questBtn.addActionListener(controller);
              
              questsPanel.add(questBtn);
           }
        
        CustomButton exitBtn = new CustomButton("Exit menu");
        exitBtn.buttonType = ButtonType.EXITMENU;
        exitBtn.setMaximumSize(new Dimension(170, 30));
        exitBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        exitBtn.addActionListener(controller);
        questsPanel.add(exitBtn);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(questsPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
          
        this.add(guiLayoutPanel);
        this.revalidate();
        this.repaint();
        
        this.currentMenu = "NpcQuests";
    }
    
    public void displayQuest(Quest quest)
    {
        this.guiLayoutPanel.removeAll();
        JPanel questPanel = new JPanel();
        questPanel.setLayout(new BoxLayout(questPanel, BoxLayout.Y_AXIS));
        questPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel questTitle = new JLabel(quest.getName());
        questTitle.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        questTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        questPanel.add(questTitle);
        
        JLabel questDescription = new JLabel("<html>" + quest.getDescription() + "</html>");
        questDescription.setMaximumSize(new Dimension(500, 100));
        questDescription.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        questPanel.add(questDescription);
        
        if(quest.isAccepted())
        {
           CustomButton alreadyBtn = new CustomButton("Accepted");
           alreadyBtn.setMaximumSize(new Dimension(170, 30));
           alreadyBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
           questPanel.add(alreadyBtn); 
        }
        else
        {
            CustomButton acceptBtn = new CustomButton("Accept", ButtonType.ACCEPTQUEST);
            acceptBtn.setMaximumSize(new Dimension(170, 30));
            acceptBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            acceptBtn.addActionListener(controller);
            questPanel.add(acceptBtn);  
        }

        
        CustomButton exitBtn = new CustomButton("Exit menu");
        exitBtn.buttonType = ButtonType.EXITMENU;
        exitBtn.setMaximumSize(new Dimension(170, 30));
        exitBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        exitBtn.addActionListener(controller);
        questPanel.add(exitBtn);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(questPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.add(guiLayoutPanel);
        this.revalidate();
        this.repaint();
        
        this.currentMenu = "CurrentQuest";
    }
   
    public void displayCombatNpcs(ArrayList<CombatNpc> npcList, String menuTitle)
    {
        this.guiLayoutPanel.removeAll();
        JPanel combatNpcPanel = new JPanel();
        combatNpcPanel.setLayout(new BoxLayout(combatNpcPanel, BoxLayout.Y_AXIS));
        combatNpcPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel menuName = new JLabel(menuTitle);
        menuName.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        menuName.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        combatNpcPanel.add(menuName);
        
        for(int i = 0; i < npcList.size() && i < 7; i++)
        {
            CombatNpc npc = npcList.get(i);
            CustomButton npcBtn = new CustomButton(npc.getName(), ButtonType.FIGHTENEMY, i);
              
            npcBtn.setMaximumSize(new Dimension(170, 30));
            npcBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            npcBtn.addActionListener(controller);
             
            combatNpcPanel.add(npcBtn);
        }
        
        CustomButton exitBtn = new CustomButton("Exit menu");
        exitBtn.buttonType = ButtonType.EXITMENU;
        exitBtn.setMaximumSize(new Dimension(170, 30));
        exitBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        exitBtn.addActionListener(controller);
        combatNpcPanel.add(exitBtn);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(combatNpcPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.add(guiLayoutPanel);
        this.revalidate();
        this.repaint();
        
        this.currentMenu = "DisplayEnemies";
    }
    
    public void initiateBattle(CombatNpc enemy, Player player)
    {
        this.guiLayoutPanel.removeAll();
        JPanel battlePanel = new JPanel();
        battlePanel.setLayout(new BoxLayout(battlePanel, BoxLayout.Y_AXIS));
        battlePanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel menuName = new JLabel("Battle");
        menuName.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        menuName.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        battlePanel.add(menuName);
        
        JLabel enemyStatus = new JLabel("| " + enemy.getName() + " | Hp: " + enemy.getHealth() + " |");
        enemyStatus.setMaximumSize(new Dimension(250, 75));
        enemyStatus.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        battlePanel.add(enemyStatus);
        
        /*this.battleTextPanel.setLayout(new BoxLayout(battleTextPanel, BoxLayout.Y_AXIS));
        battleTextPanel.setMaximumSize(new Dimension(150, 75));
        battleTextPanel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        battlePanel.add(this.battleTextPanel);*/
        
        JLabel playerStatus = new JLabel("| " + player.getName() + " | Hp: " + player.getCurrentHealth() + " |");
        playerStatus.setMaximumSize(new Dimension(250, 75));
        playerStatus.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        battlePanel.add(playerStatus);
        
        CustomButton attackBtn = new CustomButton("Attack", ButtonType.ATTACK);
        attackBtn.setMaximumSize(new Dimension(170, 30));
        attackBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        attackBtn.addActionListener(controller);
        battlePanel.add(attackBtn);
        
        CustomButton runBtn = new CustomButton("Run", ButtonType.RUN);
        runBtn.setMaximumSize(new Dimension(170, 30));
        runBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        runBtn.addActionListener(controller);
        battlePanel.add(runBtn);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(battlePanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.add(guiLayoutPanel);
        this.revalidate();
        this.repaint();
        
        this.currentMenu = "Battle";
    }
    
    public void battleRound(CombatNpc enemy, Player player, boolean runAttempt, int enemyDmg, int playerDmg)
    {
        this.guiLayoutPanel.removeAll();
        JPanel battlePanel = new JPanel();
        battlePanel.setLayout(new BoxLayout(battlePanel, BoxLayout.Y_AXIS));
        battlePanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel menuName = new JLabel("Battle");
        menuName.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        menuName.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        battlePanel.add(menuName);
        
        JLabel enemyStatus = new JLabel("| " + enemy.getName() + " | Hp: " + enemy.getHealth() + " |");
        enemyStatus.setMaximumSize(new Dimension(100, 75));
        enemyStatus.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        battlePanel.add(enemyStatus);
        
        if(runAttempt == true)
        {
            JLabel enemyAttackMsg = new JLabel("<html>The " + enemy.getName() + " attacks you and deals "
                                               + enemyDmg + " damage.</html>");
            enemyAttackMsg.setMaximumSize(new Dimension(150, 30));
            enemyAttackMsg.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            battlePanel.add(enemyAttackMsg);
            
            JLabel failedRunMsg = new JLabel("<html>You have failed to run away.</html>");
            failedRunMsg.setMaximumSize(new Dimension(150, 30));
            failedRunMsg.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            battlePanel.add(failedRunMsg);
        }
        else
        {
            JLabel enemyAttackMsg = new JLabel("<html>The " + enemy.getName() + " attacks you and deals "
                                               + enemyDmg + " damage.</html>");
            enemyAttackMsg.setMaximumSize(new Dimension(150, 30));
            enemyAttackMsg.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            battlePanel.add(enemyAttackMsg);
            
            JLabel playerAttackMsg = new JLabel("<html>You attack the " + enemy.getName() + " and deal "
                                               + playerDmg + " damage.</html>");
            playerAttackMsg.setMaximumSize(new Dimension(150, 30));
            playerAttackMsg.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            battlePanel.add(playerAttackMsg);
        }
        
        JLabel playerStatus = new JLabel("| " + player.getName() + " | Hp: " + player.getCurrentHealth() + " |");
        playerStatus.setMaximumSize(new Dimension(100, 75));
        playerStatus.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        battlePanel.add(playerStatus);
        
        CustomButton attackBtn = new CustomButton("Attack", ButtonType.ATTACK);
        attackBtn.setMaximumSize(new Dimension(170, 30));
        attackBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        attackBtn.addActionListener(controller);
        battlePanel.add(attackBtn);
        
        CustomButton runBtn = new CustomButton("Run", ButtonType.RUN);
        runBtn.setMaximumSize(new Dimension(170, 30));
        runBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        runBtn.addActionListener(controller);
        battlePanel.add(runBtn);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(battlePanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.revalidate();
        this.repaint();
        
        this.currentMenu = "Battle";
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
        Data data = (Data)arg;
        
        if(this.currentMenu.equals("LocationMain"))
        {
            if(data.getTravelDirections() != null)
            {
                this.displayDirectionsGUI(data.getTravelDirections());   
            }
            else if(data.getNonCombatNpcList() != null)
            {
                this.displayLocationNpcs(data.getNonCombatNpcList());
            }
            else if(data.getCombatNpcList() != null)
            {
                this.displayCombatNpcs(data.getCombatNpcList(), data.getMenuTitle());
            }
        }
        else if(this.currentMenu.equals("Directions"))
        {
            this.displayLocationGUI(data.getPlayerLocation());
        }
        else if(this.currentMenu.equals("LocationNpcs"))
        {
            this.displayNpcOptions(data.getNonCombatNpcOptionList(), data.getMenuTitle());
        }
        else if(this.currentMenu.equals("NpcOptions"))
        {
            this.displayNpcQuests(data.getQuestList(), data.getMenuTitle());
        }
        else if(this.currentMenu.equals("NpcQuests"))
        {
            this.displayQuest(data.getCurrentquest());
        }
        else if(this.currentMenu.equals("CurrentQuest"))
        {
            this.displayQuest(data.getCurrentquest());
        }
        else if(this.currentMenu.equals("DisplayEnemies"))
        {
            this.initiateBattle(data.getCurrentEnemy(), data.getPlayer());
        }
        else if(this.currentMenu.equals("Battle"))
        {
            if(data.getRunSuccess() == true)
            {
                this.displayLocationGUI(data.getPlayerLocation());
            }
            else
            {
                this.battleRound(data.getCurrentEnemy(), data.getPlayer(), data.isRunAttempt(),
                                 data.getEnemyDmg(), data.getPlayerDmg()); 
            }
        }
    }
}
