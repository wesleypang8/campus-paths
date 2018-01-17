package mGui;



import model.CampusGraph;
import model.MalformedDataException;

/**
 * initializes the gui which allows the client to find the shortest path between two buildings at UW
 * @author WP
 *
 */
public class CampusPathsMain {
    
    /**
     * main
     * @param args
     */
    public static void main(String[] args){
        CampusGraph model=null;
        try {
             model = new CampusGraph("campus_buildings.dat", "campus_paths.dat");
        } catch (MalformedDataException e) {
            e.printStackTrace();
        }
        
        CampusGUI campusGUI = new CampusGUI(model);
        
        
    }

}
