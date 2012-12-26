# clojar-search

A Clojure library designed to search the clojar repository for packages

This is in pretty rough shape right now, probably not worth using at all. Basically, it's a learning exercise

## Usage

lein run "package_name"

or

lein repl
> (require 'clojar-search.core)
> (clojar-search.core/-main "package_name")

## License

Copyright © 2012 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
