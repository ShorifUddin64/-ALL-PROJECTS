import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
// ==========================
// 1. Student Data Model Class
// ==========================
class Student {
 
    private String name;
    private String course;
    private String section;
    private int credit;
 
    public Student(String name, String course, int credit, String section) {
        this.name = name;
        this.course = course;
        this.credit = credit;
        this.section = section;
    }
 
    public String getName() {
        return name;
    }
 
    public String getCourse() {
        return course;
    }
 
    public int getCredit() {
        return credit;
    }
 
    public String getSection() {
        return section;
    }
}
// ==========================
// 2. Student Operations Class
// ==========================
class StudentOperations {
 
    ArrayList<Student> list = new ArrayList<>();
    // Add Student
    public String add(String name, String course, int credit, String section) {
        list.add(new Student(name, course, credit, section));
        return "Status: Student added successfully!";
    }
    // Search Student By Course
    public String searchByCourse(String courseName) {
        String result = "";
 
        for (Student s : list) {
            if (s.getCourse().equalsIgnoreCase(courseName)) {
                result += "Name: " + s.getName()
                        + " | Course: " + s.getCourse()
                        + " | Section: " + s.getSection()
                        + " | Credits: " + s.getCredit()
                        + "\n";
            }
        }
 
        if (result.equals("")) {
            return "Status: Course not found!";
        }
        return result;
    }
 
    // Update Student Record
    public String update(String course, int credit, String section) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCourse().equalsIgnoreCase(course)) {
                String oldName = list.get(i).getName();
                list.set(i, new Student(oldName, course, credit, section));
                return "Status: Record updated successfully!";
            }
        }
        return "Status: Course not found!";
    }
 
    // Delete Student Record
    public String delete(String course) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCourse().equalsIgnoreCase(course)) {
                list.remove(i);
                return "Status: Record deleted successfully!";
            }
        }
        return "Status: Course not found!";
    }
 
    // Display All Records
    public String displayAll() {
        if (list.isEmpty()) {
            return "No records found in the system.";
        }
 
        String all = "===== ALL REGISTERED STUDENTS =====\n\n";
        for (Student s : list) {
            all += "Name     : " + s.getName() + "\n"
                    + "Course   : " + s.getCourse() + "\n"
                    + "Section  : " + s.getSection() + "\n"
                    + "Credits  : " + s.getCredit() + "\n"
                    + "-----------------------------------\n";
        }
        return all;
    }
}
 
// ==========================
// 3. Main GUI Class
// ==========================
public class Studentportal extends JFrame implements ActionListener {
 
    JTextField tfSearch;
    JTextField tfName;
    JTextField tfCourse;
    JTextField tfCredit;
    JTextField tfSection;
 
    JButton btnAdd;
    JButton btnSearch;
    JButton btnUpdate;
    JButton btnDelete;
    JButton btnDisplay;
 
    JLabel lblStatus;
    JTextArea areaDisplay;
 
    StudentOperations ops = new StudentOperations();
 
    // Constructor
    public Studentportal() {
        setTitle("Student Portal - Professional Edition");
        setSize(600, 720);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 250));
 
        // Student Name
        JLabel lName = new JLabel("STUDENT NAME:");
        lName.setBounds(30, 20, 150, 30);
        lName.setFont(new Font("Arial", Font.BOLD, 14));
        add(lName);
 
        tfName = new JTextField();
        tfName.setBounds(180, 20, 350, 35);
        add(tfName);
 
        // Search Field
        tfSearch = new JTextField();
        tfSearch.setBounds(30, 80, 350, 35);
        add(tfSearch);
 
        btnSearch = new JButton("SEARCH");
        btnSearch.setBounds(400, 80, 130, 35);
        btnSearch.setBackground(new Color(173, 216, 230));
        add(btnSearch);
 
        // Course Field
        JLabel lCourse = new JLabel("COURSE NAME:");
        lCourse.setBounds(30, 150, 120, 30);
        add(lCourse);
 
        tfCourse = new JTextField();
        tfCourse.setBounds(180, 150, 350, 30);
        add(tfCourse);
 
        // Credit Field
        JLabel lCredit = new JLabel("CREDIT:");
        lCredit.setBounds(30, 210, 100, 30);
        add(lCredit);
 
        tfCredit = new JTextField();
        tfCredit.setBounds(180, 210, 80, 30);
        add(tfCredit);
 
        // Section Field
        JLabel lSection = new JLabel("SECTION:");
        lSection.setBounds(280, 210, 100, 30);
        add(lSection);
 
        tfSection = new JTextField();
        tfSection.setBounds(360, 210, 80, 30);
        add(tfSection);
 
        // Add Button
        btnAdd = new JButton("ADD");
        btnAdd.setBounds(460, 210, 70, 30);
        btnAdd.setBackground(new Color(144, 238, 144));
        add(btnAdd);
 
        // Operation Buttons
        btnUpdate = new JButton("UPDATE");
        btnUpdate.setBounds(30, 280, 150, 40);
        add(btnUpdate);
 
        btnDelete = new JButton("DELETE");
        btnDelete.setBounds(210, 280, 150, 40);
        add(btnDelete);
 
        btnDisplay = new JButton("DISPLAY ALL");
        btnDisplay.setBounds(390, 280, 150, 40);
        add(btnDisplay);
 
        // Status Label
        lblStatus = new JLabel("System Status: Ready", SwingConstants.CENTER);
        lblStatus.setBounds(30, 340, 510, 40);
        lblStatus.setOpaque(true);
        lblStatus.setBackground(new Color(230, 230, 250));
        add(lblStatus);
 
        // Display Area
        areaDisplay = new JTextArea();
        areaDisplay.setBounds(30, 410, 510, 220);
        areaDisplay.setEditable(false);
        areaDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaDisplay.setBorder(BorderFactory.createTitledBorder("Portal Workspace"));
        add(areaDisplay);
 
        // Action Listener
        btnAdd.addActionListener(this);
        btnSearch.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnDisplay.addActionListener(this);
 
        setLocationRelativeTo(null);
        setVisible(true);
    }
 
    // Action Events
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = tfName.getText();
        String course = tfCourse.getText();
        String section = tfSection.getText();
 
        try {
            if (e.getSource() == btnAdd) {
                int credit = Integer.parseInt(tfCredit.getText());
                lblStatus.setText(ops.add(name, course, credit, section));
            }
            else if (e.getSource() == btnSearch) {
                areaDisplay.setText(ops.searchByCourse(tfSearch.getText()));
            }
            else if (e.getSource() == btnUpdate) {
                int credit = Integer.parseInt(tfCredit.getText());
                lblStatus.setText(ops.update(course, credit, section));
            }
            else if (e.getSource() == btnDelete) {
                lblStatus.setText(ops.delete(course));
            }
            else if (e.getSource() == btnDisplay) {
                areaDisplay.setText(ops.displayAll());
            }
        } 
        catch (Exception ex) {
            lblStatus.setText("Error: Credit must be a valid number!");
        }
    }
 
    // Main Method
    public static void main(String[] args) {
        new Studentportal();
    }
}
