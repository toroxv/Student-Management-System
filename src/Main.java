
//Import packages
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;

public class Main {
    private static final int CAPACITY = 100; // Maximum number of students declared as 100 in the array
    private static final Student[] students = new Student[CAPACITY]; // Array to store student details
    private static int studentCount = 0; // Number of students registered

    /**
     * Main method to run the program.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMenu();
            int choice = -1;  // -1 to indicate it's an invalid input
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a number.");
                scanner.nextLine();  // Clear the invalid input
                continue;  // Restart the loop
            }catch (Exception e) {
                System.err.println("Invalid input. Try again.");
                scanner.nextLine();  // Clear the invalid input
                continue;
            }

            // Process the choice
            switch (choice) {
                case 1:
                    checkAvailableSeats();
                    break;
                case 2:
                    registerStudent(scanner);
                    break;
                case 3:
                    System.out.print("Enter Student ID to delete: ");
                    String deleteId = scanner.nextLine();
                    deleteStudent(deleteId);
                    break;
                case 4:
                    System.out.print("Enter Student ID to find: ");
                    String findId = scanner.nextLine();
                    findStudent(findId);
                    break;
                case 5:
                    storeStudentDetailsToFile();
                    break;
                case 6:
                    loadStudentDetails();
                    break;
                case 7:
                    viewStudentsSortedByName();
                    break;
                case 8:
                    handleAdditionalControls(scanner);
                    break;
                case 9:
                    System.out.println("Exiting the system.");

                    return;
                default:
                    System.out.println("Invalid choice. Please enter a value between 1 and 9.");
            }
        }
    }

    /**
     * Displays the menu options to the user.
     */
    private static void showMenu() {
        System.out.print("""
                
                *************************************
                                MENU
                *************************************
                1. Check available seats
                2. Register student (with ID)
                3. Delete student
                4. Find student by ID
                5. Store student details into a file
                6. Load student details from a file
                7. View all students
                8. Additional controls
                9. Exit
                
                Enter your choice :\s""");
    }

    /**
     * Checks if there are available seats for the next module.
     * If there are available seats, it displays the available seats.
     * If there are no available seats, it displays a message indicating that all seats are booked.
     */
    private static void checkAvailableSeats() {
        int availableSeats = CAPACITY - studentCount;
        System.out.println("Available seats: " + availableSeats);
    }

    /**
     * Registers a new student with the given ID and name.
     * If the ID is already taken, it displays an error message.
     * If the ID is valid, it creates a new Student object and adds it to the students array.
     * It also updates the studentCount variable.
     * @param scanner the Scanner object to read user input
     */
    private static void registerStudent(Scanner scanner) {
        if (studentCount >= CAPACITY) {
            System.out.println("No available seats.");
            return;
        }

        String id;
        while (true) {
            System.out.print("Enter Student ID (wXXXXXXX): ");
            id = scanner.nextLine();
            if (isValidID(id)) {
                if (!isDuplicateId(id)) {
                    break;
                } else {
                    System.out.println("Student ID already exists.");
                }
            } else {
                System.out.println("Invalid ID format. The ID should start with 'w' followed by 7 digits.");
            }
        }

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        if (!isValidStudentName(name)) {
            System.out.println("Invalid student name. The name should not be empty.");
            return;
        }

        Student student = new Student(id, name);
        students[studentCount] = student;
        studentCount++;
        System.out.println("Student registered successfully.");
    }

