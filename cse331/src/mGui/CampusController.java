package mGui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.CampusGraph;

/**
 * controller for gui, allows client to choose which path or reset tool
 * 
 * @author WP
 *
 */
public class CampusController extends JPanel {

    /**
     * action listener for find path and reset buttons
     * 
     * @author WP
     *
     */
    private class ButtonListener implements ActionListener {

        /**
         * activates on ActionEvents. Either tells view to find a path or resets
         * the view and controller
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            if (action.equals("Find Path"))
                view.findPath((String) start.getSelectedItem(), (String) end.getSelectedItem());
            else {
                view.reset();
                //reset controller
                start.setSelectedIndex(0);
                end.setSelectedIndex(0);
            }

        }

    }

    private static final long serialVersionUID = 1L;

    CampusGraph model;

    // view to interact with
    CampusView view;

    // combo boxes with buildings to select from
    JComboBox<String> start;
    JComboBox<String> end;

    /**
     * constructor of controller for gui
     * 
     * @param model
     *            the model for path finding
     * @param view
     *            the view to interact with
     * @effects constructs a CampusController
     * @throws IllegalArgumentException
     *             if args are null
     */
    public CampusController(CampusGraph model, CampusView view) {
        if (model == null || view == null) {
            throw new IllegalArgumentException();
        }
        this.model = model;
        this.view = view;

        this.setLayout(new FlowLayout());

        String[] buildings = model.getBuildings().keySet().toArray(new String[0]);

        // initialize and add components
        this.add(new JLabel("Choose Starting Building: "));
        this.add(start = new JComboBox<String>(buildings));
        this.add(new JLabel("Choose Ending Building: "));
        this.add(end = new JComboBox<String>(buildings));

        // initialize and add buttons w/ listeners
        JButton findPath = new JButton("Find Path");
        JButton reset = new JButton("Reset");
        findPath.addActionListener(new ButtonListener());
        reset.addActionListener(new ButtonListener());
        this.add(reset);
        this.add(findPath);
    }

}
