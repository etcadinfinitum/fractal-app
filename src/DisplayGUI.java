import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;


/**
 * The DisplayGUI class provides the on-screen visual display window for the 
 * fractal image.
 * @author lizzy
 */
public class DisplayGUI extends JFrame implements Observer {
    
    private Toolkit toolkit;
    private JPanel drawing;
    private JLabel graphicsStatus;
    private static int width;
    private static int height;
    
    /**
     * Complex constructor to create a JFrame instance, 
     * register the JFrame as an Observer in the fractal 
     * generator class, and otherwise prepare the JFrame 
     * for displaying items as needed.
     * @param myFractals The Subject class instance this object is observing
     */
    public DisplayGUI(Subject myFractals) {
        toolkit = getToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setLocation(500, 0);
        setSize(screenSize.width - 500, screenSize.height - 50);
        setTitle("Fractal Drawing");
        setDefaultCloseOperation(0);
        drawing = new JPanel();
        getContentPane().add(drawing);
        drawing.setLayout(null);
        width = drawing.getWidth();
        height = drawing.getHeight();
        myFractals.addObserver(this);
        
        graphicsStatus = new JLabel();
        graphicsStatus.setText("<html>" + "No fractal images have been drawn yet.");
        graphicsStatus.setBounds(25, this.getHeight() - 75, this.getWidth() - 50, 50);
        
        drawing.add(graphicsStatus);
        
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                drawing.repaint();
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                drawing.repaint();
            }
            @Override
            public void componentResized(ComponentEvent e) {
                width = drawing.getWidth();
                height = drawing.getHeight();

            }

        });
    }
    
    /**
     * A required method override for the Observer interface. This method clears 
     * any existing components from the drawing JPanel and draws new components.
     * @param myFractals The current set of objects to be drawn
     */
    @Override
    public void getUpdate(ArrayList<DrawingObject> myFractals) {
        drawing.removeAll();
        graphicsStatus.setBounds(25, height - 75, width - 50, 50);
        graphicsStatus.setText("Working...");
        drawing.add(graphicsStatus);
        drawing.paintImmediately(0,0,height,width);
        Rectangle dims = new Rectangle(drawing.getX(), drawing.getY(), drawing.getWidth(), drawing.getHeight());
        if (FractalGenerator.getDrawingSpeed() == 0 && FractalGenerator.specifyRenderSpeed()) {
            for (int i = 0; i < myFractals.size(); i++) {
                myFractals.get(i).draw(drawing);
                drawing.repaint();
            }
            graphicsStatus.setText("<html>" + "Congratulations! You found the Easter "
                    + "Egg and made ol' Sierpinsky proud. \nThis is what happens "
                    + "when you divide by zero. :)");
        } else {
            if (FractalGenerator.specifyRenderSpeed()) {
                long beginning = System.currentTimeMillis();
                for (int i = 0; i < myFractals.size(); i++) {
                    long startTime = System.currentTimeMillis();
                    myFractals.get(i).draw(drawing);
                    graphicsStatus.setText("<html>" + "Fractal generating. " + 
                            Math.round(100 * (i + 1) / myFractals.size()) + 
                            "% complete.");
                    drawing.paintImmediately(dims);
                    while (System.currentTimeMillis() - startTime < (1000 / FractalGenerator.getDrawingSpeed())) {
                        // wait
                    }
                }
                long end = System.currentTimeMillis();
                graphicsStatus.setText("<html>" + "Fractal drawing successfully rendered. Total "
                        + "elapsed time: " + (int)Math.floor(((end - beginning) / 1000) / 60)
                        + " minuntes " + (int)Math.ceil((end - beginning) / 1000) % 60 + " seconds");
            } else {
                long beginning = System.currentTimeMillis();
                for (int i = 0; i < myFractals.size(); i++) {
                    myFractals.get(i).draw(drawing);
                }
                drawing.repaint();
                long end = System.currentTimeMillis();
                graphicsStatus.setText("<html>" + "Fractal drawing successfully rendered. Total "
                        + "elapsed time (milliseconds): " + Math.round(end - beginning));
            }
        }
    }
    
    /**
     * Simple accessor method for the current width of this JFrame instance.
     * @return The GUI window's width in pixels
     */
    public static int windowWidth() {
        return width;
    }
    
    /**
     * Simple accessor method for the current height of this JFrame instance.
     * @return The GUI window's height in pixels
     */
    public static int windowHeight() {
        return height;
    }
    


}