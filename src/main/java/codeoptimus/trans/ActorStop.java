package codeoptimus.trans;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Created with IntelliJ IDEA.
 * User: Aaron Allred
 */

public class ActorStop {
    private ActorSystem system;
    private ActorRef actorRef;

    public ActorStop() { }
    public ActorStop(ActorSystem system, ActorRef actorRef) {
        this.system = system;
        this.actorRef = actorRef;
    }

    public ActorRef getActorRef() {
        return actorRef;
    }

    public ActorSystem getActorSystem() {
        return system;
    }
}
