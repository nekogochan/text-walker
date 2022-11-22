package nekogochan.textwalker.util.ref;

public class IntRef {
  int val;

  public int get() {
    return val;
  }

  public void set(int val) {
    this.val = val;
  }

  public int getAndIncrement() {
    return val++;
  }

  public void increment() {
    val++;
  }
}
