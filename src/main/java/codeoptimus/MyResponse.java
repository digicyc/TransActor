package codeoptimus;

public class MyResponse {
  private String msg;


  public MyResponse() { 
    this.msg = "";
  }

  public MyResponse(String msg) {
    this.msg = msg;
  }

  public String getResult() {
    return msg;
  }

  public void setResult(String msg) {
    this.msg = msg;
  }
  
}
