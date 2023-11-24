/**
 * Author: Lon Smith, Ph.D.
 * Description: This is the framework for the database program. Additional requirements and functionality
 *    are to be built by you and your group.
 */

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class EmployeeSearchFrame extends JFrame {

  private static final long serialVersionUID = 1L;
  private JPanel contentPane;
  private JTextField txtDatabase;
  private JList<String> lstDepartment;
  private DefaultListModel<String> department = new DefaultListModel<String>();
  private JList<String> lstProject;
  private DefaultListModel<String> project = new DefaultListModel<String>();
  private JTextArea textAreaEmployee;
  private String databaseName = "";


  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(
      new Runnable() {
        public void run() {
          try {
            EmployeeSearchFrame frame = new EmployeeSearchFrame();
            frame.setVisible(true);
          } catch (Exception e) {
            e.printStackTrace();
            
          }
        }
      }
    );
  }

  /**
   * Create the frame.
   */
  public EmployeeSearchFrame() 
  {
    setTitle("Employee Search");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 347);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblNewLabel = new JLabel("Database:");
    lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
    lblNewLabel.setBounds(21, 23, 59, 14);
    contentPane.add(lblNewLabel);

    txtDatabase = new JTextField();
    txtDatabase.setBounds(90, 20, 193, 20);
    contentPane.add(txtDatabase);
    txtDatabase.setColumns(10);

    JButton btnDBFill = new JButton("Fill");
    /**
     * The btnDBFill should fill the department and project JList with the
     * departments and projects from your entered database name.
     */
    btnDBFill.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            databaseName = txtDatabase.getText();
            Connection conn = DatabaseConnection.getConnection(databaseName);
            Statement statementDept = conn.createStatement();
            Statement statementProj = conn.createStatement();
            ResultSet rsDepartment = statementDept.executeQuery(
              "SELECT Dname FROM DEPARTMENT"
            );
            ResultSet rsProject = statementProj.executeQuery(
              "SELECT Pname FROM PROJECT"
            );
            department.clear();
            project.clear();
            while (rsDepartment.next()) {
              String departmentName = rsDepartment.getString("Dname");
              department.addElement(departmentName);
            }
            while (rsProject.next()) {
              String projectName = rsProject.getString("Pname");
              project.addElement(projectName);
            }
            rsProject.close();
            rsDepartment.close();
            statementDept.close();
            statementProj.close();
            conn.close();
          } 
          // catch(Exception exep) 
          // {
          //   error(exep);
          // }
          //updated the pop up window when user enter wrong database name 
          //it also lets user to enter the correct database name
          catch (Exception exep) {
            // Display an error dialog and allow the user to enter the database name again
            String errorMessage = "Invalid database name. Please enter a valid database name.";
            String newDatabaseName = promptForDatabaseName(errorMessage);
            if (newDatabaseName != null) {
                // If the user entered a new database name, try to connect again
                txtDatabase.setText(newDatabaseName);
                actionPerformed(e); // Recursively call the actionPerformed to try again
            }
          }
        }

        private String promptForDatabaseName(String errorMessage) {
          return javax.swing.JOptionPane.showInputDialog(
            null, errorMessage, "Invalid Database Name", JOptionPane.ERROR_MESSAGE);
        }
      }
 
    );
      

    btnDBFill.setFont(new Font("Times New Roman", Font.BOLD, 12));
    btnDBFill.setBounds(307, 19, 68, 23);
    contentPane.add(btnDBFill);

    lstProject = new JList<String>(new DefaultListModel<String>());
    lstProject.setFont(new Font("Tahoma", Font.PLAIN, 12));
    lstProject.setModel(project);
    lstProject.setBounds(225, 84, 150, 42);

    JLabel lblProject = new JLabel("Project");
    lblProject.setFont(new Font("Times New Roman", Font.BOLD, 12));
    lblProject.setBounds(255, 63, 47, 14);
    contentPane.add(lblProject);

    JLabel lblDepartment = new JLabel("Department");
    lblDepartment.setFont(new Font("Times New Roman", Font.BOLD, 12));
    lblDepartment.setBounds(52, 63, 89, 14);
    contentPane.add(lblDepartment);

    JScrollPane scrollPaneProject = new JScrollPane();
    scrollPaneProject.setBounds(225, 84, 150, 42);
    contentPane.add(scrollPaneProject);
    scrollPaneProject.setViewportView(lstProject);

    JCheckBox chckbxNotDept = new JCheckBox("Not");
    chckbxNotDept.setBounds(71, 133, 59, 23);
    contentPane.add(chckbxNotDept);

    JCheckBox chckbxNotProject = new JCheckBox("Not");
    chckbxNotProject.setBounds(270, 133, 59, 23);
    contentPane.add(chckbxNotProject);

    lstDepartment = new JList<String>(new DefaultListModel<String>());
    lstDepartment.setBounds(36, 84, 172, 40);
    contentPane.add(lstDepartment);
    lstDepartment.setFont(new Font("Tahoma", Font.PLAIN, 12));
    lstDepartment.setModel(department);

    JScrollPane scrollPaneDepartment = new JScrollPane();
    scrollPaneDepartment.setBounds(36, 84, 172, 40);
    contentPane.add(scrollPaneDepartment);
    scrollPaneDepartment.setViewportView(lstDepartment);

    JLabel lblEmployee = new JLabel("Employee");
    lblEmployee.setFont(new Font("Times New Roman", Font.BOLD, 12));
    lblEmployee.setBounds(52, 179, 89, 14);
    contentPane.add(lblEmployee);

    JButton btnSearch = new JButton("Search");
    btnSearch.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            textAreaEmployee.setText("");
            Connection conn = DatabaseConnection.getConnection(databaseName);
            Statement statementPrint = conn.createStatement();
            List<String> selectedProjects = lstProject.getSelectedValuesList();
            List<String> selectedDepartment = lstDepartment.getSelectedValuesList();
            String projectName = "";
            String departmentName = "";
            String notDepartment = "";
            String notProject = "";
            if (!selectedProjects.isEmpty()) {
              for (String project : selectedProjects) {
                projectName = projectName + "'" + project + "'" + ",";
              }
              projectName = projectName.substring(0, projectName.length() - 1);
            }
            if (!selectedDepartment.isEmpty()) {
              for (String department : selectedDepartment) {
                departmentName = departmentName + "'" + department + "',";
              }
              departmentName =
                departmentName.substring(0, departmentName.length() - 1);
            }
            if (chckbxNotDept.isSelected()) {
              notDepartment = "NOT";
            }
            if (chckbxNotProject.isSelected()) {
              notProject = "NOT";
            }
            String queryString = "";
            if (selectedProjects.isEmpty() && selectedDepartment.isEmpty()) {
              queryString = "SELECT Fname, Minit, Lname FROM EMPLOYEE";
            } else if (selectedDepartment.isEmpty()) {
              queryString =
                "SELECT DISTINCT E.Fname, E.Minit, E.Lname FROM EMPLOYEE E LEFT JOIN WORKS_ON WO ON E.Ssn = WO.Essn LEFT JOIN PROJECT P ON WO.Pno = P.Pnumber WHERE " +
                " E.Ssn " +
                notProject +
                " IN (SELECT Essn FROM WORKS_ON LEFT JOIN PROJECT ON Pno = Pnumber WHERE Pname IN (" +
                projectName +
                "));";
            } else if (selectedProjects.isEmpty()) {
              queryString =
                "SELECT DISTINCT Fname, Minit,  Lname FROM EMPLOYEE, DEPARTMENT WHERE DEPARTMENT.Dnumber = EMPLOYEE.Dno AND DEPARTMENT.Dname " +
                notDepartment +
                " IN (" +
                departmentName +
                ");";
            } else {
              queryString =
                "SELECT DISTINCT E.Fname, E.Minit, E.Lname FROM EMPLOYEE E INNER JOIN DEPARTMENT D ON D.Dnumber = E.Dno INNER JOIN WORKS_ON W ON E.Ssn = W.Essn WHERE D.Dname " +
                notDepartment +
                " IN (" +
                departmentName +
                ") AND " +
                " E.Ssn " +
                notProject +
                " IN (SELECT Essn FROM WORKS_ON LEFT JOIN PROJECT ON Pno = Pnumber WHERE Pname IN (" +
                projectName +
                "));";
            }
            ResultSet employees = statementPrint.executeQuery(queryString);
            while (employees.next()) {
              String employeeName =
                employees.getString("Fname") +
                " " +
                employees.getString("Minit") +
                " " +
                employees.getString("Lname");
              textAreaEmployee.append(employeeName + "\n");
            }
            employees.close();
            statementPrint.close();
            conn.close();
          } 
          catch (Exception exep) 
          {
            error(exep);
          }
        }
      }
    );
    btnSearch.setBounds(80, 276, 89, 23);
    contentPane.add(btnSearch);

    //adding functionality to clear button
    JButton btnClear = new JButton("Clear");
    btnClear.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          // Clear the contents of the Employees list
          textAreaEmployee.setText("");

          // Deselect any selected items in the Department and Project lists
          lstDepartment.clearSelection();
          lstProject.clearSelection();

          // Turn off the "Not" checkboxes
          chckbxNotDept.setSelected(false);
          chckbxNotProject.setSelected(false);
        }
      }
    );
    btnClear.setBounds(236, 276, 89, 23);
    contentPane.add(btnClear);

    textAreaEmployee = new JTextArea();
    textAreaEmployee.setBounds(36, 197, 339, 68);

    JScrollPane scrollPaneEmployee = new JScrollPane();
    scrollPaneEmployee.setBounds(36, 197, 339, 68);
    scrollPaneEmployee.setViewportView(textAreaEmployee);
    contentPane.add(scrollPaneEmployee);
    }

    /**
     * 
     * @param error
     * Error class will take an exception and create a pop-up
     * message corresponding to the exception thrown.
     */
    private void error(Exception error)
    {
      JFrame errorMessage = new JFrame();
	      errorMessage.setTitle("Task Error");
        errorMessage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JPanel infoPane = new JPanel();
    	  infoPane.setBounds(1500, 1000, 1000, 1000);
	      infoPane.setBorder(new EmptyBorder(5,5,5,5));
	
	    errorMessage.setContentPane(infoPane);
	    infoPane.setLayout(null);
      
      String exception = error.toString();
	    JLabel message = new JLabel
      (
        exception.substring(
          exception.indexOf(" "), exception.length())
      );

	    message.setFont(new Font("Times New Roman", Font.BOLD, 24));
    	message.setBounds(25, -300, 1000, 1000);
      infoPane.add(message);

	    errorMessage.setVisible(true);
  }
}
