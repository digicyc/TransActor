package codeoptimus.actors;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import codeoptimus.future.FutureProc;

public class GreetingActor extends UntypedActorWithStash {

  @Override
  public void onReceive(Object message) {
    if (message instanceof FastGreeting) {
      FastGreeting fastGreeting = (FastGreeting) message;
      System.out.println("[FastGreeting Msg]: " + fastGreeting.who);
      Long millisPause = Math.round(Math.random() * 4000) + 1000;
      try {
          Thread.sleep(millisPause);
      } catch (Exception e) { }
    } else if (message instanceof SlowGreeting) {
      SlowGreeting slowGreeting = (SlowGreeting) message;
      ActorStop actorStop = slowGreeting.getActorStop();

      // It seems the message gets stuck as always sending "2"
      if(slowGreeting.getMsg().equals("2")) {
          stash();

          FutureProc futureProc = new FutureProc();
          futureProc.graceFulStopActors(actorStop.getActorSystem(), actorStop.getActorRef());
          System.out.println("Simulating a Big PROCESS JOB.");
          try {
              Long millisPause = Math.round(Math.random() * 4000) + 1000;
              Thread.sleep(millisPause);
          } catch (Exception e) { }

          System.out.println("^_^ UNSTASH MAILBOX!");
          unstashAll();
      }

      System.out.println("[SlowGreeting Msg]: " + slowGreeting.getMsg());
      Long millisPause = Math.round(Math.random() * 8000) + 2000; // 2,000 to 10,0000
      try {
          Thread.sleep(millisPause);
      } catch (Exception e) { }
    }
  }
}
