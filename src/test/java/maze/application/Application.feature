Feature: Application main
  Scenario Outline: output file - relative path or absolute path
    When start app with <input_file> path and <output_file> path
    Then createAnOutputFile flag is true

    Examples:
      |input_file|output_file|
      |"./src/test/resources/fileDataParse/correctInputFileExample.txt"|"C:/Users/sb5844/Projects/biq/reports/output.txt"|
#      |"./src/test/resources/fileDataParse/correctInputFileExample.txt"|"./src/test/java/maze/application/output.txt"|