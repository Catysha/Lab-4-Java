import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class GradeBook {

    private String surname;
    private String name;
    private int course;
    private int group;
    private int numberOfGradeBook;
    List<Session> sessions = new ArrayList<>();
    public class Session {

        private int numberOfSession;
        List<Exam> exams = new ArrayList<>();


        public Session(int numberOfSession) {
            this.numberOfSession = numberOfSession;
        }
        //Добавляет экзамен в сессию
        public void addExam(String lesson, int grade) {
            exams.add(new Exam(lesson, grade));
        }

        public List<Exam> getExams() {
            return exams;
        }
        public int getNumberOfSession() {
            return numberOfSession;
        }
        //Проверяет, есть ли в сессии экзамены с оценками ниже 9
        public boolean findNineInExams() {
            for (Exam exam : exams) {
                if (exam.grade < 9) {
                    return false;
                }
            }
            return true;
        }

    }

    public class Exam {

        private String subject;
        private int grade;

        public Exam(String subject, int grade) {
            this.subject = subject;
            this.grade = grade;
        }

        public String getSubject() {
            return subject;
        }

        public int getGrade() {
            return grade;
        }
    }
    private Session findSessionByNumber(int sessionNumber) {
        for (Session session : sessions) {
            if (session.numberOfSession == sessionNumber) {
                return session;
            }
        }
        return null;
    }
    private static GradeBook findGradeBook(List<GradeBook> myList, int studakNumber) {
        for (GradeBook gradeBook : myList) {
            if (gradeBook.numberOfGradeBook == studakNumber) {
                return gradeBook;
            }
        }
        return null;
    }
    public static void setStudentFromFile(List<GradeBook> myList, String inputFile) {
        try (Scanner scanner = new Scanner(new File(inputFile))) {
            while (scanner.hasNextLine()) {
                GradeBook gradeBook = new GradeBook();
                gradeBook.numberOfGradeBook = scanner.nextInt();
                gradeBook.name = scanner.next();
                gradeBook.surname = scanner.next();
                gradeBook.group = scanner.nextInt();
                gradeBook.course = scanner.nextInt();
                myList.add(gradeBook);
                scanner.nextLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void importExamReport(List<GradeBook> myList, String inputFile) {
        try (Scanner scanner = new Scanner(new File(inputFile))) {

            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] s = line.split(" ");

                int semester = Integer.parseInt(s[s.length - 1]);

                StringBuilder subject = new StringBuilder();
                for (int i = 0; i < s.length - 1; i++) {
                    subject.append(s[i]);
                    if (i < s.length - 2) {
                        subject.append(" ");
                    }
                }


                while (scanner.hasNextLine()) {

                    if (scanner.hasNextInt()) {
                        int studakNumber = scanner.nextInt();
                        int grade = scanner.nextInt();
                        GradeBook student = findGradeBook(myList, studakNumber);
                        if (student != null) {
                            GradeBook.Session session = student.findSessionByNumber(semester);
                            if (session == null) {
                                session = student.new Session(semester);
                                student.sessions.add(session);
                            }
                            session.addExam(subject.toString(), grade);
                        } else {
                            System.out.println("Студент с номером студенческого " + studakNumber + " не найден.");
                        }

                        if (scanner.hasNextLine()) {
                            scanner.nextLine();
                        }
                    } else {
                        System.out.println("Неверный формат данных. Ожидался номер студента.");
                        scanner.nextLine();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }
    }
    public String getSurname() {
        return surname;
    }
    public static void writeExcellentStudentsToFile(String outputFile, List<GradeBook> gradeBooks) {
        try (FileWriter writer = new FileWriter(outputFile)) {
            for (GradeBook gradeBook : gradeBooks) {


                for (Session session : gradeBook.sessions) {

                    if (session.findNineInExams()) {
                        writer.write("Сессия " + session.numberOfSession + "\n");
                        writer.write(gradeBook.surname + " " + gradeBook.name +  ", курс "
                                + gradeBook.course + ", группа " + gradeBook.group + "\n" );
                        for (Exam exam : session.getExams()) {

                            writer.write("Предмет : " + exam.getSubject() + "\nОценка : " + exam.getGrade() + "\n");
                        }
                        writer.write("\n");
                    }
                }
            }
            System.out.println("Файл с отличниками создан");
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
    }
}




