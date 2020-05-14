
<p align="center">
<img src="https://raw.githubusercontent.com/Mixermachine/base-test/feature/new_logo/img/base-test-logo.svg" width="30%">
  </p>

# base-test &middot; [![Travis build](https://api.travis-ci.com/Mixermachine/base-test.svg?branch=master)](https://travis-ci.com/Mixermachine/base-test) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Mixermachine_base-test&metric=alert_status)](https://sonarcloud.io/dashboard?id=Mixermachine_base-test) [![Maven Central](https://img.shields.io/maven-central/v/de.a9d3.testing/base-test)](https://search.maven.org/artifact/de.a9d3.testing/base-test)

This project can perform some basic checks for your Java project so you don't have to, making it possible to reach a very high test coverage without writing repetitiv test cases.

## Description
This project was sparked by the need for testing getter/setter in a database heavy rest application (many entity classes).
Getter/Setter test might feel redundant and repetitive (you check the same thing for every class after all) but serve a purpose. 
Simple methods like getter and setter often fall victim to copy and paste errors and can introduce bugs which are very hard to find (nobody expects a method setA(int a) to internally set the variable b).
Additionally, this library should make it easier for you to instantiate classes with random data as it is often needed for further testing.
Simply execute fill(*yourClass*, true) on an object of the class TestDataProvider and see what you get back :).

<br/>
This project is tested on three environments (openjdk8, openjdk10, openjdk11 all on Travis Ubuntu Xenial) and uses no external libs except for JUnit 4 in the testing phase.
It should be very lightweight and fast to execute on nearly every system.

Sadly Travis needed to [drop the support for the Oracle JDK](https://github.com/sormuras/bach/issues/56) due to license changes by Oracle
and thus this project is no longer tested on this JDK.
Nonetheless it should still work. If you notice odd behaviour, please report it in an issue.

## Maven dependency
```
<dependency>
  <groupId>de.a9d3.testing</groupId>
  <artifactId>base-test</artifactId>
  <version>1.0.0</version>
</dependency>
```
Other build system in the [wiki](https://github.com/Mixermachine/base-test/wiki/Download-and-Install)

# Quick start example
Ok let's get dirty. In the code block below we will test the class Example with all available checks.
```
package de.example.test

import de.a9d3.testing.checks.*;
import de.a9d3.testing.executer.SingleThreadExecutor;

import org.junit.Test; //example uses JUnit4

public class ExampleTest {

    @Test
    public void baseTest() {
        SingleThreadExecutor executor = new SingleThreadExecutor();

        assertTrue(executor.execute(Example.class, Arrays.asList( 
                new CopyConstructorCheck(), new DefensiveCopyingCheck(),
                new EmptyCollectionCheck(), new GetterIsSetterCheck(),
                new HashcodeAndEqualsCheck(), new PublicVariableCheck())));
    }
}
```

Thats it? Yes thats it (in many cases).<br>
If your class contains more complex data structures which can not be initialized with the default TestDataProvider (the console output will tell you this) please refer to the [wiki](https://github.com/Mixermachine/base-test/wiki/Creating-a-custom-TestDataProvider)


## Checks
Many parts of this project can be used on their own, but I understand that you simply want to get done with the annoying base test stuff.
Therefore I created checks that help you to quickly test your code.

### GetterIsSetterCheck
(This is the check which sparked this project)
Getter/Is and Setter methods are often overlooked and annoying to test.
This checkClass will execute setter and getter/is methods and compare the results.

### CopyConstructorTest
Complex objects sometimes need to be copied (especially when you apply defensive copying) and thus we create copyConstructors.
These constructors are often forgotten when it comes to refactoring/adding new variables to the class.
This checkClass will check if the copyConstructors copies all objects correctly (needs equals methods at least in the classes of the inner variables).

### DefensiveCopyingCheck
Working with many objects in a large project can become quite complex and error-prone.
We can reduce this complexity if we take away the possibility to change the internal state of an object via a returned pointer by always returning a copy of mutable objects instead of directly returning the object.
A modification of the state should only be possible via the offered methods (edge cases exist of course).
This checkClass will compare the pointers of return/parameter values of the getter/is and setter methods.

### EmptyCollectionCheck
When for example working with lists in objects a calling program has to check for null, emptyList and list with a payload.
We cut away the null pointer check if the object never returns a null pointer for a collection (edge cases exist where a null pointer is truly wanted).
This checkClass will collect all getter methods with a collection as return type returns no null pointer.

### HashCodeandEqualsCheck
HashCode and equals methods compare the states of two objects with each other. A broken implementation can introduce bugs.
This checkClass will check if the hashCode and equals methods react to changes in the state of the objects.

### PublicVariableCheck
This checkClass will check if any public variables are presented. Initialize with PublicVariableCheck(true) to allow public static final variables.
