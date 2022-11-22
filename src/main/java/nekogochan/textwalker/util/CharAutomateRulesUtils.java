package nekogochan.textwalker.util;

import nekogochan.textwalker.charautomate.CharAutomate.MatchRule;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CharAutomateRulesUtils {
  private CharAutomateRulesUtils() {
  }

  @SuppressWarnings("SuspiciousToArrayCall")
  static Collector<Object, Object, MatchRule> combineAsOr() {
    return Collectors.collectingAndThen(
      Collectors.toList(),
      x -> combineAsOr(x.toArray(MatchRule[]::new))
    );
  }

  static MatchRule combineAsOr(MatchRule... predicates) {
    return x -> {
      for (var p : predicates) {
        if (p.test(x)) {
          return true;
        }
      }
      return false;
    };
  }
}
