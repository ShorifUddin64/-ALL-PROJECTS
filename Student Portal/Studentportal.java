import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// 1. Data Model Class
class Student {
    private String name, course, section;
    private int credit;

    public Student(String name, String course, int credit, String section) {
        this.name = name;
        this.course = course;
        this.credit = credit;
        this.section = section;
    }

    public String getName() { return name; }
    public String getCourse() { return course; }
    public int getCredit() { return credit; }
    public String getSection() { return section; }
}

// 2. Logic Class (Operations)
class StudentOperations {
    ArrayList<Student> list = new ArrayList<>();

    public String add(String n, String c, int cr, String s) {
        list.add(new Student(n, c, cr, s));
        return "Status: Student added successfully!";
    }

    public String searchByCourse(String courseName) {
        for (Student s : list) {
            if (s.getCourse().equalsIgnoreCase(courseName)) {
                return "Course: " + s.getCourse() + " | Section: " + s.getSection() + " | Credits: " + s.getCredit();
            }
        }
        return "Status: Course not found!";
    }

    public String update(String course, int credit, String sec) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCourse().equalsIgnoreCase(course)) {
                list.set(i, new Student("Updated", course, credit, sec));
                return "Status: Record updated successfully!";
            }
        }
        return "Status: Course not found to update.";
    }

    public String delete(String course) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCourse().equalsIgnoreCase(course)) {
                list.remove(i);
                return "Status: Record deleted successfully!";
            }
        }
        return "Status: Course not found.";
    }

    public String displayAll() {
        if (list.isEmpty()) return "No records found in the system.";
        String all = "All Registered Courses:\n";
        for (Student s : list) {
            all += "- " + s.getCourse() + " (" + s.getSection() + ") | Credits: " + s.getCredit() + "\n";
        }
        return all;
    }
}

```java
// 3. Main GUI Class

public class Studentportal extends JFrame implements ActionListener {

    JTextField tfSearch, tfName, tfCourse, tfCredit, tfSection;

    JButton btnAdd, btnSearch, btnUpdate, btnDelete, btnDisplay;

    JLabel lblStatus1;

    // Replaced JLabel with JTextArea for easier English display
    JTextArea areaDisplay;

    StudentOperations ops = new StudentOperations();


    public Studentportal() {

        setTitle("Student Portal - Professional Edition");

        setSize(550, 700);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(245, 245, 250));


        // Student Name
        JLabel lName = new JLabel("STUDENT NAME:");

        lName.setBounds(30, 20, 150, 30);

        lName.setFont(new Font("Arial", Font.BOLD, 14));

        add(lName);


        tfName = new JTextField();

        tfName.setBounds(160, 20, 330, 35);

        tfName.setBorder(
                BorderFactory.createLineBorder(
                        new Color(0, 102, 204), 2
                )
        );

        add(tfName);


        // Course Search Bar
        tfSearch = new JTextField("Search by Course Name");

        tfSearch.setBounds(30, 80, 330, 35);

        add(tfSearch);


        btnSearch = new JButton("Search");

        btnSearch.setBounds(370, 80, 120, 35);

        btnSearch.setBackground(new Color(173, 216, 230));

        add(btnSearch);


        // Input Fields
        JLabel lCourse = new JLabel("Course Name:");

        lCourse.setBounds(30, 150, 100, 30);

        add(lCourse);


        tfCourse = new JTextField();

        tfCourse.setBounds(130, 150, 360, 30);

        add(tfCourse);


        JLabel lCredit = new JLabel("Credit:");

        lCredit.setBounds(30, 200, 100, 30);

        add(lCredit);


        tfCredit = new JTextField();

        tfCredit.setBounds(130, 200, 70, 30);

        add(tfCredit);


        JLabel lSection = new JLabel("Section:");

        lSection.setBounds(220, 200, 80, 30);

        add(lSection);


        tfSection = new JTextField();

        tfSection.setBounds(300, 200, 70, 30);

        add(tfSection);


        // Add Button
        btnAdd = new JButton("ADD");

        btnAdd.setBounds(390, 200, 100, 30);

        btnAdd.setBackground(new Color(144, 238, 144));

        add(btnAdd);


        // Operation Buttons
        btnUpdate = new JButton("Update");

        btnUpdate.setBounds(30, 260, 140, 40);

        add(btnUpdate);


        btnDelete = new JButton("Delete");

        btnDelete.setBounds(190, 260, 140, 40);

        add(btnDelete);


        btnDisplay = new JButton("Display All");

        btnDisplay.setBounds(350, 260, 140, 40);

        add(btnDisplay);


        // Status Label
        lblStatus1 = new JLabel(
                "System Status: Ready",
                SwingConstants.CENTER
        );

        lblStatus1.setBounds(30, 320, 460, 40);

        lblStatus1.setOpaque(true);

        lblStatus1.setBackground(new Color(230, 230, 250));

        add(lblStatus1);


        // Main Display Area
        areaDisplay = new JTextArea(
                "Welcome! Workspace Ready."
        );

        areaDisplay.setBounds(30, 380, 460, 250);

        areaDisplay.setBorder(
                BorderFactory.createTitledBorder(
                        "Portal Workspace"
                )
        );

        areaDisplay.setEditable(false);

        add(areaDisplay);


        // Action Listeners
        btnAdd.addActionListener(this);

        btnSearch.addActionListener(this);

        btnUpdate.addActionListener(this);

        btnDelete.addActionListener(this);

        btnDisplay.addActionListener(this);


        setLocationRelativeTo(null);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        String name = tfName.getText();

        String course = tfCourse.getText();

        String sec = tfSection.getText();


        try {

            if (e.getSource() == btnAdd) {

                int cr = Integer.parseInt(tfCredit.getText());

                lblStatus1.setText(
                        ops.add(name, course, cr, sec)
                );
            }

            else if (e.getSource() == btnSearch) {

                areaDisplay.setText(
                        "Searching for Course...\n"
                                + ops.searchByCourse(
                                        tfSearch.getText()
                                )
                );
            }

            else if (e.getSource() == btnUpdate) {

                int cr = Integer.parseInt(tfCredit.getText());

                lblStatus1.setText(
                        ops.update(course, cr, sec)
                );
            }

            else if (e.getSource() == btnDelete) {

                lblStatus1.setText(
                        ops.delete(course)
                );
            }

            else if (e.getSource() == btnDisplay) {

                areaDisplay.setText(
                        "Full Record for "
                                + name
                                + ":\n"
                                + ops.displayAll()
                );
            }

        } catch (Exception ex) {

            lblStatus1.setText(
                    "Error: Credit must be a valid number!"
            );
        }
    }


    public static void main(String[] args) {

        new Studentportal();
    }
}
```
