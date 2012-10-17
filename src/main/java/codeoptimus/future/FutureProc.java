package codeoptimus.future;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.*;
import akka.util.Timeout;
import akka.util.Duration;
import java.util.concurrent.Callable;
import static akka.dispatch.Futures.future;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import codeoptimus.MyResponse;


public class FutureProc {
  public MyResponse processFuture(String msg) throws Exception {
    ActorSystem system = ActorSystem.create("ActorSystem");
    String result = "";
  
    for(int i = 0;i <= 20; i++) {
      final String finalMsg = msg + ": " + i;

      // Use a future directly.
      Future<String> f = future(new Callable<String>() {
        public String call() {
          try {
            Long millisPause = Math.round(Math.random() * 2000) + 800;
            Thread.sleep(millisPause);
          } catch (Exception e) { 
            System.out.println("THE FUTURE BROKE: \n" + e); 
          }   

          return finalMsg;
        }   
      }, system.dispatcher());

      result = (String) Await.result(f, Duration.create(10, SECONDS)); 

    }   
    return new MyResponse(result);
  }
}
