# Thomas Millers Async Programming


I generally do not do much async programming in java as i would normally be using JavaScript or the RxJava Libary

I did not use RxJava for the solutions as i had to use the interface `CompletionStage<Optional<String>> processTransaction(Transaction tx);` 
I would have had to break the contract of the return type as Rx would have returned as Obersable or Flowable which TBO i would perfer, however i did quickly implement it for fun:)



To show i can do async programming in java if required, i have pulled out the java8 CompleteFutres, with a bit of cheeky reference to Java 8 in Action. Also that if the contract was change i would be just as easy to do in rx plus it has alot more benfits.




 ## Akka

I have never used akka before, but when i was quickly going over the docs i did not think i could complete this challange within that time scale.