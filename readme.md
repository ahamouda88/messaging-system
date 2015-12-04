# Messaging System

###### The Messaging System is implemented using Java 1.7 and tested using JUnit 4.10:
A Note regarding the messaging system, when posting a message with a duplicate text, I still consider it a new message, because you can send the same text as much as you can.

For removing compilation errors and testing every aspect of the application you will need JUnit jar file and Java 1.7.
###### Following are the steps to download the jar file and test the application:
- Download JUnit 4.10 from following URL: http://sourceforge.net/projects/junit/files/latest/download
- Create a "lib" folder inside the project and add the jar file in the "lib" folder.
- Right click on the jar file and select "Add to Build Path".
- Now we will be able to run the JUnit test classes in the "com.messaging.test" package.

###### For testing the whole application in a multi-thread environment, you will need to run the "MultiThreadTestCase" class in the "com.messaging.test" package. Instructions are given in this class.
