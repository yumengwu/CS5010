import java.util.Map;

/**
 * This class represent an ObserveGraduate. Graduate means having a GPA of at
 * least 3.0 in all core courses combined (CS 5010, CS 5800 and one of CS 5500
 * and CS 5600) in order to graduate. For ALIGN students, CS 5004 substitutes
 * CS 5010 as a core course.
 */
public class ObserveGraduate extends AbstractObserver {
  /**
   * Construct an ObserveGraduate object.
   * @param subject subject object
   */
  public ObserveGraduate(StudentSubject subject) {
    super(subject);
  }

  /**
   * Private helper method to get CS5010 or CS5004 course.
   * @param map course map
   * @return score of course
   */
  private double get5010Or5004(Map<String, String> map) {
    if (subject.getProgramType() == ProgramType.MSCS) {
      if (!map.containsKey("CS5010")) {
        throw new IllegalStateException();
      }
      else {
        return super.scoreTransfer(map.get("CS5010"));
      }
    }
    else {
      if (!map.containsKey("CS5004")) {
        state = false;
        throw new IllegalStateException();
      }
      else {
        return super.scoreTransfer(map.get("CS5004"));
      }
    }
  }

  /**
   * Private helper method to get CS5800 course.
   * @param map course map
   * @return score of course
   */
  private double get5800(Map<String, String> map) {
    if (!(map.containsKey("CS5800"))) {
      throw new IllegalStateException();
    }
    else {
      return super.scoreTransfer(map.get("CS5800"));
    }
  }

  /**
   * Private helper method to get CS5500 or CS5600 course.
   * @param map course map
   * @return score of course
   */
  private double get5500Or5600(Map<String, String> map) {
    double temp = -1;
    if (map.containsKey("CS5500")) {
      temp = super.scoreTransfer(map.get("CS5500"));
    }
    if (map.containsKey("CS5600")) {
      if (super.scoreTransfer(map.get("CS5600")) > temp) {
        temp = super.scoreTransfer(map.get("CS5600"));
      }
    }
    if (temp < 0) {
      throw new IllegalStateException();
    }
    return temp;
  }

  /**
   * Update observer's state.
   */
  public void update() {
    Map<String, String> courseMap = subject.getCourse();
    if (courseMap.size() != 8) {
      state = false;
      return;
    }
    double score = 0.0;
    try {
      score += get5010Or5004(courseMap);
      score += get5800(courseMap);
      score += get5500Or5600(courseMap);
    }
    catch (IllegalStateException e) {
      state = false;
      return;
    }
    score /= 3;
    state = (score > 3.0 || Math.abs(score - 3.0) <= 1e-4);
  }

  /**
   * Set subject.
   * @param studentSubject subject
   */
  public void setSubject(StudentSubject studentSubject) {
    if (studentSubject == null) {
      throw new IllegalArgumentException("Subject cannot be null.");
    }
    subject = studentSubject;
    update();
  }

  /**
   * Get current state.
   * @return current state
   */
  @Override
  public Boolean getState() {
    return state;
  }
}
