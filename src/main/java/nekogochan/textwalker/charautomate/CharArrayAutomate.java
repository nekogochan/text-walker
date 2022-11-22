package nekogochan.textwalker.charautomate;

public class CharArrayAutomate implements CharAutomate {
  private final char[] source;
  private int pos = 0;

  public CharArrayAutomate(char[] source) {
    this.source = source;
  }

  public CharArrayAutomate(String source) {
    this(source.toCharArray());
  }

  @Override
  public char current() {
    return source[pos];
  }

  @Override
  public char next() {
    return source[pos + 1];
  }

  @Override
  public char lookForward(int deltaPos) {
    return source[pos + deltaPos];
  }

  @Override
  public char previous() {
    return source[pos - 1];
  }

  @Override
  public char lookBack(int deltaPos) {
    return source[pos - deltaPos];
  }

  @Override
  public char[] source() {
    return source;
  }

  @Override
  public boolean outOfBounds() {
    return pos >= source.length || pos < 0;
  }

  @Override
  public int currentPos() {
    return pos;
  }

  @Override
  public int maxPos() {
    return source.length - 1;
  }

  @Override
  public void setPos(int pos) {
    this.pos = pos;
  }

  @Override
  public void goForward() {
    pos++;
  }

  @Override
  public void goBack() {
    pos--;
  }

  @Override
  public void skipWhile(MatchRule skipRule) {
    while (skipRule.test(this)) {
      goForward();
    }
  }

  @Override
  public StringBuilder takeWhile(MatchRule takeRule) {
    var s = new StringBuilder();
    while (takeRule.test(this)) {
      s.append(current());
      goForward();
    }
    return s;
  }

  @Override
  public void skipUntil(MatchRule skipRule) {
    skipWhile(skipRule.negate());
  }

  @Override
  public StringBuilder takeUntil(MatchRule takeRule) {
    return takeWhile(takeRule.negate());
  }
}
