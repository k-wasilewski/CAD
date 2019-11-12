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
    private JMenuItem grid;
            
    public Popup() {
        if (Canvas.isSnapMode()) snap = new JMenuItem("Snap mode OFF");
        else snap = new JMenuItem("Snap mode ON");
        if (Canvas.isGridMode()) grid = new JMenuItem("Grid OFF");
        else grid = new JMenuItem("Grid ON");
        add(snap);
        add(grid);
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
    }
    
    private void snapActionPerformed(java.awt.event.ActionEvent evt) {
        if (!Canvas.isSnapMode()) Canvas.snapModeOn();
        else Canvas.snapModeOff();
    }
    private void gridActionPerformed(java.awt.event.ActionEvent evt) {
        if (!Canvas.isGridMode()) Canvas.gridModeOn();
        else Canvas.gridModeOff();
    }
}
