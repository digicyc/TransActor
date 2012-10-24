package codeoptimus.future;

/**
 * FutureCallBack:
 * Author: Aaron Allred
 */
import akka.actor.ActorSystem;
import akka.dispatch.ExecutionContext;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;

import akka.dispatch.Future;

public class FutureCallBack {
    private ActorSystem system = ActorSystem.create("CallbackSystem");

    public void setCallback(Future<String> myfuture) {
        //final ExecutionContext ec = system.dispatcher();

        // SUCCESS
        myfuture.onSuccess(new OnSuccess<String>() {
            public void onSuccess(String result) {
                System.out.println(result +": WAS SUCCESSFULL!!@#&!^@*#&^");
            }
        });

        // FAIL
        myfuture.onFailure(new OnFailure() {
           public void onFailure(Throwable failure) {
                System.out.println("[X][X][X] FAILURE OF FUTURE [X][X][X]");
           }
        });
    }
}
