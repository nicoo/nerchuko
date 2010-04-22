(ns test.nerchuko.feature-selection.country
  (:use nerchuko.feature-selection
        nerchuko.utils
        nerchuko.helpers)
  (:use clojure.test))

(def training-data [[[:chinese :beijing :chinese] :yes]
                    [[:chinese :chinese :shanghai] :yes]
                    [[:chinese :macao] :yes]
                    [[:tokyo :japan :chinese] :no]])

(def training-dataset
     (map-firsts bag training-data))

(deftest feature-selection-document-frequency
  (are [k features] (= features
                       (find-features 'nerchuko.feature-selectors.document-frequency
                                      k training-dataset))
       1 #{:chinese}
       2 #{:shanghai :chinese}
       4 #{:shanghai :tokyo :macao :chinese}))

(deftest feature-selection-collection-frequency
  (are [k features] (= features
                       (find-features 'nerchuko.feature-selectors.collection-frequency
                                      k training-dataset))
       1 #{:chinese}
       2 #{:shanghai :chinese}
       4 #{:shanghai :tokyo :macao :chinese}))

(deftest feature-selection-chi-squared
  (are [k features] (= features
                       (find-features 'nerchuko.feature-selectors.chi-squared
                               k training-dataset))
       1 #{:tokyo}
       2 #{:tokyo :japan}
       4 #{:shanghai :tokyo :beijing :japan}))
