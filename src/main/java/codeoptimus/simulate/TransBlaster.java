package codeoptimus.simulate;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import codeoptimus.actors.TransActor;
import codeoptimus.trans.SaleTransaction;

/**
 * Simulate the sending of Several Transactions
 * From several different merchants and terminals.
 * TransBlaster:
 * Author: Aaron Allred
 */

public class TransBlaster {
    private final static int JOBCOUNT = 35;
    private final static String dispatchS = "my-custom-dispatcher";

    public void severalTrans() throws Exception {
        ActorSystem lSystem = ActorSystem.create("TransactionSystem");

        for(int i=0;i<=JOBCOUNT;i++) {
            Integer iStr = i;
            String actorName = "trans" + iStr.toString();
            //ActorRef lActor = lSystem.actorFor("akka://ActorSystem/user/"+actorName);
            ActorRef lActor = lSystem.actorOf(new Props(TransActor.class).withDispatcher(dispatchS), actorName);
            lActor.tell(new SaleTransaction(iStr.toString()));
        }
    }
}
