package codeoptimus.actors;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyFutureActor extends UntypedActor {

  @Override
  public void onReceive(Object message) throws Exception {

    if (message instanceof FastGreeting) {
      System.out.println("[Future Msg]: " + ((FastGreeting) message).who);
      Long millisPause = Math.round(Math.random() * 200000) + 80000;
      Thread.sleep(millisPause);
    }
  }
}
