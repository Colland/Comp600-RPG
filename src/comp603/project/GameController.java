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
public class GameController
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {   
       World.populate();
       
       Model model = new Model();
       Controller controller = new Controller(model);
       model.addObserver(controller.view);  
    }
}
    