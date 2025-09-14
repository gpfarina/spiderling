(ns spiderling.schemas-test
  (:require [clojure.test :refer [deftest is testing]]
            [spiderling.schemas :refer [url-schema]]
            [malli.core :as m]))

(deftest url-schema-validation-test
  (testing "Valid urls"
    (m/validate url-schema {:urls ["http://a.com"]})                   ;; ✅ valid
    (m/validate url-schema {:urls ["http://a.com"] :options {}})       ;; ✅ valid
    (m/validate url-schema {:urls ["http://a.com"] :options {:max-workers 5}})
    (m/validate url-schema {:urls ["https://c.it", "d.org"] :options {:max-workers 9}}))) ;; ✅ valid
