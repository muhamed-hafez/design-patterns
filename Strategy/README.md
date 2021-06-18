Wiki reference: https://en.wikipedia.org/wiki/Strategy_pattern
Video tutorial: https://www.youtube.com/watch?v=-NCgRD9-C6o&list=PLF206E906175C7E07&index=3https://en.wikipedia.org/wiki/Strategy_pattern


## Notes
* Decouple functionality to be determined at runtime
* Use composition instead of inheritence
* Consumer has a reference to an interface defining the strategy
* The exact implementation details (a class implementing the interface) are provided at runtime

### Examples
* Zoo: birds can fly, but dogs can't
* JuiceShop: different prices depending on the time of the day
