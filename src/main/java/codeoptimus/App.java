package codeoptimus;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

// Futures
import akka.dispatch.*;
import akka.util.Timeout;
import akka.util.Duration;
import akka.japi.Function;
import static akka.dispatch.Futures.future;
import static java.util.concurrent.TimeUnit.SECONDS;
import static akka.pattern.Patterns.gracefulStop;
import akka.actor.ActorTimeoutException;

import java.io.Serializable;
import java.util.concurrent.Callable;

import codeoptimus.future.FutureProc;
import codeoptimus.actors.*;


public class App implements Serializable {
  private ActorSystem system = ActorSystem.create("ActorSystem");

  public static void main(String[] args) {
    App app = new App();

    try {
      System.out.println("\n####### RUNNING FUTURES #########\n");
      //app.futureMsgBlaster("FUTURE #"); // This blocks all others from starting.
      System.out.println("\n####### RUNNING GOODBYE #########\n");
      app.goodbye("じゃあまた");
      app.fastMsgBlaster("SUPER MSG BLASTER!@#!@#!@");
      System.out.println("\n####### RUNNING SLOW ACTOR #########\n");
      app.slowMsgBlaster("SLOWWWWWWW Actor");
    } catch(Exception e) { }
  }

  /**
   *  Handle our Actor with a future response.
   *  This blocks.
   */
  public void futureMsgBlaster(String msg) throws Exception {

      FutureProc fProc = new FutureProc();
      MyResponse resp = fProc.processFuture(msg);

      System.out.println("FROM THE FUTURE: ["+resp.getResult()+"]");
  }

  public void fastMsgBlaster(String msg) throws Exception {
    ActorRef greeter = system.actorOf(new Props(GreetingActor.class), "msgblaster");

    for(int i = 0;i<=30;i++) {
      String finalMsg = msg + ": " + i;
      greeter.tell(new FastGreeting(finalMsg));
    }   
  }

  public void slowMsgBlaster(String msg) throws Exception {
    ActorRef greeter = system.actorOf(new Props(GreetingActor.class), "msgblaster");

    for(int i = 0;i<=30;i++) {
      if(i == 25) {
        // Kill all the msgblaster actors.
        ActorRef mygreeter = system.actorFor("msgblaster");
        // Simulate a Settlement coming in.
        // TODO: Put Mailbox in StashMode.
        // TODO: Graceful Stop all existing Actors.
        try {
          // Inject KillPill
          Future<Boolean> stopped = gracefulStop(mygreeter, Duration.create(25, SECONDS), system);
          Await.result(stopped, Duration.create(6, SECONDS));

          System.out.println("All Actors finished Succesfully!\nNow process Settlement\n");
          // Simulate a Settlement process here with a Thread.sleep()
          // TODO: Take Mailbox out of StashMode
        } catch (ActorTimeoutException e) { 

          System.out.println("[X][X][X][X] Something went wrong with Graceful stopping: " + e);
        }
      } else {
        String finalMsg = msg + ": " + i;
        greeter.tell(new SlowGreeting(finalMsg));
      }
    }   
  }

  public void goodbye(String msg) throws Exception {
    ActorRef waver = system.actorOf(new Props(GoodByeActor.class), "goodbyemsg");

    for(int i = 0;i<=30;i++) {
      String finalMsg = msg + "[" + i + "]";
      waver.tell(new GoodBye(finalMsg));
    }
  }
}
