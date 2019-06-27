Feature: E2E Flow
  Scenario Outline: output file - relative path or absolute path
    Given init the match
    When start app with correct <mazes_folder> and <players_package>
    Then results after execution are correct
    Examples:
      |mazes_folder|players_package|
      |"./src/test/resources/mazeExamples"|"maze.player"|
