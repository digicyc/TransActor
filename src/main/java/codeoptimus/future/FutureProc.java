package codeoptimus.future;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.*;
import akka.util.Duration;
import java.util.concurrent.Callable;
import static akka.dispatch.Futures.future;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static akka.pattern.Patterns.gracefulStop;

import codeoptimus.MyResponse;


public class FutureProc {
  private final static int LOOPCOUNT = 10;

  public MyResponse processFuture(String msg) throws Exception {
    ActorSystem system = ActorSystem.create("ActorSystem");
    String result = "";
  
    for(int i = 0; i <= LOOPCOUNT; i++) {
      final String finalMsg = msg + ": " + i;

      // Use a future directly.
      Future<String> f = future(new Callable<String>() {
        public String call() {
          try {
            Long millisPause = Math.round(Math.random() * 2000) + 800;
            System.out.println("Processing Future at: " + millisPause.toString());
            Thread.sleep(millisPause);
          } catch (Exception e) {
            System.out.println("THE FUTURE IS BROKEN: \n" + e);
          }   

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
          Future<Boolean> stopped = gracefulStop(actorRef, Duration.create(90, SECONDS), system);
          Await.result(stopped, Duration.create(120, SECONDS));
      } catch (Exception e) {
        System.out.println("[X][X][X][X] Something went wrong with Graceful stopping: " + e);
      }
  }
}
