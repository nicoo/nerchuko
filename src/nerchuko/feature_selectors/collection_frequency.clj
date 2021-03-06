(ns nerchuko.feature-selectors.collection-frequency
  "Selection of features based on the number of occurrence across the
dataset.

This feature selector works with numeric features map. If given
documents are in any other representation, they are converted to a
numeric features map using prepare-doc."
  (:use nerchuko.utils
        nerchuko.helpers)
  (:use clojure.contrib.generic.functor))

(defn prepare-doc
  "Converts doc to a numeric features map by calling
nerchuko.helpers/numeric-features-map."
  [doc]
  (numeric-features-map doc))

(defn find-features
  "Returns a set of the top k most frequently occurring features.
Multiple occurrences of a feature in a document are counted
multiple times."
  [k training-dataset]
  (->> training-dataset
       (firsts)
       (map prepare-doc)
       (reduce merge-with-+)
       (largest-n-by-vals k)
       (keys)
       (set)))
