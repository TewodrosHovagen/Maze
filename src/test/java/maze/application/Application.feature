Feature: Application main
  Scenario Outline: output file - relative path or absolute path
    Given init the application
    When start app with <input_file> path and <output_file> path
    Then createAnOutputFile flag is true
    Examples:
      |input_file|output_file|
      |"./src/test/resources/fileDataParse/correctInputFileExample.txt"|"C:\Users\sb5844\Projects\biq\Maze\src\test\java\maze\application\output1.txt"|
      |"./src/test/resources/fileDataParse/correctInputFileExample.txt"|"./src/test/java/maze/application/output2.txt"|
    #TO TEACHER NOTE - The relative path needs to be changed according to your own environment path.

  Scenario Outline: input file - relative path or absolute path
    Given init the application
    When start app with <input_file> path and <output_file> path
    Then isInputFileExist flag is true
    Examples:
      |input_file|output_file|
      |"C:\Users\sb5844\Projects\biq\Maze\src\test\resources\fileDataParse\correctInputFileExample.txt"|"./src/test/java/maze/application/output3.txt"|
      |"./src/test/resources/fileDataParse/correctInputFileExample.txt"|"./src/test/java/maze/application/output4.txt"|
    #TO TEACHER NOTE - The relative path needs to be changed according to your own environment path.

  Scenario Outline: output file - incorrect path
    Given init the application
    When start app with correct <input_file> path and incorrect <output_file> path
    Then createAnOutputFile flag is false
    Examples:
      |input_file|output_file|
      |"./src/test/resources/fileDataParse/correctInputFileExample.txt"|"./src/test/java/maze/application/xxx/output1.txt"|

  Scenario Outline: input file - incorrect path
    Given init the application
    When start app with incorrect <input_file> path and correct <output_file> path
    Then isInputFileExist flag is false
    Examples:
      |input_file|output_file|
      |"./src/test/resources/fileDataParse/xxx/correctInputFileExample.txt"|"./src/test/java/maze/application/output1.txt"|


  Scenario Outline: output file - missing path
    Given init the application
    When start app with one missing path <input_file>
    Then isRunThePlayer flag is false
    And isInputFileExist flag is true
    And createAnOutputFile flag is false
    Examples:
      |input_file|
      |"./src/test/resources/fileDataParse/correctInputFileExample.txt"|


  Scenario Outline: input file - missing path
    Given init the application
    When start app with one missing path <output_file>
    Then isRunThePlayer flag is false
    And isInputFileExist flag is false
    And createAnOutputFile flag is false
    Examples:
      |output_file|
      |"./src/test/java/maze/application/output1.txt"|

  Scenario: input file and output file - missing path
    Given init the application
    When start app with two missing paths
    Then isRunThePlayer flag is false
    And isInputFileExist flag is false
    And createAnOutputFile flag is false
