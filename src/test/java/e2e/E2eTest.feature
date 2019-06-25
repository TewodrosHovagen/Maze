Feature: e2e test
  Scenario : this test will run e2e test
    Given init the application
    When validating inputs arguments
    And parse file
    And create Single Game Output File
    And create new game manager object and set the output file
    Then run the game
    Then game ended maze solved within 33 steps.

      | arguments                                |
      | ".\src\main\resources\MazeExamples\maze1" |
      | "./output.txt" |