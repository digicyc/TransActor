==========
TransActor
==========

Demonstrating Akka's usage in a Payment Gateway transaction handler.
Written in Java. 

Transactions
------------
 - Auth 
 - Credit
 - Settlement

Futures
--------
 Settlements expect a Future back as to Block on the Mailbox in order to "Pause" any additional transactions on that particular Mailbox.

Actors
-------
 We have one Actor to handle each Transaction Type.
