package ui;

import draw.Canvas;

import javax.swing.*;

//----------RIGHT MOUSEBUTTON MENU----------------------------------------------------------------------------
@SuppressWarnings({"SpellCheckingInspection", "FieldCanBeLocal", "CanBeFinal", "unused"})
public class Popup extends JPopupMenu {
    private JMenuItem snap;
    private JMenuItem snapToGrid;
    private JMenuItem grid;
    private GridsizeInput gi;
            
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
        snap.addActionListener(this::snapActionPerformed);
        grid.addActionListener(this::gridActionPerformed);
        snapToGrid.addActionListener(this::snapToGridActionPerformed);
    }
    
    private void snapActionPerformed(java.awt.event.ActionEvent evt) {
        if (!Canvas.isSnapMode()) Canvas.snapModeOn();
        else {
            Canvas.snapModeOff();
        }
    }
    private void gridActionPerformed(java.awt.event.ActionEvent evt) {
        if (!Canvas.isGridMode()) {
            gi = new GridsizeInput();
            gi.setLocation((int)200, (int)200);
            gi.setVisible(true);
        }
        else Canvas.gridModeOff();
    }
    private void snapToGridActionPerformed(java.awt.event.ActionEvent evt) {
        if (!Canvas.isSnapToGridMode()) {
            Canvas.snapToGridOn();
            if (Canvas.getSRadded()) Canvas.setSRfalse();
        }
        else {
            Canvas.setSRfalse();
            Canvas.snapToGridOff();
        }
    }
}
