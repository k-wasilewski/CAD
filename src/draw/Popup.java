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
public class Popup extends JPopupMenu {
    private JMenuItem snap;
    private JMenuItem snapToGrid;
    private JMenuItem grid;
    private gInput gi;
            
    public Popup() {
        if (Canvas.isSnapToGridMode()) snapToGrid = new JMenuItem("Snap-to-grid mode OFF");
        else snapToGrid = new JMenuItem("Snap-to-grid mode ON");
        if (Canvas.isSnapMode()) snap = new JMenuItem("Snap mode OFF");
        else snap = new JMenuItem("Snap mode ON");
        if (Canvas.isGridMode()) grid = new JMenuItem("Grid OFF");
        else grid = new JMenuItem("Grid ON");
        add(snap);
        add(grid);
        add(snapToGrid);
        snap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                snapActionPerformed(evt);
            }
        });
        grid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gridActionPerformed(evt);
            }
        });
        snapToGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                snapToGridActionPerformed(evt);
            }
        });
    }
    
    private void snapActionPerformed(java.awt.event.ActionEvent evt) {
        if (!Canvas.isSnapMode()) Canvas.snapModeOn();
        else {
            Canvas.snapModeOff();
        }
    }
    private void gridActionPerformed(java.awt.event.ActionEvent evt) {
        if (!Canvas.isGridMode()) {
            gi = new draw.gInput();
            gi.setLocation((int)200, (int)200);
            gi.setVisible(true);
        }
        else Canvas.gridModeOff();
    }
    private void snapToGridActionPerformed(java.awt.event.ActionEvent evt) {
        if (!draw.Canvas.isSnapToGridMode()) {
            draw.Canvas.snapToGridOn();
            if (draw.Canvas.getSRadded()==true) draw.Canvas.setSRfalse();
        }
        else {
            draw.Canvas.setSRfalse();
            draw.Canvas.snapToGridOff();
        }
    }
}
