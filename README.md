# java-formatter

Format Source Code written in Java.

## Yet another code formatter!

Whys:
* Code formatting should be a part of the build process, not something, the developer must keep in mind
* This solution is independent from IDE, editors or whatever
* It is not configurable like you know it. But even better: With Java knowledge you can implement it to whatever needs you have
* Thus, even code changing action is possible (if you want to).<br>
  For example if you want to kill all inline documentation or just the `@author` or `@Override` annotations

## How to implement a formatter

1. Check out this project
2. Change code to your needs (A more detailed second step will follow on stable release)
3. compile and run

## What it is not (yet)

* Ready to use!
* Only debugging output to sysout yet

## Usage

`java -jar java-formatter.jar /absolute/path/to/directory/to/scan`
