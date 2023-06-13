/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Tristan
 */
public class Controller implements ActionListener
{
    public View view;
    public Model model;
    
    public Controller(Model model)
    {
        this.model = model;
        this.view = new View(this);
    }
    
    //Catches the event from the view elements and parses them based on the ButtonType 
    //enum tied to them.
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        CustomButton eventBtn = (CustomButton)e.getSource();
        
       if (eventBtn.buttonType != null)
       {
            switch(eventBtn.buttonType)
            {
                case TRAVEL:
                    this.model.getAvailableDirections();
                    break;

                case LOOKFORSHOPS:
                    this.view.displayShop();
                    break;
                    
                case DISPLAYSHOPHELMETS:
                    this.model.displayShopItems(ItemType.HELMET);
                    break;
                  
                case DISPLAYSHOPBREASTPLATES:
                    this.model.displayShopItems(ItemType.BREASTPLATE);
                    break;
                    
                case DISPLAYSHOPPLATELEGS:
                    this.model.displayShopItems(ItemType.PLATELEGS);
                    break;
                    
                case DISPLAYSHOPWEAPONS:
                    this.model.displayShopItems(ItemType.WEAPON);
                    break;
                    
                case BUYITEM:
                    this.model.buyItem(eventBtn.boughtItem);
                    break;
                    
                case CHECKINVENTORY:
                    this.view.showInventoryOptions();
                    break;
                    
                case SHOWEQUIPS:
                    this.model.showInventory();
                    break;
                    
                case EQUIPITEMS:
                    ComboItem helmet = (ComboItem)this.view.helmetEquip.getSelectedItem();
                    ComboItem breastplate = (ComboItem)this.view.breastPlateEquip.getSelectedItem();
                    ComboItem platelegs = (ComboItem)this.view.plateLegEquip.getSelectedItem();
                    ComboItem weapon = (ComboItem)this.view.weaponEquip.getSelectedItem();
                    
                    this.model.equipItems(helmet, breastplate, platelegs, weapon);
                    break;
                    
                case DIRECTIONAL:
                    this.model.movePlayer(eventBtn.getText());
                    break;
                    
                case EXITMENU:
                    this.view.displayLocationGUI(World.getPlayer().getCurrentLocation());
                    break;

                case LOOKFORNPCS:
                    this.model.getCurrentLocationNPCs();
                    break;
                    
                case TALKTONPC:
                    this.model.getNpcOptions(eventBtn.buttonNumber);
                    break;
                    
                case REQUESTQUEST:
                    this.model.getNpcQuests();
                    break;
                    
                case SHOWQUEST:
                    this.model.getQuest(eventBtn.buttonNumber);
                    break;
                    
                case ACCEPTQUEST:
                    this.model.acceptQuest();
                    break;
                    
                case LOOKFORENEMIES:
                    this.model.displayLocationEnemies();
                    break;
                    
                case FIGHTENEMY:
                    this.model.initiateBattle(eventBtn.buttonNumber);
                    break;
                    
                case ATTACK:
                    this.model.attackRound();
                    break;
                    
                case RUN:
                    this.model.runRound();
                    break;
                    
                case RESTOREHEALTH:
                    this.model.restorePlayerHealth();
                    break;
                    
                case EXITGAME:
                    this.model.saveCharacter();
                    view.showSaveScreen(World.getPlayer().getId());
                    break;
                    
                case LOADCHARACTER:
                    String userInput = this.view.idInput.getText();
                    
                    //If user inputs nothing or an ID that returns null from the database,
                    //display the page again with an error code(true).
                    
                    if(userInput.equals(""))
                    {
                        this.view.displayLoadPlayerGUI(true);
                    }
                    else
                    {
                        Player loadedPlayer = this.model.db.loadPlayerData(String.valueOf(userInput));
                        if(loadedPlayer == null)
                        {
                           this.view.displayLoadPlayerGUI(true); 
                        }
                        else
                        {
                           World.setPlayer(loadedPlayer);
                           this.model.player = loadedPlayer;
                           this.view.displayLocationGUI(loadedPlayer.getCurrentLocation()); 
                        }
                    }
                    break;
                    
                case NEWCHARACTER:
                    this.model.generateNewCharacter();
                    this.view.displayLocationGUI(World.getPlayer().getCurrentLocation());
                    break;
                    
                case CLOSEGAME:
                    System.exit(0);
                    break;
            }   
       } 
    }
}
