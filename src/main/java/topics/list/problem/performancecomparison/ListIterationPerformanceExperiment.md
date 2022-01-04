# List Iteration Performance Experiment
In this problem, I have implemented an experiment using the run time of a couple different ways to iterate over a list 
and do some operations on the elements.
The goal of the experiment is to see which school of iteration technique produces the lowest overhead.

## Experiment Setup
In this experiment, I am going to time the execution of blocks of code,
where every code block adopts one way of iteration with one operation on the element iterated.

### Hyper Parameters
1. List: an array list filled with `1 million` integers
2. Repetition: each code block will be executed `100` times and the average of all executions will be calculated.

### Schools of Iterations
#### For Loop with Index
This is the common java for loop implementations. It looks something like this
```java
for (int i = 0; i < list.size(); i++) {
    // Logic operated on each element
}
```

#### For Each Loop
This syntax is a slightly more human-readable version of the previous loop
```java
for (Integer element: list) {
    // Logic operated on each element
}
```

#### Streaming from `java.util.stream`
This adopts `Functional Programming` concepts and is introduced to Java in Java 8.
```java
list.stream().map(<lambda function>)
```

#### Parallel Streaming from `java.util.stream`
This is a parallel version of the previous method.

Although it leverages multi-threading out of the box, 
it has stronger restrictions on the type of operations being performed.
The operation must be `Commutative`, `Associative`, and `Distributive`.
Yes, they are from the good old days of algebra classes when you learn additions etc.
We can spend a lecture on why these properties are important, but for now let's keep it simple.

### Operations on each element
In order to compare them apple-to-apple and make sure the power of `Parallel Streaming` can be leveraged,
I choose to the following two operations:
1. Max of a list of integers
2. Sum of a list of integers

# Results
The run time of all different types of iterations on the two different operations:

| Run Time      | For Loop With Index | For Each Loop | Streaming | Parallel Streaming |
|---------------|:-------------------:|:-------------:|:---------:|:------------------:|
| Max Operation |       1.68 ms       |    1.87 ms    |  4.56 ms  |       1.39 ms      |
| Sum Operation |       1.69 ms       |    1.67 ms    |   8.0 ms  |       3.47 ms      |

# Analysis
It looks like the run time results from the `Max` operation is more aligned with my expectations:
1. `Parallel Streaming` method has the best performance over ways of iteration
2. `Parallel Streaming` takes roughly a quarter of the time of `Regular Streaming` 
   because the device I run the experiment has 4 cores.
   
However, I am surprised to see
1. `Regular Streaming` has this much overhead compared to the first two ways of iterations
2. Both ways of streaming nature perform poorly on the `Sum` operation.
   I wonder if it is because of some internal JVM optimizations on the `+`.
