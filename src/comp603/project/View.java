/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JFrame;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Tristan
 */
public class View extends JFrame implements Observer
{
    private Controller controller;
    private JPanel locationPanel = new JPanel();
    private JPanel directionsPanel = new JPanel();
    private JPanel guiLayoutPanel = new JPanel();
    private JLabel locationTitle = new JLabel("Town");
    private JButton locationBtn1 = new JButton("Travel somewhere else");
    private JButton locationBtn2 = new JButton("Look around for people");
    private JButton locationBtn3 = new JButton("Look around for shops");
    private JButton locationBtn4 = new JButton("Check your inventory");
    private JButton locationBtn5 = new JButton("Rest at an inn");
    private JButton locationBtn6 = new JButton("Save and exit game");
    
    private String currentMenu;
            
    public View(Controller controller)
    {
        this.controller = controller;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);
        guiLayoutPanel.setLayout(new BoxLayout(guiLayoutPanel, BoxLayout.X_AXIS));
        locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.Y_AXIS));
        locationPanel.setMaximumSize(new Dimension(600, 300));
        
        this.displayLocationGUI();
        this.currentMenu = "LocationMain";
        this.setVisible(true);
    }
    
    public void displayLocationGUI()
    {
        this.guiLayoutPanel.removeAll();
        this.locationPanel.removeAll();
        //Model updates and sends a data packet, containing info on what screen, what location etc.
        Location location = World.getPlayer().getCurrentLocation();
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
            locationPanel.add(optionBtn);
        }
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(locationPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        
        this.add(guiLayoutPanel);
        this.revalidate();
        this.repaint();
        
        this.currentMenu = "LocationMain";
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
    
    @Override
    public void update(Observable o, Object arg)
    {
        Data data = (Data)arg;
        
        if(this.currentMenu.equals("LocationMain"))
        {
            displayDirectionsGUI(data.getTravelDirections());
        }
        else if(this.currentMenu.equals("Directions"))
        {
            displayLocationGUI(data.getPlayerLocation());
        }
    }
}
