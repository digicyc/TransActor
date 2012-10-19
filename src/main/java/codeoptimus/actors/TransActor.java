package codeoptimus.actors;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import codeoptimus.FakeWork;
import codeoptimus.future.FutureProc;
import codeoptimus.trans.ActorStop;
import codeoptimus.trans.AuthTransaction;
import codeoptimus.trans.SaleTransaction;

public class TransActor extends UntypedActorWithStash {
    private Boolean isOpen = true;
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) {
        if (message instanceof SaleTransaction) {
            SaleTransaction saleTransaction = (SaleTransaction) message;
            log.info("\t --> [SaleTransaction Complete]: [" + saleTransaction.getMsg() + "]");
            Long millisPause = Math.round(Math.random() * 4000) + 1000;
            try {
                Thread.sleep(millisPause);
            } catch (Exception e) {
            }
        } else if (message instanceof AuthTransaction) {

            AuthTransaction authTransaction = (AuthTransaction) message;
            log.info("\t --> [AuthTransaction Complete]: [" + authTransaction.getMsg() + "]");
            FakeWork.fakeWork(8000);

        } else if (message instanceof ActorStop) {
            ActorStop actorStop = (ActorStop) message;
            stash();

            FutureProc futureProc = new FutureProc();
            futureProc.graceFulStopActors(actorStop.getActorSystem(), actorStop.getActorRef());
            log.info("Simulating a Big PROCESS JOB.\n-_- *GRUNT*\n");

            FakeWork.fakeWork();

            log.info("^_^ UNSTASH MAILBOX!");
            unstashAll();
        } else if (message instanceof String) {
            if (isOpen) {
                if (message.equals("stash")) {
                    isOpen = false;
                    log.info("Stash all incoming Actors");
                    log.info("\t>>>>>>>>> [^]");
                    stash();
                    FakeWork.fakeWork(80000);
                }
            }

            if (message.equals("unstash")) {
                log.info("Unstash our MailBox");
                log.info("\t[-] >>>>>>>>");
                context().unbecome();
                unstashAll();
                isOpen = true;
            } else if (message.equals("stash") && !isOpen) {
                log.info("You already did a Stash.. no need to do it twice in a row.");
            }
        }
    }
}