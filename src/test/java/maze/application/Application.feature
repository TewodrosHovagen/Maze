Feature: Application main
  Scenario Outline: output file - relative path or absolute path
    Given start the application
    When a list of paths
    Examples:
      |C:\\|
      |./src/test/java/maze/application/|
    Then