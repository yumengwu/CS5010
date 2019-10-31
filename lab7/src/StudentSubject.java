import java.util.Map;

/**
 * This interface represent a student subject. A student object could add course,
 * add observer, notify observers, get course a map, and get program type.
 */
public interface StudentSubject {
  /**
   * Notify all the observers of this subject.
   */
  void notifyObservers();

  /**
   * Add a observer to this subject. If observer is null, this method will throw
   * IllegalArgumentException.
   * @param observer observer object
   */
  void addObserver(Observer observer);

  /**
   * Add a course.
   * @param course course object
   */
  void addCourse(Course course);

  /**
   * Get course map of this subject. This method will return a copy of map. Map will
   * Map&lt;String, String&gt;.
   * @return course map
   */
  Map<String, String> getCourse();

  /**
   * Return program type.
   * @return program type
   */
  ProgramType getProgramType();
}
