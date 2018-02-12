# Unique Identifier

## Building 

The project comes with a `build.gradle` to build the project and run the unit tests. To build the project 

```
   ./gradlew build
```
If you use a Windows operating system a `gradlew.bat` has been included. 

## Running

You can either import the  project in your IDE of preference or you can run the project from the CLI. For the first, the IDE should automatically create the runnable. For the second, there is gradle task called `myrun` that will run the application. The project can work in two modes.

### Stdin

In this case a single argument should be provided. That is the value of `X`. For example, if the top ten values are requested:

```
   ./gradlew myrun -Pmyargs="10" 
```

### File

To read from a file two arguments must of be provided:

* Number of entries to output, the value of `X`
* File name location



```
   ./gradlew myrun -Pmyargs="10","src/main/resources/testFile.txt"
```


## Algorithmic Complexity Analysis

There are a number of ways to address this problem. In the following, I describe all the solutions that were investigated prior to making the proper choice. 

### Priority Blocking Queue (selected)

An unbounded priority queue based on a priority heap. The elements of the priority queue are ordered according to their natural ordering, or by a Comparator provided at queue construction time, depending on which constructor is used.

This implementation provides:

* `O(log n)` time for the enqueing and dequeing methods (offer, poll, remove() and add); 
* linear time for the remove(Object) and contains(Object) methods; 
* constant time for the retrieval methods (peek, element, and size);
* `O(n)` for space complexity.

### Custom Heap Implementation

To implement a heap with a binary tree with `O(log n)` time complexity, we need to store the total number of nodes as an instance variable. 
Since this approach has the same time complexity as the Priority Queue, it is easier and safer to use the Priority Queue. In summary, I had to choose between *build vs buy*. 

### Tree Map

A Red-Black tree based NavigableMap implementation. The map is sorted according to the natural ordering of its keys, or by a Comparator provided at map creation time, depending on which constructor is used. 
This implementation provides guaranteed `O(log n)` time cost for the containsKey, get, put and remove operations, and `O(n)` for space.  The algorithms are adaptations of those in Cormen, Leiserson, and Rivest's Introduction to Algorithms.

The advantage of a Priority Queue over a Tree Map is the linear time for the removal of an object.

### Threaded Divide and Conquer

This is a great option but there is a significant development complexity.

### Read entire file into memory + sort

This is probably the worst option as the solution should accommodate large files. It would take a lot of memory and the sorting would happen in a separate step. Assuming we use merge sort that would increase the worst time complexity to `O(n log(n))`.

## Logging

I have used `log4j2` for logging the output. A `log4j2.xml` is included under the `src/main/resources` folder (standard practice). It has been a modern practice to use `log4j2` since it provides the ability to define different levels of importance of the logged messages and the ability to use different sink for the output - the console, a file, etc. We could have also used a facade pattern with `SLF4J`, and then leverage swappable logging backends. It may have been an overkill for this project :)

## Unit testing

The tests can also be run separately by using `./gradlew test`. I have also added the ability to print out test result. There are many levels of unit testing:

* File format
* Main function testing 
* The size of the queue 
* Peeking the largest value
* Handling the data properly
* Handling the same numeric value 
* Overall test end-to-end


