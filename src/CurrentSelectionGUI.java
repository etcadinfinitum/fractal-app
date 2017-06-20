import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * This class instantiates a window to display a sample output of the fractal 
 * image and receives subsequent updates if data changes.
 * @author lizzy
 */
public class CurrentSelectionGUI extends JFrame implements Observer {
    
    public static boolean alreadyDisplayed = false;
    private JPanel canvas;
    private JPanel labelPanel;
    private JLabel recursionLabel;
    private JLabel speedLabel;
    private JButton close;
    public static int recursionDepth;
    public static boolean timeDrawing;
    public static int drawingSpeed;
    
    /**
     * Complex constructor to create a JFrame instance, 
     * register the JFrame as an Observer in the settings 
     * interface class, and otherwise prepare the JFrame 
     * for displaying updates as needed.
     * @param settings The Subject class instance this object is observing
     */
    public CurrentSelectionGUI(Subject settings) {
        alreadyDisplayed = true;
        Toolkit toolkit = getToolkit();
        setSize(500,250);
        Dimension screenSize = new Dimension(toolkit.getScreenSize());
        setLocation(500, screenSize.height - 350);
        setResizable(false);
        setTitle("Sample Output");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        
        labelPanel = new JPanel();
        labelPanel.setLayout(null);
        getContentPane().add(labelPanel);        
        
        recursionLabel = new JLabel();
        recursionLabel.setBounds(25, 25, 225, 75);
        labelPanel.add(recursionLabel);
        
        speedLabel = new JLabel();
        speedLabel.setBounds(25, 100, 200, 50);
        labelPanel.add(speedLabel);        

        
        canvas = new JPanel();
        canvas.setLayout(null);
        canvas.setBounds(275, 25, 200, 200);
        labelPanel.add(canvas);
    
        
        
        settings.addObserver(this);
        
        close = new JButton("Close");
        close.setBounds(87, 175, 100, 50);
        labelPanel.add(close);
        close.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                close(settings);
            }
        });
    }
    
    /**
     * Helper method which removes this JFrame 
     * from the Subject registry, updates the JButton 
     * which triggers the instantiation of this class,
     * and closes the window.
     * @param settings The Subject class instance this object is observing
     */
    private void close(Subject settings) {
        alreadyDisplayed = false;
        settings.removeObserver(this);
        settings.notifyObservers();
        this.dispose();
    }

    /**
     * A required method override for the Observer interface. This method clears 
     * any existing fractal components from the drawing JPanel and draws new 
     * components, as well as updating JLabels with other pertinent data.
     * @param mySample The current set of objects to be drawn
     */
    public void getUpdate(ArrayList<DrawingObject> mySample) {
        canvas.removeAll();

        recursionLabel.setText("<html>" + "You will see " + recursionDepth + 
                " levels of branches and 1 level of flowers.");
        if (timeDrawing) {
            speedLabel.setText("<html>" + "Timing enabled. Current FPS setting: " + drawingSpeed);
        } else {
            speedLabel.setText("<html>" + "Timing is not enabled.");
        }
        for (int i = 0; i < mySample.size(); i++) {
            mySample.get(i).draw(canvas);
        }
        canvas.paintImmediately(0, 0, 200, 200);
        labelPanel.repaint();
    }


    
}
