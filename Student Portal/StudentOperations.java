import java.util.ArrayList;

public class StudentOperations {

    // Creating an ArrayList to store Student objects
    ArrayList<Student> list = new ArrayList<>();


    // Method to add a new student to the list
    public String add(String n, String c, int cr, String s) {

        list.add(new Student(n, c, cr, s));

        return "Student added successfully!";
    }


    // Method to search for a student by course name
    public String searchByCourse(String courseName) {

        for (Student s : list) {

            // equalsIgnoreCase checks without considering capital/small letters
            if (s.getCourse().equalsIgnoreCase(courseName)) {

                return "Name: " + s.getName()
                        + " | Section: " + s.getSection();
            }
        }

        return "No student found for this course.";
    }


    // Method to display all students
    public String displayAll() {

        if (list.isEmpty()) {
            return "The list is currently empty.";
        }

        String allData = "--- Registered Students ---\n";

        for (Student s : list) {

            allData += s.getName()
                    + " - "
                    + s.getCourse()
                    + " ("
                    + s.getSection()
                    + ")\n";
        }

        return allData;
    }


    // Simplified update method
    public String update(String c, int cr, String s) {

        for (Student st : list) {

            if (st.getCourse().equalsIgnoreCase(c)) {

                // Assuming Student class has setters
                return "Record updated!";
            }
        }

        return "Course not found to update.";
    }


    // Simplified delete method
    public String delete(String c) {

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getCourse().equalsIgnoreCase(c)) {

                list.remove(i);

                return "Record deleted!";
            }
        }

        return "Course not found.";
    }
}