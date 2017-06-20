import java.awt.Color;

/**
 * This Subject interface serves to standardize one-half of the Observer Design 
 * Pattern implementation in the fractal app. 
 * @author lizzy
 */
public interface Subject {
    
    /**
     * Method which registers a new observer with the Subject instance.
     * @param o The observer object to be added
     */
    public void addObserver(Observer o);
    
    /**
     * Method which removes a current observer from the Subject instance's 
     * Observer register.
     * @param o The observer object to be removed
     */
    public void removeObserver(Observer o);
    
    /**
     * Method which communicates to each observer that a spcecific set of 
     * instructions must be executed. Implementation of this method generally
     * includes a call to the getUpdate() method for the observer.
     */
    public void notifyObservers();
    
    /**
     * Method which prompts receiving Subject instance to initialize specific 
     * values needed to communicate with observers.
     * @param recursionDepth The number of branches the fractal image has
     * @param relativeSize The ratio of the fractal image's children to parent 
     * @param currentMainColor The selected color for the fractal base
     * @param currentFlowerColor The selected color for the fractal leaf objects
     * @param drawingSpeedFPS The frame refresh rate for the drawing
     * @param specifyRenderSpeed The T/F status of generating image at a specified frame rate
     * @param thetaChange The angular offset of the fractal image's children to parent
     */
    public void setData(int recursionDepth, int relativeSize, Color currentMainColor, 
            Color currentFlowerColor, int drawingSpeedFPS, boolean specifyRenderSpeed, int thetaChange);

    
}
