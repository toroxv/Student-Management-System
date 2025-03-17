class Student {
    private String studentID;
    private String studentName;
    private Module moduleMarks = new Module (0,0,0);

    /**
     * Constructor to create a new student object.
     * @param studentID ID of the student.
     * @param studentName Name of the student.
     */
    public Student(String studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
    }

    /**
     * Getter for the student ID.
     * @return Student ID.
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Setter for the student ID.
     * @return Student ID.
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Setter for the student name.
     * @param studentName Name of the student.
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Getter for the module marks.
     * @return Module marks.
     */
    public Module getModuleMarks() {
        return moduleMarks;
    }

    /**
     * Setter for the module marks.
     * @param mark1 mark1 of the module.
     * @param mark2 mark2 of the module.
     * @param mark3 mark3 of the module.
     */
    public void setModuleMarks(double mark1, double mark2, double mark3) {
        moduleMarks.setMark1(mark1);
        moduleMarks.setMark2(mark2);
        moduleMarks.setMark3(mark3);
    }

    /**
     * Displays the student details.
     */
    public void displayDetails() {
        double mark1 = moduleMarks.getMark1();
        double mark2 = moduleMarks.getMark2();
        double mark3 = moduleMarks.getMark3();

        double total = mark1 + mark2 + mark3;
        double average = total / 3.0;
        char grade = getGrade(average);

        System.out.println("Student ID: " + studentID);
        System.out.println("    Student Name: " + studentName);
        System.out.println("    Module 1 Marks: " + mark1);
        System.out.println("    Module 2 Marks: " + mark2);
        System.out.println("    Module 3 Marks: " + mark3);
        System.out.println("    Total: " + total);
        System.out.println("    Average: " + average);
        System.out.println("    Grade: " + grade);
    }

    /**
     * Calculates the grade based on the average marks.
     * @param average Average marks of the student.
     * @return Grade of the student.
     */
    static char getGrade(double average) {
        if (average >= 70) {
            return 'A';
        } else if (average >= 60) {
            return 'B';
        } else if (average >= 50) {
            return 'C';
        } else if (average >= 40) {
            return 'D';
        } else {
            return 'F';
        }
    }

    /**
     * Displays the student details in short format.
     */
    public void displayDetailsShort() {
        double mark1 = moduleMarks.getMark1();
        double mark2 = moduleMarks.getMark2();
        double mark3 = moduleMarks.getMark3();

        double total = mark1 + mark2 + mark3;
        double average = total / 3.0;
        char grade = getGrade(average);

        System.out.printf("%-10s %-20s %-10.2f %-10c%n", studentID, studentName, average, grade);
    }

    /**
     * Calculates the average marks of the student.
     * @return Average marks of the student.
     */
    double getAverageMarks() {
        double mark1 = moduleMarks.getMark1();
        double mark2 = moduleMarks.getMark2();
        double mark3 = moduleMarks.getMark3();

        double total = mark1 + mark2 + mark3;
        return total / 3;
    }

}
