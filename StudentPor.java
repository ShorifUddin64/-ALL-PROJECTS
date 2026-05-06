import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// ১. Entities: ডেটা মডেল
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

    public void setCredit(int cr) { this.credit = cr; }
    public void setSection(String s) { this.section = s; }
}

// ২. Operations: লজিক লেয়ার
class StudentOperations {
    private Student[] list = new Student[50];
    private int count = 0;

    public String add(String n, String c, int cr, String s) {
        if (n.isEmpty() || c.isEmpty()) return "Error: Name & Course required!";
        if (count < list.length) {
            list[count++] = new Student(n, c, cr, s);
            return "Record Saved!";
        }
        return "System Full!";
    }

    public String searchByCourse(String courseName) {
        for (int i = 0; i < count; i++) {
            if (list[i].getCourse().equalsIgnoreCase(courseName)) {
                return "Course: " + list[i].getCourse() + " | Credit: " + list[i].getCredit() + " | Sec: " + list[i].getSection();
            }
        }
        return "Course Not Found!";
    }

    public String update(String c, int cr, String s) {
        for (int i = 0; i < count; i++) {
            if (list[i].getCourse().equalsIgnoreCase(c)) {
                list[i].setCredit(cr); 
                list[i].setSection(s);
                return "Updated Successfully!";
            }
        }
        return "Update Failed: Course not found!";
    }

    public String delete(String c) {
        for (int i = 0; i < count; i++) {
            if (list[i].getCourse().equalsIgnoreCase(c)) {
                for (int j = i; j < count - 1; j++) list[j] = list[j + 1];
                count--;
                return "Course Deleted!";
            }
        }
        return "Delete Failed!";
    }

    public String displayAll() {
        if (count == 0) return "No records found.";
        StringBuilder sb = new StringBuilder("<html><b style='color:green;'>--- Enrolled Courses ---</b><br>");
        for (int i = 0; i < count; i++) {
            sb.append("• " + list[i].getCourse() + " (Section: " + list[i].getSection() + ")<br>");
        }
        sb.append("</html>");
        return sb.toString();
    }
}

// ৩. GUI Layer (আপনার ফাইলের নাম অনুযায়ী P বড় হাতের করা হয়েছে)
public class StudentPor extends JFrame implements ActionListener {
    JTextField tfSearch, tfName, tfCourse, tfCredit, tfSection;
    JButton btnAdd, btnSearch, btnUpdate, btnDelete, btnDisplay;
    JLabel lblStatus1, lblStatus2;
    StudentOperations ops = new StudentOperations();

    public StudentPor() { // কনস্ট্রাক্টরের নামও পরিবর্তন করা হয়েছে
        setTitle("Student Portal - Power By Shorif");
        setSize(550, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 245, 255));

        JLabel lName = new JLabel("STUDENT NAME:"); 
        lName.setBounds(30, 20, 150, 30); 
        lName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lName);
        
        tfName = new JTextField(); 
        tfName.setBounds(160, 20, 330, 35); 
        tfName.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        add(tfName);

        tfSearch = new JTextField("Enter course name to search...");
        tfSearch.setBounds(30, 80, 330, 35);
        tfSearch.setForeground(Color.GRAY);
        tfSearch.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if(tfSearch.getText().equals("Enter course name to search...")) {
                    tfSearch.setText("");
                    tfSearch.setForeground(Color.BLACK);
                }
            }
        });
        add(tfSearch);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(370, 80, 120, 35);
        btnSearch.setBackground(new Color(100, 149, 237));
        btnSearch.setForeground(Color.WHITE);
        add(btnSearch);

        JLabel lCourse = new JLabel("Course Name:"); lCourse.setBounds(30, 150, 100, 30); add(lCourse);
        tfCourse = new JTextField(); tfCourse.setBounds(130, 150, 360, 30); add(tfCourse);

        JLabel lCredit = new JLabel("Credit:"); lCredit.setBounds(30, 200, 100, 30); add(lCredit);
        tfCredit = new JTextField(); tfCredit.setBounds(130, 200, 70, 30); add(tfCredit);

        JLabel lSec = new JLabel("Section:"); lSec.setBounds(220, 200, 80, 30); add(lSec);
        tfSection = new JTextField(); tfSection.setBounds(300, 200, 70, 30); add(tfSection);

        btnAdd = new JButton("ADD");
        btnAdd.setBounds(390, 200, 100, 30);
        btnAdd.setBackground(new Color(60, 179, 113));
        btnAdd.setForeground(Color.WHITE);
        add(btnAdd);

        btnUpdate = new JButton("Update"); btnUpdate.setBounds(30, 260, 140, 40); add(btnUpdate);
        btnDelete = new JButton("Delete"); btnDelete.setBounds(190, 260, 140, 40); add(btnDelete);
        btnDisplay = new JButton("Display All"); btnDisplay.setBounds(350, 260, 140, 40); add(btnDisplay);

        lblStatus1 = new JLabel("System Status: Ready", SwingConstants.CENTER);
        lblStatus1.setBounds(30, 330, 460, 45);
        lblStatus1.setOpaque(true);
        lblStatus1.setBackground(new Color(220, 220, 220));
        add(lblStatus1);

        lblStatus2 = new JLabel("Welcome! Please fill in the details.", SwingConstants.CENTER);
        lblStatus2.setBounds(30, 400, 460, 230);
        lblStatus2.setOpaque(true);
        lblStatus2.setBackground(Color.WHITE);
        lblStatus2.setBorder(BorderFactory.createTitledBorder("Workspace Area"));
        add(lblStatus2);

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
        String name = tfName.getText().trim();
        String course = tfCourse.getText().trim();
        String sec = tfSection.getText().trim();
        int credit = 0;

        try {
            if (!tfCredit.getText().isEmpty()) credit = Integer.parseInt(tfCredit.getText());
        } catch (NumberFormatException ex) {
            lblStatus1.setText("Error: Credit must be a number!");
            return;
        }

        if (e.getSource() == btnAdd) {
            String msg = ops.add(name, course, credit, sec);
            lblStatus1.setText(msg);
            lblStatus2.setText("<html><h3 style='color:blue;'>User: " + name + "</h3>" + course + " added successfully!</html>");
        } 
        else if (e.getSource() == btnSearch) {
            String searchText = tfSearch.getText().trim();
            String result = ops.searchByCourse(searchText);
            lblStatus2.setText("<html><h3>Results for " + name + ":</h3>" + result + "</html>");
            lblStatus1.setText("Search Complete.");
        } 
        else if (e.getSource() == btnUpdate) {
            lblStatus1.setText(ops.update(course, credit, sec));
        } 
        else if (e.getSource() == btnDelete) {
            lblStatus1.setText(ops.delete(course));
        } 
        else if (e.getSource() == btnDisplay) {
            lblStatus2.setText("<html><h3 style='color:blue;'>Enrolled by: " + (name.isEmpty() ? "Student" : name) + "</h3>" + ops.displayAll() + "</html>");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }
        new StudentPor(); // এখানেও P বড় হাতের করা হয়েছে
    }
}