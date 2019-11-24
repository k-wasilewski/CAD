package draw;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Draw extends JFrame {
  private Canvas canvas;
  private static boolean unknown;
  private static javax.swing.JTextArea jTextArea3;
  private boolean export;
  private javax.swing.JMenuItem menuItem4;
  private ArrayList<ImageClass> toDelete;
  private ArrayList<ImageClass> imageClasses;
  private static Index index;

  public void run(JFrame f) {
      SwingUtilities.invokeLater(new Runnable() {
          public void run() {f.setVisible(true);}
      });
  }
  public Draw() {
        canvas = new Canvas();
        javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jTextArea3.setColumns(20);
        jTextArea3.setRows(1);
        jScrollPane3.setViewportView(jTextArea3);
        javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu help = new javax.swing.JMenu();
        javax.swing.JMenu file = new javax.swing.JMenu();
        javax.swing.JMenuItem menuItem1 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem menuItem2 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem menuItem3 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem menuItem5 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem menuItem6 = new javax.swing.JMenuItem();
        menuItem4 = new javax.swing.JMenuItem();
        index = new Index();
        About about = new About();
        javax.swing.JFileChooser jFileChooser1 = new javax.swing.JFileChooser();
        javax.swing.JFileChooser jFileChooser2 = new javax.swing.JFileChooser();
        javax.swing.JFileChooser jFileChooser3 = new javax.swing.JFileChooser();

        file.setText("File");
        menuBar.add(file);
        help.setText("Help");
        menuBar.add(help);
        setJMenuBar(menuBar);
        menuItem1.setText("Index of commands");
        help.add(menuItem1);
        menuItem2.setText("About app");
        help.add(menuItem2);
        menuItem3.setText("Save");
        file.add(menuItem3);
        menuItem6.setText("Open");
        file.add(menuItem6);
        menuItem4.setText("Export");
        file.add(menuItem4);
        menuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
                java.awt.event.InputEvent.CTRL_MASK));
        menuItem5.setText("Import");
        file.add(menuItem5);

        menuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem1ActionPerformed(evt);
            }

            private void menuItem1ActionPerformed(ActionEvent evt) {
                index.setVisible(true);
            }
        });
        menuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    menuItem2ActionPerformed(evt);
                } catch (Exception e) {jTextArea3.setText("Error");}
            }

            private void menuItem2ActionPerformed(ActionEvent evt) throws Exception {
                about.setVisible(true);
            }
        });

        menuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int button = jFileChooser1.showSaveDialog(Draw.this);
                if (button == jFileChooser1.APPROVE_OPTION) {
                    canvas.setFilename(jFileChooser1.getSelectedFile().getName());
                    canvas.setDir(jFileChooser1.getCurrentDirectory().toString());
                }
                try {
                    menuItem3ActionPerformed(evt);
                } catch (Exception exc) {jTextArea3.setText(exc.toString());}
            }
            //SAVE FILE (ONLY LINES)
            private void menuItem3ActionPerformed(ActionEvent evt) throws Exception {
                String dirc="";
                if (System.getProperty("os.name").toLowerCase().contains("win")) dirc="\\";
                else if (System.getProperty("os.name").toLowerCase().contains("linux")) dirc="/";
                FileOutputStream myFileOutputStream = new FileOutputStream(canvas.getDir()+dirc+canvas.getFilename()+".cvs");
                ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
                myObjectOutputStream.writeObject(new Cvs(canvas.getLines(), canvas.getCircles(), canvas.getRectangles(),
                    canvas.getImageClasses()));
                myObjectOutputStream.close();
            }
        });

         menuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    menuItem5ActionPerformed(evt);
                } catch (IOException ioe) {;}
            }

            private void menuItem5ActionPerformed(ActionEvent evt) throws IOException {
                int button = jFileChooser2.showOpenDialog(Draw.this);
                if (button == jFileChooser2.APPROVE_OPTION) {
                    canvas.setFilename(jFileChooser2.getSelectedFile().getName());
                    canvas.setDir(jFileChooser2.getCurrentDirectory().toString());
                }
                    try {
                        canvas.importt();
                    } catch (Exception exc) {jTextArea3.setText(exc.getMessage());}
                if (button == jFileChooser2.CANCEL_OPTION) {
                    jFileChooser1.setVisible(false);
                }
            }
        });

         //OPEN FILE *&YHJ)(GF ???
      menuItem6.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
              try {
                  menuItem6ActionPerformed(evt);
              } catch (IOException ioe) {;}
          }

        private void menuItem6ActionPerformed(ActionEvent evt) throws IOException {
          jFileChooser3.setFileFilter(new CvsExt("cvs"));
          int button = jFileChooser3.showOpenDialog(Draw.this);
          if (button == jFileChooser3.APPROVE_OPTION) {
              canvas.setFilename(jFileChooser3.getSelectedFile().getName());
              canvas.setDir(jFileChooser3.getCurrentDirectory().toString());
              FileInputStream myFileInputStream = new FileInputStream(canvas.getDir() +"/"+ canvas.getFilename());
              ObjectInputStream myObjectInputStream = new ObjectInputStream(myFileInputStream);
              Cvs cvs=null;
              try {cvs = (Cvs) myObjectInputStream.readObject();} catch (ClassNotFoundException cne) {};
              canvas.setLines(cvs.getLines());
              canvas.setRectangles(cvs.getRectangles());
              canvas.setCircles(cvs.getCircles());
              canvas.setImageClasses(cvs.getImageClasses());
              myObjectInputStream.close();
              try {
                  canvas.open();
              } catch (Exception exc) {jTextArea3.setText("Error");}
          }
          if (button == jFileChooser1.CANCEL_OPTION) {
              jFileChooser1.setVisible(false);
          }
      }
  });

        menuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem4ActionPerformed(evt);
            }

            public void menuItem4ActionPerformed(ActionEvent evt) {
                int button = jFileChooser1.showSaveDialog(Draw.this);
                if (button == jFileChooser1.APPROVE_OPTION) {
                    canvas.setFilename(jFileChooser1.getSelectedFile().getName());
                    canvas.setDir(jFileChooser1.getCurrentDirectory().toString());
                    try {
                        canvas.export();
                    } catch (Exception exc) {jTextArea3.setText("Error");}
                }
                if (button == jFileChooser1.CANCEL_OPTION) {
                    jFileChooser1.setVisible(false);
                }
            }
        });

        jTextArea3.addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        try {canvas.commandLineInput(jTextArea3.getText());
                        } catch (NullPointerException npe) {canvas.commandLineInput("null");}
                        if (!unknown) jTextArea3.setText("");
                        unknown=false;
                        for (Line l : (ArrayList<Line>) canvas.getLines()) l.polylineOff();
                } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    if (canvas.getTextInput()!=null&&canvas.getTextInput().isVisible()) canvas.getTextInput().dispose();
                    canvas.commandLineInput("esc");
                    jTextArea3.setText("");
                } else if(e.getKeyCode() == KeyEvent.VK_DELETE){    //gets all lines but iterates on just a few !!!
                        for (int i=0; i<canvas.getLines().size(); i++) {
                            Line l = (Line) canvas.getLines().get(i);
                            if (l.isSelected()) canvas.removeLine(i);
                        }
                        for (int i=0; i<canvas.getRectangles().size();i++) {
                            Rectangle r = (Rectangle)canvas.getRectangles().get(i);
                            if (r.isSelected()) {
                                if (r.getImageClass()!=null) canvas.removeImageClass(r.getImageClass());
                                canvas.removeRec(i);
                            }
                        }
                        for (int i=0; i<canvas.getCircles().size();i++) {
                            Circle c = (Circle) canvas.getCircles().get(i);
                            if (c.isSelected()) canvas.removeCircle(i);
                        }
                        toDelete = new ArrayList();
                        imageClasses = canvas.getImageClasses();
                        if (!imageClasses.isEmpty()) for (ImageClass imageClass : imageClasses)
                            if (imageClass.isSelected()) toDelete.add(imageClass);
                        for (ImageClass imageClass : toDelete) {
                            imageClasses.remove(imageClass);
                            imageClass=null;
                        }
                        canvas.setImage(null);
                        canvas.repaint();
                } else if (e.getKeyCode()==KeyEvent.VK_CONTROL) export=true;
                canvas.repaint();
            }
            public void keyTyped(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_S&&export) {
                    try {
                        canvas.export();
                    } catch (Exception ex) {jTextArea3.setText("Error");}
                }
                else if (e.getKeyCode()==KeyEvent.VK_CONTROL) export=false;
            }
        });

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup())
                .addComponent(canvas, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
            );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(canvas, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                 .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            ));

        pack();
        canvas.setBackground(Color.white);
  }
  public static void unknownOn() {
      unknown=true;
  }
  public static void setText(String s) {
      jTextArea3.setText(s);
  }
  public static void main(String[] args) {
      System.setProperty("java.awt.headless", "true");
    Draw sw = new Draw();
    sw.run(sw);
  }
}
