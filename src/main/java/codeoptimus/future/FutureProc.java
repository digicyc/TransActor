package codeoptimus.future;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.util.Duration;

import java.util.concurrent.Callable;

import static akka.dispatch.Futures.future;
import static java.util.concurrent.TimeUnit.SECONDS;
import static akka.pattern.Patterns.gracefulStop;

import codeoptimus.simulate.FakeWork;
import codeoptimus.trans.TransResponse;


public class FutureProc {
    private final ActorSystem system;
    private final LoggingAdapter log;

    public FutureProc(ActorSystem system) {
        this.system = system;
        log = Logging.getLogger(system, this);
    }

    public TransResponse processFuture(final String msg) throws Exception {
        Future<String> f = future(new Callable<String>() {
            public String call() {
                Long millisPause = FakeWork.fakeWork(10000);
                log.info("Processing Future LoadTime = " + millisPause.toString());

                return "["+msg+"][FUTURE] Returned!";
            }
        }, system.dispatcher());
        FutureCallBack fCallBack = new FutureCallBack();
        fCallBack.setCallback(f);

        return new TransResponse("++");
    }

    public void graceFulStopActors(ActorSystem system, ActorRef actorRef) {

        log.info("Running a Graceful Stop on the Specified ActorRef.");

        try {
            Future<Boolean> stopped = gracefulStop(actorRef, Duration.create(20, SECONDS), system);
            Await.result(stopped, Duration.create(30, SECONDS));
            log.info("Actors all Stopped SUCCESSFULLY!");
        } catch (Exception e) {
            log.info("[X][X][X][X] Something went wrong with Graceful stopping: " + e);
        }
    }
}