    /**
     * Checks if rhe ID format is correct
     * First letter should be W and followed by numbers
     * @param id the ID to check
     * @return true if it meets the criteria
     */
    private static boolean isValidID(String id) {
        if (id.length() != 8 || !id.startsWith("w")) {
            return false;
        }
        for (int i = 1; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * Checks if the ID is a duplicate.
     *
     * @param id the ID to check
     * @return true if the ID is a duplicate, false otherwise
     */
    private static boolean isDuplicateId(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the student name is valid.
     * It checks if the name is not empty.
     *
     * @param name the student name to check
     * @return true if the name is valid, false otherwise
     */
    private static boolean isValidStudentName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Deletes a student with the given ID.
     * If the ID is not found, it displays an error message.
     * If the ID is found, it shifts the elements to the left to fill the gap and deletes the student.
     * @param id the ID of the student to delete
     */
    private static void deleteStudent(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentID().equals(id)) {
                // Shift elements to the left to fill the gap
                for (int j = i; j < studentCount - 1; j++) {
                    students[j] = students[j + 1];
                }
                studentCount--;
                students[studentCount] = null;
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student with that ID not found.");
    }

    /**
     * Finds a student with the given ID.
     * If the ID is not found, it displays an error message.
     * If the ID is found, it displays the details of the student.
     *
     * @param id the ID of the student to find
     */
    private static void findStudent(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentID().equals(id)) {
                students[i].displayDetails();
                return;
            }
        }
        System.out.println("Student not found.");
    }

    /**
     * Stores the student details into a file.
     * If the file already exists, it overwrites the existing data.
     * If the file does not exist, it creates a new file.
     */
    private static void storeStudentDetailsToFile() {
        if (studentCount == 0) {
            System.out.println("No student details to store.");
            return;
        }
        File file = new File("student_details.txt");
        try (FileWriter fileWriter = new FileWriter(file)) {
            for (int i = 0; i < studentCount; i++) {
                Module marks = students[i].getModuleMarks();
                fileWriter.write(students[i].getStudentID() + ","
                        + students[i].getStudentName() + ","
                        + marks.getMark1() + ","
                        + marks.getMark2() + ","
                        + marks.getMark3() + "\n");
            }
            System.out.println("Student details stored successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file. Try again");
        }
    }

    /**
     * Loads the student details from a file.
     * If the file does not exist, it displays an error message.
     * If the file is empty, it displays a message indicating that no data was found.
     * If the file is not empty, it loads the data into the students array.
     */
    private static void loadStudentDetails() {
        File file = new File("student_details.txt");
        if (!file.exists()) {
            System.out.println("No data file found to load.");
            return;
        }
        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine() && studentCount < CAPACITY) {
                String line = fileReader.nextLine();
                String[] studentDetails = line.split(",");
                if (studentDetails.length == 5) {
                    String id = studentDetails[0];
                    String name = studentDetails[1];

                    // Checks if it is a valid ID
                    if (!isValidID(id)) {
                        System.out.println("Invalid ID format found in file: " + id + "and skipped");
                        continue;
                    }

                    // Initializing marks
                    double mark1;
                    double mark2;
                    double mark3;

                    // Check if the Marks are Double values
                    try {
                        // Converting String values into Integer values
                        mark1 = Double.parseDouble(studentDetails[2]);
                        mark2 = Double.parseDouble(studentDetails[3]);
                        mark3 = Double.parseDouble(studentDetails[4]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid marks found in file for ID: " + id);
                        continue;
                    }

                    // Checks if the ID is not a duplicate
                    if (!isDuplicateId(id)) {
                        Student student = new Student(id, name);
                        student.setModuleMarks(mark1, mark2, mark3);
                        students[studentCount] = student;
                        studentCount++;
                    } else {
                        System.out.println("Duplicate ID found in file: " + id + " and skipped");
                    }
                } else {
                    System.out.println("Invalid data in file. Please check the file format. File name: " + file.getName());
                }
            }
            System.out.println("Student details loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while loading file. " + e.getMessage());
        }
    }

    /**
     * Displays the details of all students sorted by their names in ascending order.
     * This method first creates an array of student indexes, sorts them by name, and then
     * displays the details of each student in the sorted order.
     */
    private static void viewStudentsSortedByName() {
        if (studentCount == 0) {
            System.out.println("No student information is currently available to display.");
            return;
        }

        int[] sortedIndexes = new int[studentCount];
        for (int i = 0; i < studentCount; i++) {
            sortedIndexes[i] = i;
        }

        sortIndexByName(sortedIndexes);
        for (int index : sortedIndexes) {
            students[index].displayDetailsShort();
        }
    }


