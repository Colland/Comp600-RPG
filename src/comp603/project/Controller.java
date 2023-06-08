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
                    //Model checks available positions
                    break;

                case LOOKFORSHOPS:
                    //Model updates
                    break;
                    
                case DIRECTIONAL:
                    this.model.movePlayer(eventBtn.getText());
                    break;
                    
                case EXITMENU:
                    view.displayLocationGUI(World.getPlayer().getCurrentLocation());
                    break;
            }   
       }
        
        System.out.println(e.getSource());
        System.out.println(command);
        
    }
}
