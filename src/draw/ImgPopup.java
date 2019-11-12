/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw;
import javax.swing.*;

/**
 *
 * @author lenovo
 */
public class ImgPopup extends JPopupMenu {
    private JMenuItem front;
    private JMenuItem back;
    private ImageClass imageClass;
    private Canvas canvas;
    
    public ImgPopup(Canvas c) {
        canvas = c;  
        
        front = new JMenuItem("Move image to front");
        back = new JMenuItem("Move image to back");
        add(front);
        add(back);
        front.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frontActionPerformed(evt);
            }
        });
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
    }
    
    private void frontActionPerformed(java.awt.event.ActionEvent evt) {
        canvas.removeImageClass(imageClass);    
        canvas.addImageClass(imageClass);   
        imageClass.backOff();
        canvas.popupOff();
        canvas.repaint();
    }
    
    private void backActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("back");
        canvas.removeImageClass(imageClass);
        canvas.addImageClass(0, imageClass);
        imageClass.backOn();
        canvas.popupOff();
        canvas.repaint();
    }
    public void setImageClass(ImageClass i) {
        this.imageClass=i;
    }
}
