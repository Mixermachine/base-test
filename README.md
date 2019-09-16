# base-test &middot; [![Travis build](https://api.travis-ci.com/Mixermachine/base-test.svg?branch=master)](https://travis-ci.com/Mixermachine/base-test) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Mixermachine_base-test&metric=alert_status)](https://sonarcloud.io/dashboard?id=Mixermachine_base-test) [![Maven Central](https://img.shields.io/maven-central/v/de.a9d3.testing/base-test)](https://search.maven.org/artifact/de.a9d3.testing/base-test)

This project can perform some basic checks for your Java project so you don't have to, making it possible to reach a 100% test coverage.

## Description
This project was sparked by the need for testing getter/setter in a database heavy rest application (many entity classes).
Getter/Setter test might feel redundant and repetitive (you check the same thing for every class after all) but serve a purpose. 
Simple methods like getter and setter often fall victim to copy and paste errors and can introduce bugs which are very hard to find (nobody expects a method setA(int a) to internally set the variable b).
Additionally, this library should make it easier for you to instantiate classes with random data as it is often needed for further testing.
Simply execute fill(*yourClass*, true) on an object of the class TestDataProvider and see what you get back :).

The main concern of this project is to be easy to use and lightweight.
To meet the second goal no external libraries are used and all functionality stems from Java reflections.

<br/>
The project is tested on three environments (oraclejdk11, openjdk8, openjdk11 all on Travis Ubuntu Bionic).

## Checkers
Many parts of this project can be used on their own, but I understand that you simply want to get done with the annoying base test stuff.
Therefore I created checkers that help you to quickly test your code.

### GetterIsSetterCheck
Getter/Is and Setter methods are often overlooked and annoying to test.
This class will execute setter and getter/is methods and compare the results.

### HashCodeandEqualsCheck
HashCode and equals methods compare the states of two objects with each other. A broken implementation can introduce bugs.
This class will check if the hashCode and equals methods react to changes in the state of the objects.

### EmptyCollectionCheck
When for example working with lists in objects a calling program has to check for null, emptyList and list with a payload.
We cut away the null pointer check if the object never returns a null pointer for a collection (edge cases exist where a null pointer is truly wanted).
This class will collect all getter methods with a collection as return type returns no null pointer.

### DefensiveCopyingCheck
Working with many objects in a large project can become quite complex and error-prone.
We can reduce this complexity if we take away the possibility to change the internal state of an object via a returned pointer by always returning a copy of mutable objects instead of directly returning the object.
A modification of the state should only be possible via the offered methods (edge cases exist of course).
This class will compare the pointers of return/parameter values of the getter/is and setter methods.

### PublicVariableCheck
On the same note, a public variable allows for unsupervised external changes.
This class will check if any public variables are presented.
