# Formal grammars
The two files here specify a formalized grammar for the following languages:
- **[Mashup Backus-Naur Form]()** (`.mbnf`), a variation on the IETF's
  [Augmented Backus-Naur Form]() that includes order-independent (i.e.
  permutation) grouping and quantified selection of elements within a group, 
  and
- **[Mashup Patch Format]()** (`.mpf`), a declarative language for declaring 
  the way the files of the game _Cassette Beasts_ should be changed, inspired 
  by the syntax of SQL.
  - If you are looking for the _implementation_ of Mashup Patch Format, see
    the `loader.parsers.mpf` package of this repository.