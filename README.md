# Expression.WolframAlpha-Calculator
My Expression.WolframAlpha Java project (Calculator only through). Inspired by my Professor.

Expressions are evaluated by Recursive Descent Parsing done in the Expression.ExpressionEvaluate class. The expressions are converted to a series
of Token objects built into an expression tree. The expression tree is then evaluated in the Operation class.

TO-DO:
<p>
1. Introduce the ability to create functions. Created by using the 'define' keyword
  -e.g. define square x = x * x, so define square 5 = 5 * 5 => 25
</p>
