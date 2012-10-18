package codeoptimus.actors;

public class SlowGreeting {
  private final String msg;
  private final ActorStop actorStop;

  public SlowGreeting(String msg, ActorStop actorStop) {
      this.msg = msg;
      this.actorStop = actorStop;
  }

  public String getMsg() {
      return msg;
  }

  public ActorStop getActorStop() {
      return actorStop;
  }
}
