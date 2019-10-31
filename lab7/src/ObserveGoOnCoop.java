import java.util.Map;

/**
 * This class represent an ObserveGoOnCoop. Go on coop means taking at least 16 credits
 * and maintaining a cumulative GPA of at least 3.0.
 */
public class ObserveGoOnCoop extends AbstractObserver {

  /**
   * Construct an ObserveGoOnCoop object.
   * @param subject student subject
   */
  public ObserveGoOnCoop(StudentSubject subject) {
    super(subject);
  }

  /**
   * Update observer's state.
   */
  public void update() {
    double score = 0.0;
    Map<String, String> courseMap = subject.getCourse();
    if (courseMap.size() < 4) {
      state = false;
      return;
    }
    for (String c : courseMap.keySet()) {
      score += super.scoreTransfer(courseMap.get(c));
    }
    score /= courseMap.size();
    state = (score >= 3.0);
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
