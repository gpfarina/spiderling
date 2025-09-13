(ns spiderling.schemas
  (:require [malli.core :as m]))

(def url-schema
  [:map
   [:urls [:vector string?]]
   [:options {:optional true}
    [:map
     [:max-workers {:optional true} pos-int?]]]])
