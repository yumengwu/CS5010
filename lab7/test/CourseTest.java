import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * A JUnit test class for Course.
 */
public class CourseTest {
  private Course course;

  @Test
  public void testValidCourse() {
    course = new Course("CS5010", "A");
    assertEquals("CS5010", course.getCourse());
    assertEquals("A", course.getGrade());
  }

  @Test
  public void testInvalidCourse1() {
    try {
      course = new Course(null, "A");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Course cannot be null or empty string.", e.getMessage());
    }
    try {
      course = new Course("", "A");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Course cannot be null or empty string.", e.getMessage());
    }
  }

  @Test
  public void testInvalidCourse2() {
    try {
      course = new Course("CS5004", null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Grade cannot be null or empty string.", e.getMessage());
    }
    try {
      course = new Course("CS5004", "");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Grade cannot be null or empty string.", e.getMessage());
    }
  }
}