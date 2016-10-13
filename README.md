# java-formatter

Format Source Code written in Java.

## Yet another code formatter!

Whys:
* Code formatting independent from IDE, editors or whatever
* Not configurable but (with Java knowledge easy) implementable to whatever needs you have
* Code changing action (if you want to).<br>
  For example if you want to kill all inline documentation or just the `@author` or `@Override` annotations
* automatable and priceless

## How to implement a formatter

1. Check out this project
2. let CodeActionDeciderSimpleFactory return your own implementation of CodeActionDecider
3. compile and run

## What it is not (yet)

* Ready to use!
* Able to produce good looking code from Chaos.<br>
  (However, it is up to you writing a good pre-processing method).
* Only debugging output to sysout yet
