/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

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
    