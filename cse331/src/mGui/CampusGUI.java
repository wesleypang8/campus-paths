package mGui;

import javax.swing.*;

import model.CampusGraph;

import java.awt.*;

/**
 * Gui w/ view and controller for pathfinding at UW. Can fit and usable on a screen with 1024 x 768 resolution, but also resizable.
 * @author WP
 *
 */
public class CampusGUI {
    
    private JFrame frame;
    
    public CampusGUI(CampusGraph model){
        
        //initialize frame
        frame = new JFrame("UW Path Finder");
        frame.setPreferredSize(new Dimension(1024,768));    
        frame.setLayout(new BoxLayout(frame.getContentPane(),
                BoxLayout.Y_AXIS));
        
        //intialize and add view
        CampusView view = new CampusView(model);
        frame.add(view);

        //intialize and add controller
        CampusController control = new CampusController(model, view);
        frame.add(control);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        
        
    }

}
