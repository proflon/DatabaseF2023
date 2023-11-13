/**
 * Author: Lon Smith, Ph.D.
 * Description: This is the framework for the database program. Additional requirements and functionality
 *    are to be built by you and your group.
 */

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
  public EmployeeSearchFrame() {
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
            String databaseName = txtDatabase.getText();
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
          } catch (Exception exep) {
            exep.printStackTrace();
          }
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
          textAreaEmployee.setText(
            "John Smith\nFranklin Wong\nAshamsa Adhikari\nNigesh Byanjankar\nJordan Crumpton"
          );
        }
      }
    );
    btnSearch.setBounds(80, 276, 89, 23);
    contentPane.add(btnSearch);

    JButton btnClear = new JButton("Clear");
    btnClear.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          textAreaEmployee.setText("");
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
}
