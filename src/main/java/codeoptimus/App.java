package codeoptimus;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.Serializable;

import akka.event.Logging;
import akka.event.LoggingAdapter;
import codeoptimus.future.FutureProc;
import codeoptimus.trans.*;
import codeoptimus.actors.TransActor;


public class App implements Serializable {
    private final static int JOBCOUNT = 15;
    private ActorSystem system = ActorSystem.create("ActorSystem");
    private LoggingAdapter log = Logging.getLogger(system, this);

    public static void main(String[] args) {
        App app = new App();

        try {
            System.out.println("\nRun our Chains of Transactions.\n");
            app.transBlaster();
        } catch (Exception e) {
            System.out.println("Unable to process our Transactions. \n[Excp]: " + e);
        }
    }

    public void transBlaster() throws Exception {
        ActorRef myActor = system.actorOf(new Props(TransActor.class).withDispatcher("my-custom-dispatcher"),
                "greetingactor");

        for (int i = 0; i <= JOBCOUNT; i++) {
            Integer iMsg = i;
            // Fake the getting of Transactions
            FakeWork.fakeWork();
            System.out.printf("[%s] Recieved a Transaction. Send to Process!\n", iMsg.toString());

            if (i == 5) {
                FutureProc fProc = new FutureProc(system);
                MyResponse resp = fProc.processFuture("FROM THE FUTURE");
                System.out.println(resp.getResult());
            }
            if ((i % 2) == 0) {
                ActorRef actorRef = system.actorFor("akka://ActorSystem/user/greetingactor");
                actorRef.tell(new AuthTransaction(iMsg.toString()));
            } else {
                myActor.tell(new SaleTransaction(iMsg.toString()));
            }
        }
    }
}
