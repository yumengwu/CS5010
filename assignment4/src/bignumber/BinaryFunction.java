package bignumber;

/**
 * Functional interface to input two argument and output one result. All these three variable have
 * same type.
 * @param <T> general type
 */
@FunctionalInterface
public interface BinaryFunction<T> {
  T apply(T t1, T t2);
}