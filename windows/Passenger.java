
package IBMS.windows;
import IBMS.*;
import IBMS.database.*;
import IBMS.windows.BusDriver;
import IBMS.windows.Controller;
import IBMS.windows.Window;

import java.awt.*; 
import java.awt.event.*;
import javax.swing.*; 
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

/**
  * A class that handles everything related to the 'Passenger' in the IBMS system
  * @author Audrey Leow Hui-Li
  * @author bingjian zhang (Jason)
  * 
  */
public class Passenger extends Window {

 public static int startDay, startYear, startMonth, startHour, startMins, buses, busesStop, fromareas,toarea,toareas,fromarea, dayOfTheWeek;
 public static String daysOfTheWeek[] = {
      "Monday", "Tuesday", "Wednesday", "Thursday",
      "Friday", "Saturday", "Sunday"
    };
 public static JComboBox busPicker;
 public static JComboBox busStopPicker;
 public static JComboBox fromareaPicker;
 public static JComboBox toareaPicker;

 public static JComboBox toBusStopPicker;
 public static JComboBox fromBusStopPicker;
 public static String chosenBus = "383";
 public static String chosenFromArea = "Stockport";
 public static String chosenToArea = "Glossop";
 public static String busStop1 = " ";
 public static String busStop2 = " ";
 public static String from = " ";
 public static String to = " ";

 public static DefaultComboBoxModel model = new DefaultComboBoxModel();;
 public static DefaultComboBoxModel model2 = new DefaultComboBoxModel();;

  /**
    * Constructs a Passenger with a stored reference to a database object
    * @param databaseOps          A reference to a database object
    */
  public Passenger(DBOperations databaseOps) {
    // Store reference to DB project
    db = databaseOps;
  }

  // ============================================
  // Launch main window for Passenger
  // ============================================

  /**
    * Creates a main window for the passenger
    */
  public static void createMainWindow() {

    JButton button;
    final JFrame frame = new JFrame("IBMS v" + version + " - " + "Passenger");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    frame.getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    //Simulator button
    button = new JButton("Simulator");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 40;
    c.gridy = 0;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          frame.setVisible(false);
          viewSimulator();
      }
    });
    frame.getContentPane().add(button, c);

    //Planner button
    button = new JButton("Planner");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.ipady = 40;
    c.gridy = 1;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        viewPlanner();
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

// ===========================================
// Create JPanel to select the day of the week
// ===========================================

  /**
    * Picks the day of the week wanted
    * @param boxTitle         A string for the title of the JPanel
    * @return JPanel          This returns a JPanel with title boxTitle                        
    */
  public static JPanel dayOfTheWeekPicker (String boxTitle) {
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());

    panel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), boxTitle));

    GridBagConstraints c = new GridBagConstraints();

    final JComboBox dayPicker = new JComboBox(daysOfTheWeek);
    dayPicker.setSelectedIndex(0);
    dayPicker.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        dayOfTheWeek = dayPicker.getSelectedIndex();
      }
    });

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.weightx = 0.5;
    c.gridy = 0;
    panel.add(dayPicker, c); 

    panel.setOpaque(true); 

    return panel;
  }

 
// ============================================
// Create JPanel to use dates
// ============================================
  /**
    * Picks the date wanted
    * @param boxTitle         A string for the title of the JPanel
    * @return JPanel          This returns a JPanel with title boxTitle                        
    */
  public static JPanel datePicker(String boxTitle) {
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
          startDay = Integer.parseInt(dayPicker.getSelectedItem().toString());
     
      }
    });

    final JComboBox monthPicker = new JComboBox(months);
    monthPicker.setSelectedIndex(0);
    monthPicker.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          startMonth = monthPicker.getSelectedIndex() + 1;
      
      }
    });

    final JComboBox yearPicker = new JComboBox(years);
    yearPicker.setSelectedIndex(0);
    yearPicker.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          startYear = Integer.parseInt(yearPicker.getSelectedItem().toString());
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
 // Create JPanel to use time
 // ============================================
  /**
    * Picks the time wanted
    * @param boxTitle         A string for the title of the JPanel
    * @return JPanel          This returns a JPanel with title boxTitle                        
    */
  public static JPanel timePicker(String boxTitle) {
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());

    panel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), boxTitle));

    GridBagConstraints c = new GridBagConstraints();
    

    String hours[] = new String[24];
    for (int i = 0; i < 24; i++)
      hours[i] = Integer.toString(i);

    String mins[] = new String[4];
    int a = 0;
    for (int i = 0; i < 4; i++){
      mins[i] = Integer.toString(a);
      a = a + 15;
    }


    final JComboBox hourPicker = new JComboBox(hours);
    hourPicker.setSelectedIndex(0);
    hourPicker.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          startHour = Integer.parseInt(hourPicker.getSelectedItem().toString());

      }
    });

    final JComboBox minsPicker = new JComboBox(mins);
    minsPicker.setSelectedIndex(0);
    minsPicker.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          startMins = Integer.parseInt(minsPicker.getSelectedItem().toString());
   
      }
    });


    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.weightx = 0.5;
    c.gridy = 0;
    panel.add(hourPicker, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 2;
    c.weightx = 0.5;
    c.gridy = 0;
    panel.add(minsPicker, c);

    panel.setOpaque(true); 

    return panel;
  }


