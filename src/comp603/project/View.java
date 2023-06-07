/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import javax.swing.JFrame;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Tristan
 */
public class View extends JFrame implements Observer
{
    private JPanel locationPanel = new JPanel();
    private JPanel guiLayoutPanel = new JPanel();
    private JLabel locationTitle = new JLabel("Town");
    private JButton locationBtn1 = new JButton("Travel somewhere else");
    private JButton locationBtn2 = new JButton("Look around for people");
    private JButton locationBtn3 = new JButton("Look around for shops");
    private JButton locationBtn4 = new JButton("Check your inventory");
    private JButton locationBtn5 = new JButton("Rest at an inn");
    private JButton locationBtn6 = new JButton("Save and exit game");
            
    public View()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);
        guiLayoutPanel.setLayout(new BoxLayout(guiLayoutPanel, BoxLayout.X_AXIS));
        
        locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.Y_AXIS));
        locationPanel.setMaximumSize(new Dimension(600, 300));
        locationTitle.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        locationTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        locationBtn1.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        locationBtn2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        locationBtn3.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        locationBtn4.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        locationBtn5.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        locationBtn6.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        
        locationBtn1.setMaximumSize(new Dimension(170, 30));
        locationBtn2.setMaximumSize(new Dimension(170, 30));
        locationBtn3.setMaximumSize(new Dimension(170, 30));
        locationBtn4.setMaximumSize(new Dimension(170, 30));
        locationBtn5.setMaximumSize(new Dimension(170, 30));
        locationBtn6.setMaximumSize(new Dimension(170, 30));
        
        
        locationPanel.add(locationTitle);
        locationPanel.add(locationBtn1);
        locationPanel.add(locationBtn2);
        locationPanel.add(locationBtn3);
        locationPanel.add(locationBtn4);
        locationPanel.add(locationBtn5);
        locationPanel.add(locationBtn6);
        
        guiLayoutPanel.add(Box.createHorizontalGlue());
        guiLayoutPanel.add(locationPanel);
        guiLayoutPanel.add(Box.createHorizontalGlue());
        this.add(guiLayoutPanel);
        this.setVisible(true);
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
        
    }
}
