import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.util.Map;

/**
 * A JUnit test class for subject.
 */
public class SubjectTest {
  private StudentSubject subject;

  @Test
  public void testInvalidConstructor() {
    try {
      subject = new Student(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Program type cannot be null.", e.getMessage());
    }
  }

  @Test
  public void testValidConstructor() {
    try {
      subject = new Student(ProgramType.MSCS);
    }
    catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testAddInvalidCourse1() {
    subject = new Student(ProgramType.MSCS);
    try {
      subject.addCourse(new Course("abcd", "A"));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid course.", e.getMessage());
    }
  }

  @Test
  public void testAddInvalidCourse2() {
    subject = new Student(ProgramType.MSCS);
    try {
      subject.addCourse(new Course("IS4200", "A"));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid course.", e.getMessage());
    }
  }

  @Test
  public void testAddInvalidCourse3() {
    subject = new Student(ProgramType.MSCS);
    try {
      subject.addCourse(new Course("CS520", "A"));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid course.", e.getMessage());
    }
  }

  @Test
  public void testAddInvalidCourse4() {
    subject = new Student(ProgramType.MSCS);
    try {
      subject.addCourse(new Course("CS52000", "A"));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid course.", e.getMessage());
    }
  }

  @Test
  public void testAddInvalidCourse5() {
    subject = new Student(ProgramType.MSCS);
    try {
      subject.addCourse(new Course("CS5004", "4"));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid grade.", e.getMessage());
    }
  }

  @Test
  public void testAddInvalidCourse6() {
    subject = new Student(ProgramType.MSCS);
    try {
      subject.addCourse(new Course("CS5004", "a"));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid grade.", e.getMessage());
    }
  }

  @Test
  public void testAddInvalidCourse7() {
    subject = new Student(ProgramType.MSCS);
    try {
      subject.addCourse(new Course("CS5004", "A+"));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid grade.", e.getMessage());
    }
  }

  @Test
  public void testAddInvalidCourse8() {
    subject = new Student(ProgramType.MSCS);
    try {
      subject.addCourse(new Course("CS5004", "F+"));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid grade.", e.getMessage());
    }
  }

  @Test
  public void testAddInvalidCourse9() {
    subject = new Student(ProgramType.MSCS);
    try {
      subject.addCourse(new Course("CS5004", "A"));
      subject.addCourse(new Course("cs5005", "A"));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid course.", e.getMessage());
    }
  }

  @Test
  public void testAddInvalidCourse10() {
    subject = new Student(ProgramType.MSCS);
    try {
      subject.addCourse(new Course("CS5004", "A"));
      subject.addCourse(new Course("CS5005", "a"));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid grade.", e.getMessage());
    }
  }

  @Test
  public void testValidAddCourse() {
    subject = new Student(ProgramType.MSCS);
    try {
      subject.addCourse(new Course("CS5004", "A"));
      subject.addCourse(new Course("CS5100", "A-"));
      subject.addCourse(new Course("CS5200", "B+"));
      subject.addCourse(new Course("CS5500", "B"));
      subject.addCourse(new Course("CS5600", "B-"));
      subject.addCourse(new Course("CS5800", "C+"));
      subject.addCourse(new Course("CS6120", "C"));
      subject.addCourse(new Course("CS6140", "C-"));
      subject.addCourse(new Course("CS6200", "D+"));
      subject.addCourse(new Course("CS6220", "D"));
      subject.addCourse(new Course("CS6400", "D-"));
      subject.addCourse(new Course("CS6600", "F"));
      subject.addCourse(new Course("CS6220", "A"));
    }
    catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testGetProgramType1() {
    subject = new Student(ProgramType.MSCS);
    assertEquals(ProgramType.MSCS, subject.getProgramType());
  }

  @Test
  public void testGetProgramType2() {
    subject = new Student(ProgramType.ALIGN);
    assertEquals(ProgramType.ALIGN, subject.getProgramType());
  }

  @Test
  public void testGetCourse() {
    subject = new Student(ProgramType.MSCS);
    Map<String, String> map = subject.getCourse();
    assertEquals(0, map.size());
    subject.addCourse(new Course("CS5004", "A"));
    subject.addCourse(new Course("CS5100", "A-"));
    subject.addCourse(new Course("CS5200", "B+"));
    subject.addCourse(new Course("CS5500", "B"));
    map = subject.getCourse();
    assertEquals(4, map.size());
    assertEquals("A", map.get("CS5004"));
    assertEquals("A-", map.get("CS5100"));
    assertEquals("B+", map.get("CS5200"));
    assertEquals("B", map.get("CS5500"));
    subject.addCourse(new Course("CS5800", "C+"));
    subject.addCourse(new Course("CS6120", "C"));
    subject.addCourse(new Course("CS5800", "A"));
    map = subject.getCourse();
    assertEquals(6, map.size());
    assertEquals("A", map.get("CS5800"));
    assertEquals("C", map.get("CS6120"));
  }

  @Test
  public void testAddObserver1() {
    subject = new Student(ProgramType.MSCS);
    try {
      subject.addObserver(new ObserveGraduate(subject));
    }
    catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testAddObserver2() {
    subject = new Student(ProgramType.MSCS);
    try {
      subject.addObserver(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Observer cannot be null.", e.getMessage());
    }
  }
}