// ============================================
// Create JPanel to use Bus
// ============================================
  // public static JPanel busSelect(String boxTitle){
  //   final JFrame frame = new JFrame("Planner");
  //   frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  //   frame.getContentPane().setLayout(new GridBagLayout());

  //   JPanel panel = new JPanel();
  //   panel.setLayout(new GridBagLayout());

  //   panel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), boxTitle));

  //   GridBagConstraints c = new GridBagConstraints();
    
  //   String bus[] = {
  //     "383" , "384" , "358out" , "358back"
  //   };
    
  //   busPicker = new JComboBox(bus);
  //   busPicker.setSelectedIndex(0);
  //   busPicker.addActionListener(new ActionListener() {
  //       @Override
  //       public void actionPerformed(ActionEvent e) {
  //         buses = busPicker.getSelectedIndex() + 1;
  //         chosenBus = (String)busPicker.getSelectedItem();
  //         if (chosenBus == null) chosenBus = "383";
  //         frame.getContentPane().add(busStopSelect("Bus Stop"));
  //     }
  //   });
    
  //   c.fill = GridBagConstraints.HORIZONTAL;
  //   c.gridx = 1;
  //   c.weightx = 0.5;
  //   c.gridy = 0;
  //   panel.add(busPicker, c);

  //   panel.setOpaque(true); 


  //   return panel;
  // }

// ============================================
// Create JPanel to use BusStop
// ============================================

  // public static JPanel busStopSelect(String boxTitle) {

  //   JPanel panel = new JPanel();
  //   panel.setLayout(new GridBagLayout());

  //   panel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), boxTitle));

  //   GridBagConstraints c = new GridBagConstraints();
 
  //   busStopPicker = new JComboBox(model);

  //   if (chosenBus.equals("383")) {
  //     model.removeAllElements();
  //     model.addElement("Bus Station");
  //     model.addElement("Dialstone Lane/Hillcrest Road");
  //     model.addElement("Navigation Hotel");
  //     model.addElement("Norfolk Arms");
  //     model.addElement("Corcoran Drive");
  //     model.addElement("Train Station");
  //     model.addElement("Lower Bents Lane/Stockport Road");
  //     model.addElement("Bus Station");

  //     c.fill = GridBagConstraints.HORIZONTAL;
  //     c.gridx = 1;
  //     c.weightx = 0.5;
  //     c.gridy = 0;
  //     panel.add(busStopPicker, c);
  //     panel.setOpaque(true); 
    
  //   }

  //   if (chosenBus.equals("384")) {
  //     model.removeAllElements();  
  //     model.addElement("Bus Station");
  //     model.addElement("Asda/Sainsbury's");
  //     model.addElement("Lower Bents Lane/Stockport Road");
  //     model.addElement("Train Station");
  //     model.addElement("Corcoran Drive");
  //     model.addElement("Norfolk Arms");
  //     model.addElement("Navigation Hotel");
  //     model.addElement("Dialstone Lane/Hillcrest Road");
  //     model.addElement("Bus Station");

  //     c.fill = GridBagConstraints.HORIZONTAL;
  //     c.gridx = 1;
  //     c.weightx = 0.5;
  //     c.gridy = 0;
  //     panel.add(busStopPicker, c);
  //     panel.setOpaque(true); 
  
  //   }

  //   if (chosenBus.equals("358out")) {
  //     model.removeAllElements();  
  //     model.addElement("Bus Station");
  //     model.addElement("Grouse Hotel");
  //     model.addElement("Ollerset View Hospital");
  //     model.addElement("Train Station");
  //     model.addElement("Bus Station");
  //     model.addElement("Royal Oak");
  //     model.addElement("Navigation Hotel");
  //     model.addElement("Navigation Hotel");
  //     model.addElement("Offerton Fold");
  //     model.addElement("Bus Station");

  //     c.fill = GridBagConstraints.HORIZONTAL;
  //     c.gridx = 1;
  //     c.weightx = 0.5;
  //     c.gridy = 0;
  //     panel.add(busStopPicker, c);
  //     panel.setOpaque(true); 
  
  //   }
    
  //   if (chosenBus.equals("358back")) {
  //     model.removeAllElements();  
  //     model.addElement("Bus Station");
  //     model.addElement("Offerton Fold");
  //     model.addElement("Navigation Hotel");
  //     model.addElement("Navigation Hotel");
  //     model.addElement("Royal Oak");
  //     model.addElement("Bus Station");
  //     model.addElement("Train Station");
  //     model.addElement("Ollerset View Hospital");
  //     model.addElement("Grouse Hotel");
  //     model.addElement("Bus Station");

  //     c.fill = GridBagConstraints.HORIZONTAL;
  //     c.gridx = 1;
  //     c.weightx = 0.5;
  //     c.gridy = 0;
  //     panel.add(busStopPicker, c);
  //     panel.setOpaque(true); 
  
  //   }
  //   return panel;
  // }

