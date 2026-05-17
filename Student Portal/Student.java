public class Student {

    private String name;
    private String course;
    private String section;
    private int credit;



    // ==========================
    // Constructor
    // ==========================

    public Student(
            String name,
            String course,
            int credit,
            String section
    ) {

        this.name = name;
        this.course = course;
        this.credit = credit;
        this.section = section;
    }




    // ==========================
    // Setter Methods
    // ==========================

    public void setName(String p) {

        this.name = p;
    }


    public void setCourse(String c) {

        this.course = c;
    }


    public void setCredit(int cr) {

        this.credit = cr;
    }


    public void setSection(String s) {

        this.section = s;
    }




    // ==========================
    // Getter Methods
    // ==========================

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
