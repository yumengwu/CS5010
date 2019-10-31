import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * A JUnit test class for Observer.
 */
public class ObserverTest {
  private StudentSubject subject;
  private Observer goodStanding;
  private Observer goOnCoop;
  private Observer graduate;

  @Test
  public void testInvalidConstruct() {
    try {
      goodStanding = new ObserveGoodStanding(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Subject cannot be null.", e.getMessage());
    }
    try {
      goOnCoop = new ObserveGoOnCoop(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Subject cannot be null.", e.getMessage());
    }
    try {
      graduate = new ObserveGraduate(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Subject cannot be null.", e.getMessage());
    }
  }

  @Test
  public void testInitState() {
    subject = new Student(ProgramType.MSCS);
    goodStanding = new ObserveGoodStanding(subject);
    goOnCoop = new ObserveGoOnCoop(subject);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(goodStanding);
    subject.addObserver(goOnCoop);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), goodStanding.getState());
    assertEquals(new Boolean(false), goOnCoop.getState());
    assertEquals(new Boolean(false), graduate.getState());
  }

  @Test
  public void testGoodStanding1() {
    subject = new Student(ProgramType.MSCS);
    goodStanding = new ObserveGoodStanding(subject);
    subject.addObserver(goodStanding);
    assertEquals(new Boolean(false), goodStanding.getState());
    subject.addCourse(new Course("CS5100", "B"));
    subject.addCourse(new Course("CS5200", "B+"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), goodStanding.getState());
  }

  @Test
  public void testGoodStanding2() {
    subject = new Student(ProgramType.ALIGN);
    goodStanding = new ObserveGoodStanding(subject);
    subject.addObserver(goodStanding);
    assertEquals(new Boolean(false), goodStanding.getState());
    subject.addCourse(new Course("CS5100", "B"));
    subject.addCourse(new Course("CS5200", "B+"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), goodStanding.getState());
  }

  @Test
  public void testGoodStanding3() {
    subject = new Student(ProgramType.MSCS);
    goodStanding = new ObserveGoodStanding(subject);
    subject.addObserver(goodStanding);
    assertEquals(new Boolean(false), goodStanding.getState());
    subject.addCourse(new Course("CS5100", "C"));
    subject.addCourse(new Course("CS5200", "C+"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goodStanding.getState());
  }

  @Test
  public void testGoodStanding4() {
    subject = new Student(ProgramType.ALIGN);
    goodStanding = new ObserveGoodStanding(subject);
    subject.addObserver(goodStanding);
    assertEquals(new Boolean(false), goodStanding.getState());
    subject.addCourse(new Course("CS5100", "C"));
    subject.addCourse(new Course("CS5200", "C+"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goodStanding.getState());
  }

  @Test
  public void testGoodStanding5() {
    subject = new Student(ProgramType.MSCS);
    goodStanding = new ObserveGoodStanding(subject);
    subject.addObserver(goodStanding);
    assertEquals(new Boolean(false), goodStanding.getState());
    subject.addCourse(new Course("CS5100", "C"));
    subject.addCourse(new Course("CS5200", "C+"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goodStanding.getState());
    subject.addCourse(new Course("CS5100", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), goodStanding.getState());
  }

  @Test
  public void testGoodStanding6() {
    subject = new Student(ProgramType.ALIGN);
    goodStanding = new ObserveGoodStanding(subject);
    subject.addObserver(goodStanding);
    assertEquals(new Boolean(false), goodStanding.getState());
    subject.addCourse(new Course("CS5100", "C"));
    subject.addCourse(new Course("CS5200", "C+"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goodStanding.getState());
    subject.addCourse(new Course("CS5100", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), goodStanding.getState());
  }

  @Test
  public void testGoOnCoop1() {
    subject = new Student(ProgramType.MSCS);
    goOnCoop = new ObserveGoOnCoop(subject);
    subject.addObserver(goOnCoop);
    assertEquals(new Boolean(false), goOnCoop.getState());
    subject.addCourse(new Course("CS5100", "A"));
    subject.addCourse(new Course("CS5200", "A"));
    subject.addCourse(new Course("CS5300", "A"));
    subject.addCourse(new Course("CS5400", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), goOnCoop.getState());
  }

  @Test
  public void testGoOnCoop2() {
    subject = new Student(ProgramType.ALIGN);
    goOnCoop = new ObserveGoOnCoop(subject);
    subject.addObserver(goOnCoop);
    assertEquals(new Boolean(false), goOnCoop.getState());
    subject.addCourse(new Course("CS5100", "A"));
    subject.addCourse(new Course("CS5200", "A"));
    subject.addCourse(new Course("CS5300", "A"));
    subject.addCourse(new Course("CS5400", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), goOnCoop.getState());
  }

  @Test
  public void testGoOnCoop3() {
    subject = new Student(ProgramType.MSCS);
    goOnCoop = new ObserveGoOnCoop(subject);
    subject.addObserver(goOnCoop);
    assertEquals(new Boolean(false), goOnCoop.getState());
    subject.addCourse(new Course("CS5100", "C"));
    subject.addCourse(new Course("CS5200", "C"));
    subject.addCourse(new Course("CS5300", "C"));
    subject.addCourse(new Course("CS5400", "C"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goOnCoop.getState());
  }

  @Test
  public void testGoOnCoop4() {
    subject = new Student(ProgramType.ALIGN);
    goOnCoop = new ObserveGoOnCoop(subject);
    subject.addObserver(goOnCoop);
    assertEquals(new Boolean(false), goOnCoop.getState());
    subject.addCourse(new Course("CS5100", "C"));
    subject.addCourse(new Course("CS5200", "C"));
    subject.addCourse(new Course("CS5300", "C"));
    subject.addCourse(new Course("CS5400", "C"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goOnCoop.getState());
  }

  @Test
  public void testGoOnCoop5() {
    subject = new Student(ProgramType.MSCS);
    goOnCoop = new ObserveGoOnCoop(subject);
    subject.addObserver(goOnCoop);
    assertEquals(new Boolean(false), goOnCoop.getState());
    subject.addCourse(new Course("CS5100", "A"));
    subject.addCourse(new Course("CS5200", "A"));
    subject.addCourse(new Course("CS5300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goOnCoop.getState());
  }

  @Test
  public void testGoOnCoop6() {
    subject = new Student(ProgramType.ALIGN);
    goOnCoop = new ObserveGoOnCoop(subject);
    subject.addObserver(goOnCoop);
    assertEquals(new Boolean(false), goOnCoop.getState());
    subject.addCourse(new Course("CS5100", "A"));
    subject.addCourse(new Course("CS5200", "A"));
    subject.addCourse(new Course("CS5300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goOnCoop.getState());
  }

  @Test
  public void testGoOnCoop7() {
    subject = new Student(ProgramType.MSCS);
    goOnCoop = new ObserveGoOnCoop(subject);
    subject.addObserver(goOnCoop);
    assertEquals(new Boolean(false), goOnCoop.getState());
    subject.addCourse(new Course("CS5100", "C"));
    subject.addCourse(new Course("CS5200", "C"));
    subject.addCourse(new Course("CS5300", "C"));
    subject.addCourse(new Course("CS5400", "C"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goOnCoop.getState());
    subject.addCourse(new Course("CS5100", "A"));
    subject.addCourse(new Course("CS5200", "A"));
    subject.addCourse(new Course("CS5300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), goOnCoop.getState());
  }

  @Test
  public void testGoOnCoop8() {
    subject = new Student(ProgramType.MSCS);
    goOnCoop = new ObserveGoOnCoop(subject);
    subject.addObserver(goOnCoop);
    assertEquals(new Boolean(false), goOnCoop.getState());
    subject.addCourse(new Course("CS5100", "C"));
    subject.addCourse(new Course("CS5200", "C"));
    subject.addCourse(new Course("CS5300", "C"));
    subject.addCourse(new Course("CS5400", "C"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goOnCoop.getState());
    subject.addCourse(new Course("CS5100", "A"));
    subject.addCourse(new Course("CS5200", "A"));
    subject.addCourse(new Course("CS5300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), goOnCoop.getState());
  }

  @Test
  public void testGraduate1() {
    subject = new Student(ProgramType.MSCS);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5010", "A"));
    subject.addCourse(new Course("CS5800", "A"));
    subject.addCourse(new Course("CS5500", "A"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), graduate.getState());
  }

  @Test
  public void testGraduate2() {
    subject = new Student(ProgramType.MSCS);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5010", "A"));
    subject.addCourse(new Course("CS5800", "A"));
    subject.addCourse(new Course("CS5600", "A"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), graduate.getState());
  }

  @Test
  public void testGraduate3() {
    subject = new Student(ProgramType.ALIGN);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5004", "A"));
    subject.addCourse(new Course("CS5800", "A"));
    subject.addCourse(new Course("CS5500", "A"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), graduate.getState());
  }

  @Test
  public void testGraduate4() {
    subject = new Student(ProgramType.ALIGN);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5004", "A"));
    subject.addCourse(new Course("CS5800", "A"));
    subject.addCourse(new Course("CS5600", "A"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), graduate.getState());
  }

  @Test
  public void testGraduate5() {
    subject = new Student(ProgramType.MSCS);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5004", "A"));
    subject.addCourse(new Course("CS5800", "A"));
    subject.addCourse(new Course("CS5500", "A"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), graduate.getState());
  }

  @Test
  public void testGraduate6() {
    subject = new Student(ProgramType.ALIGN);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5010", "A"));
    subject.addCourse(new Course("CS5800", "A"));
    subject.addCourse(new Course("CS5500", "A"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), graduate.getState());
  }

  @Test
  public void testGraduate7() {
    subject = new Student(ProgramType.MSCS);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5010", "A"));
    subject.addCourse(new Course("CS5800", "A"));
    subject.addCourse(new Course("CS5500", "A"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), graduate.getState());
  }

  @Test
  public void testGraduate8() {
    subject = new Student(ProgramType.ALIGN);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5004", "A"));
    subject.addCourse(new Course("CS5800", "A"));
    subject.addCourse(new Course("CS5500", "A"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), graduate.getState());
  }

  @Test
  public void testGraduate9() {
    subject = new Student(ProgramType.MSCS);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5010", "C"));
    subject.addCourse(new Course("CS5800", "C"));
    subject.addCourse(new Course("CS5500", "C"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), graduate.getState());
  }

  @Test
  public void testGraduate10() {
    subject = new Student(ProgramType.ALIGN);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5004", "C"));
    subject.addCourse(new Course("CS5800", "C"));
    subject.addCourse(new Course("CS5500", "C"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), graduate.getState());
  }

  @Test
  public void testGraduate11() {
    subject = new Student(ProgramType.MSCS);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5010", "B"));
    subject.addCourse(new Course("CS5800", "B"));
    subject.addCourse(new Course("CS5500", "C"));
    subject.addCourse(new Course("CS5600", "B+"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), graduate.getState());
  }

  @Test
  public void testGraduate12() {
    subject = new Student(ProgramType.ALIGN);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5004", "B"));
    subject.addCourse(new Course("CS5800", "B"));
    subject.addCourse(new Course("CS5500", "C"));
    subject.addCourse(new Course("CS5600", "B+"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), graduate.getState());
  }

  @Test
  public void testGraduate13() {
    subject = new Student(ProgramType.MSCS);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5010", "A"));
    subject.addCourse(new Course("CS5900", "A"));
    subject.addCourse(new Course("CS5500", "A"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), graduate.getState());
  }

  @Test
  public void testGraduate14() {
    subject = new Student(ProgramType.ALIGN);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5004", "A"));
    subject.addCourse(new Course("CS5900", "A"));
    subject.addCourse(new Course("CS5500", "A"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), graduate.getState());
  }

  @Test
  public void testGraduate15() {
    subject = new Student(ProgramType.MSCS);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5010", "B"));
    subject.addCourse(new Course("CS5800", "B"));
    subject.addCourse(new Course("CS5500", "C"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5500", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), graduate.getState());
  }

  @Test
  public void testGraduate16() {
    subject = new Student(ProgramType.ALIGN);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5004", "B"));
    subject.addCourse(new Course("CS5800", "B"));
    subject.addCourse(new Course("CS5500", "C"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5500", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), graduate.getState());
  }

  @Test
  public void testMultiOperation1() {
    subject = new Student(ProgramType.MSCS);
    goodStanding = new ObserveGoodStanding(subject);
    goOnCoop = new ObserveGoOnCoop(subject);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(goodStanding);
    subject.addObserver(goOnCoop);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), goodStanding.getState());
    assertEquals(new Boolean(false), goOnCoop.getState());
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5010", "B"));
    subject.addCourse(new Course("CS5200", "C+"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goodStanding.getState());
    assertEquals(new Boolean(false), goOnCoop.getState());
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5300", "B+"));
    subject.addCourse(new Course("CS5400", "B+"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goodStanding.getState());
    assertEquals(new Boolean(false), goOnCoop.getState());
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5800", "C-"));
    subject.addCourse(new Course("CS5500", "C-"));
    subject.addCourse(new Course("CS6300", "C"));
    subject.addCourse(new Course("CS6400", "C"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goodStanding.getState());
    assertEquals(new Boolean(false), goOnCoop.getState());
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5800", "A"));
    subject.addCourse(new Course("CS5500", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goodStanding.getState());
    assertEquals(new Boolean(false), goOnCoop.getState());
    assertEquals(new Boolean(true), graduate.getState());
    subject.addCourse(new Course("CS6300", "A"));
    subject.addCourse(new Course("CS6400", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), goodStanding.getState());
    assertEquals(new Boolean(true), goOnCoop.getState());
    assertEquals(new Boolean(true), graduate.getState());
  }

  @Test
  public void testMultiOperation2() {
    subject = new Student(ProgramType.ALIGN);
    goodStanding = new ObserveGoodStanding(subject);
    goOnCoop = new ObserveGoOnCoop(subject);
    graduate = new ObserveGraduate(subject);
    subject.addObserver(goodStanding);
    subject.addObserver(goOnCoop);
    subject.addObserver(graduate);
    assertEquals(new Boolean(false), goodStanding.getState());
    assertEquals(new Boolean(false), goOnCoop.getState());
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5004", "B"));
    subject.addCourse(new Course("CS5200", "C+"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goodStanding.getState());
    assertEquals(new Boolean(false), goOnCoop.getState());
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5300", "B+"));
    subject.addCourse(new Course("CS5400", "B+"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goodStanding.getState());
    assertEquals(new Boolean(false), goOnCoop.getState());
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5800", "C-"));
    subject.addCourse(new Course("CS5500", "C-"));
    subject.addCourse(new Course("CS6300", "C"));
    subject.addCourse(new Course("CS6400", "C"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goodStanding.getState());
    assertEquals(new Boolean(false), goOnCoop.getState());
    assertEquals(new Boolean(false), graduate.getState());
    subject.addCourse(new Course("CS5800", "A"));
    subject.addCourse(new Course("CS5500", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(false), goodStanding.getState());
    assertEquals(new Boolean(false), goOnCoop.getState());
    assertEquals(new Boolean(true), graduate.getState());
    subject.addCourse(new Course("CS6300", "A"));
    subject.addCourse(new Course("CS6400", "A"));
    subject.notifyObservers();
    assertEquals(new Boolean(true), goodStanding.getState());
    assertEquals(new Boolean(true), goOnCoop.getState());
    assertEquals(new Boolean(true), graduate.getState());
  }

  @Test
  public void testSetSubject() {
    subject = new Student(ProgramType.MSCS);
    subject.addCourse(new Course("CS5010", "A"));
    subject.addCourse(new Course("CS5800", "A"));
    subject.addCourse(new Course("CS5500", "A"));
    subject.addCourse(new Course("CS5700", "A"));
    subject.addCourse(new Course("CS6000", "A"));
    subject.addCourse(new Course("CS6100", "A"));
    subject.addCourse(new Course("CS6200", "A"));
    subject.addCourse(new Course("CS6300", "A"));
    StudentSubject s2 = new Student(ProgramType.MSCS);
    goodStanding = new ObserveGoodStanding(s2);
    goOnCoop = new ObserveGoOnCoop(s2);
    graduate = new ObserveGraduate(s2);
    assertEquals(new Boolean(false), goodStanding.getState());
    assertEquals(new Boolean(false), goOnCoop.getState());
    assertEquals(new Boolean(false), graduate.getState());
    goodStanding.setSubject(subject);
    goOnCoop.setSubject(subject);
    graduate.setSubject(subject);
    assertEquals(new Boolean(true), goodStanding.getState());
    assertEquals(new Boolean(true), goOnCoop.getState());
    assertEquals(new Boolean(true), graduate.getState());
  }
}