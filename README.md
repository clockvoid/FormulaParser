# FormulaParser
library for calculator for numbers with units

## これは何
リポジトリの説明にある通り,単位付きの計算ができる電卓を作るためのライブラリです.  
Parserオブジェクトに式を文字列で渡して,compile()メソッドを呼び出して帰ってきたExpressionオブジェクトのevaluate()を呼び出すだけで,
計算結果を文字列で返してくれます.

## Design
基調となっているデザインパターンはInterpreterPatternです.再帰下降構文解析を使っているので,当然ですね.
