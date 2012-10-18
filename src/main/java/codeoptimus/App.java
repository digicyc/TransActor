package codeoptimus;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.Serializable;

import codeoptimus.future.FutureProc;
import codeoptimus.actors.*;


public class App implements Serializable {
  private final static int JOBCOUNT = 10;
  private ActorSystem system = ActorSystem.create("ActorSystem");

  public static void main(String[] args) {
    App app = new App();

    try {
      //System.out.println("\n####### RUNNING FUTURES #########\n");
      //app.futureMsgBlaster("FUTURE #"); // This blocks all others from starting.
      //System.out.println("\n####### RUNNING GOODBYE #########\n");
      //app.goodbye("じゃあまた");
      System.out.println("\n####### RUNNING FAST ACTOR #########\n");
      app.fastMsgBlaster("SUPER MSG BLASTER!@#!@#!@");
      System.out.println("\n####### RUNNING SLOW ACTOR #########\n");
      app.slowMsgBlaster("SLOWWWWWWW Actor");
    } catch (Exception e) { }
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

    /**
     * Blast messages at a Faster Rate.
     * @param msg Message to send.
     * @throws Exception
     */
  public void fastMsgBlaster(String msg) throws Exception {
    ActorRef greeter = system.actorOf(new Props(GreetingActor.class).withDispatcher("my-custom-dispatcher"),
            "msgblaster");

    for(int i = 0; i <= JOBCOUNT; i++) {
      Integer iMsg = new Integer(i);
      greeter.tell(new FastGreeting(iMsg.toString()));
    }   
  }

    /**
     * Blast messages at a Super slow Rate.
     * Also here is where we do a Graceful stop on the msgblaster actor(s).
     * @param msg Message to blast.
     * @throws Exception
     */
  public void slowMsgBlaster(String msg) throws Exception {
    ActorRef greeter = system.actorOf(new Props(GreetingActor.class).withDispatcher("my-custom-dispatcher"),
            "slowmsgblaster");
    ActorRef fastgreeter = system.actorFor("msgblaster");

     // This blasts through and sends tells super quick and leaves out.
    for(int i = 0; i <= JOBCOUNT; i++) {
      Integer iMsg = new Integer(i);

      greeter.tell(new SlowGreeting(iMsg.toString(), new ActorStop(system, fastgreeter)));

    }
  }

    /**
     * Demonstrate a different Actor Class on a new Actor Reference branch.
     * @param msg Message to blast.
     * @throws Exception
     */
  public void goodbye(String msg) throws Exception {
    ActorSystem system = ActorSystem.create("ActorSystem");
    ActorRef waver = system.actorOf(new Props(GoodByeActor.class), "goodbyemsg");

    for(int i = 0;i<=30;i++) {
      String finalMsg = msg + "[" + i + "]";
      waver.tell(new GoodBye(finalMsg));
    }
  }
}
