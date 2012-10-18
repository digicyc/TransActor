package codeoptimus.actors;

import akka.actor.*;
import codeoptimus.FakeWork;
import codeoptimus.future.FutureProc;

public class TransActor extends UntypedActorWithStash {
    private Boolean isOpen = true;

    @Override
    public void onReceive(Object message) {
        if (message instanceof SaleTransaction) {
            SaleTransaction saleTransaction = (SaleTransaction) message;
            System.out.println("\t -> [SaleTransaction Count]: " + saleTransaction.getMsg());
            Long millisPause = Math.round(Math.random() * 4000) + 1000;
            try {
                Thread.sleep(millisPause);
            } catch (Exception e) {
            }
        } else if (message instanceof AuthTransaction) {

            AuthTransaction authTransaction = (AuthTransaction) message;
            System.out.println("\t -> [AuthTransaction Count]: " + authTransaction.getMsg());
            FakeWork.fakeWork(8000);

        } else if (message instanceof ActorStop) {
            ActorStop actorStop = (ActorStop) message;
            stash();

            FutureProc futureProc = new FutureProc();
            futureProc.graceFulStopActors(actorStop.getActorSystem(), actorStop.getActorRef());
            System.out.println("Simulating a Big PROCESS JOB.\n-_- *GRUNT*\n");

            FakeWork.fakeWork();

            System.out.println("^_^ UNSTASH MAILBOX!");
            unstashAll();
        } else if (message instanceof String) {
            if (isOpen) {
                if (message.equals("stash")) {
                    isOpen = false;
                    System.out.println("Stash all incoming Actors");
                    System.out.println("\t>>>>>>>>> [^]");
                    stash();
                    FakeWork.fakeWork(80000);
                }
            } else {
                if (message.equals("unstash")) {
                    isOpen = true;
                    System.out.println("Unstash our MailBox");
                    System.out.println("\t[-] >>>>>>>>");
                    unstashAll();
                }
            }
        }
    }
}
