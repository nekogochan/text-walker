package nekogochan.textwalker.charautomate;

import java.util.function.Predicate;

/**
 * Describes a flexible iterator for chars with position manipulating
 */
public interface CharAutomate {
  // immutable:
  char current();
  char next();
  char lookForward(int deltaPos);
  char previous();
  char lookBack(int deltaPos);
  char[] source();

  boolean outOfBounds();
  int currentPos();
  int maxPos();

  // mutable:
  void setPos(int pos);
  void goForward();
  void goBack();

  // for extra functions:
  interface MatchRule extends Predicate<CharAutomate> {
    default MatchRule or(MatchRule that) {
      return x -> test(x) || that.test(x);
    }
    default MatchRule and(MatchRule that) {
      return x -> test(x) && that.test(x);
    }
    default MatchRule negate() {
      return x -> !test(x);
    }
  }

  void skipWhile(MatchRule skipRule);
  void skipUntil(MatchRule skipRule);

  StringBuilder takeWhile(MatchRule takeRule);
  StringBuilder takeUntil(MatchRule takeRule);
}
