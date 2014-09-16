package IBMS.windows;
import IBMS.*;

import java.awt.*; 
import java.awt.event.*;
import javax.swing.*; 
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

public class Controller extends Window {

  public Controller(DBOperations databaseOps) {
    // Store reference to DB project
    db = databaseOps;
  }

  // ============================================
  // Launch main window for controller
  // ============================================

  public static void createMainWindow() {

    JButton button;
    final JFrame frame = new JFrame("IBMS v" + version + " - " + "Controller");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    frame.getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    //Generate roster button
    button = new JButton("Generate Roster");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 40;
    c.gridy = 0;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        Roster.generateRoster();
      }
    });
    frame.getContentPane().add(button, c);

    //View roster button
    button = new JButton("View Roster");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 40;
    c.gridy = 1;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        viewRosterOptions();
      }
    });  
    frame.getContentPane().add(button, c);

    //Quit button
    button = new JButton("QUIT");
    button.setForeground(Color.RED);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 40;
    c.gridy = 2;
    c.anchor = GridBagConstraints.PAGE_END;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        logoutButton();
      }
    });    
    frame.getContentPane().add(button, c);

    try{
      BufferedImage bus = ImageIO.read(new File("bus.jpg"));
      JLabel busLabel = new JLabel(new ImageIcon(bus));
      busLabel.setOpaque(true);
      busLabel.setBackground(Color.WHITE);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.gridheight = 3;
      c.gridwidth = 2;
      c.gridy = 0;
      frame.getContentPane().add(busLabel, c);
    } catch(IOException e){
      
    }

    //Display the window. 
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true); 
  }

  // ============================================
  // Launch main window to view Roster options
  // ============================================

  public static void viewRosterOptions() {

    JButton button;
    final JFrame frame = new JFrame("Choose a view option");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    frame.getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    //Weekly button
    button = new JButton("Weekly");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 47;
    c.gridy = 0;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        viewWeekRoster();
      }
    });  
    frame.getContentPane().add(button, c);

    //Daily button
    button = new JButton("Daily");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 47;
    c.gridy = 1;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        viewRosterDaily();
      }
    });  
    frame.getContentPane().add(button, c);

    //Back button
    button = new JButton("Back");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 47;
    c.gridy = 2;
    c.anchor = GridBagConstraints.PAGE_END;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        createMainWindow();
      }
    });    
    frame.getContentPane().add(button, c);

    //Quit button
    button = new JButton("QUIT");
    button.setForeground(Color.RED);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 47;
    c.gridy = 3;
    c.anchor = GridBagConstraints.PAGE_END;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        logoutButton();
      }
    });    
    frame.getContentPane().add(button, c);

    try{
      BufferedImage bus = ImageIO.read(new File("bus.jpg"));
      JLabel busLabel = new JLabel(new ImageIcon(bus));
      busLabel.setOpaque(true);
      busLabel.setBackground(Color.WHITE);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.gridheight = 4;
      c.gridwidth = 2;
      c.gridy = 0;
      frame.getContentPane().add(busLabel, c);
    } catch(IOException e){
      
    }

    //Display the window. 
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true); 
  }

  // ============================================
  // Launch main window to view selected Roster
  // ============================================

  public static void viewRosterDaily() {

    JButton button;
    final JFrame frame = new JFrame("View Roster");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    frame.getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    final JTextArea textArea = new JTextArea(40, 40);

    // ================================================
    // LHS column (gridx = 0)
    // ================================================
    button = new JButton("Monday");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 47;
    c.gridy = 0;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        readTextFile("Roster_Day_1.txt", textArea);
        // viewDayRoster(1);
      }
    }); 
    frame.getContentPane().add(button, c);

    button = new JButton("Tuesday");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 47;
    c.gridy = 1;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        readTextFile("Roster_Day_2.txt", textArea);
        // viewDayRoster(2);
      }
    }); 
    frame.getContentPane().add(button, c);

    button = new JButton("Wednesday");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 47;
    c.gridy = 2;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        readTextFile("Roster_Day_3.txt", textArea);
        // viewDayRoster(3);
      }
    }); 
    frame.getContentPane().add(button, c);

    button = new JButton("Thursday");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 47;
    c.gridy = 3;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        readTextFile("Roster_Day_4.txt", textArea);
        // viewDayRoster(4);
      }
    }); 
    frame.getContentPane().add(button, c);

    // ===============================================
    // RHS column (gridx = 1)
    // ===============================================
    button = new JButton("Friday");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.ipady = 47;
    c.gridy = 0;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        readTextFile("Roster_Day_5.txt", textArea);
        // viewDayRoster(5);
      }
    }); 
    frame.getContentPane().add(button, c);

    button = new JButton("Saturday");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.ipady = 47;
    c.gridy = 1;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        readTextFile("Roster_Day_6.txt", textArea);
        // viewDayRoster(6);
      }
    }); 
    frame.getContentPane().add(button, c);

    button = new JButton("Sunday");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.ipady = 47;
    c.gridy = 2;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        readTextFile("Roster_Day_7.txt", textArea);
        // viewDayRoster(7);
      }
    }); 
    frame.getContentPane().add(button, c);

    button = new JButton("Back");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.ipady = 47;
    c.gridy = 3;
    c.anchor = GridBagConstraints.PAGE_END;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        viewRosterOptions();
      }
    });    
    frame.getContentPane().add(button, c);

    try{
      BufferedImage bus = ImageIO.read(new File("bus.jpg"));
      JLabel busLabel = new JLabel(new ImageIcon(bus));
      busLabel.setOpaque(true);
      busLabel.setBackground(Color.WHITE);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 2;  //Picture is at the most right
      c.gridheight = 4;
      c.gridwidth = 2;
      c.gridy = 0;
      frame.getContentPane().add(busLabel, c);
    } catch(IOException e){
      
    }

    //Display the window. 
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true); 
  }

  // ============================================
  // Launch main window to view Weekly Roster
  // ============================================

  public static void viewWeekRoster() {

    // Set Column names
    String[] columnNames = {"Service", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    Object[][] cellData = { 
       { "383", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time" } //{ "2-1", "2-2" } 
    };

    // Create Table Model and add rows
    DefaultTableModel rosterTableModel = new DefaultTableModel(cellData, columnNames);
    rosterTableModel.addRow(new Object[]{ "384", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time" });
    rosterTableModel.addRow(new Object[]{ "358 Out", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time" });
    rosterTableModel.addRow(new Object[]{ "358 Back", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time", "Driver Name: Start_Time-Finish_Time" });

    // Create Table
    JTable rosterTable = new JTable(rosterTableModel);
    // Adjust Rows
    rosterTable.setRowHeight(0, 100);
    rosterTable.setRowHeight(1, 100);
    rosterTable.setRowHeight(2, 100);
    rosterTable.setRowHeight(3, 100);


    // Customise Table View
    JFrame f = new JFrame();
    f.setSize(2000,455);
    f.setTitle("Weekly Roster");
    f.add(new JScrollPane(rosterTable));
    f.setVisible(true);
  }


  // ============================================
  // Launch main window to view Weekly Roster
  // ============================================

  public static void viewDayRoster(int day) {

    // Switch Satement to Populate the columns
    String[] columnNames;
    switch (day) {
        case 1:    columnNames = new String [] {"Service", "Monday"}; break;
        case 2:    columnNames = new String [] {"Service", "Tuesday"}; break;
        case 3:    columnNames = new String [] {"Service", "Wednesday"}; break;
        case 4:    columnNames = new String [] {"Service", "Thursday"}; break;
        case 5:    columnNames = new String [] {"Service", "Friday"}; break;
        case 6:    columnNames = new String [] {"Service", "Saturday"}; break;
        case 7:    columnNames = new String [] {"Service", "Sunday"}; break;
        default:   columnNames = new String [] {"Service", "Day"}; break;
    } // switch 

    // Row information
    Object[][] cellData = { { "383", "Driver Name: Start_Time-Finish_Time" } };

    // Create Table Model and add necessary rows
    DefaultTableModel rosterTableModel = new DefaultTableModel(cellData, columnNames);
    rosterTableModel.addRow(new Object[]{ "384", "Driver Name: Start_Time-Finish_Time" });
    rosterTableModel.addRow(new Object[]{ "358 Out", "Driver Name: Start_Time-Finish_Time" });
    rosterTableModel.addRow(new Object[]{ "358 Back", "Driver Name: Start_Time-Finish_Time" });

    // Create Table
    JTable rosterTable = new JTable(rosterTableModel);
    // Adjust Rows
    rosterTable.setRowHeight(0, 100);
    rosterTable.setRowHeight(1, 100);
    rosterTable.setRowHeight(2, 100);
    rosterTable.setRowHeight(3, 100);


    // Customise Table View
    JFrame f = new JFrame();
    f.setSize(700,455);
    f.setTitle("Weekly Roster");
    f.add(new JScrollPane(rosterTable));
    f.setVisible(true);
  }

  // =====================================
  // Method to read a specific text file
  // =====================================
  public static void readTextFile(String filename, JTextArea pane)
  {
    try
    {
      FileReader reader = new FileReader(filename);
      pane.read(reader, null);
      reader.close();
      JOptionPane.showMessageDialog(null, new JScrollPane(pane));
    }
    catch (IOException e)
    {
      System.err.println(e);
    }
  }//readTextFile method

}

