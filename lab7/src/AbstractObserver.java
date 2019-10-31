/**
 * This abstract class is used to transfer grade from string to double.
 */
public abstract class AbstractObserver implements Observer {
  protected Boolean state;
  protected StudentSubject subject;

  /**
   * Construct an AbstractObserver object with given subject.
   * @param subject student subject
   */
  public AbstractObserver(StudentSubject subject) {
    if (subject == null) {
      throw new IllegalArgumentException("Subject cannot be null.");
    }
    this.subject = subject;
    this.update();
  }

  /**
   * Transfer grade from string to double.
   * @param score given score as a string
   * @return score as a double
   */
  protected Double scoreTransfer(String score) {
    if (score.equals("A")) {
      return 4.0;
    }
    else if (score.equals("A-")) {
      return 3.667;
    }
    else if (score.equals("B+")) {
      return 3.333;
    }
    else if (score.equals("B")) {
      return 3.0;
    }
    else if (score.equals("B-")) {
      return 2.667;
    }
    else if (score.equals("C+")) {
      return 2.333;
    }
    else if (score.equals("C")) {
      return 2.0;
    }
    else if (score.equals("C-")) {
      return 1.667;
    }
    else if (score.equals("D+")) {
      return 1.333;
    }
    else if (score.equals("D")) {
      return 1.0;
    }
    else if (score.equals("D-")) {
      return 0.667;
    }
    else if (score.equals("F")) {
      return 0.0;
    }
    else {
      throw new IllegalArgumentException("Invalid score.");
    }
  }
}
