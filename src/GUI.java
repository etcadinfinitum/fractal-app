import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.JColorChooser;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


/**
 * This class creates a JFrame component and desired subcomponents, as well as 
 * functioning as a Subject instance for the fractal display window.
 * @author lizzy
 */
public class GUI extends JFrame implements Subject {
    
    private Toolkit toolkit;
    private JFrame dataDisplay;
    private ArrayList<Observer> dataGUI; 
    private JButton data;
    
    private int recursionDepth = 4;
    private int relativeSize = 60;
    private Color currentMainColor = new Color(51, 204, 0);
    private Color currentFlowerColor = new Color(255, 0, 255);
    private int drawingSpeedFPS = 5;
    private boolean specifyRenderSpeed = false;
    private int thetaChange = 45;
    
    /**
     * Complex constructor which creates JFrame, subcomponents, and subcomponent 
     * ActionListeners.
     * @param myFractals The object which receives settings input and changes
     */
    public GUI(FractalGenerator myFractals) {
        
        dataGUI = new ArrayList<Observer>(1);
        
        setSize(400, 545);
        toolkit = getToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setLocation(50, 100);
        setResizable(false);
        setBackground(new Color(255, 255, 255));
        setTitle("Settings");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JButton save = new JButton("Save");
        save.setBounds(25, 470, 80, 30);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myFractals.setData(recursionDepth, relativeSize, currentMainColor, currentFlowerColor, drawingSpeedFPS, specifyRenderSpeed, thetaChange);
            }
        });
        
        JButton exit = new JButton("Exit");
        exit.setBounds(130, 470, 80, 30);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        JLabel recursionDepthLabel = new JLabel();
        recursionDepthLabel.setText("<html>" + "Number of fractal iterations:");
        recursionDepthLabel.setBounds(25, 15, 250, 50);
        
        Integer[] depthList = {2, 3, 4, 5, 6, 7, 8, 9, 10};
        JComboBox<Integer> recursionDepthPicker = new JComboBox<>(depthList);
        recursionDepthPicker.setBounds(300, 25, 80, 30);
        recursionDepthPicker.setSelectedItem(recursionDepth);
        recursionDepthPicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                recursionDepth = (Integer)recursionDepthPicker.getSelectedItem();
                notifyObservers();
            }
        });
        
        JLabel imageSizeLabel = new JLabel();
        imageSizeLabel.setText("<html>" + "Fractal child branch size (as % of parent size):");
        imageSizeLabel.setBounds(25, 80, 100, 50);
        
        JSlider imageSize = new JSlider(0, 40, 70, relativeSize);
        imageSize.setMajorTickSpacing(10);
        imageSize.setMinorTickSpacing(1);
        imageSize.setPaintTicks(true);
        imageSize.createStandardLabels(10);
        imageSize.setPaintLabels(true);
        imageSize.setSnapToTicks(true);
        imageSize.setBounds(150, 80, 230, 50);
        imageSize.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                relativeSize = imageSize.getValue();
                notifyObservers();
            }
        });
        
        JLabel mainColorLabel = new JLabel();
        mainColorLabel.setText("<html>" + "Set main color:");
        mainColorLabel.setBounds(25, 145, 100, 50);
        
        // some code to add color display
        JPanel mainColorDisplay = new MainColorDisplay();
        mainColorDisplay.setBackground(currentMainColor);
        mainColorDisplay.setBounds(340, 145, 40, 40);
        panel.add(mainColorDisplay);
        mainColorDisplay.setLayout(null);        
        
        // some code to add color picker
        JButton mainColor = new JButton();
        mainColor.setBounds(150, 145, 40, 40);
        mainColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JColorChooser clr = new JColorChooser();
                currentMainColor = clr.showDialog(panel, "Choose Color", Color.white);
                mainColorDisplay.setBackground(currentMainColor);
                notifyObservers();
            }
        });

        
        JLabel currentMainColorLabel = new JLabel();
        currentMainColorLabel.setText("<html>" + "Current selection:");
        currentMainColorLabel.setBounds(250, 145, 100, 50);        
        

        
        JLabel flowerColorLabel = new JLabel();
        flowerColorLabel.setText("<html>" + "Set flower color:");
        flowerColorLabel.setBounds(25, 210, 100, 50);
        
        // some code to add color display
        JPanel flowerColorDisplay = new FlowerColorDisplay();
        flowerColorDisplay.setBounds(340, 210, 40, 40);
        flowerColorDisplay.setBackground(currentFlowerColor);
        panel.add(flowerColorDisplay);
        flowerColorDisplay.setLayout(null);        
        
        // some code to add color picker
        JButton flowerColor = new JButton();
        flowerColor.setBounds(150, 210, 40, 40);
        flowerColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JColorChooser clr = new JColorChooser();
                currentFlowerColor = clr.showDialog(panel, "Choose Color", Color.white);
                flowerColorDisplay.setBackground(currentFlowerColor);
                notifyObservers();
            }
        });
        
        JLabel currentFlowerColorLabel = new JLabel();
        currentFlowerColorLabel.setText("<html>" + "Current selection:");
        currentFlowerColorLabel.setBounds(250, 210, 100, 50);

        
        JLabel drawingSpeedLabel = new JLabel();
        drawingSpeedLabel.setBounds(25, 405, 100, 50);
        drawingSpeedLabel.setText("<html>" + "Set drawing speed (FPS):");
        
        JSlider drawingSpeed = new JSlider(0, 0, 15, drawingSpeedFPS);
        if (!specifyRenderSpeed) {
            drawingSpeed.setEnabled(false);
        }
        drawingSpeed.setMajorTickSpacing(5);
        drawingSpeed.setPaintTicks(true);
        drawingSpeed.createStandardLabels(5);
        drawingSpeed.setPaintLabels(true);
        drawingSpeed.setBounds(150, 405, 230, 50);
        drawingSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!drawingSpeed.isEnabled()) {
                    toolkit.beep();
                } else {
                    drawingSpeedFPS = drawingSpeed.getValue();
                    notifyObservers();
                }
            }
        });
        
        JLabel setFPSLabel = new JLabel();
        setFPSLabel.setBounds(25, 340, 170, 50);
        setFPSLabel.setText("<html>" + "Drawing speed setting:");
        
        JToggleButton setFPS = new JToggleButton("Turn on", specifyRenderSpeed);
        setFPS.setBounds(230, 350, 150, 30);
        setFPS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (setFPS.getText().compareTo("Turn on") == 0) {
                    setFPS.setText("Turn off");
                    setFPS.setSelected(true);
                    specifyRenderSpeed = true;
                    drawingSpeed.setEnabled(true);
                    notifyObservers();
                } else {
                    setFPS.setText("Turn on");
                    setFPS.setSelected(false);
                    specifyRenderSpeed = false;
                    drawingSpeed.setEnabled(false);
                    notifyObservers();
                }
            }
        });
        
        JLabel angleLabel = new JLabel();
        angleLabel.setBounds(25, 275, 100, 50);
        angleLabel.setText("<html>" + "Angle offset:");
        
        JSlider angleSlider = new JSlider(0, 0, 90, thetaChange);
        angleSlider.setMajorTickSpacing(15);
        angleSlider.setPaintTicks(true);
        angleSlider.createStandardLabels(30);
        angleSlider.setPaintLabels(true);
        angleSlider.setBounds(150, 275, 230, 50);
        angleSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                thetaChange = angleSlider.getValue();
                notifyObservers();
            }
        });
        
        data = new JButton("<html>" + "Sample output");
        data.setBounds(235, 470, 140, 30);
        
        data.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!CurrentSelectionGUI.alreadyDisplayed) {
                    dataDisplay = createNewWindow();
                    dataDisplay.setVisible(true);
                    data.setEnabled(false);
                    notifyObservers();
                } else {
                    notifyObservers();
                }
            }
        });
        
        
        
        panel.add(recursionDepthLabel);
        panel.add(recursionDepthPicker);
        panel.add(imageSizeLabel);
        panel.add(imageSize);
        panel.add(mainColorLabel);
        panel.add(mainColor);
        panel.add(currentMainColorLabel);
        panel.add(flowerColorLabel);
        panel.add(flowerColor);
        panel.add(currentFlowerColorLabel);
        panel.add(drawingSpeedLabel);
        panel.add(drawingSpeed);
        panel.add(setFPSLabel);
        panel.add(setFPS);
        panel.add(angleLabel);
        panel.add(angleSlider);
        panel.add(save);
        panel.add(exit);
        panel.add(data);
    }
    
    /**
     * A helper method to create a Sample Output dialog and pass Subject instance 
     * reference for Observer Pattern implementation
     * @return A new instance of the Sample Output dialog
     */
    private CurrentSelectionGUI createNewWindow() {
        return new CurrentSelectionGUI(this);
    }

    /**
     * A required method override for the Subject interface which adds a  
     * JFrame instance as a new Observer of this object.
     * @param o  The Observer instance to be registered with Subject instance
     */
    @Override
    public void addObserver(Observer o) {
        dataGUI.add(o);
    }
    
    /**
     * A required method override for the Subject interface which removes an 
     * existing JFrame instance from the Subject's observer register.
     * @param o The Observer instance to be removed from Subject's register
     */    
    @Override
    public void removeObserver(Observer o) {
        dataGUI.remove(o);
    }
    
    /**
     * A required method override for the Subject interface which prompts all 
     * Observer instances associated with this Subject instance to execute 
     * instructions.
     */    
    @Override
    public void notifyObservers() {
        if (CurrentSelectionGUI.alreadyDisplayed) {
            data.setEnabled(false);
            setData(recursionDepth, relativeSize, currentMainColor, 
                    currentFlowerColor, drawingSpeedFPS, specifyRenderSpeed, thetaChange);
            ArrayList<DrawingObject> mySampleFractals = new ArrayList<>(3);        
            mySampleFractals.add(new DrawingObject(58, 58, 83, currentMainColor));
            mySampleFractals.add(new DrawingObject(
                    (int)Math.floor(58 +
                    ((1 + Math.cos(((90 + thetaChange      ) * Math.PI) / 180)) * (83 / 2)) - 
                    ((1 + Math.cos(((90 + thetaChange + 180) * Math.PI) / 180)) * (((83 * relativeSize) / 100) / 2))), 
                    (int)Math.floor(58 + 
                    ((1 - Math.sin(((90 + thetaChange      ) * Math.PI) / 180)) * (83 / 2)) -
                    ((1 - Math.sin(((90 + thetaChange + 180) * Math.PI) / 180)) * ((83 * relativeSize) / 100) / 2)), 
                    (int)Math.floor((83 * relativeSize) / 100), 
                    currentFlowerColor));
            mySampleFractals.add(new DrawingObject(
                    (int)Math.floor(58 +
                    ((1 + Math.cos(((90 - thetaChange      ) * Math.PI) / 180)) * (83 / 2)) -
                    ((1 + Math.cos(((90 - thetaChange + 180) * Math.PI) / 180)) * (((83 * relativeSize) / 100) / 2))), 
                    (int)Math.floor(58 + 
                    ((1 - Math.sin(((90 - thetaChange      ) * Math.PI) / 180)) * (83 / 2)) -
                    ((1 - Math.sin(((90 - thetaChange + 180) * Math.PI) / 180)) * ((83 * relativeSize) / 100) / 2)), 
                    (int)Math.floor((83 * relativeSize) / 100), 
                    currentFlowerColor));
            for (Observer observer : dataGUI) {
                observer.getUpdate(mySampleFractals);
            }
        } else {
            data.setEnabled(true);
        }
        
    }
    

    
    /**
     * A required method override for the Subject interface which receives fractal  
     * specifications.
     * @param recursionDepth The number of branches the fractal image has
     * @param relativeSize The ratio of the fractal image's children to parent 
     * @param currentMainColor The selected color for the fractal base
     * @param currentFlowerColor The selected color for the fractal leaf objects
     * @param drawingSpeedFPS The frame refresh rate for the drawing
     * @param specifyRenderSpeed The T/F status of generating image at a specified frame rate
     * @param thetaChange The angular offset of the fractal image's children to parent
     */
    @Override
    public void setData(int recursionDepth, int relativeSize, Color currentMainColor, 
            Color currentFlowerColor, int drawingSpeedFPS, boolean specifyRenderSpeed, int thetaChange) {
        CurrentSelectionGUI.drawingSpeed = drawingSpeedFPS;
        CurrentSelectionGUI.recursionDepth = recursionDepth;
        CurrentSelectionGUI.timeDrawing = specifyRenderSpeed;
    }    
    
    /**
     * Simple inner class which provides Graphics context for drawing on the 
     * created JPanel.
     */
    private class MainColorDisplay extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawRect(200, 165, 40, 40);
        }
    }
    
    /**
     * Simple inner class which provides Graphics context for drawing on the 
     * created JPanel.
     */
    private class FlowerColorDisplay extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawRect(200, 230, 40, 40);
        }
    }
    
}
