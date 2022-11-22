package nekogochan.textwalker.charautomate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nekogochan.textwalker.util.CharAutomateRules.backEq;
import static nekogochan.textwalker.util.CharAutomateRules.currentEq;
import static nekogochan.textwalker.util.CharAutomateRules.forwardEq;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TakeUntilTests {

  CharAutomate auto;

  @BeforeEach
  void init() {
    auto = new CharArrayAutomate(
      "         many spaces $ many $"
    );
  }

  @Test
  void currentEqOne() {
    var sb = auto.takeUntil(currentEq('$'));
    assertEquals("         many spaces ", sb.toString());
    assertEquals('$', auto.current());
  }

  @Test
  void currentEqAny() {
    var sb = auto.takeUntil(currentEq('$', 'y'));
    assertEquals("         man", sb.toString());
    assertEquals('y', auto.current());
  }

  @Test
  void forwardEqOne() {
    var sb = auto.takeUntil(forwardEq("many"));
    assertEquals("        ", sb.toString());
    assertEquals(' ', auto.current());
  }

  @Test
  void forwardEqAny() {
    var sb = auto.takeUntil(forwardEq("spaces", "many"));
    assertEquals("        ", sb.toString());
    assertEquals(' ', auto.current());

    auto.goForward();
    sb = auto.takeUntil(forwardEq("spaces", "many"));
    assertEquals("many", sb.toString());
    assertEquals(' ', auto.current());
  }

  @Test
  void backEqOne() {
    var sb = auto.takeUntil(backEq("many"));
    assertEquals("         many", sb.toString());
    assertEquals(' ', auto.current());
  }

  @Test
  void forwardAndBackEq() {
    var sb = auto.takeUntil(backEq("many").and(forwardEq("$")));
    assertEquals("         many spaces $ many", sb.toString());
    assertEquals(' ', auto.current());
  }
}
