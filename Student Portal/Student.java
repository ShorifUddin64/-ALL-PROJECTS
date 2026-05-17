public class Student {
    private String name, course, section;
    private int credit;

    public Student(String name, String course, int credit, String section) {
        this.name = name;
        this.course = course;
        this.credit = credit;
        this.section = section;
    }
     public void setName(String p) {
     this.course = p; 
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

    