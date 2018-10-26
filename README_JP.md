# FormulaParser
library for calculator for numbers with units

## Description
KotlinとGradleを使って書き直しました！🎉

リポジトリの説明にある通り,単位付きの計算ができる電卓を作るためのライブラリです.  
Parserオブジェクトに式を文字列で渡して,compile()メソッドを呼び出して帰ってきたExpressionオブジェクトのevaluate()を呼び出すだけで,
計算結果を文字列で返してくれます.

## Installation
以下の設定を`build.gradle`に記述するだけです！

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
コードをダウンロードしてIDEでプロジェクトを作り,Jarにまとめるなり,CLIでそれをするなり,どんな使い方でもできます.

## Test
なぜTestディレクトリが無いのか,不思議に思う方がいらっしゃるかもしれません.理由は簡単,main()行っているからです.最悪ですね.善処します.

## Design
基調となっているデザインパターンはInterpreterPatternです.再帰下降構文解析を使っているので,当然ですね.
