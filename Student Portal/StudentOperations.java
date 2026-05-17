import java.util.ArrayList;

public class StudentOperations {
    // Creating an ArrayList to store Student objects
    ArrayList<Student> list = new ArrayList<>();
    
    // ==========================
    // Add Student Method
    // ==========================

    public String add(String n, String c, int cr, String s) {
        list.add(new Student(n, c, cr, s));
        return "Student added successfully!";
    }

    // ==========================
    // Search Student By Course
    // ==========================
    public String searchByCourse(String courseName) {
        String result = "";
        
        for (Student s : list) {
            // equalsIgnoreCase checks without capital/small letter issues
            if (s.getCourse().equalsIgnoreCase(courseName)) {
                result +=
                        "Name: " + s.getName()
                        + " | Course: " + s.getCourse()
                        + " | Credit: " + s.getCredit()
                        + " | Section: " + s.getSection()
                        + "\n";
            }
        }


        if (result.equals("")) {
            return "No student found for this course.";
        }
        return result;
    }

    // ==========================
    // Display All Students
    // ==========================
    public String displayAll() {
        if (list.isEmpty()) {
            return "The list is currently empty.";
        }
        String allData = "===== REGISTERED STUDENTS =====\n\n";
        for (Student s : list) {
            allData +=
                    "Name     : " + s.getName() + "\n"
                    + "Course   : " + s.getCourse() + "\n"
                    + "Credit   : " + s.getCredit() + "\n"
                    + "Section  : " + s.getSection() + "\n"
                    + "-----------------------------------\n"; 
        return allData;
    }

    // ==========================
    // Update Student Method
    // ==========================
    public String update(String c, int cr, String s) {

        for (Student st : list) {

            if (st.getCourse().equalsIgnoreCase(c)) {
                st.setCredit(cr);
                st.setSection(s);
                return "Record updated successfully!";
            }
        }
        return "Course not found to update.";
    }
    // ==========================
    // Delete Student Method
    // ==========================
    public String delete(String c) {

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getCourse().equalsIgnoreCase(c)) {
                list.remove(i);
                return "Record deleted successfully!";
            }
        }
        return "Course not found.";
    }
}

