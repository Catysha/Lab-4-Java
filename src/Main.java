import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<GradeBook> students = new ArrayList<>();
        String studentsInputFile = "students.txt";
        GradeBook.setStudentFromFile(students, studentsInputFile);
        String[] examInputFiles = {"AISD.txt", "MA.txt", "IP.txt"};
        for (String examFile : examInputFiles) {
            GradeBook.importExamReport(students, examFile);
        }
        String excellentStudentsOutputFile = "output.txt";
        GradeBook.writeExcellentStudentsToFile(excellentStudentsOutputFile, students);
        GradeBook.writeExcellentStudentsToJSONFile("output.json", students);
    }
}