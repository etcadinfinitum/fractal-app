
import java.util.ArrayList;


/**
 * This Observer interface serves to standardize one-half of the Observer Design 
 * Pattern implementation in the fractal app. 
 * @author lizzy
 */
public interface Observer {
    
    /**
     * A method called by a Subject instance for each of its related Observers
     * that prompts the Observer to execute a set of instructions, including 
     * accessing and manipulating DrawingObject instances in the argument array 
     * when the method is called.
     * @param myFractals The current set of objects to be drawn
     */
    public void getUpdate(ArrayList<DrawingObject> myFractals);
    
    
}
