# FormulaParser
library for calculator for numbers with units

## Description
リポジトリの説明にある通り,単位付きの計算ができる電卓を作るためのライブラリです.  
Parserオブジェクトに式を文字列で渡して,compile()メソッドを呼び出して帰ってきたExpressionオブジェクトのevaluate()を呼び出すだけで,
計算結果を文字列で返してくれます.

## Usage
コードをダウンロードしてIDEでプロジェクトを作り,Jarにまとめるなり,CLIでそれをするなり,どんな使い方でもできます.

ライセンスもない上にコピーライトもないのでコピペもOKです.(なんでもあり.面倒くさいから.)

## Test
なぜTestディレクトリが無いのか,不思議に思う方がいらっしゃるかもしれません.理由は簡単,main()行っているからです.最悪ですね.善処します.

## Design
基調となっているデザインパターンはInterpreterPatternです.再帰下降構文解析を使っているので,当然ですね.
