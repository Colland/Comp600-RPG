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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Tristan
 */
public class View extends JFrame implements Observer
{
    public Controller controller;
    public JPanel locationPanel = new JPanel();
    public JPanel directionsPanel = new JPanel();
    public JPanel guiLayoutPanel = new JPanel();
    public JTextField idInput;
    public JComboBox helmetEquip;
    public JComboBox plateLegEquip;
    public JComboBox breastPlateEquip;
    public JComboBox weaponEquip;
    
    private String currentMenu;
            
    public View(Controller controller)
    {
        this.controller = controller;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);
        guiLayoutPanel.setLayout(new BoxLayout(guiLayoutPanel, BoxLayout.X_AXIS));
        locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.Y_AXIS));
        locationPanel.setMaximumSize(new Dimension(600, 350));
        
        this.displayLoadPlayerGUI(false);
        this.currentMenu = "LoadPlayer";
        this.setVisible(true);
    }
    
    public void displayLoadPlayerGUI(boolean error)
    {
        this.guiLayoutPanel.removeAll();
        JPanel loadPanel = new JPanel();
        loadPanel.setLayout(new BoxLayout(loadPanel, BoxLayout.Y_AXIS));
        loadPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel loadTitle = new JLabel("Load a character?");
        loadTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        loadTitle.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        loadPanel.add(loadTitle);
        
        JLabel loadMsg1 = new JLabel("<html>Would you like to load a past character?"
                                 + " Enter the ID you got when you ended the game.</html>");
        loadMsg1.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        loadMsg1.setMaximumSize(new Dimension(300, 50));
        loadPanel.add(loadMsg1);
        
        idInput = new JTextField();
        idInput.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        idInput.setMaximumSize(new Dimension(150, 20));
        loadPanel.add(idInput);
        
        if(error == true)
        {
            JLabel loadMsg2 = new JLabel("Please enter a valid ID, or create a new character.");
            loadMsg2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            loadMsg2.setMaximumSize(new Dimension(300, 40));
            loadPanel.add(loadMsg2);   
        }
        else
        {
           JLabel loadMsg2 = new JLabel();
            loadMsg2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            loadMsg2.setMaximumSize(new Dimension(300, 20));
            loadPanel.add(loadMsg2); 
        }
        
        CustomButton loadBtn = new CustomButton("Load character", ButtonType.LOADCHARACTER);
        loadBtn.setMaximumSize(new Dimension(170, 30));
        loadBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        loadBtn.addActionListener(controller);
        loadPanel.add(loadBtn);
        
        CustomButton newBtn = new CustomButton("New character", ButtonType.NEWCHARACTER);
        newBtn.setMaximumSize(new Dimension(170, 30));
        newBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        newBtn.addActionListener(controller);
        loadPanel.add(newBtn);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(loadPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.add(guiLayoutPanel);
        this.revalidate();
        this.repaint();
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
          
          CustomButton exitGameBtn = new CustomButton("Save and exit game", ButtonType.EXITGAME);
          exitGameBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
          exitGameBtn.setMaximumSize(new Dimension(170, 30));
          exitGameBtn.addActionListener(controller);
          locationPanel.add(exitGameBtn);

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
        enemyStatus.setMaximumSize(new Dimension(275, 75));
        enemyStatus.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        battlePanel.add(enemyStatus);
        
        /*this.battleTextPanel.setLayout(new BoxLayout(battleTextPanel, BoxLayout.Y_AXIS));
        battleTextPanel.setMaximumSize(new Dimension(150, 75));
        battleTextPanel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        battlePanel.add(this.battleTextPanel);*/
        
        JLabel playerStatus = new JLabel("| " + player.getName() + " | Hp: " + player.getCurrentHealth() + " |");
        playerStatus.setMaximumSize(new Dimension(275, 75));
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
        enemyStatus.setMaximumSize(new Dimension(100, 90));
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
        playerStatus.setMaximumSize(new Dimension(100, 90));
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
    
    public void displayVictoryScreen(Data data)
    {
        CombatNpc enemy = data.getCurrentEnemy();
        Player player = data.getPlayer();
        
        this.guiLayoutPanel.removeAll();
        JPanel victoryPanel = new JPanel();
        victoryPanel.setLayout(new BoxLayout(victoryPanel, BoxLayout.Y_AXIS));
        victoryPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel victoryTitle = new JLabel("Victory!");
        victoryTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        victoryTitle.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        victoryPanel.add(victoryTitle);
        
        JLabel victoryMessage = new JLabel();
        victoryMessage.setText("<html> " + enemy.getDeathText() + " </html>");
        victoryMessage.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        victoryMessage.setMaximumSize(new Dimension(175, 50));
        victoryPanel.add(victoryMessage);
        
        JLabel victoryMessage2 = new JLabel();
        victoryMessage2.setText("<html> You have gained " + enemy.getXpReward() + " xp and " +
                                enemy.getGoldReward() + " gold." +  "</html>");
        victoryMessage2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        victoryMessage2.setMaximumSize(new Dimension(175, 50));
        victoryPanel.add(victoryMessage2);
        
        if(data.getLevelUp() == true)
        {
           JLabel victoryMessage3 = new JLabel();
            victoryMessage3.setText("<html>You leveled up! You are now level " + player.getLevel()
                                  + " and now have " + player.getMaxHealth() + " hp.</html>");
            victoryMessage3.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            victoryMessage3.setMaximumSize(new Dimension(175, 50));
            victoryPanel.add(victoryMessage3); 
        }
        
        CustomButton continueBtn = new CustomButton("Continue", ButtonType.EXITMENU);
        continueBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        continueBtn.addActionListener(controller);
        victoryPanel.add(continueBtn);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(victoryPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.revalidate();
        this.repaint();
    }
    
    public void displayDeathScreen()
    {
        this.guiLayoutPanel.removeAll();
        JPanel deathPanel = new JPanel();
        deathPanel.setLayout(new BoxLayout(deathPanel, BoxLayout.Y_AXIS));
        deathPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel deathTitle = new JLabel("Death");
        deathTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        deathTitle.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        deathPanel.add(deathTitle);
        
        JLabel deathMessage= new JLabel("<html>You died. You will respawn at town.</html>");
        deathMessage.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        deathMessage.setMaximumSize(new Dimension(175, 75));
        deathPanel.add(deathMessage);
        
        CustomButton continueBtn = new CustomButton("Respawn", ButtonType.EXITMENU);
        continueBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        continueBtn.addActionListener(controller);
        deathPanel.add(continueBtn);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(deathPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.revalidate();
        this.repaint();
    }
    
    public void displayInn(boolean innSuccess, Player player)
    {
        this.guiLayoutPanel.removeAll();
        JPanel innPanel = new JPanel();
        innPanel.setLayout(new BoxLayout(innPanel, BoxLayout.Y_AXIS));
        innPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel innTitle = new JLabel("Inn");
        innTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        innTitle.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        innPanel.add(innTitle);
        
        if(innSuccess == true)
        {
            JLabel innMsg1 = new JLabel("<html>You pay 5 gold to rest at the inn. You have " + player.getGold() + " gold left.</html>");
            innMsg1.setMaximumSize(new Dimension(200, 50));
            innMsg1.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            innPanel.add(innMsg1);
            
            JLabel innMsg2 = new JLabel("<html>You are fully rested and your health has been restored to " 
                                      + player.getCurrentHealth() + " health.</html>");
            innMsg2.setMaximumSize(new Dimension(200, 100));
            innMsg2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            innPanel.add(innMsg2);
        }
        else
        {
           JLabel innMsg1 = new JLabel("<html>You need 5 gold to stay at the inn. You only have " + player.getGold() + " gold.</html>");
           innMsg1.setMaximumSize(new Dimension(200, 50));
           innMsg1.setAlignmentX(JLabel.CENTER_ALIGNMENT);
           innPanel.add(innMsg1);
            
           JLabel innMsg2 = new JLabel("<html>You were not able to stay at the inn, you currently have " 
                                      + player.getCurrentHealth() + " health.</html>");
           innMsg2.setMaximumSize(new Dimension(200, 100));
           innMsg2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
           innPanel.add(innMsg2); 
        }
        
        CustomButton continueBtn = new CustomButton("Back to town", ButtonType.EXITMENU);
        continueBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        continueBtn.setMaximumSize(new Dimension(130, 40));
        continueBtn.addActionListener(controller);
        innPanel.add(continueBtn);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(innPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.revalidate();
        this.repaint();
    }
    
    public void showSaveScreen(String playerId)
    {
        this.guiLayoutPanel.removeAll();
        JPanel savePanel = new JPanel();
        savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.Y_AXIS));
        savePanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel saveTitle = new JLabel("Character saved");
        saveTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        saveTitle.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        savePanel.add(saveTitle);
        
        JLabel saveMsg1 = new JLabel("<html>Your character has been saved Here is your unique character Id:</html>");
        saveMsg1.setMaximumSize(new Dimension(200, 40));
        saveMsg1.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        savePanel.add(saveMsg1);
        
        JLabel saveMsg2 = new JLabel(playerId);
        saveMsg2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        savePanel.add(saveMsg2);
        
        JLabel saveMsg3 = new JLabel();
        saveMsg3.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        saveMsg3.setMaximumSize(new Dimension(150, 40));
        savePanel.add(saveMsg3);
        
        CustomButton exitBtn = new CustomButton("Exit game", ButtonType.CLOSEGAME);
        exitBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        exitBtn.setMaximumSize(new Dimension(130, 40));
        exitBtn.addActionListener(controller);
        savePanel.add(exitBtn);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(savePanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.revalidate();
        this.repaint();
    }
    
    public void showInventoryOptions()
    {
        this.guiLayoutPanel.removeAll();
        JPanel invPanel = new JPanel();
        invPanel.setLayout(new BoxLayout(invPanel, BoxLayout.Y_AXIS));
        invPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel invTitle = new JLabel("Inventory options");
        invTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        invTitle.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        invPanel.add(invTitle);
        
        /*CustomButton checkInvBtn = new CustomButton("Show all items", ButtonType.SHOWALLITEMS);
        checkInvBtn.setMaximumSize(new Dimension(170, 30));
        checkInvBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        checkInvBtn.addActionListener(controller);
        invPanel.add(checkInvBtn);*/
        
        CustomButton equipItemBtn = new CustomButton("Equip an item", ButtonType.SHOWEQUIPS);
        equipItemBtn.setMaximumSize(new Dimension(170, 30));
        equipItemBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        equipItemBtn.addActionListener(controller);
        invPanel.add(equipItemBtn);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(invPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.revalidate();
        this.repaint();
        
        this.currentMenu = "InventoryMenu";
    }
    
    public void showEquipMenu(Player player)
    {
        ArrayList<Item> inventory = player.getInventory();
        
        this.guiLayoutPanel.removeAll();
        JPanel equipsPanel = new JPanel();
        equipsPanel.setLayout(new BoxLayout(equipsPanel, BoxLayout.Y_AXIS));
        equipsPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel equipsTitle = new JLabel("Equips");
        equipsTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        equipsTitle.setFont(new Font("Century Gothic", Font.PLAIN, 28));
        equipsPanel.add(equipsTitle);
        
        JLabel helmetLabel = new JLabel("Equipped helmet: ");
        helmetLabel.setMaximumSize(new Dimension(200, 30));
        helmetLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        equipsPanel.add(helmetLabel);
        
        this.helmetEquip = new JComboBox();
        helmetEquip.setMaximumSize(new Dimension(150, 25));
        
        for(int i = 0; i < inventory.size(); i++)
        {
            Item currentItem = inventory.get(i);
            if(currentItem.getItemType() == ItemType.HELMET)
            {
                helmetEquip.addItem(new ComboItem(currentItem, currentItem.getName()));
            }
        }
        
        helmetEquip.setSelectedItem(player.getEquippedHelmet());
        equipsPanel.add(helmetEquip);
        
        JLabel breastPlateLabel = new JLabel("Equipped breastplate: ");
        breastPlateLabel.setMaximumSize(new Dimension(200, 30));
        breastPlateLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        equipsPanel.add(breastPlateLabel);
        
        this.breastPlateEquip = new JComboBox();
        breastPlateEquip.setMaximumSize(new Dimension(150, 25));
        
        for(int i = 0; i < inventory.size(); i++)
        {
            Item currentItem = inventory.get(i);
            if(currentItem.getItemType() == ItemType.BREASTPLATE)
            {
                breastPlateEquip.addItem(new ComboItem(currentItem, currentItem.getName()));
            }
        }
        
        breastPlateEquip.setSelectedItem(player.getEquippedBreastPlate());
        equipsPanel.add(breastPlateEquip);
        
        JLabel plateLegsLabel = new JLabel("Equipped platelegs: ");
        plateLegsLabel.setMaximumSize(new Dimension(200, 30));
        plateLegsLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        equipsPanel.add(plateLegsLabel);
        
        this.plateLegEquip = new JComboBox();
        plateLegEquip.setMaximumSize(new Dimension(150, 25));
        
        for(int i = 0; i < inventory.size(); i++)
        {
            Item currentItem = inventory.get(i);
            if(currentItem.getItemType() == ItemType.PLATELEGS)
            {
                plateLegEquip.addItem(new ComboItem(currentItem, currentItem.getName()));
            }
        }
        
        plateLegEquip.setSelectedItem(player.getEquippedPlateLegs());
        equipsPanel.add(plateLegEquip);
        
        JLabel weaponLabel = new JLabel("Equipped weapon: ");
        weaponLabel.setMaximumSize(new Dimension(200, 30));
        weaponLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        equipsPanel.add(weaponLabel);
        
        this.weaponEquip = new JComboBox();
        weaponEquip.setMaximumSize(new Dimension(150, 25));
        
        for(int i = 0; i < inventory.size(); i++)
        {
            Item currentItem = inventory.get(i);
            if(currentItem.getItemType() == ItemType.WEAPON)
            {
                weaponEquip.addItem(new ComboItem(currentItem, currentItem.getName()));
            }
        }
        
        weaponEquip.setSelectedItem(player.getEquippedWeapon());
        equipsPanel.add(weaponEquip);
        
        JPanel exitPanel = new JPanel();
        
        CustomButton exitButton = new CustomButton("Exit menu", ButtonType.EXITMENU);
        exitButton.setMaximumSize(new Dimension(150, 20));
        exitButton.addActionListener(controller);
        exitPanel.add(exitButton);
        
        JPanel exitPanel2 = new JPanel();
        
        CustomButton exitButton2 = new CustomButton("Equip items", ButtonType.EQUIPITEMS);
        exitButton2.setMaximumSize(new Dimension(150, 20));
        exitButton2.addActionListener(controller);
        exitPanel2.add(exitButton2);
        
        guiLayoutPanel.add(exitPanel);
        guiLayoutPanel.add(equipsPanel);
        guiLayoutPanel.add(exitPanel2);
        
        this.revalidate();
        this.repaint();
        
        this.currentMenu = "EquipsMenu";
    }
    
    public void displayShop()
    {
        this.guiLayoutPanel.removeAll();
        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));
        shopPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel shopTitle = new JLabel("Shop");
        shopTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        shopTitle.setFont(new Font("Century Gothic", Font.PLAIN, 28));
        shopPanel.add(shopTitle);
        
        CustomButton helmetBtn = new CustomButton("Helmets", ButtonType.DISPLAYSHOPHELMETS);
        helmetBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        helmetBtn.setMaximumSize(new Dimension(150, 30));
        helmetBtn.addActionListener(controller);
        shopPanel.add(helmetBtn);
        
        CustomButton breastplateBtn = new CustomButton("Breastplates", ButtonType.DISPLAYSHOPBREASTPLATES);
        breastplateBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        breastplateBtn.setMaximumSize(new Dimension(150, 30));
        breastplateBtn.addActionListener(controller);
        shopPanel.add(breastplateBtn);
        
        CustomButton platelegBtn = new CustomButton("Platelegs", ButtonType.DISPLAYSHOPPLATELEGS);
        platelegBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        platelegBtn.setMaximumSize(new Dimension(150, 30));
        platelegBtn.addActionListener(controller);
        shopPanel.add(platelegBtn);
        
        CustomButton weaponBtn = new CustomButton("Weapons", ButtonType.DISPLAYSHOPWEAPONS);
        weaponBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        weaponBtn.setMaximumSize(new Dimension(150, 30));
        weaponBtn.addActionListener(controller);
        shopPanel.add(weaponBtn);
        
        CustomButton exiBtn = new CustomButton("Exit shop", ButtonType.EXITMENU);
        exiBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        exiBtn.setMaximumSize(new Dimension(170, 30));
        exiBtn.addActionListener(controller);
        shopPanel.add(exiBtn);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(shopPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.revalidate();
        this.repaint();
        
        this.currentMenu = "ShopMenu";
    }
    
    public void displayShopItems(ArrayList<Item> items)
    {
        this.guiLayoutPanel.removeAll();
        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));
        shopPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel shopTitle = new JLabel("Items");
        shopTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        shopTitle.setFont(new Font("Century Gothic", Font.PLAIN, 28));
        shopPanel.add(shopTitle);
        
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new GridLayout(3, 3));
        shopPanel.add(itemsPanel);
        
        for(int i = 0; i < items.size(); i++)
        {
            CustomButton itemBtn = new CustomButton("<html>" + items.get(i).getName() + " Gold: "
                                                  + items.get(i).getGoldCost() + "</html>", ButtonType.BUYITEM, items.get(i));
            itemBtn.addActionListener(controller);
            itemsPanel.add(itemBtn);
        }
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(shopPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.revalidate();
        this.repaint();
        
        this.currentMenu = "ItemBuyMenu";
    }
    
    public void displayBuyScreen(boolean buySuccess, Player player)
    {
        this.guiLayoutPanel.removeAll();
        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));
        shopPanel.setMaximumSize(new Dimension(600, 300));
        
        JLabel shopTitle = new JLabel("Shop");
        shopTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        shopTitle.setFont(new Font("Century Gothic", Font.PLAIN, 28));
        shopPanel.add(shopTitle);
        
        if(buySuccess == true)
        {
            JLabel buyLabel = new JLabel("<html>Item bought. You have "  + player.getGold()
                              + " gold left. </html>");
            buyLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            buyLabel.setMaximumSize(new Dimension(175, 50));
            shopPanel.add(buyLabel);
        }
        else
        {
            JLabel buyLabel = new JLabel("<html>You dont have enough gold to buy the item. You have "  + player.getGold()
                              + " gold. </html>");
            buyLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            buyLabel.setMaximumSize(new Dimension(175, 50)); 
            shopPanel.add(buyLabel);
        }
        
        CustomButton exitBtn = new CustomButton("Exit", ButtonType.EXITMENU);
        exitBtn.addActionListener(controller);
        exitBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        exitBtn.setMaximumSize(new Dimension(175, 50)); 
        shopPanel.add(exitBtn);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(shopPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.revalidate();
        this.repaint();
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
            else if(data.getInnAttempt() == true)
            {
                this.displayInn(data.getInnSuccess(), data.getPlayer());
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
            else if(data.getEnemyDead() == true)
            {
                this.displayVictoryScreen(data);
            }
            else if(data.getPlayerDead() == true)
            {
                this.displayDeathScreen();
            }
            else
            {
                this.battleRound(data.getCurrentEnemy(), data.getPlayer(), data.isRunAttempt(),
                                 data.getEnemyDmg(), data.getPlayerDmg()); 
            }
        }
        else if(this.currentMenu.equals("InventoryMenu"))
        {
            this.showEquipMenu(data.getPlayer());
        }
        else if(this.currentMenu.equals("ShopMenu"))
        {
            this.displayShopItems(data.getShopItems());
        }
        else if(this.currentMenu.equals("ItemBuyMenu"))
        {
            this.displayBuyScreen(data.getItemBought(), data.getPlayer());
        }
    }
}
