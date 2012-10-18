package codeoptimus.actors;

import akka.actor.*;
import codeoptimus.FakeWork;
import codeoptimus.trans.BatchTransaction;

public class GoodByeActor extends UntypedActor {

    @Override
    public void onReceive(Object message) {
        if (message instanceof BatchTransaction) {
            System.out.println("[BatchTransaction Actor]: " + ((BatchTransaction) message).who);
            FakeWork.fakeWork();
        }
    }
}
