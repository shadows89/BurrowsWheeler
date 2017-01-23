/**
  * Submitter:
  *     Alexander Bogacheno
  *     312867187
  *     shadow89@campus.technion.ac.il
  *
  *
  * An implementation of the Burrows-Wheeler algorithm.
  * In the BurrowsWheeler class there are two methods:
  *
  *   1. regular - preforms the Burrows-Wheeler transform
  *   2. inverse - preforms the inverse Burrows-Wheeler transform
*/
object BurrowsWheeler {
  def main(args: Array[String]) = {
    if(args.length < 2)
      println("Too few input arguments")
    if(args.length > 2)
      println("Too much input arguments")
    


    if(args(0).equals("regular")){
      if(args(1).last != '|')
        println("Invalid input string. String must end with '|' symbol.")
      else
        println(regular(args(1)))
    }
    else if(args(0).equals("inverse")){
      if(args(1).contains('|'))
        println(inverse(args(1)))
      else
        println("Invalid input string. String must end with '|' symbol.")
    }
    else
      println("Invalid option!");
  }

  /**
    * regular: preform the Burrows-Wheeler transform
    *     input: a string you would like to get its Burrows-Wheeler transform
    *         must have  the terminating symbol "|"
    *     output: - a string which is the Burrows-Wheeler transformation of the input string
    *
    *  example:
    *         input = "^BANANA|"
    *         output = "BNN^AA|A"
    */

  def regular(string: String): String = {

    /*
    * rotations: an auxiliary function that produces all the rotations of a string
    *     input: a string
    *     output: a list of strings that contains the all rotations of the input string.
    *             the string represents the table that is needed for the Burrows-Wheeler algorithm
    *
    *     example:
    *           input = "^BANANA|"
    *           output = {^BANANA|,
    *                    |^BANANA,
    *                    A|^BANAN,
    *                    NA|^BANA,
    *                    ANA|^BAN,
    *                    NANA|^BA,
    *                    ANANA|^B,
    *                    BANANA|^ }
    *
     */
    def rotations(string: String): List[String] = {
      /*
          recursively build all rotations by splitting the input string at different index
           and concating the two parts of the string in reverse
           example:
                index = 1
                input = "^BANANA|"
                "^BANANA|"  => "^" , "BANANA|"  => "BANANA|^"
       */
      def rotations_aux(string: String, index: Int): List[String] = {
        if (index == 0) {
          List[String]()
        }
        else {
          val stringsTuple = string.splitAt(index)
          List(stringsTuple._2.concat(stringsTuple._1)) ::: rotations_aux(string, index - 1)
        }
      }
      rotations_aux(string, string.length())
    }

    /*
     *  lastColumn: return the last column of lexically sorted table of rotations
     *      input: list of string rotations that represents the needed table
     *      output: a string representing the last column of the table
     *
     *      example:
     *          input = { ANANA|^B,
     *                    ANA|^BAN,
     *                    A|^BANAN,
     *                    BANANA|^,
     *                    NANA|^BA,
     *                    NA|^BANA,
     *                    ^BANANA|,
     *                    |^BANANA }
     *          output = "BNN^AA|A"
     *
     */
    def lastColumn(rotations: List[String]): String = {
      if (rotations.isEmpty)
        ""
      else {
        val char = rotations.head.last
        char.toString.concat(lastColumn(rotations.tail))
      }
    }

    // get all rotations and sort the rotations by lexical order
    val rotationsList: List[String] = rotations(string).sortWith((a: String, b: String) => a.compareTo(b) < 0)
    // return the last column of the table
    lastColumn(rotationsList)
  }

  /*
   *  inverse: preforms the reverse Burrows-Wheeler transform
   *      input: a string that is an output of the regular Burrows-Wheeler transform
   *      output: the original string before the Burrows-Wheeler transform (the original string must end
   *              with the "|" symbol)
   *
   *    example:
   *          input = "BNN^AA|A"
   *          output = "^BANANA|"
   */

  def inverse(string: String): String = {

    /*
     *  toStringList: creates a list of strings that contain only one letter from a given string
     *      input: string
     *      output: list of strings
     *
     *    example:
     *        input = "abcd"
     *        output = {"a",
     *                  "b",
     *                  "c",
     *                  "d" }
     */
    def toStringList(string: String): List[String] ={
      val charList = string.toCharArray.toList
      def charList2StringList(charList : List[Char]): List[String] ={
        if(charList.isEmpty)
          List[String]()
        else
          List(charList.head.toString) ::: charList2StringList(charList.tail)
      }
      charList2StringList(charList)
    }

    /*
     *  inverseAux: builds the next iteration table of the inverse Burrows-Wheeler transform
     *      input: 1. a list representing the input string to the inverse Burrows-Wheeler transform
     *             2. a list representing the table that was built and sorted in the previous iteration
      *     output: a list representing the table at the current iteration , before sorting
      *
      *
      *   example:
      *       input = 1. {"B",       2. {"A",
      *                   "N",           "A",
      *                   "N",           "A",
      *                   "^",           "B",
      *                   "A",           "N",
      *                   "A",           "N",
      *                   "|",           "^",
      *                   "A" }          "|" }
      *
      *        outout =  {"BA",
      *                   "NA",
      *                   "NA",
      *                   "^B",
      *                   "AN",
      *                   "AN",
      *                   "|^",
       *                  "A|" }
      *
      *
     */
    def inverseAux(string: List[String] , table: List[String]): List[String] ={
      if(string.isEmpty || table.isEmpty)
        List[String]()
      else
        List(string.head.concat(table.head)) ::: inverseAux(string.tail,table.tail)
    }

    /*
     * findOutputString: finding the output string in the table that was produced
     *      input: a list of strings that represents the table built during the inverse Burrows-Wheeler transform
     *      output: he original string (must end with the "|" symbol)
     *
     *    example:
     *        input = { "ANANA|^B",
     *                  "ANA|^BAN,
     *                  "A|^BANAN",
     *                  "BANANA|^",
     *                  "NANA|^BA",
     *                  "NA|^BANA",
     *                  "^BANANA|",
     *                  "|^BANANA" }
     *
     *        output = "^BANANA|"
     *
     */
    def findOutputString(table: List[String]): String = {
      if(table.isEmpty){
        throw new IllegalStateException("No String with End Of File!");
      }

      if(table.head.last.equals('|')){
        table.head
      }
      else{
        findOutputString(table.tail)
      }
    }

    // convert the input string to list
    val stringList = toStringList(string)
    // init the table (list of strings)
    var table = stringList
    // sort table by lexical order
    table = table.sortWith((a: String, b: String) => a.compareTo(b) < 0)
    // make (string.length - 1) iterations of adding the input string
    // to the table and sorting the table
    for( i <- 1 to (string.length - 1)){
      table = inverseAux(stringList,table)
      table = table.sortWith((a: String, b: String) => a.compareTo(b) < 0)
    }
    // finding the output string
    findOutputString(table)
  }
}
