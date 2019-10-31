/**
 * This class represent a course object. A course could have course symbol and grade.
 */
public class Course {
  private String course;
  private String grade;

  /**
   * Construct a course object. If given course symbol or grade is null or empty string,
   * this method will throw IllegalArgumentException. Course symbol and grade could be
   * any format.
   * @param course course symbol
   * @param grade grade
   */
  public Course(String course, String grade) {
    if (course == null || course.length() == 0) {
      throw new IllegalArgumentException("Course cannot be null or empty string.");
    }
    if (grade == null || grade.length() == 0) {
      throw new IllegalArgumentException("Grade cannot be null or empty string.");
    }
    this.course = course;
    this.grade = grade;
  }

  /**
   * Return course.
   * @return course
   */
  public final String getCourse() {
    return this.course;
  }

  /**
   * Return grade.
   * @return grade
   */
  public final String getGrade() {
    return this.grade;
  }
}
