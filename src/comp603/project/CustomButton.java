/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import javax.swing.JButton;

/**
 *
 * @author Tristan
 */
public class CustomButton extends JButton
{
    public ButtonType buttonType;
    public int buttonNumber;
    public Item boughtItem;
    
    CustomButton(String text)
    {
        super(text);
    }
    
    CustomButton(String text, ButtonType buttonType)
    {
        super(text);
        this.buttonType = buttonType;
    }
    
    CustomButton(String text, ButtonType buttonType, int buttonNumber)
    {
        super(text);
        this.buttonType = buttonType;
        this.buttonNumber = buttonNumber;
    }
    
    CustomButton(String text, ButtonType buttonType, Item item)
    {
        super(text);
        this.buttonType = buttonType;
        this.boughtItem = item;
    }
}
