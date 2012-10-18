package codeoptimus;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.Serializable;

import codeoptimus.trans.*;
import codeoptimus.actors.TransActor;


public class App implements Serializable {
    private final static int JOBCOUNT = 15;
    private ActorSystem system = ActorSystem.create("ActorSystem");

    public static void main(String[] args) {
        App app = new App();

        try {
            System.out.println("Run our Chains of Transactions.");
            app.transBlaster();
        } catch (Exception e) {
            System.out.println("Unable to process our Transactions. \n[Excp]: " + e);
        }
    }

    public void transBlaster() throws Exception {
        ActorRef myActor = system.actorOf(new Props(TransActor.class).withDispatcher("my-custom-dispatcher"),
                "greetingactor");

        for (int i = 0; i <= JOBCOUNT; i++) {
            Integer iMsg = new Integer(i);
            // Fake the getting of Transactions
            FakeWork.fakeWork();
            System.out.printf("[%s] Recieved a Transaction. Send to Process!\n", iMsg.toString());

            if (i == 5) {
                myActor.tell("stash");
                System.out.println("\tXXXXXXXX STASH CALLED! XXXXXXXXXXX\n");
                // TODO: Process a settlement
            }
            if ((i % 2) == 0) {
                myActor.tell(new AuthTransaction(iMsg.toString()));
            } else {
                myActor.tell(new SaleTransaction(iMsg.toString()));
            }
            if (i == 8) {
                myActor.tell("unstash");
            }
        }
    }
}
