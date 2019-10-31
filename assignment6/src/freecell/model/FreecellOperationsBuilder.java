package freecell.model;

/**
 * This interface represent a Freecell operations builder. A builder could set variables and
 * return object implements FreecellOperations.
 */
public interface FreecellOperationsBuilder<K> {
  /**
   * Set the cascade piles number. If cascade number is less than 4, this method will
   * throw IllegalArgumentException. This method will return itself after changing
   * cascade piles number.
   * @param c new cascade piles number
   * @return builder object itself
   */
  FreecellOperationsBuilder cascades(int c);

  /**
   * Set the open piles number. If open number is less than 1, this method will
   * throw IllegalArgumentException. This method will return itself after changing
   * open piles number.
   * @param o new open piles number
   * @return builder object itself
   */
  FreecellOperationsBuilder opens(int o);

  /**
   * This method will return a FreecellModel object with given cascade and open number.
   * One must use this method to get a model.
   * @return a new FreecellModel object
   */
  FreecellOperations<K> build();
}