// ============================================
// Create JPanel to use from area
// ============================================
  /**
    * Picks the area that the passenger wants to travel from
    * @param boxTitle         A string for the title of the JPanel
    * @return JPanel          This returns a JPanel with title boxTitle                        
    */
  public static JPanel fromAreaSelect(String boxTitle){
    final JFrame frame = new JFrame("Simulator");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().setLayout(new GridBagLayout());

    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());

    panel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), boxTitle));

    GridBagConstraints c = new GridBagConstraints();
    
    String fromarea[] = {
      "Stockport" , "Romiley" , "Compstall" , "Marple", "Offerton", "Strines",
      "New Mills", "Birch Vale", "Hayfield", "Newtown", "Low Leighton","Thornsett","Glossop"
    };
    
    fromareaPicker = new JComboBox(fromarea);
    fromareaPicker.setSelectedIndex(0);
    fromareaPicker.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          fromareas = fromareaPicker.getSelectedIndex() + 1;
          chosenFromArea = (String)fromareaPicker.getSelectedItem();
          if (chosenFromArea == null) chosenFromArea = "Stockport";
          frame.getContentPane().add(fromBusStopSelect("Bus Stop"));
      }
    });
    
    
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.weightx = 0.5;
    c.gridy = 0;
    panel.add(fromareaPicker, c);

    panel.setOpaque(true); 


    return panel;
  }

