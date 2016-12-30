package-info
============

Do we need a package info, when we can have this README?
Anyhow. I have to write down some important issues.


Formatter Strategy
------------------

Adding a node to a code snippet will care about the Format of the node immediately. The node is not visited a second time and everything is only looking forward.

This means, NodeWrapper has methods "nextNode", but not "previousNode".

Another rule: When a node is added to a formatter, the resulting Source of the node must be the first thing to add (after whitespace things).

This is very important to not get insane. But there are some consequences:

### Indent Strategy

Snippets are controlling the indents itself and the indent must be appended to a specific node. Even, if the indent concerns the next line (after an EOL).