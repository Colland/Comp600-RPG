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
    
    public ArrayList<Option> getOptionList()
    {
        return this.optionList;
    }
    
    public ArrayList<Quest> getQuestList()
    {
        return this.questList;
    }
}
