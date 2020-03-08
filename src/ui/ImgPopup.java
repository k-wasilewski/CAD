package ui;

import draw.Canvas;
import objs.ImageClass;

import javax.swing.*;

//----------------MOVE IMG TO FRONT/BACK MENU----------------------------------------------------------------
@SuppressWarnings({"FieldCanBeLocal", "CanBeFinal", "unused"})
public class ImgPopup extends JPopupMenu {
    private JMenuItem front;
    private JMenuItem back;
    private ImageClass imageClass;
    private Canvas canvas;
    
    public ImgPopup(Canvas c) {
        canvas = c;  
        
        front = new JMenuItem("Moving image to front");
        back = new JMenuItem("Moving image to back");
        add(front);
        add(back);
        front.addActionListener(this::frontActionPerformed);
        back.addActionListener(this::backActionPerformed);
    }
    
    private void frontActionPerformed(java.awt.event.ActionEvent evt) {
        canvas.removeImageClass(imageClass);    
        canvas.addImageClass(imageClass);   
        imageClass.backOff();
        canvas.popupOff();
        canvas.repaint();
    }
    
    private void backActionPerformed(java.awt.event.ActionEvent evt) {
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
