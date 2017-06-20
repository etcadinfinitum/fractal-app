import java.util.ArrayList;
import java.awt.Color;
import java.lang.Math;

/**
 * The class responsible for recursively plotting drawing components and managing
 * Observer instances.
 * @author lizzy
 */
public class FractalGenerator implements Subject {
    
    private ArrayList<DrawingObject> myItems;
    private ArrayList<Observer> observers;
    private Color mainColor;
    private Color flowerColor;
    private double myRelativeSize;
    private static boolean specifyRenderSpeed;
    private static int drawingSpeedFPS;
    private int thetaChange;
    
    /**
     * A simple constructor to initialize ArrayList objects.
     */
    public FractalGenerator() {
        myItems = new ArrayList<DrawingObject>();
        observers = new ArrayList<Observer>();
    }
    
    /**
     * A required method override for the Subject interface which adds a  
     * JFrame instance as a new Observer of this object.
     * @param o The Observer instance to be registered with Subject instance
     */
    public void addObserver(Observer o) {
        observers.add(o);
    }
    
    /**
     * A required method override for the Subject interface which removes an 
     * existing JFrame instance from the Subject's observer register
     * @param o The Observer instance to be removed from Subject's register
     */
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    
    /**
     * A required method override for the Subject interface which prompts all 
     * Observer instances associated with this Subject instance to execute 
     * instructions.
     */
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.getUpdate(myItems);
        }
    }
    
    /**
     * A required method override for the Subject interface which receives fractal  
     * specifications and triggers the fractal generation process.
     * @param recursionDepth The number of branches the fractal image has
     * @param relativeSize The ratio of the fractal image's children to parent 
     * @param currentMainColor The selected color for the fractal base
     * @param currentFlowerColor The selected color for the fractal leaf objects
     * @param drawingSpeedFPS The frame refresh rate for the drawing
     * @param specifyRenderSpeed The T/F status of generating image at a specified frame rate
     * @param thetaChange The angular offset of the fractal image's children to parent
     */
    public void setData(int recursionDepth, int relativeSize, Color currentMainColor, 
            Color currentFlowerColor, int drawingSpeedFPS, boolean specifyRenderSpeed, int thetaChange) {
        if (!myItems.isEmpty()) {
            myItems.clear();
        }
        myRelativeSize = (double)relativeSize/100.0;
        mainColor = currentMainColor;
        flowerColor = currentFlowerColor;
        this.specifyRenderSpeed = specifyRenderSpeed;
        this.drawingSpeedFPS = drawingSpeedFPS;
        this.thetaChange = thetaChange;
        if (drawingSpeedFPS == 0 && specifyRenderSpeed) {
            makeNextSurprise(DisplayGUI.windowWidth(), DisplayGUI.windowHeight() - 100, recursionDepth);
        } else {
            makeNextFractal((DisplayGUI.windowWidth() - 100) / 2, DisplayGUI.windowHeight() - 250,recursionDepth, 100);
        }
        notifyObservers();
    }
    
    /**
     * A public method which initializes the fractal generation process.
     * @param startX The x-coordinate of the parent instance in the container
     * @param startY The y-coordinate of the parent instance in the container
     * @param recursionDepth The number of branches the fractal image has yet to generate
     * @param objSize The diameter of the fractal image parent instance
     */
    public void makeNextFractal(int startX, int startY, int recursionDepth, 
            int objSize) {
        makeNextFractal(startX, startY, recursionDepth, objSize, 90);
    }
    
    /**
     * A recursive helper method which creates new fractal component parent (DrawingObject 
     * instances) with the (x, y) coordinate location, size and Color needed, and 
     * recursively does the same for the parent instance's children (if any).
     * @param startX The x-coordinate of the parent instance in the container
     * @param startY The y-coordinate of the parent instance in the container
     * @param recursionDepth The number of branches the fractal image has yet to generate
     * @param objSize The diameter of the fractal image parent instance
     * @param theta 
     */
    private void makeNextFractal(int currX, int currY, int recursionDepth, 
            int objSize, int theta) {
        int newSize = (int)Math.ceil(objSize * myRelativeSize);
        int leftX = (int)Math.floor(currX +
                ((1 + Math.cos(((theta + thetaChange      ) * Math.PI) / 180)) * (objSize / 2)) -
                ((1 + Math.cos(((theta + thetaChange + 180) * Math.PI) / 180)) * (newSize / 2)));
        int leftY = (int)Math.floor(currY + 
                ((1 - Math.sin(((theta + thetaChange      ) * Math.PI) / 180)) * (objSize / 2)) -
                ((1 - Math.sin(((theta + thetaChange + 180) * Math.PI) / 180)) * (newSize / 2)));
        int rightX = (int)Math.floor(currX +
                ((1 + Math.cos(((theta - thetaChange      ) * Math.PI) / 180)) * (objSize / 2)) -
                ((1 + Math.cos(((theta - thetaChange + 180) * Math.PI) / 180)) * (newSize / 2)));
        int rightY = (int)Math.floor(currY + 
                ((1 - Math.sin(((theta - thetaChange      ) * Math.PI) / 180)) * (objSize / 2)) -
                ((1 - Math.sin(((theta - thetaChange + 180) * Math.PI) / 180)) * (newSize / 2)));
        
        if (recursionDepth < 0 || objSize < 1) {
            
        } else {
            if (recursionDepth == 0) {
                myItems.add(new DrawingObject(currX, currY, objSize, flowerColor));
            } else {
                myItems.add(new DrawingObject(currX, currY, objSize, mainColor));
            }
            makeNextFractal(leftX, leftY, recursionDepth - 1, newSize, theta + thetaChange);
            makeNextFractal(rightX, rightY, recursionDepth - 1, newSize, theta - thetaChange);
        }
    }
    
    /**
     * Simple accessor method to establish whether the drawing should be rendered at 
     * a specific frame-per-second rate.
     * @return The T/F value representing whether the drawing will be 
     */
    public static boolean specifyRenderSpeed() {
        return specifyRenderSpeed;
    }
    
    /**
     * Simple accessor method to retrieve the specified frame-per-second rendering rate.
     * @return The desired number of frames per second
     */
    public static int getDrawingSpeed() {
        return drawingSpeedFPS;
    }
    
    /**
     * A public method which initializes the fractal generation process.
     * @param frameWidth The current width of the fractal display JFrame
     * @param frameHeight The current height of the fractal display JFrame
     * @param recursionDepth The number of branches the fractal image has yet to generate 
     */
    public void makeNextSurprise(int frameWidth, int frameHeight, int recursionDepth) {
        if (frameWidth > frameHeight) {
            int startX = (int)Math.floor((frameWidth - frameHeight) / 2);
            int startY = 0;
            makeNextSurprise(recursionDepth, 1, frameHeight, startX, startY);
        } else {
            int startX = 0;
            int startY = (int)Math.floor((frameHeight - frameWidth) / 2);
            makeNextSurprise(recursionDepth, 1, frameWidth, startX, startY);            
        }
    }
    
    /**
     * A recursive helper method which creates new fractal component parent (DrawingObject 
     * instances) with the (x, y) coordinate location, size and Color needed, and 
     * recursively does the same for the parent instance's children (if any).
     * @param recursionDepth The number of fractal branches yet to be generated
     * @param currentDepth The number of fractal branches already generated
     * @param frameSize The width of the image's "canvas"
     * @param startX The x-coordinate of the image's "canvas" (relative to supercomponent)
     * @param startY The y-coordinate of the image's "canvas" (relative to supercomponent)
     */
    private void makeNextSurprise(int recursionDepth, int currentDepth, int frameSize, int startX, int startY) {
        if (recursionDepth < 0 || frameSize / Math.pow(3.0, currentDepth) < 1) {
            myItems.add(new DrawingObject(startX, startY, frameSize, mainColor));
        } else {
            int childSize = (int)Math.floor(frameSize / Math.pow(3.0, currentDepth));
            for (int row = 1; row < Math.pow(3.0, currentDepth); row += 3) {
                for (int col = 1; col < Math.pow(3.0, currentDepth); col += 3) {
                    myItems.add(new DrawingObject(startX + (int)Math.floor(row * frameSize / Math.pow(3.0, currentDepth)),
                            startY + (int)Math.floor(col * frameSize / Math.pow(3.0, currentDepth)),
                            childSize, flowerColor));
                }
            }
            makeNextSurprise(recursionDepth - 1, currentDepth + 1, frameSize, startX, startY);
        }
    }
    
}
