package codeoptimus.actors;

import akka.actor.*;
import codeoptimus.simulate.FakeWork;
import codeoptimus.trans.SaleTransaction;

public class MyFutureActor extends UntypedActor {

  @Override
  public void onReceive(Object message) {

    if (message instanceof SaleTransaction) {
      System.out.println("[Future Msg]: " + ((SaleTransaction) message).getMsg());
      FakeWork.fakeWork(200000);
    }
  }
}
