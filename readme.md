# Messaging System

###### The Messaging System is implemented using Java 1.7 and tested using JUnit 4.10:
Note regarding the messaging system, when posting a message with a duplicate text, I still consider it a new message, 
because you can send the same text as much as you can.

For removing compilation errors and testing every aspect of the application you will need JUnit jar file and Java 1.7.
###### Following are the steps to download the jar file and test the application:
1) Download JUnit 4.10 from following URL: http://sourceforge.net/projects/junit/files/latest/download
2) Add the jar file in the "lib" folder of the application.
3) Right click on the jar file and select "Add to Build Path".
4) Now we will be able to run the JUnit test classes in the "com.messaging.test" package.

###### For testing the whole application in a multi-thread environment, you will need to run the "MultiThreadTestCase" class
in the "com.messaging.test" package.
Note: Instructions are given in this class.