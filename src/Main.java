import javax.swing.JFrame;

/**
 * Main class with application method for the Fractal App.
 * @author lizzy
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FractalGenerator myFractals = new FractalGenerator();
        JFrame settings = new GUI(myFractals);
        settings.setVisible(true);
        JFrame display = new DisplayGUI(myFractals);
        display.setVisible(true);
    }
    
}
