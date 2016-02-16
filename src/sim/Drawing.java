package sim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

//derived from Oracle's Java tutorials and StackOverflow

public class Drawing extends JPanel {
    private int mCellWidth = 3; 	// pixels per cell
    private int mCellHeight = 3;

    private int mRows;				// max rows and cols we should draw
    private int mCols;

    JFrame mFrame;

    public Drawing (int rows, int cols) {

        super ();

        mRows = rows;
        mCols = cols;

        //Create and set up the drawing area
        mFrame = new JFrame("Drawing");
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        setOpaque(true); //content panes must be opaque
        mFrame.setContentPane(this);

        //Display the window, make large enough to hold grid
        mFrame.setSize(new Dimension(mCols * mCellWidth + 20, mRows * mCellHeight + 50));
//	        frame.pack();
        mFrame.setLocation(100, 100);
        mFrame.setVisible(true);

        // Listen for mouse clicks
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final Point pos = e.getPoint();
                final int x = pos.x;
                final int y = pos.y;
                int row = y / mCellHeight;
                int col = x / mCellWidth;
                System.out.println(String.format("Click at %d, %d", row, col));
                Main.simulation.onClick(row, col);
                Main.mDrawing.mFrame.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
            }
            @Override
            public void mouseExited(MouseEvent arg0) {
            }
            @Override
            public void mousePressed(MouseEvent arg0) {
            }
            @Override
            public void mouseReleased(MouseEvent arg0) {
            }
        });
    }

    private int where = 0;

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int x = 0; x < Main.simulation.maxcolumns; ++x) {
            for (int y = 0; y < Main.simulation.maxrows; ++y) {

                g2.setColor(Main.simulation.getColor(x, y));
                g2.fillRect(mCellWidth * x, mCellHeight * y, mCellWidth, mCellHeight);
            }
        }

        Sensor s = Main.simulation.getSensor ();
        int x = s.getX();
        int y = s.getY();
        g2.setColor(s.getColor(x, y));
        g2.fillRect(mCellWidth * x, mCellHeight * y, mCellWidth, mCellHeight);
    }
}
