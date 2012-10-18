package codeoptimus.future;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.*;
import akka.util.Duration;

import java.util.concurrent.Callable;

import static akka.dispatch.Futures.future;
import static java.util.concurrent.TimeUnit.SECONDS;
import static akka.pattern.Patterns.gracefulStop;

import codeoptimus.FakeWork;
import codeoptimus.MyResponse;


public class FutureProc {
    private final static int LOOPCOUNT = 10;

    public MyResponse processFuture(String msg) throws Exception {
        ActorSystem system = ActorSystem.create("ActorSystem");
        String result = "";

        for (int i = 0; i <= LOOPCOUNT; i++) {
            final String finalMsg = msg + ": " + i;

            // Use a future directly.
            Future<String> f = future(new Callable<String>() {
                public String call() {
                    Long millisPause = FakeWork.fakeWork(2000);
                    System.out.println("Processing Future in: " + millisPause.toString());

                    return "Future Finished Processing ^_^";
                }
            }, system.dispatcher());

            result = (String) Await.result(f, Duration.create(10, SECONDS));

        }
        return new MyResponse(result);
    }

    public void graceFulStopActors(ActorSystem system, ActorRef actorRef) {

        System.out.println("Running a Graceful Stop on the Specified ActorRef.");

        try {
            Future<Boolean> stopped = gracefulStop(actorRef, Duration.create(20, SECONDS), system);
            Await.result(stopped, Duration.create(30, SECONDS));
            System.out.println("Actors all Stopped SUCCESSFULLY!");
        } catch (Exception e) {
            System.out.println("[X][X][X][X] Something went wrong with Graceful stopping: " + e);
        }
    }
}
