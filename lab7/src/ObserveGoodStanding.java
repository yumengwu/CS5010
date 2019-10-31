import java.util.Map;

/**
 * This class represent an ObserveGoodStanding. Good standing means the cumulative
 * GPA must be at 3.0 or above at any point in the program.
 */
public class ObserveGoodStanding extends AbstractObserver {
  /**
   * Construct an ObserveGoodStanding object.
   * @param subject student subject
   */
  public ObserveGoodStanding(StudentSubject subject) {
    super(subject);
  }

  /**
   * Update observer's state.
   */
  public void update() {
    double score = 0.0;
    Map<String, String> courseMap = subject.getCourse();
    for (String c : courseMap.keySet()) {
      score += super.scoreTransfer(courseMap.get(c));
    }
    score /= courseMap.size();
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
