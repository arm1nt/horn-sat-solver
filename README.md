# horn-sat-solver

A Horn formula is a special formula that consists of conjunctions of so called horn clauses.

A horn clause is an implication, where the left side is a conjunction of atoms and the right side is a single atom.

Instead of an atom, $\top$ and $\bot$ can be used as well.

e.g. $(a \land b \land c \implies d) \land (b \land d \land \top \implies a) \land (c \implies \bot)$



### Input format

| logical symbol | equivalent encoding |
| -------------- | ------------------- |
| $\top$         | 1                   |
| $\bot$         | 0                   |
| $\land$        | &                   |
| $\implies$     | ->                  |

Each horn clause has to be given on a new line and without the surrounding parentheses.



For the aforementioned given example the input file would look as follows:

``````
a & b & c -> d
b & d & 1 -> a
c -> 0
``````

