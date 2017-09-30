/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wjholden;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author William John Holden (wjholden@gmail.com)
 */
public class PolishGraph extends GLJPanel implements GLEventListener, KeyListener {
    
    private final double[][] vertices;
    private float x, y, z, rx, ry, rz;
    
    public PolishGraph(String function, float xmin, float xmax, float ymin, float ymax, int segments) {
        super(new GLCapabilities(null));
        setPreferredSize(new Dimension(400, 400));
        addGLEventListener(this);
        addKeyListener(this);
        x = y = z = rx = ry = rz = 0.0f;
        vertices = Polish.plot3d(function, xmin, xmax, ymin, ymax, segments);
    }
    
    public static void main(String[] args) {
        String function = "+ ^ sin x 2 ^ cos y 2";
        JFrame window = new JFrame("3D plot of: " + function);
        PolishGraph panel = new PolishGraph(function, 0, 10, 0, 10, 25);
        window.setContentPane(panel);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        panel.requestFocusInWindow();
    }
    
    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();

        double[] first, last;
        first = vertices[0];
        last = vertices[vertices.length - 1];
        
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glFrustumf(-2, 2, -2, 2, -2, 2);
        
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glClearColor( 0, 0, 0, 1 );
        gl2.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // not used
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        
        gl2.glLoadIdentity();
        
        gl2.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        
        gl2.glTranslated(x, y, z);
        gl2.glScalef(0.15f, 0.15f, 0.15f);
        gl2.glRotated(rx, 1, 0, 0);
        gl2.glRotated(ry, 0, 1, 0);
        gl2.glRotated(rz, 0, 0, 1);
        
        int rows = (int) Math.sqrt(vertices.length);
        
        for (int row = 0 ; row < rows - 1; row++) {
            for (int column = 0 ; column < rows - 1; column++) {        
                gl2.glPolygonOffset(1, 1);
                
                gl2.glBegin(GL2.GL_TRIANGLE_FAN);
                gl2.glColor3f(0, 0, 1.0f);
                gl2.glVertex3dv(vertices[column + row * rows], 0);
                gl2.glVertex3dv(vertices[column + (row + 1) * rows], 0);
                gl2.glVertex3dv(vertices[column + (row + 1) * rows + 1], 0);
                gl2.glVertex3dv(vertices[column + row * rows + 1], 0);
                gl2.glEnd();
                
                gl2.glBegin(GL2.GL_LINE_LOOP);
                gl2.glColor3f(1, 1, 1.0f);
                gl2.glVertex3dv(vertices[column + row * rows], 0);
                gl2.glVertex3dv(vertices[column + (row + 1) * rows], 0);
                gl2.glVertex3dv(vertices[column + (row + 1) * rows + 1], 0);
                gl2.glVertex3dv(vertices[column + row * rows + 1], 0);
                gl2.glEnd();
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // not used
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                z += 0.1f;
                break;
            case KeyEvent.VK_DOWN:
                z -= 0.1f;
                break;
            case KeyEvent.VK_LEFT:
                x -= 0.1f;
                break;
            case KeyEvent.VK_RIGHT:
                x += 0.1f;
                break;
        }

        switch (e.getKeyChar()) {
            case 'w':
                rx += 5f;
                break;
            case 's':
                rx -= 5f;
                break;
            case 'a':
                rz -= 5f;
                break;
            case 'd':
                rz += 5f;
                break;
            case ' ':
                y += 0.1f;
                break;
            case 'c':
                y -= 0.1f;
                break;
            case 'r':
                x = y = z = rx = ry = rz = 0.0f;
                break;
            case 'q':
                System.exit(0);
                break;
        }

        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // do nothing
    }

}
