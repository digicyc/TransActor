package codeoptimus.trans;

public class TransResponse {
  private String msg;


  public TransResponse() {
    this.msg = "";
  }

  public TransResponse(String msg) {
    this.msg = msg;
  }

  public String getResult() {
    return msg;
  }

  public void setResult(String msg) {
    this.msg = msg;
  }
  
}
