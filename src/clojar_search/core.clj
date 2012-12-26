(ns clojar-search.core)
(require '[net.cgrand.enlive-html :as html]
         '[clj-http.client :as http])

(def ^:dynamic *base-url* "https://clojars.org/search?q=")

(defn build-query-string
  "build the string for the query"
  [search-term]
  (str *base-url* search-term))

(defn grab
  "grab a web page"
  [url]
  (html/html-resource (html/html-snippet (:body (http/get url)))))

(defn build-results-list
  "build the results data structure"
  [search-term]
  (html/select (grab (build-query-string search-term)) [:li.search-results]))

(defn content-block
  "get the content out of the block"
  [blk]
  (:content blk))

(defn name-only-block
  "grab the name"
  [blk]
  (first (:content (first (:content blk)))))

(defn name-block
  "grab the name"
  [blk]
  (first (:content (first blk))))

(defn description-block
  "grab the description"
  [blk]
  (first (:content (nth blk 3))))

(defn version-block
  "grab the version"
  [blk]
  (nth blk 1))

(defn url-block
  "grab the url"
  [blk]
  (str "https://clojars.org" (:href (:attrs (first blk)))))

(defn bust-block
  "break apart the block to just get the values we want"
  [blk]
  (let [hash-block (content-block blk)]
    {:name  (name-block hash-block)
     :description (description-block hash-block) 
     :version (version-block hash-block)
     :url (url-block hash-block)}))

(defn full-search
  "search for a package in the clojar repo"
  [search-term]
  (map bust-block (build-results-list search-term)))

(defn name-only-search
  "search for packages in the clojar repo, just return the names"
  [search-term]
  (map name-only-block (build-results-list search-term)))

(defn pretty-print
  "prints a line"
  [line]
  (do
    line))

(defn print-search
  "prints the search"
  [search-term]
  (map #(println %1 " - " %2) (range) (name-only-search search-term)))

(defn -main
  "search for a package"
  [term]
  (print-search term))
