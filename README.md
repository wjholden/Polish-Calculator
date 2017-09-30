# Polish-Calculator

![screenshot](https://pbs.twimg.com/media/DK__ZsAVoAA0Y_K.jpg:large)

## Description
This program is a calculator that uses [prefix notation](https://en.wikipedia.org/wiki/Polish_notation) (aka "Polish" notation) instead of the more common infix notation. In prefix notation, operators are always placed before their operands instead of between or after them.

## Operation
You can use this program in two ways: as a Read-Evaluate-Print Loop (REPL) or as a simple 3D plotting tool.

To run this program as a REPL, execute ``java -jar Polish-Repl.jar`` in a command prompt.

To run the GUI version of this program, execute ``java -jar Polish-Graphical.jar`` in a command prompt. The GUI uses JOGL. A ``lib`` folder must coexist in the same directory as ``Polish-Graphical.jar`` and it must contain the files [gluegen-rt-natives-windows-amd64.jar](http://jogamp.org/deployment/jogamp-current/jar/gluegen-rt-natives-windows-amd64.jar), [gluegen-rt.jar](http://jogamp.org/deployment/jogamp-current/jar/gluegen-rt.jar), [jogl-all-natives-windows-amd64.jar](http://jogamp.org/deployment/jogamp-current/jar/jogl-all-natives-windows-amd64.jar), and [jogl-all.jar](http://jogamp.org/deployment/jogamp-current/jar/jogl-all.jar) or their equivalents for platforms other than Windows/x64.

## Examples
```
$ java -jar .\Polish-Repl.jar
Input: * 3 + 2 ^ 5 ^ 9 0
Output: 21.0
Input: ! 5
Output: 120.0
Input: log 10
Output: 1.0
Input: - ln e / 9 9
Output: 0.0
Input: sqrt ^ 3 8
Output: 81.0
Input: + ^ sin pi 2 ^ cos pi 2
Output: 1.0
Input: quit```
