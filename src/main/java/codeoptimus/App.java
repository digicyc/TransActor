package codeoptimus;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.Serializable;

import akka.event.Logging;
import akka.event.LoggingAdapter;
import codeoptimus.future.FutureProc;
import codeoptimus.simulate.FakeWork;
import codeoptimus.simulate.TransBlaster;
import codeoptimus.trans.*;
import codeoptimus.actors.TransActor;


public class App implements Serializable {
    private final static int JOBCOUNT = 15;
    private ActorSystem system = ActorSystem.create("ActorSystem");
    private LoggingAdapter log = Logging.getLogger(system, this);

    public static void main(String[] args) {

        try {
            System.out.println("\n=====] Run our Chain of Transactions. [=====\n");
            App app = new App();
            app.transBlaster();
            //TransBlaster transB = new TransBlaster();
            //transB.severalTrans();
        } catch (Exception e) {
            System.out.println("[X][X] Unable to process our Transactions. \n[Excp]: " + e);
        }
    }

    public void transBlaster() throws Exception {

        // Send Messages to the MailBox to SPAWN out Actors.
        for (int i = 0; i <= JOBCOUNT; i++) {
            Integer iMsg = i;

            ActorSystem lsystem = ActorSystem.create("LocalActor"+iMsg);
            ActorRef myActor = lsystem.actorOf(new Props(TransActor.class).withDispatcher("my-custom-dispatcher"),
                    "greetingactor"+iMsg);

            FakeWork.fakeWork();
            System.out.printf("[%s] Transaction Received!\n", iMsg.toString());

            //if (i == 5) {
            FutureProc fProc = new FutureProc(lsystem);
            TransResponse resp = fProc.processFuture("ID# "+iMsg);
            System.out.println(resp.getResult());
            //}
            //if ((i % 2) == 0) {
            //    // Every other Transaction is an AuthTransaction.
            //    ActorRef actorRef = system.actorFor("akka://ActorSystem/user/greetingactor");
            //    actorRef.tell(new AuthTransaction(iMsg.toString()));
            //} else {
            //    myActor.tell(new SaleTransaction(iMsg.toString()));
            //}
        }
    }
}
