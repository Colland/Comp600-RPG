/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Tristan
 */
public class NonCombatNpc extends Npc
{
    private ArrayList<Option> optionList = new ArrayList<>();
    private ArrayList<Quest> questList = new ArrayList<>();
    private ArrayList<Item> itemList = new ArrayList<>();
    
    public NonCombatNpc(String name)
    {
        super(name);
    }
    
    public void addOption(Option option)
    {
        this.optionList.add(option);
    }
    
    public void addQuest(Quest quest)
    {
        this.questList.add(quest);
    }
    
    public void addItem(Item item)
    {
        this.itemList.add(item);
    }
    
    /*Displays any options the npc may have. Prompts user for input and calls the
    corresponding function for the option chosen.*/
    public void displayOptions()
    {
        boolean flag = true;
       do
       {
            System.out.println(this.getName());
            System.out.println("----------");
            
            int i;
            for(i = 0; i < optionList.size(); i++)
            {
                System.out.println((i+1) + ". " + optionList.get(i).getOptionText());
            }
            System.out.println((i+1) + ". Exit menu");

            Scanner scan = new Scanner(System.in);
            try
            {
                int userInput = scan.nextInt();

                if(userInput == i+1)
                {
                    flag = false;
                }
                else if((userInput > i+1) || (userInput <= 0))
                {
                    System.out.println("Please input a valid number");
                }
                else
                {
                    OptionType userOptionChosen = optionList.get(userInput-1).getOptionType();

                    switch(userOptionChosen)
                    {
                        case REQUESTQUEST:
                            this.displayAvailableQuests();
                    }
                }
            }
            catch(InputMismatchException e)
            {
                System.out.println("Please enter a number");
            }
            
       }while(flag);
    }
    
    /*Displays all quests the npc may have and prompts to choose one. Once chosen
    the quest is displayed and the user is prompted to acccept or decline the quest*/    
    public void displayAvailableQuests()
    {
        boolean questDisplayFlag = true;      
        do
        {
            System.out.println("\nQuests");
            System.out.println("------");
            int i;
            
            //Displays quests
            for(i = 0; i < questList.size(); i++)
            {
                Quest currentQuest = questList.get(i);
                
                String bufferString = ((i+1) + ". | " + currentQuest.getName() +
                        " | Gold: " + currentQuest.getGoldReward() + " | XP: " +
                        currentQuest.getXpReward() + " |");
                
                if(currentQuest.isAccepted())
                {
                    bufferString += " In progress |";
                }
                System.out.println(bufferString);
            }
            System.out.println((i+1) + ". Exit menu");

            Scanner scan = new Scanner(System.in);
            //Prompts input
            try
            {
                int userInput = scan.nextInt();
                //Flush buffer
                scan.nextLine();

                if(userInput == i+1)
                {
                    questDisplayFlag = false;
                }
                else if((userInput > i+1) || (userInput <= 0))
                {
                    System.out.println("Please input a valid number");
                }
                else if(questList.get(userInput-1).isCompleted())
                {
                    questList.get(userInput-1).complete();
                    questList.remove(userInput-1);
                }
                else if(questList.get(userInput-1).isAccepted())
                {
                    System.out.println("You have already accepted this quest.");
                }
                //Displays selected quest
                else
                {
                    Quest selectedQuest = questList.get(userInput-1);
                    selectedQuest.displayQuest();
                    System.out.println("");

                    boolean questAcceptanceFlag = true;
                    //Prompts user to accept or decline quest.
                    do
                    {
                        System.out.println("Accept?(Y)   Decline?(N)");
                        String userQuestInput = scan.nextLine().trim();
                        
                        switch(userQuestInput)
                        {
                            case "Y":
                            case "y":
                                System.out.println("Quest accepted!\n");
                                selectedQuest.acceptQuest();
                                World.getPlayer().addAcceptedQuest(selectedQuest);
                                questAcceptanceFlag = false;
                            break;

                            case "N":
                            case "n":
                                System.out.println("Quest declined.\n");
                                questAcceptanceFlag = false;
                                break;

                            default:
                                System.out.println("Please input Y or N");
                                questAcceptanceFlag = true;
                        }
                    }while(questAcceptanceFlag);
                }
            }catch(InputMismatchException e)
            {
                System.out.println("Please enter a number");
            }
        }while(questDisplayFlag);
    }
    
    public void displayItems()
    {
        boolean flag = true;
        do
        {
            System.out.println("\nItems");
            System.out.println("-----");

            int i;
            for(i = 0; i < this.itemList.size(); i++)
            {
                System.out.println((i+1) + ". " + this.itemList.get(i).getName() + " | Cost: "
                + (this.itemList.get(i).getLevelReq()*10) + " gold");
            }
            System.out.println((i+1) + ". Exit menu");   
            
            Scanner scan = new Scanner(System.in);
            
            try
            {
                int userInput = scan.nextInt();
                
                if(userInput == (i+1))
                {
                    flag = false;
                }
                else if(userInput <= 0 || userInput > i+1)
                {
                    System.out.println("Please input a valid number");
                }
                else
                {
                     Player player = World.getPlayer();
                 
                    if(player.buyItem(this.itemList.get(userInput-1),
                            this.itemList.get(userInput-1).getLevelReq()*10));
                    {
                        this.itemList.remove(userInput-1);
                    }
                }
            }
            catch(InputMismatchException e)
            {
                System.out.println("Please only input numbers");
            }
        }while(flag);
    }
}
