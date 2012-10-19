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

import codeoptimus.FakeWork;
import codeoptimus.MyResponse;


public class FutureProc {
    private final static int LOOPCOUNT = 10;
    private final ActorSystem system;
    private final LoggingAdapter log;

    public FutureProc(ActorSystem system) {
        this.system = system;
        log = Logging.getLogger(system, this);
    }

    public MyResponse processFuture(String msg) throws Exception {
        String result = "";

        for (int i = 0; i <= LOOPCOUNT; i++) {
            // Use a future directly.
            Future<String> f = future(new Callable<String>() {
                public String call() {
                    Long millisPause = FakeWork.fakeWork(2000);
                    log.info("Processing Future in: " + millisPause.toString());

                    return "^_^ Future Finished Processing ^_^";
                }
            }, system.dispatcher());

            result = (String) Await.result(f, Duration.create(10, SECONDS));

        }
        // Boxup our result in a MyResponse object.
        return new MyResponse(result);
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
