package hr.fer.zemris.test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Paths;

/**
 * @author Filip Gulan
 */
public class TestPaint extends JFrame {

    public TestPaint() {
        super();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(900, 600);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                System.out.println("Crtam");
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                try {
                    g.drawImage(ImageIO.read(Paths.get("/Users/Filip/Pictures/Picture1.png").toFile()), 0, 0, this);
                } catch (Exception e) {

                }
            }
        };
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new TestPaint();
            frame.setVisible(true);
        });
    }
}
