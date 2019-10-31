/**
 * This interface represent an observer. An observer could update, get current state,
 * and set subject.
 */
public interface Observer {
  /**
   * Update observer's state.
   */
  void update();

  /**
   * Get current state.
   * @return current state
   */
  Object getState();

  /**
   * Set subject.
   * @param studentSubject subject
   */
  void setSubject(StudentSubject studentSubject);
}
