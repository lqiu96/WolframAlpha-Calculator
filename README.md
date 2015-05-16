# WolframAlpha-Calculator
My WolframAlpha Java project (Calculator only through). Inspired by my Professor.

Expressions are evaluated by Recursive Descent Parsing done in the ExpressionEvaluate class. The expressions are converted to a series
of Token objects built into an expression tree. The expression tree is then evaluated in the Operation class.

TO-DO:
1. Modify so that variables are only created created by using the keyword 'let' in front. Done so that I can introduce new
global scientific and mathmatical constants to be evaluated.
  -e.g. let x = 5 (new) instead of x = 5 (old)
  Proposed: 5/15/15
  Completed: NA

2. Introduce the ability to create functions. Created by using the 'define' keyword
  -e.g. define square x = x * x, so define square 5 = 5 * 5 => 25
