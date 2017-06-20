import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * This simple class generates a JPanel component with the specified size, 
 * position, shape and fill color.
 * @author lizzy
 */
public class DrawingObject {
    private int xCor;
    private int yCor;
    private int width;
    private int height;
    private Color color;
    
    /**
     * Simple constructor which assigns values to local variables.
     * @param xCor The x-coordinate of the component's bounding box (relative to supercomponent)
     * @param yCor The y-coordinate of the component's bounding box (relative to supercomponent)
     * @param size The size of the component's bounding box (in pixels)
     * @param color The fill color of the component
     */
    public DrawingObject(int xCor, int yCor, int size, Color color) {
        this.xCor = xCor;
        this.yCor = yCor;
        this.width = size;
        this.height = size;
        this.color = color;
    }
    
    /**
     * This method creates the JPanel object for the class instance and adds the
     * new JPanel to the parent panel (supercomponent).
     * @param currentPanel The supercomponent to be painted on
     */
    public void draw(JPanel currentPanel) {
        JPanel thisItem = new PaintMe();
        thisItem.setBounds(xCor, yCor, width, height);
        thisItem.setOpaque(false);
        thisItem.setLayout(null);
        currentPanel.add(thisItem);
    }
    
    /**
     * Simple inner class which provides Graphics context for drawing on the 
     * created JPanel.
     */
    private class PaintMe extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(color);
            if (FractalGenerator.getDrawingSpeed() == 0 && FractalGenerator.specifyRenderSpeed()) {
                g.fillRect(0, 0, width, height);
            } else {
                g.fillOval(0, 0, width, height);
            }
        }
    }
    
    /**
     * A toString override (useful for debugging).
     * @return A string representation of this class instance's properties
     */
    @Override
    public String toString() {
        String info = "";
        info += "size: " + width + " at coordinates (" + xCor + "," + yCor + "), color: " + color;
        return info;
    }
    
}
