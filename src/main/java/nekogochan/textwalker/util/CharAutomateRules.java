package nekogochan.textwalker.util;


import nekogochan.textwalker.charautomate.CharAutomate;
import nekogochan.textwalker.charautomate.CharAutomate.MatchRule;
import nekogochan.textwalker.util.function.CharPredicate;

import static java.util.Arrays.stream;
import static nekogochan.textwalker.util.CharAutomateRulesUtils.combineAsOr;

public class CharAutomateRules {
  private CharAutomateRules() {
  }

  public static MatchRule notOutOfBounds() {
    return outOfBounds().negate();
  }

  public static MatchRule outOfBounds() {
    return CharAutomate::outOfBounds;
  }

  public static MatchRule currentIs(CharPredicate predicate) {
    return x -> predicate.test(x.current());
  }

  public static MatchRule currentEq(char ch) {
    return x -> x.current() == ch;
  }

  @SuppressWarnings("unchecked")
  public static MatchRule currentEq(char... chars) {
    var predicates = new MatchRule[chars.length];
    for (int i = 0; i < chars.length; i++) {
      predicates[i] = currentEq(chars[i]);
    }
    return combineAsOr(predicates);
  }

  public static MatchRule forwardEq(String s) {
    return x -> {
      if (s.length() + x.currentPos() > x.maxPos()) {
        return false;
      }
      for (int i = 0; i < s.length(); i++) {
        if (x.lookForward(i + 1) != s.charAt(i)) {
          return false;
        }
      }
      return true;
    };
  }

  public static MatchRule forwardEq(String... strings) {
    return stream(strings).map(CharAutomateRules::forwardEq)
                          .collect(combineAsOr());
  }

  public static MatchRule backEq(String s) {
    return x -> {
      if (x.currentPos() < s.length()) {
        return false;
      }
      for (int i = 0; i < s.length(); i++) {
        var j = s.length() - i - 1;
        if (x.lookBack(i + 1) != s.charAt(j)) {
          return false;
        }
      }
      return true;
    };
  }

  public static MatchRule backEq(String... strings) {
    return stream(strings).map(CharAutomateRules::backEq)
                          .collect(combineAsOr());
  }
}
