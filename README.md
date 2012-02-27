# Swank Clojure

[Swank Clojure](http://github.com/technomancy/swank-clojure) is a
server that allows SLIME (the Superior Lisp Interaction Mode for
Emacs) to connect to Clojure projects.

For any further information please refer to the original page.

## REPL Testing

This is a hack that does basically two things:

1. Add variables to the REPL which hold the last few inputs. These
   variables are in the namespace swank.repl-starvars and named **1,
   **2 and **3.

   The names are inspired by *1, *2 and *3, but instead of holding the
   last output values, they contain the last, second to last and third
   most recent input form that you entered to the REPL.

2. Using this variables an new slime functionality slime-extract-test
   is provided. Calling this function will take your last REPL
   interaction and compile it into a test form suitable for using with
   clojure.test.
   
   The extracted test forms are simply appended to a buffer called
   "Testing".  Using the global variable TODO any other buffer can be
   used for collecting the tests.

### Example usage

    