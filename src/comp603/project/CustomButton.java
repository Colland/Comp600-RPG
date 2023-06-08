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
    
    CustomButton(String text)
    {
        super(text);
    }
    
    CustomButton(String text, ButtonType buttonType)
    {
        super(text);
        this.buttonType = buttonType;
    }
}
