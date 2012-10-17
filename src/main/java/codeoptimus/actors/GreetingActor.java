package codeoptimus.actors;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class GreetingActor extends UntypedActor {

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof FastGreeting) {
      System.out.println("Hello from " + ((FastGreeting) message).who);
      Long millisPause = Math.round(Math.random() * 2000) + 800;
      Thread.sleep(millisPause);
    } else if (message instanceof SlowGreeting) {
      System.out.println("yaaaawwwwnnnnnnn from " + ((SlowGreeting) message).who);
      Long millisPause = Math.round(Math.random() * 8000) + 2000; // 2,000 to 10,0000
      Thread.sleep(millisPause);
    }
  }
}
