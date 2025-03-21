import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

// Base class: Plate
abstract class Plate extends JPanel {
    private BufferedImage plateImage;
    protected String description;

    public Plate() {
        loadImage();
        description = "A fruit plate with a ";
    }

    protected void loadImage() {
        try {
            plateImage = ImageIO.read(new File("plate.png"));
            plateImage = resizeImage(plateImage, 300, 300); // Plate image set to max size
        } catch (IOException e) {
            System.out.println("Error: Could not load plate image.");
            e.printStackTrace();
        }
    }

    protected BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        if (originalImage == null) return null;
        Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (plateImage != null) {
            g.drawImage(plateImage, 0, 0, this);
        }
    }
}

// Plate with Banana
class PlateWithBanana extends Plate {
    private BufferedImage bananaImage;

    public PlateWithBanana() {
        super();
        loadImage();
        description += "banana, ";
    }

    @Override
    protected void loadImage() {
        super.loadImage();
        try {
            bananaImage = ImageIO.read(new File("Banana.png"));
            bananaImage = resizeImage(bananaImage, 100, 100);
        } catch (IOException e) {
            System.out.println("Error: Could not load banana image.");
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bananaImage != null) {
            g.drawImage(bananaImage, 0, 0, this);
        }
    }
}

// Plate with Banana and Orange
class PlateWithOrange extends PlateWithBanana {
    private BufferedImage orangeImage;

    public PlateWithOrange() {
        super();
        loadImage();
        description += "orange, ";
    }

    @Override
    protected void loadImage() {
        super.loadImage();
        try {
            orangeImage = ImageIO.read(new File("Orange.jpg"));
            orangeImage = resizeImage(orangeImage, 100, 100);
        } catch (IOException e) {
            System.out.println("Error: Could not load orange image.");
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (orangeImage != null) {
            g.drawImage(orangeImage, 0, 0, this);
        }
    }
}

// Plate with Banana, Orange, and Apple (Randomized Red or Green)
class PlateWithApple extends PlateWithOrange {
    private BufferedImage appleImage;
    private boolean isRedApple;

    public PlateWithApple() {
        super();
        Random rand = new Random();
        isRedApple = (rand.nextInt(2) == 0); // 50% chance for Red or Green
        System.out.println("Randomized Apple: " + (isRedApple ? "Red" : "Green"));
        loadImage();
        description += "and a " + (isRedApple ? "red" : "green") + " apple.";
    }

    @Override
    protected void loadImage() {
        super.loadImage();
        try {
            String appleFile = isRedApple ? "RedApple.png" : "GreenApple.png";
            System.out.println("Trying to load: " + appleFile);
            File file = new File(appleFile);
            if (!file.exists()) {
                System.out.println("Error: " + appleFile + " not found!");
            }
            appleImage = ImageIO.read(file);
            appleImage = resizeImage(appleImage, 100, 100);
        } catch (IOException e) {
            System.out.println("Error: Could not load apple image.");
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (appleImage != null) {
            g.drawImage(appleImage, 0, 0, this);
        }
    }
}

// Main GUI Class
public class FruitPlateGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Fruit Plate Builder");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            
            Plate finalPlate = new PlateWithApple();
            
            JLabel descriptionLabel = new JLabel(((PlateWithApple) finalPlate).description);
            descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            
            frame.setLayout(new BorderLayout());
            frame.add(finalPlate, BorderLayout.CENTER);
            frame.add(descriptionLabel, BorderLayout.SOUTH);
            
            frame.setVisible(true);
        });
    }
}
