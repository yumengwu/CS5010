import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class represent a student subject implements StudentSubject interface. A
 * student object could add course, add observer, notify observers, get course a
 * map, and get program type.
 */
public class Student implements StudentSubject {
  private List<Observer> observers;
  private Map<String, String> courses;
  private ProgramType type;

  /**
   * Construct a Student object by given program type.
   * @param programType program type
   */
  public Student(ProgramType programType) {
    if (programType == null) {
      throw new IllegalArgumentException("Program type cannot be null.");
    }
    this.type = programType;
    this.observers = new LinkedList<>();
    this.courses = new TreeMap<>();
  }

  /**
   * Add a observer to this subject. If observer is null, this method will throw
   * IllegalArgumentException.
   * @param observer observer object
   */
  public void addObserver(Observer observer) {
    if (observer == null) {
      throw new IllegalArgumentException("Observer cannot be null.");
    }
    this.observers.add(observer);
    observer.update();
  }

  /**
   * Private helper method to check if a string is a valid course symbol.
   * @param c given course symbol
   */
  private void checkCourseNumber(String c) {
    if (c.length() != 6 || !c.substring(0, 2).equals("CS")) {
      throw new IllegalArgumentException("Invalid course.");
    }
    for (int i = 2; i < c.length(); ++i) {
      if (!(c.charAt(i) >= '0' && c.charAt(i) <= '9')) {
        throw new IllegalArgumentException("Invalid course.");
      }
    }
  }

  /**
   * Private helper method to check if a string is a valid course grade.
   * @param g given grade
   */
  private void checkCourseGrade(String g) {
    if (!(g.length() == 1 || g.length() == 2)) {
      throw new IllegalArgumentException("Invalid grade.");
    }
    if (!(g.charAt(0) == 'A' || g.charAt(0) == 'B' || g.charAt(0) == 'C' || g.charAt(0) == 'D'
            || g.charAt(0) == 'F')) {
      throw new IllegalArgumentException("Invalid grade.");
    }
    if (g.length() == 2) {
      if (!(g.charAt(1) == '+' || g.charAt(1) == '-')) {
        throw new IllegalArgumentException("Invalid grade.");
      }
      if (g.charAt(0) == 'F') {
        throw new IllegalArgumentException("Invalid grade.");
      }
      if (g.equals("A+")) {
        throw new IllegalArgumentException("Invalid grade.");
      }
    }
  }

  /**
   * Add a course to this student subject. For MSCS and ALIGN student, course symbol
   * must start with "CS" and 4 digits following "CS". Course grade must be A, A-, B+,
   * B, B-, C+, C, C-, D+, D, D-, or F.
   * @param course course object
   */
  public void addCourse(Course course) {
    if (course == null) {
      throw new IllegalArgumentException("Course cannot be null.");
    }
    String courseNumber = course.getCourse();
    checkCourseNumber(courseNumber);
    String courseGrade = course.getGrade();
    checkCourseGrade(courseGrade);
    this.courses.put(courseNumber, courseGrade);
  }

  /**
   * Get course map of this subject. This method will return a copy of map. Map will
   * Map&lt;String, String&gt;.
   * @return course map
   */
  public final Map<String, String> getCourse() {
    Map<String, String> res = new TreeMap<>();
    for (String c : this.courses.keySet()) {
      res.put(c, this.courses.get(c));
    }
    return res;
  }

  /**
   * Return program type.
   * @return program type
   */
  public ProgramType getProgramType() {
    return this.type;
  }

  /**
   * Notify all the observers of this subject.
   */
  public void notifyObservers() {
    for (Observer o : this.observers) {
      o.update();
    }
  }
}
