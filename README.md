# FormulaParser
[![CircleCI](https://circleci.com/gh/clockvoid/FormulaParser.svg?style=svg)](https://circleci.com/gh/clockvoid/FormulaParser)
[![codecov](https://codecov.io/gh/clockvoid/FormulaParser/branch/master/graph/badge.svg)](https://codecov.io/gh/clockvoid/FormulaParser)
[![jitpack](https://jitpack.io/v/clockvoid/FormulaParser.svg)](https://jitpack.io/#clockvoid/FormulaParser)

library for calculator for numbers with units

## Description
Now, it become a Kotlin with Gradle project!🎉

This is a library to calculate with units.
To make calculation, give `Parser` object a string valued numerical expression and call `compile()` function.
You will get `Expression` object and that's `evaluate()` Function will return the result by string.

## Installation
Please add these settings on your `build.gradle`.

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
    implementation 'com.github.clockvoid:FormulaParser:v0.1.0'
}
```

## Usage
At this time, you have to `clone` this repository to your local and make Jar or something.

## Test
I'm working hard.
I added code coverage for controlling test.

## Design
I used Recursive Descent Parsing.
