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
public class Option 
{ 
    private String optionText;
    private ButtonType optionType;
    
    public Option(String optionText, ButtonType optionType)
    {
        this.optionText = optionText;
        this.optionType = optionType;
    }
    
    public String getOptionText()
    {
        return this.optionText;
    }
    
    public ButtonType getOptionType()
    {
        return this.optionType;
    }
}