    /**
     * Sorts an array of student indexes based on the students' names in ascending order.
     * This method uses the bubble sort algorithm to sort the indexes.
     *
     * @param indexes an array of indexes representing the students to be sorted.
     */
    private static void sortIndexByName(int[] indexes) {
        int n = indexes.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (students[indexes[j]].getStudentName().compareToIgnoreCase(students[indexes[j + 1]].getStudentName()) > 0) {
                    int temp = indexes[j];
                    indexes[j] = indexes[j + 1];
                    indexes[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Handles additional controls for managing student information.
     * This method provides a menu for adding student names and module marks.
     *
     * @param scanner the Scanner object for reading user input.
     */
    private static void handleAdditionalControls(Scanner scanner) {
        while (true) {
            System.out.print("""
                
                ***** Additional Controls *****
                1. Add student name
                2. Add module marks
                3. Generate summary report
                4. Generate complete report
                5. Back to main menu
                Enter your choice:\s""");

            int choice = -1;  // Default invalid choice
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume the remaining newline character
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please try again.");
                scanner.nextLine();  // Consume the remaining newline character
                continue;  // Restart the loop to ask for input again
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String id = scanner.nextLine();
                    Student student1 = findStudentByID(id);
                    if (student1 != null) {
                        System.out.print("Enter Student Name: ");
                        String name = scanner.nextLine();
                        if (isValidStudentName(name)) {
                            student1.setStudentName(name);
                            System.out.println("Student name updated successfully.");
                        } else {
                            System.out.println("Invalid student name. The name should not be empty.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 2:
                    System.out.print("Enter Student ID: ");
                    id = scanner.nextLine();
                    Student student2 = findStudentByID(id);
                    if (student2 != null) { // Check if student exists
                        try {
                            System.out.print("Enter Module 1 Marks: ");
                            double mark1 = scanner.nextDouble();
                            System.out.print("Enter Module 2 Marks: ");
                            double mark2 = scanner.nextDouble();
                            System.out.print("Enter Module 3 Marks: ");
                            double mark3 = scanner.nextDouble();
                            scanner.nextLine();  // Consume the remaining newline character

                            // Validate marks are within a reasonable range of 0 to 100
                            if (mark1 < 0 || mark1 > 100 || mark2 < 0 || mark2 > 100 || mark3 < 0 || mark3 > 100) {
                                System.out.println("Invalid marks. Please enter marks between 0 and 100.");
                            } else {
                                student2.setModuleMarks(mark1, mark2, mark3); // Update marks
                                System.out.println("Module marks updated successfully.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid marks. Please enter valid integers.");
                            scanner.nextLine();  // Consume the remaining invalid input
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 3:
                    generateSummaryReport();
                    break;

                case 4:
                    generateCompleteReport();
                    break;

                case 5:
                    return;  // Back to main menu

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Finds and returns a student by their unique ID.
     *
     * @param id The ID of the student to be checked.
     * @return Returns the matching student if found, null otherwise.
     */
    private static Student findStudentByID(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentID().equals(id)) {
                return students[i];
            }
        }
        return null;
    }

    /**
     * Generates a summary report of the student registration details.
     * This method iterates through all the students and calculates the total number of students,
     * the number of students scoring more than 40 marks in all modules, and the number of students scoring more than 40 marks in each module.
     */
    private static void generateSummaryReport() {
        int totalStudents = studentCount;
        int studentsAbove40InAllModules = 0;

        // Declaring passModule1, passModule2, passModule3 as 0
        int passModule1 = 0;
        int passModule2 = 0;
        int passModule3 = 0;

        for (int i = 0; i < studentCount; i++) {
            double marks1 = students[i].getModuleMarks().getMark1();
            double marks2 = students[i].getModuleMarks().getMark2();
            double marks3 = students[i].getModuleMarks().getMark3();

            // Checking if marks are greater than 40 in each module
            if (marks1 > 40) {
                passModule1++;
            }
            if (marks2 > 40) {
                passModule2++;
            }
            if (marks3 > 40) {
                passModule3++;
            }

            // Checking if marks are greater than 40 in all modules
            if (marks1 > 40 && marks2 > 40 && marks3 > 40) {
                studentsAbove40InAllModules++;
            }
        }

        System.out.println("Summary Report:");

        System.out.println("Total student registrations: " + totalStudents);

        System.out.println("Total students scoring more than 40 marks in Module 1: " + passModule1);
        System.out.println("Total students scoring more than 40 marks in Module 2: " + passModule2);
        System.out.println("Total students scoring more than 40 marks in Module 3: " + passModule3);

        System.out.println("Total students scoring more than 40 marks in all modules: " + studentsAbove40InAllModules);
    }

    /**
     * Generates a detailed report of the student registration details.
     * This method iterates through all the students and calculates the average marks for each module.
     * It then sorts the students based on their average marks in descending order.
     * It also displays the student ID, name, and average marks for each student.
     */
    private static void generateCompleteReport() {

        if (studentCount == 0) {
            System.out.println("\nNo student information available to display.");
            return;
        }
        // Sort the averages and get the sorted indices
        double[] sortedavg = sort();
        int[] index = new int[studentCount];

        // Print the detailed report header
        System.out.println("\nDetailed Report:\n");
        System.out.printf("%-15s %-20s %-16s %-16s %-16s %-10s %-10s %-10s\n",
                "Student ID", "Student Name", "Module 1", "Module 2", "Module 3",
                "Total", "Average", "Grade");
        System.out.println("--------------------------------------------------------------------------------------------------------------");

        // Find the index of each student's average marks in the sorted array
        for (int i = 0; i < studentCount; i++) {
            for (int j = 0; j < studentCount; j++) {
                if (students[i].getAverageMarks() == sortedavg[j]) {
                    index[j] = i;
                    break;
                }
            }
        }

        // Print the student details in the order of sorted averages
        for (int i = 0; i < studentCount; i++) {
            int studentIndex = index[i];
            String id = students[studentIndex].getStudentID();
            String name = students[studentIndex].getStudentName();
            double marks1 = students[studentIndex].getModuleMarks().getMark1();
            double marks2 = students[studentIndex].getModuleMarks().getMark2();
            double marks3 = students[studentIndex].getModuleMarks().getMark3();
            double total = marks1 + marks2 + marks3;
            double average = total / 3;
            char grade = students[studentIndex].getGrade(average);
            System.out.printf("%-15s %-20s %-16.2f %-16.2f %-16.2f %-10.2f %-10.2f %-10c\n", id, name, marks1, marks2, marks3, total, average, grade);
        }
    }

    private static double[] sort() {
        double[] average = new double[studentCount];

        // Fill the array with average marks
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getAverageMarks() != 0) {
                average[i] = students[i].getAverageMarks();
            }
        }


        // Sort the array in descending order
        for (int k = 0; k < studentCount - 1; k++) {
            for (int j = 0; j < studentCount - k - 1; j++) {
                if (average[j] < average[j + 1]) {
                    double temp = average[j];
                    average[j] = average[j + 1];
                    average[j + 1] = temp;
                }
            }
        }

        return average;
    }

}
