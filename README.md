# Burrows-Wheeler
* Submiter : Alexander Bogachenko
* ID: 312867187
* Project type: Small mini project
* Files: 
*       BurrowsWheeler.scala
* Scala version that was used:
*       2.10.6

#### Burrows–Wheeler transform
The Burrows–Wheeler transform (BWT, also called block-sorting compression) rearranges a character string into runs of similar characters. This is useful for compression, since it tends to be easy to compress a string that has runs of repeated characters. More importantly, the transformation is reversible, without needing to store any additional data. The BWT is thus a "free" method of improving the efficiency of text compression algorithms, costing only some extra computation.

More information on the Burrows–Wheeler transform see: [Burrows–Wheeler transform](https://en.wikipedia.org/wiki/Burrows%E2%80%93Wheeler_transform)
 
#### Implementation  
This implementation of the Burrows–Wheeler transformation is in scala and is based on the explanations and exapmles in the link showen above.
The implementation consists of a single object named BurrowsWheeler that has two functions: 
 - regular - preforms the regular Burrows-Wheeler transform
     * input: a string you would like to get its Burrows-Wheeler transform
            must have  the terminating symbol "|"
     *  output: - a string which is the Burrows-Wheeler transformation of the input string
    *  example: 
             ```sh
             input = "^BANANA|"
             output = "BNN^AA|A"
             ```
             
 - inverse - preforms the inverse Burrows-Wheeler transform
   * input: a string that is an output of the regular Burrows-Wheeler transform
   * output: the original string before the Burrows-Wheeler transform (the original string must end with the "|" symbol)
   *    example:
             ```sh
             input = "BNN^AA|A"
             output = "^BANANA|"
             ```
             
Further explanation and documentation of auxiliry function can be found in the BurrowsWheeler.scala file.

#### Running Example

* <path> is the path to the containing folder of the BurrowsWheeler.scala file
* <option> can get one of two values:
    *   regular  -- for running the regular Burrows-Wheeler transform
    *   inverse -- for running the inverse Burrows-Wheeler transform
* <string> the string on which either of the transformations will be applied.
    *   For the regular transformation the string must end with the '|' symbol.
    *   For the inverse transformation the string must include the '|' symbol.

To run the code:
```sh
$ cd <path>
$ sbt
$ run <option> <string>
```


