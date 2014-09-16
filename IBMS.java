package IBMS;
import IBMS.database.*;
import IBMS.windows.BusDriver;
import IBMS.windows.Controller;
import IBMS.windows.Passenger;
import IBMS.windows.Window;

import java.awt.*; 
import java.awt.event.*;
import javax.swing.*; 

// Create a simple GUI window
public class IBMS {

  private static String  driverName;
  private static int     driverNumber;

  // ============================================================
  // Checks whether the user is a driver, controller or passenger
  // ============================================================

  private static void checkIdentity() {

    JButton button;
    final JFrame frame = new JFrame("IBMS Pilot System");
    
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setPreferredSize(new Dimension(400, 80));

    //Text to welcome to the pilot system 
    JLabel welcomeText = new JLabel("Welcome to the IBMS Pilot System!", JLabel.CENTER);
    mainPanel.add(welcomeText, BorderLayout.PAGE_START);
    welcomeText.setFont(welcomeText.getFont().deriveFont(20f));
    welcomeText.setForeground(Color.BLUE);

    //Create the panel which includes the text and the selection buttons
    JPanel userIdentityPanel = new JPanel(new BorderLayout());

    //Text which asks to choose a category
    JLabel text = new JLabel("Please choose your category", JLabel.CENTER);
    userIdentityPanel.add(text, BorderLayout.PAGE_START);
    //Set the size of the text in the JLabel
    text.setFont(text.getFont().deriveFont(15f));

    //Create the selection buttons
    JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    //Driver
    button = new JButton("Driver");
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        while (!askDriverId()) {
        JOptionPane.showMessageDialog(
          null, "Please enter a valid Driver ID!", 
          "Driver ID Error", JOptionPane.ERROR_MESSAGE
        );
      }
      //Creates reference to DB object
      BusDriver busDriverWindow = new BusDriver(new DBOperations());
      //Set driver's name and number
      busDriverWindow.setDriver(driverNumber, driverName);
      //Creates the main window for bus driver
      busDriverWindow.createMainWindow();
      }
    });
    selectionPanel.add(button);

    //Controller
    button = new JButton("Controller");
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        while (!askControllerPassword()) {
        JOptionPane.showMessageDialog(
          null, "Please enter the correct password!",
          "Incorrect Password", JOptionPane.ERROR_MESSAGE
        );
      }
      //Creates reference to DB object
      Controller controllerWindow = new Controller(new DBOperations());
      //Creates the main window for controller
      controllerWindow.createMainWindow(); 
      }
    });
    selectionPanel.add(button);

    //Passenger
    button = new JButton("Passenger");
    button.addActionListener(new ActionListener() {

     @Override
        public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
      //Creates reference to DB object
      Passenger passengerWindow = new Passenger(new DBOperations());
      //Creates the main window for controller
      passengerWindow.createMainWindow(); 
     }
    });
    

    selectionPanel.add(button);

    userIdentityPanel.add(selectionPanel, BorderLayout.CENTER);
    mainPanel.add(userIdentityPanel, BorderLayout.CENTER);
    
    //Display the contents of the JFrame
    frame.setContentPane(mainPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    //Place frame at the center 
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

  }

  // ============================================
  // Ensure Driver ID is valid
  // ============================================

  private static Boolean askDriverId() {
    String idStr = JOptionPane.showInputDialog("Please enter your Driver ID:");
    if (idStr == null) System.exit(0);

    // Is it a number?
    try { driverNumber = Integer.parseInt(idStr); }
    catch(Exception e) { return false; }

    if (driverNumber == 0)
      return false;
    else {
      // Does the driver exist in the DB?
      driverName = DriverInfo.getName(driverNumber);
      return (driverName.isEmpty()) ? false : true;
    }
  }

  // ============================================
  // Ensure Controller Password is valid
  // ============================================

  private static Boolean askControllerPassword() {
    String idStr = JOptionPane.showInputDialog("Please enter your controller password:");
    if (idStr == null) System.exit(0);

    // Is it a number?
    try { driverNumber = Integer.parseInt(idStr); }
    catch(Exception e) { return false; }

    // Check if the password is correct
    return (idStr.equals("1234")) ? true : false;
  } 

  // ============================================
  // Main
  // ============================================

  public static void main(String[] args) {

    DBOperations dbo = new DBOperations();
    checkIdentity();
  }
}
