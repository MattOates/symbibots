package ui;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel with a background image.
 * @author Matt Oates
 * @version 18/04/2005.
 */
public class ImagePanel extends JPanel {

    private static final long serialVersionUID = 5953450291451461294L;

    /**
     * The image to be displayed in the image
     */
    private ImageIcon image;
    
    private boolean scaled = true;

    /**
     * Default Constructor used by XML encoder etc.
     * Required to be a Java Bean.
     */
    public ImagePanel() {
        super();
        image = null;
    }


    /**
     * One of JPanel's constructor with the image URL added.
     * @param url
     */
    public ImagePanel(String url) {
        super();
        image = new ImageIcon(url);
    }
    
    /**
     * One of JPanel's constructors with the image URL and scaled option added.
     * @param url URL or path to image.
     * @param scaled Should the image scale with the resizing of the Panel.
     */
    public ImagePanel(String url, boolean scaled) {
        super();
        image = new ImageIcon(url);
        this.scaled = scaled;
    }
    
    /**
     * One of JPanel's constructors with the image URL added.
     * @param url
     * @param isDoubleBuffered
     */
    public ImagePanel(String url, boolean scaled, boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        this.scaled = scaled;
        image = new ImageIcon(url);
    }


    /**
     * One of JPanel's constructors with the image URL added.
     * @param url
     * @param scaled
     * @param layout
     */
    public ImagePanel(String url, boolean scaled, LayoutManager layout) {
        super(layout);
        this.scaled = scaled;
        image = new ImageIcon(url);
    }

    /**
     * One of JPanel's constructor with the image URL added.
     * @param url
     * @param scaled
     * @param layout
     * @param isDoubleBuffered
     */
    public ImagePanel(String url, boolean scaled, LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        this.scaled = scaled;
        image = new ImageIcon(url);
    }

    /**
     * Used to paint the image before all the other components etc.
     * @param g Graphics context drawing in.
     */
    public void paintComponent(Graphics g) {
    	setForeground(getBackground());
    	g.fillRect(0, 0, getWidth(), getHeight());
        if (image != null) {
               
            if (scaled) {
            	//Scale image to size of component
            	Dimension d = getSize();
            	g.drawImage(image.getImage(), 0, 0, d.width, d.height, null);
            }
            else {
            	//Dispaly image at full size no skew/stretch
                g.drawImage(image.getImage(), 0, 0, null);
            }
            
            // Needed to prevent odd transparancy problems.
            setOpaque(false);
        }
        // Do all the normal Panel drawing
        super.paintComponent(g);
    }
    
    /**
     * Used with pack and swing to makesure the image is displayed.
     */
    public Dimension getPreferredSize() {
        if (image != null) return new Dimension(image.getIconWidth(), image.getIconHeight());
        return super.getPreferredSize();
    }
    
    /**
     * Used with pack and swing to makesure the image is displayed.
     */
    public Dimension getMinimumSize() {
        if (image != null) return new Dimension(image.getIconWidth(), image.getIconHeight());
        return super.getMinimumSize();
    }

    /**
     * Get the image from the image.
     * @return Returns the image.
     */
    public ImageIcon getImage() {
        return image;
    }
    /**
     * Set the image image.
     * @param image The image to set.
     */
    public void setImage(ImageIcon image) {
        this.image = image;
    }


	/**
	 * @return the scaled
	 */
	public boolean isScaled() {
		return scaled;
	}


	/**
	 * @param scaled the scaled to set
	 */
	public void setScaled(boolean scaled) {
		this.scaled = scaled;
	}
}