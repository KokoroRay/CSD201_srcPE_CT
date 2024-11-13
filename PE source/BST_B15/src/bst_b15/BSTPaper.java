/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bst_b15;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author 
 */
public class BSTPaper extends JPanel {

    public static final int NODE_RADIUS = 12;

    BSTree tree;
    int screenWidth;
    int yMin;

    Graphics2D g;

    public BSTPaper(BSTree tree, int screenWidth, int yMin) {
        this.tree = tree;
        this.screenWidth = screenWidth;
        this.yMin = yMin;
    }

    public void addNode(int data) {
        this.tree.addNode(data);
        repaint();
    }
    
    public void deleteNode(int data){
        this.tree.removeNode(data); 
        this.tree.path.clear();
        repaint();
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     */
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    public void drawPath() {
        ArrayList<BSTNode> path = this.tree.getPath();
        if (path != null) {
            //draw line
            BSTNode n1, n2;
            g.setColor(Color.red);
            for (int i = 1; i < path.size(); i++) {
                n1 = path.get(i - 1);
                n2 = path.get(i);
                g.drawLine(n1.getX(), n1.getY(), n2.getX(), n2.getY());
            }

            //draw node path
            int x, y;
            for (int i = 0; i < path.size(); i++) {
                n1 = path.get(i);
                x = n1.getX();
                y = n1.getY();

                //fill the node with new color
                g.setColor(Color.yellow);
                g.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

                //drawing outline with new color
                g.setColor(Color.red);
                g.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

                //display centered data inside the circle of the node 
                drawCenteredString(g, n1.getData() + "", new Rectangle(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2), new Font("Aril", Font.PLAIN, 10));

                //display count label of the node 
                drawCenteredString(g, "c=" + n1.getCount(), new Rectangle(x - NODE_RADIUS, y + NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2), new Font("Aril", Font.PLAIN, 10));

            }
        }
    }

    public void drawNode(BSTNode node) {
        if (node == null) {
            return;
        }

        int x = node.getX();
        int y = node.getY();

        g.setColor(Color.black);

        //draw left child link
        if (node.hasLeftChild()) {
            g.drawLine(x, y, node.getLeftChild().getX(), node.getLeftChild().getY());
        }

        //draw right child link
        if (node.hasRightChild()) {
            g.drawLine(x, y, node.getRightChild().getX(), node.getRightChild().getY());
        }

        //fill the node
        g.setColor(Color.white);
        g.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        //drawing outline
        g.setColor(Color.black);
        g.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        //display centered data inside the circle of the node 
        drawCenteredString(g, node.getData() + "", new Rectangle(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2), new Font("Aril", Font.PLAIN, 10));

        //display count label of the node 
        drawCenteredString(g, "c=" + node.getCount(), new Rectangle(x - NODE_RADIUS, y + NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2), new Font("Aril", Font.PLAIN, 10));

        drawNode(node.getLeftChild());
        drawNode(node.getRightChild());

    }

    @Override
    public void paint(Graphics graph) {
        super.paint(graph); //To change body of generated methods, choose Tools | Templates.

        this.g = (Graphics2D) graph;
        drawNode(this.tree.getRoot());

        drawPath();
    }

    public void clearTree() {
        this.tree.clearTree();
        repaint();
    }

    void blacing() {
        this.tree.balancing();
        repaint();
    }

}
