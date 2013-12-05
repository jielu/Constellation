Constellation
=============

Project for class CS6422

Constellation is distrubted framework for testing evolving software systems based on Hadoop MapReduce. This is a pilot study and implementation to help assess whether MapReduce framework can fit to Software Engineering Research directly. 

Three components are currently supported:
1. Java Program Instrumentor
2. JUnit Testcases Runner
3. Fault Locator

Environment tested:
Hadoop 1.2.1

Section 1: Completed code structure
The completed code for our solution including the customized subjects are at github https://github.com/jielu/Constellation. Structure is as below:
- Hadoop
  # Our main source code, including all the implementation in MapReduce framework and some test driver.
- Hadoop_Test
  # Some of our test cases
- insectJ
  # We reused and changed the implementation of our original instrumentation and coverage tool to make it fit to MapReduce. 
- subjects
  # The subjects we customized. They can be used directly in Hadoop.
- FaultLocalization_Baseline
  # The customized subjects to test fault localization baseline.
- Instrumentation_Baseline
  # The customized subjects to test instrumentation baseline. 

Section 2: Run executables
1. Run Java Program Instrumentor
1) Prepare your subject to be instrumented. Suppose your subject is in ../Instrumentation_Baseline/ant. The directory structure should be as below:
- classes
  Put your compiled classes to this folder. 
- input
  Create a text file to list the path to each class file. Make sure each entry starts with "insect_input" as below, because we are going to put your classes to "insect_input" folder. 
  insect_input/classes/org/apache/tools/ant/AntClassLoader$ResourceEnumeration.class
- lib
  Just copy the lib in our example. 
2) Run the following commands or put them together into one single bash script. test_insect.jar is the executable to instrument your program. 
  bin/hadoop fs -mkdir /insect
  bin/hadoop fs -mkdir /insect/insect_input
  bin/hadoop fs -copyFromLocal ../Instrumentation_Baseline/ant/* /insect/insect_input
  bin/hadoop jar ../Instrumentation_Baseline/ant/test_insect.jar 
3) You can get the instrumented class files by running the following command line to your local drive:
  bin/hadoop fs -get /insect/insect_output ~/Download

  
2. Run JUnit Testcases Runner & Fault Locator together
  Let's use Apache Common Collector as an example, the necessary jar and other resource files are stored in subjects/common.
  bin/hadoop fs -cp subjects/common /common
  bin/hadoop jar ../subjects/common/test_common.jar
Then you can see the intermediate result and running status from the console, and finally the result will be stores in /common/output.
