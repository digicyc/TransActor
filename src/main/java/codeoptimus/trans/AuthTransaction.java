package codeoptimus.trans;

public class AuthTransaction {
  private final String msg;

  public AuthTransaction(String msg) {
      this.msg = msg;
  }

  public String getMsg() {
      return msg;
  }
}
