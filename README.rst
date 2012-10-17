===========
TransActor
==========

Demonstrating Akka's usage in a Payment Gateway transaction handler. 

Transactions
------
 - Auth 
 - Credit
 - Settlement

Futures
--------
 Settlements expect a Future back as to Block on the Mailbox in order to "Pause" any additional transactions on that particular Mailbox.
 TODO: Add in Mailbox stashing.
