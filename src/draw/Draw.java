package draw;

import objs.Circle;
import objs.ImageClass;
import objs.Text;
import objs.Line;
import objs.Rectangle;
import serialize.Cvs;
import serialize.CvsExt;
import ui.About;
import ui.Index;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

@SuppressWarnings({"SpellCheckingInspection", "deprecation", "unchecked", "AccessStaticViaInstance", "CanBeFinal", "RedundantThrows", "unused", "rawtypes"})
public class Draw extends JFrame {
  private Canvas canvas;
  private static boolean unknown;
  private static javax.swing.JTextArea jTextArea3;
  private boolean ctrl;
  @SuppressWarnings("FieldCanBeLocal")
  private javax.swing.JMenuItem menuItem4;
  private ArrayList<Line> linesToDelete;
  private ArrayList<Circle> circlesToDelete;
  private ArrayList<Rectangle> rectanglesToDelete;
  private ArrayList<Text> textsToDelete;
  private ArrayList<ImageClass> toDelete;
  private ArrayList<ImageClass> imageClasses;
  private static Index index;

  public void run(JFrame f) {
      SwingUtilities.invokeLater(() -> f.setVisible(true));
  }
  public Draw() {
      canvas = new Canvas();
      //.......scrolling command lines...................
      javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
      jTextArea3 = new javax.swing.JTextArea();
      jTextArea3.setColumns(20);
      jTextArea3.setRows(1);
      jScrollPane3.setViewportView(jTextArea3);
      //........menu bar..................................
      javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
      javax.swing.JMenu help = new javax.swing.JMenu();
      javax.swing.JMenu file = new javax.swing.JMenu();
      javax.swing.JMenuItem menuItem1 = new javax.swing.JMenuItem();
      javax.swing.JMenuItem menuItem2 = new javax.swing.JMenuItem();
      javax.swing.JMenuItem menuItem3 = new javax.swing.JMenuItem();
      javax.swing.JMenuItem menuItem5 = new javax.swing.JMenuItem();
      javax.swing.JMenuItem menuItem6 = new javax.swing.JMenuItem();
      menuItem4 = new javax.swing.JMenuItem();
      //........Index and About windows...................
      index = new Index();
      About about = new About();
      //........save/export, import, open fileChoosers.....
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
      menuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
              java.awt.event.InputEvent.CTRL_MASK));
      menuItem6.setText("Open");
      file.add(menuItem6);
      menuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O,
              java.awt.event.InputEvent.CTRL_MASK));
      menuItem4.setText("Export");
      file.add(menuItem4);
      menuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E,
              java.awt.event.InputEvent.CTRL_MASK));
      menuItem5.setText("Import");
      file.add(menuItem5);
      menuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I,
              java.awt.event.InputEvent.CTRL_MASK));

      //.........index of commands window............
      menuItem1.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
              menuItem1ActionPerformed(evt);
          }

          private void menuItem1ActionPerformed(ActionEvent evt) {
              index.setVisible(true);
          }
      });

      //..........about window......................
      menuItem2.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
              try {
                  menuItem2ActionPerformed(evt);
              } catch (Exception e) {
                  jTextArea3.setText("Error");
              }
          }

          private void menuItem2ActionPerformed(ActionEvent evt) throws Exception {
              about.setVisible(true);
          }
      });

      //--------------SAVE FILE-------------------------------------------------------------------------
      menuItem3.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
              int button = jFileChooser1.showSaveDialog(Draw.this);
              if (button == jFileChooser1.APPROVE_OPTION) {
                  canvas.setFilename(jFileChooser1.getSelectedFile().getName());
                  canvas.setDir(jFileChooser1.getCurrentDirectory().toString());
              }
              try {
                  menuItem3ActionPerformed(evt);
              } catch (Exception exc) {
                  jTextArea3.setText(exc.toString());
              }
          }

          private void menuItem3ActionPerformed(ActionEvent evt) throws Exception {
              String dirc = "";
              if (System.getProperty("os.name").toLowerCase().contains("win")) dirc = "\\";
              else if (System.getProperty("os.name").toLowerCase().contains("linux")) dirc = "/";
              FileOutputStream myFileOutputStream = new FileOutputStream(canvas.getDir() + dirc + canvas.getFilename() + ".cvs");
              ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
              myObjectOutputStream.writeObject(new Cvs(canvas.getLines(), canvas.getCircles(), canvas.getRectangles(),
                      canvas.getImageClasses(), canvas.getTexts()));
              myObjectOutputStream.close();
          }
      });

      //----------------IMPORT IMAGE----------------------------------------------------------------------
      menuItem5.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
              try {
                  menuItem5ActionPerformed(evt);
              } catch (IOException ignored) {
              }
          }

          private void menuItem5ActionPerformed(ActionEvent evt) throws IOException {
              int button = jFileChooser2.showOpenDialog(Draw.this);
              if (button == jFileChooser2.APPROVE_OPTION) {
                  canvas.setFilename(jFileChooser2.getSelectedFile().getName());
                  canvas.setDir(jFileChooser2.getCurrentDirectory().toString());
              }
              try {
                  canvas.importt();
              } catch (Exception exc) {
                  jTextArea3.setText(exc.getMessage());
              }
              if (button == jFileChooser2.CANCEL_OPTION) {
                  jFileChooser1.setVisible(false);
              }
          }
      });

      //------------------OPEN FILE-----------------------------------------------------------------------
      menuItem6.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
              try {
                  menuItem6ActionPerformed(evt);
              } catch (IOException ioe) {
                  jTextArea3.setText(ioe.toString());
              }
          }

          private void menuItem6ActionPerformed(ActionEvent evt) throws IOException {
              jFileChooser3.setFileFilter(new CvsExt("cvs"));
              int button = jFileChooser3.showOpenDialog(Draw.this);
              if (button == jFileChooser3.APPROVE_OPTION) {
                  canvas.setFilename(jFileChooser3.getSelectedFile().getName());
                  canvas.setDir(jFileChooser3.getCurrentDirectory().toString());
                  String dirc = "";
                  if (System.getProperty("os.name").toLowerCase().contains("win")) dirc = "\\";
                  else if (System.getProperty("os.name").toLowerCase().contains("linux")) dirc = "/";
                  FileInputStream myFileInputStream = new FileInputStream(canvas.getDir() + dirc + canvas.getFilename());
                  ObjectInputStream myObjectInputStream = new ObjectInputStream(myFileInputStream);
                  Cvs cvs = null;
                  try {
                      cvs = (Cvs) myObjectInputStream.readObject();
                  } catch (Exception cne) {
                      jTextArea3.setText(cne.toString());
                  }
                  if (cvs != null && cvs.getLines() != null) canvas.setLines(cvs.getLines());
                  if (cvs != null && cvs.getRectangles() != null) canvas.setRectangles(cvs.getRectangles());
                  if (cvs != null && cvs.getCircles() != null) canvas.setCircles(cvs.getCircles());
                  if (cvs != null && cvs.getImageClasses() != null) canvas.setImageClasses(cvs.getImageClasses());
                  if (cvs != null && cvs.getTexts() != null) canvas.setTexts(cvs.getTexts());
                  myObjectInputStream.close();
                  try {
                      canvas.open();
                  } catch (Exception exc) {
                      jTextArea3.setText(exc.toString());
                  }
              }
              if (button == jFileChooser1.CANCEL_OPTION) {
                  jFileChooser1.setVisible(false);
              }
          }
      });

      //-----------------EXPORT IMAGE----------------------------------------------------------------------------
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
                  } catch (Exception exc) {
                      jTextArea3.setText("Error");
                  }
              }
              if (button == jFileChooser1.CANCEL_OPTION) {
                  jFileChooser1.setVisible(false);
              }
          }
      });

      //---------------KEYBOARD INPUT--------------------------------------------------------------------------
      jTextArea3.addKeyListener(new KeyListener() {
          @SuppressWarnings("RedundantCast")
          public void keyPressed(KeyEvent e) {
              if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                  //.......command line input...............
                  if (canvas.command("pl")) {
                      canvas.commandLineInput("null");
                      canvas.setPlindex(canvas.getPlindex()+1);
                  }
                  try {
                      canvas.commandLineInput(jTextArea3.getText());
                  } catch (NullPointerException npe) {
                      canvas.commandLineInput("null");
                  }
                  if (!unknown) jTextArea3.setText("");
                  unknown = false;
                  for (Line l : (ArrayList<Line>) canvas.getLines()) l.polylineOff();
              } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                  //...........cancel...........................
                  if (canvas.getTextInput() != null && canvas.getTextInput().isVisible())
                      canvas.getTextInput().dispose();
                  canvas.commandLineInput("esc");
                  jTextArea3.setText("");
              } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                  //...........delete drawn objects.............
                  linesToDelete = new ArrayList();
                  circlesToDelete = new ArrayList();
                  rectanglesToDelete = new ArrayList();
                  toDelete = new ArrayList();
                  textsToDelete = new ArrayList();

                  for (Line l : canvas.getLines()) {
                      if (l.isSelected()) linesToDelete.add(l);
                  }
                  for (Line l : linesToDelete) canvas.removeLine(l);

                  for (Rectangle r : canvas.getRectangles()) {
                      if (r.isSelected()) rectanglesToDelete.add(r);
                  }
                  for (Rectangle r : rectanglesToDelete) canvas.removeRec(r);

                  for (Circle c : canvas.getCircles()) {
                      if (c.isSelected()) circlesToDelete.add(c);
                  }
                  for (Circle c : circlesToDelete) canvas.removeCircle(c);

                  for (Text t : canvas.getTexts()) {
                      if (t.isSelected()) textsToDelete.add(t);
                  }
                  for (Text t : textsToDelete) canvas.removeText(t);

                  imageClasses = canvas.getImageClasses();
                  if (!imageClasses.isEmpty()) for (ImageClass imageClass : imageClasses)
                      if (imageClass.isSelected()) toDelete.add(imageClass);
                  for (ImageClass imageClass : toDelete) {
                      imageClasses.remove(imageClass);
                      //noinspection UnusedAssignment
                      imageClass = null;
                  }
                  canvas.setImage(null);
                  canvas.repaint();
              } else if (e.getKeyCode() == KeyEvent.VK_CONTROL) ctrl = true;
              canvas.repaint();
          }

          public void keyTyped(KeyEvent e) {
          }

          public void keyReleased(KeyEvent e) {
              //.......ctrl+z...............................................
              if (e.getKeyCode() == KeyEvent.VK_Z && ctrl) {
                  try {
                      canvas.revCmd();
                  } catch (Exception ex) {
                      jTextArea3.setText("Error");
                  }
              } else if (e.getKeyCode() == KeyEvent.VK_CONTROL) ctrl = false;
          }
      });

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

      //.................Canvas as JPanel.....................
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

  //----------------------UNKNOWN COMMAND----------------------------------------------------------------
  public static void unknownOn() {
      unknown=true;
  }

  //---------------------OUTPUT MSGS THRU CMD LINE-------------------------------------------------
  public static void setText(String s) {
      jTextArea3.setText(s);
  }

  //------------------RUN----------------------------------------------------------------------------------
  public static void main(String[] args) {
    System.setProperty("java.awt.headless", "true");    //just in case (f/ linux os)
    Draw sw = new Draw();
    sw.run(sw);
  }
}