// ============================================
// Create JPanel to use From BusStop
// ============================================
  /**
    * Picks the bus stop the passenger wants to travel from
    * @param boxTitle         A string for the title of the JPanel
    * @return JPanel          This returns a JPanel with title boxTitle                        
    */
  public static JPanel fromBusStopSelect(String boxTitle) {

    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());

    panel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), boxTitle));

    GridBagConstraints c = new GridBagConstraints();
 
    fromBusStopPicker = new JComboBox(model);

    fromBusStopPicker.addItemListener(new ItemListener(){
     @Override
            public void itemStateChanged(ItemEvent e)
            {     if(fromBusStopPicker.getSelectedItem() != null){
        	busStop1 = fromBusStopPicker.getSelectedItem().toString();
        	if (e.getStateChange() == ItemEvent.SELECTED){
        	from = (busStop1 +" - "+chosenFromArea);
              //System.out.println(one)
       	    ;}
            }else{     

    }
            }
        });

    if (chosenFromArea.equals("Stockport")) {
      model.removeAllElements();
      model.addElement("Bus Station");
      model.addElement("intermediate Road");
      model.addElement("Dialstone Lane/Hillcrest Road");
      model.addElement("Asda/Sainsbury's");
      model.addElement("Lower Bents Lane/Stockport Road");
    
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;

      panel.add(fromBusStopPicker, c);
      panel.setOpaque(true); 
    
    }
    
    if (chosenFromArea.equals("Romiley")) {
      model.removeAllElements();
      model.addElement("Corcoran Drive");
      model.addElement("Train Station");
    
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;

      panel.add(fromBusStopPicker, c);
      panel.setOpaque(true); 
    
   }

    if (chosenFromArea.equals("Compstall")) {
      model.removeAllElements();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(fromBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenFromArea.equals("Marple")) {
      model.removeAllElements();
      model.addElement("Navigation Hotel");
      model.addElement("Back of Beyong");
      model.addElement("Norfolk Arms");
      model.addElement("Offerton Fold");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(fromBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenFromArea.equals("Offerton")) {
      model.removeAllElements();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(fromBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenFromArea.equals("Strines")) {
      model.removeAllElements();
      model.addElement("Royal Oak");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(fromBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenFromArea.equals("New Mills")) {
      model.removeAllElements();
      model.addElement("Bus Station");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(fromBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenFromArea.equals("Birch Vale")) {
      model.removeAllElements();
      model.addElement("Grouse Hotel");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(fromBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenFromArea.equals("Hayfield")) {
      model.removeAllElements();
      model.addElement("Bus Station");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(fromBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenFromArea.equals("Newtown")) {
      model.removeAllElements();
      model.addElement("Train Station");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(fromBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenFromArea.equals("Low Leighton")) {
      model.removeAllElements();
      model.addElement("Ollerset View Hospital");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(fromBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenFromArea.equals("Thornsett")) {
      model.removeAllElements();
      model.addElement("Printers Arms");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(fromBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenFromArea.equals("Glossop")) {
      model.removeAllElements();
      model.addElement("Henry Street");
      model.addElement("Grouse Inn");
      model.addElement("Little Hayfield");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(fromBusStopPicker, c);
      panel.setOpaque(true); 
    
    }             
    return panel;
  }

// ============================================
// Create JPanel to use to area
// ============================================
  /**
    * Picks the area the passenger wants to travel to
    * @param boxTitle         A string for the title of the JPanel
    * @return JPanel          This returns a JPanel with title boxTitle                        
    */
  public static JPanel toAreaSelect(String boxTitle){

    final JFrame frame = new JFrame("Simulator");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().setLayout(new GridBagLayout());

    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());

    panel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), boxTitle));

    GridBagConstraints c = new GridBagConstraints();
    
    String toarea[] = {
      "Glossop" , "Romiley" , "Compstall" , "Marple", "Offerton", "Strines",
      "New Mills", "Birch Vale", "Hayfield", "Newtown", "Low Leighton","Thornsett","Stockport"
    };
    
    toareaPicker = new JComboBox(toarea);
    toareaPicker.setSelectedIndex(0);
    toareaPicker.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          toareas = toareaPicker.getSelectedIndex() + 1;
          chosenToArea = (String)toareaPicker.getSelectedItem();
          if (chosenToArea == null) chosenToArea = "Glossop";
          frame.getContentPane().add(toBusStopSelect("Bus Stop"));
      }
    });
    
    
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.weightx = 0.5;
    c.gridy = 0;
    panel.add(toareaPicker, c);

    panel.setOpaque(true); 


    return panel;
  }

// ============================================
// Create JPanel to use From BusStop
// ============================================
  /**
    * Picks the bus stop the passenger wants to travel to
    * @param boxTitle         A string for the title of the JPanel
    * @return JPanel          This returns a JPanel with title boxTitle                        
    */
  public static JPanel toBusStopSelect(String boxTitle) {

    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());

    panel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), boxTitle));

    GridBagConstraints c = new GridBagConstraints();
 
    toBusStopPicker = new JComboBox(model2);

     toBusStopPicker.addItemListener(new ItemListener(){
     @Override
            public void itemStateChanged(ItemEvent e)
            {     if(toBusStopPicker.getSelectedItem() != null){
		    busStop2 = toBusStopPicker.getSelectedItem().toString();
		    if (e.getStateChange() == ItemEvent.SELECTED){
		    to = (busStop2 +" - " +chosenToArea);
	            //System.out.println(two)
		   ;}
        	  }else{		 

		}
            }
        });


    if (chosenToArea.equals("Stockport")) {
      model2.removeAllElements();
      model2.addElement("Bus Station");
      model2.addElement("intermediate Road");
      model2.addElement("Dialstone Lane/Hillcrest Road");
      model2.addElement("Asda/Sainsbury's");
      model2.addElement("Lower Bents Lane/Stockport Road");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(toBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenToArea.equals("Romiley")) {
      model2.removeAllElements();
      model2.addElement("Corcoran Drive");
      model2.addElement("Train Station");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(toBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenToArea.equals("Compstall")) {
      model2.removeAllElements();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(toBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenToArea.equals("Marple")) {
      model2.removeAllElements();
      model2.addElement("Navigation Hotel");
      model2.addElement("Back of Beyong");
      model2.addElement("Norfolk Arms");
      model2.addElement("Offerton Fold");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(toBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenToArea.equals("Offerton")) {
      model2.removeAllElements();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(toBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenToArea.equals("Strines")) {
      model2.removeAllElements();
      model2.addElement("Royal Oak");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(toBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenToArea.equals("New Mills")) {
      model2.removeAllElements();
      model2.addElement("Bus Station");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(toBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenToArea.equals("Birch Vale")) {
      model2.removeAllElements();
      model2.addElement("Grouse Hotel");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(toBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenToArea.equals("Hayfield")) {
      model2.removeAllElements();
      model2.addElement("Bus Station");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(toBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenToArea.equals("Newtown")) {
      model2.removeAllElements();
      model2.addElement("Train Station");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(toBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenToArea.equals("Low Leighton")) {
      model2.removeAllElements();
      model2.addElement("Ollerset View Hospital");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(toBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenToArea.equals("Thornsett")) {
      model2.removeAllElements();
      model2.addElement("Printers Arms");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(toBusStopPicker, c);
      panel.setOpaque(true); 
    
    }

    if (chosenToArea.equals("Glossop")) {
      model2.removeAllElements();
      model2.addElement("Henry Street");
      model2.addElement("Grouse Inn");
      model2.addElement("Little Hayfield");
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.weightx = 0.5;
      c.gridy = 0;
      panel.add(toBusStopPicker, c);
      panel.setOpaque(true); 
    
    }             
    return panel;
  }

// ============================================
// Launch simulator window
// ============================================
  /**
    * Displays the simulator                      
    */
  public static void viewSimulator() {

    JButton button;

    final JFrame frame = new JFrame("Simulator");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.weightx = 0.5;
    c.ipady = 20;
    c.gridy = 0;
    c.insets = new Insets(10,10,10,5);
    frame.getContentPane().add(fromAreaSelect("Area:"), c);
 
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.weightx = 0.5;
    c.ipady = 20;
    c.gridy = 1;
    c.insets = new Insets(10,10,10,5);
    frame.getContentPane().add(fromBusStopSelect("Bus Stop:"), c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.weightx = 0.5;
    c.ipady = 20;
    c.gridy = 2;
    c.insets = new Insets(10,10,10,5);
    frame.getContentPane().add(dayOfTheWeekPicker("Day"), c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.weightx = 0.5;
    c.ipady = 20;
    c.gridy = 3;
    c.insets = new Insets(10,10,10,5);
    frame.getContentPane().add(timePicker("Time"), c);

    //Search Button 
    button = new JButton("Search");
    c.gridx = 0;
    c.ipady = 20;
    c.gridy = 4;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (startDay == 0)    startDay = 1;
          if (startMonth == 0)  startMonth = 1;
          if (startYear == 0)   startYear = 2014;

          //Display the results of the search
          try{
            System.out.print("Day of the week: " + daysOfTheWeek[dayOfTheWeek]);
            System.out.print("; Time: " + startHour);
            System.out.print(":" + startMins);
            System.out.println("");

            Interaction.startInteraction(chosenFromArea, busStop1, daysOfTheWeek[dayOfTheWeek], startHour, startMins);
            // viewSimulatorResults();
            // Interaction.startInteraction();
          }
          catch (Exception exception) {
          }
      }
    });     
    frame.getContentPane().add(button, c);


    //Back button
    button = new JButton("Back");
    c.gridx = 0;
    c.ipady = 20;
    c.gridy = 5;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          frame.setVisible(false);
          createMainWindow();
          //logoutButton();
      }
    }); 
    frame.getContentPane().add(button, c);

    //Quit button
    button = new JButton("Quit");
    c.gridx = 0;
    c.ipady = 20;
    c.gridy = 6;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          frame.setVisible(false);
          logoutButton();

      }
    }); 
    frame.getContentPane().add(button, c);

    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true); 
  }

// ============================================
// Launch planner window
// ============================================
  /**
    * Displays the planner                    
    */
  public static void viewPlanner() {

    JButton button;

    final JFrame frame = new JFrame("Planner");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.weightx = 0.5;
    c.ipady = 20;
    c.gridy = 0;
    c.insets = new Insets(10,10,10,5);
    frame.getContentPane().add(fromAreaSelect("From area:"), c);
 
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.weightx = 0.5;
    c.ipady = 20;
    c.gridy = 0;
    c.insets = new Insets(10,10,10,5);
    frame.getContentPane().add(fromBusStopSelect("Bus Stop:"), c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.weightx = 0.5;
    c.ipady = 20;
    c.gridy = 1;
    c.insets = new Insets(10,10,10,5);
    frame.getContentPane().add(toAreaSelect("To:"), c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.weightx = 0.5;
    c.ipady = 20;
    c.gridy = 1;
    c.insets = new Insets(10,10,10,5);
    frame.getContentPane().add(toBusStopSelect("Bus Stop:"), c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.weightx = 0.5;
    c.ipady = 20;
    c.gridy = 2;
    c.insets = new Insets(10,10,10,5);
    frame.getContentPane().add(datePicker("Date"), c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.weightx = 0.5;
    c.ipady = 20;
    c.gridy = 2;
    c.insets = new Insets(10,10,10,5);
    frame.getContentPane().add(timePicker("Time"), c);


    //Search Button 
    button = new JButton("Search");
    c.gridx = 0;
    c.ipady = 20;
    c.gridy = 4;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (startDay == 0)    startDay = 1;
          if (startMonth == 0)  startMonth = 1;
          if (startYear == 0)   startYear = 2014;
           BusGraph graph = new BusGraph();
    	   graph.populate(TimetableInfo.timetableKind.weekday, 400);
           String[] output  =   graph.findRoute(from,to);
         viewRoute(output);
      }
    });     
    frame.getContentPane().add(button, c);


    //Back button
    button = new JButton("Back");
    c.gridx = 1;
    c.ipady = 20;
    c.gridy = 4;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          frame.setVisible(false);
          createMainWindow();
          //logoutButton();

      }
    }); 
    frame.getContentPane().add(button, c);

    //Quit button
    button = new JButton("Quit");
    c.gridx = 0;
    c.ipady = 20;
    c.gridy = 6;
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          frame.setVisible(false);
          logoutButton();

      }
    }); 
    frame.getContentPane().add(button, c);

    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true); 
  }

  //=============================================================
  // Launch window to display the results of the simulator search
  //=============================================================
  /**
    * Displays the search results for the simulator
    * @throw Exception                   
    */
  public static void viewSimulatorResults() throws Exception
  {
    final JFrame frame = new JFrame("Results");
    frame.getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    final JTextArea textArea = new JTextArea(40, 40);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.weightx = 0.5;
    c.ipady = 20;
    c.gridy = 0;
    // c.insets = new Insets(10,10,10,5);
    textArea.setEditable(false);
    frame.getContentPane().add(textArea, c);

    // Interaction.startInteraction();
    // Interaction.startInteraction(textArea);

    //Display the contents of the JFrame
    // frame.setContentPane(textArea);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    //Place frame at the center
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    
    // Interaction.startInteraction();
  }

  public static void viewRoute(String[] a) {
  
    String[] columnNames = {"Route"};
    Object[][] cellData = { a };

    // Create Table Model and add rows
    DefaultTableModel rosterTableModel = new DefaultTableModel(null, columnNames);
    for(int i =0; i <a.length;i++){    
    if(a[i] != null)
      rosterTableModel.addRow(new Object[]{ a[i]});
    }

    // Create Table
    JTable rosterTable = new JTable(rosterTableModel);
    // Adjust Rows
    for(int i=0; i<a.length;i++){
     if(a[i] != null)
      rosterTable.setRowHeight(i, 50);
   }

    // Customise Table View
    JFrame f = new JFrame();
    f.setSize(500,600);
    f.setTitle("Find Route");
    f.add(new JScrollPane(rosterTable));
    f.setVisible(true);
  }
  
}
