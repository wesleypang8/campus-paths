package mGui;

import javax.imageio.ImageIO;
import javax.swing.*;

import graphStructure.Edge;
import model.CampusGraph;
import model.PointD;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * view for gui, displays the campus map and paths and buildings
 * @author WP
 *
 */
public class CampusView extends JComponent {

    private static final long serialVersionUID = 1L;
    
    //radius of marker for start/end buildings
    private static final int CIRCLE_RAD = 5;

    private CampusGraph model;

    private BufferedImage img;

    private List<Edge<PointD, Double>> path;

    private String start;


    /**
     * constructor of view for gui
     * @param model the model for path finding
     * @effects constructs a CampusView
     * @throws IllegalArgumentException if model is null
     */
    public CampusView(CampusGraph model) {
        if(model==null){
            throw new IllegalArgumentException();
        }
        this.model = model;
        img = this.getImage("./src/model/data/campus_map.jpg");
        
        //make component big enough to match the image
        this.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
    }

    /**
     * finds path between two buildings then updates the view
     * @param start start building
     * @param end end building
     * @modifies this.path
     * @modifies this.start
     * @effects sets path to the path between the two buildings
     * @effects sets start to the start of path
     * @throws IllegalArgumentException if args are null
     */
    public void findPath(String start, String end) {
        if(start==null || end==null){
            throw new IllegalArgumentException();
        }
        this.start = start;
        path = model.findPath(start, end);
        //update view
        repaint();
    }

    /**
     * resets and updates the view for gui
     * @modifies this.path
     * @modifies this.start
     * @effects sets path to null
     * @effects sets start to null
     */
    public void reset() {
        start = null;
        path = null;
        //update view
        repaint();
    }

    /**
     * returns the image corresponding to input
     * @param s name/relative path of image
     * @return the image as a BufferedImage
     * @throws IllegalArgumentException if arg is null
     */
    private BufferedImage getImage(String s) {
        if(s==null){
            throw new IllegalArgumentException();
        }
        try {
            return ImageIO.read(new File(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * paints the components
     * @param g graphics for painting
     * @modifies the view displayed
     * @effects draws the image and path/markers if a path is chosen.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g;

        //scales image pixels to size of view so locations match
        double wRatio = (this.getWidth() + 0.0) / img.getWidth();
        double hRatio = (this.getHeight() + 0.0) / img.getHeight();

        //draws map
        graphics.drawImage(img, 0, 0, getWidth(), getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);
        //if path has been chosen
        if (path != null) {
            PointD startLoc = model.getLocation(start);
            
            g.setColor(Color.BLUE);
            //mark start
            g.fillOval((int)(startLoc.getX()*wRatio-CIRCLE_RAD),(int)(startLoc.getY()*hRatio-CIRCLE_RAD),2*CIRCLE_RAD,2*CIRCLE_RAD);

            //draw paths
            for (Edge<PointD, Double> e : path) {
                PointD end = e.getEnd();
                g.drawLine((int) (startLoc.getX() * wRatio), (int) (startLoc.getY() * hRatio), (int) (end.getX() * wRatio),
                        (int) (end.getY() * hRatio));
                startLoc=end;
            }

            //mark end
            g.setColor(Color.RED);
            g.fillOval((int)(startLoc.getX()*wRatio-CIRCLE_RAD),(int)(startLoc.getY()*hRatio-CIRCLE_RAD),2*CIRCLE_RAD,2*CIRCLE_RAD);
        } 
    }

}
