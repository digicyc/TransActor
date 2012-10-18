package codeoptimus.actors;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class GoodByeActor extends UntypedActor {

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof GoodBye) {
      System.out.println("[GoodBye Actor]: " + ((GoodBye) message).who);
      Long millisPause = Math.round(Math.random() * 800) + 200;
      Thread.sleep(millisPause);
    }
  }
}
