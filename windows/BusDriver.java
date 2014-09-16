package IBMS.windows;
import IBMS.*;

import java.awt.*; 
import java.awt.event.*;
import javax.swing.*; 
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.border.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BusDriver extends Window {

  public static int startDay, endDay, startYear, endYear, startMonth, endMonth;

  public BusDriver(DBOperations databaseOps) {
    // Store reference to DB object
    db = databaseOps;
  }
  
  // ============================================
  // Launch main window for bus driver
  // ============================================

  public static void createMainWindow() {

    JButton button;
    final JFrame frame = new JFrame("IBMS v" + version + " - " + driverName);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    frame.getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    button = new JButton("Book Holiday");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 40;
    c.gridy = 0;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        bookHolidayWindow();
      }
    });
    frame.getContentPane().add(button, c);

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
  // Launch main window to view selected Roster
  // ============================================

  public static void viewRosterDaily() {

    JButton button;
    final JFrame frame = new JFrame("View Roster");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    frame.getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

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
        viewDayRoster(1);
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
        viewDayRoster(2);
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
        viewDayRoster(3);
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
        viewDayRoster(4);
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
        viewDayRoster(5);
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
        viewDayRoster(6);
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
        viewDayRoster(7);
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

  // ============================================
  // Create JPanel to use for both dates
  // ============================================

  public static JPanel datePicker(final Boolean date, String boxTitle) {
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());

    panel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), boxTitle));

    GridBagConstraints c = new GridBagConstraints();
    
    String months[] = {
      "January" , "February" , "March" , "April", "May",
      "June", "July", "August", "September", "October",
      "November", "December"
    };

    String days[] = new String[31];
    for (int i = 0; i < 31; i++)
      days[i] = Integer.toString(i+1);

    String years[] = new String[6];
    for (int i = 0; i < 6; i++)
      years[i] = Integer.toString(i+2014);

    final JComboBox dayPicker = new JComboBox(days);
    dayPicker.setSelectedIndex(0);
    dayPicker.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (date) 
          startDay = Integer.parseInt(dayPicker.getSelectedItem().toString());
        else 
          endDay = Integer.parseInt(dayPicker.getSelectedItem().toString());
      }
    });

    final JComboBox monthPicker = new JComboBox(months);
    monthPicker.setSelectedIndex(0);
    monthPicker.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (date) 
          startMonth = monthPicker.getSelectedIndex() + 1;
        else 
          endMonth = monthPicker.getSelectedIndex() + 1;
      }
    });

    final JComboBox yearPicker = new JComboBox(years);
    yearPicker.setSelectedIndex(0);
    yearPicker.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        if (date) 
          startYear = Integer.parseInt(yearPicker.getSelectedItem().toString());
        else 
          endYear = Integer.parseInt(yearPicker.getSelectedItem().toString()); 
      }
    });

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.weightx = 0.5;
    c.gridy = 0;
    panel.add(monthPicker, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.weightx = 0.5;
    c.gridy = 0;
    panel.add(dayPicker, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 2;
    c.weightx = 0.5;
    c.gridy = 0;
    panel.add(yearPicker, c);

    panel.setOpaque(true); 

    return panel;
  }

  // ============================================
  // Launch window to book a holiday
  // ============================================

  public static void bookHolidayWindow() {

    JButton button;

    final JFrame frame = new JFrame("Book a Holiday");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.weightx = 0.5;
    c.ipady = 20;
    c.gridy = 0;
    c.insets = new Insets(10,10,10,5);
    frame.getContentPane().add(datePicker(true, "Start Date"), c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.weightx = 0.5;
    c.ipady = 20;
    c.gridy = 0;
    c.insets = new Insets(10,0,10,10);
    frame.getContentPane().add(datePicker(false, "End Date"), c);

    button = new JButton("Cancel");
    c.gridx = 0;
    c.ipady = 20;
    c.gridy = 1;
    c.weightx = 0.5;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          frame.setVisible(false);
      }
    });    
    frame.getContentPane().add(button, c);

    button = new JButton("Book");
    c.gridx = 1;
    c.ipady = 20;
    c.gridy = 1;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (startDay == 0)    startDay = 1;
          if (endDay == 0)      endDay = 1;
          if (startMonth == 0)  startMonth = 1;
          if (endMonth == 0)    endMonth = 1;
          if (startYear == 0)   startYear = 2014;
          if (endYear == 0)     endYear = 2014;

          Boolean status = db.requestHoliday(
            startDay, endDay, startMonth, endMonth, startYear, endYear, driverNumber
          );

          JOptionPane.showMessageDialog(
            frame, db.getResult(), (status) ? "Success!" : "Error",
            (status) ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
          );

          if (status) frame.setVisible(false);
      }
    });    
    frame.getContentPane().add(button, c);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true); 
  }

}
