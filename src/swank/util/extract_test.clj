(ns swank.util.extract-test
  (:use [clojure.string :only (replace)]
	[clojure.pprint :only (pprint)]
	[clojure.tools.macro :only (symbol-macrolet mexpand-1)]
	swank.repl-starvars)) ; to access new variables

(defn replace-var-in-expr [[var val] expr]
  (letfn [(replacer [var val expr]
	    (let [[tag new-expr] (mexpand-1
				  `(symbol-macrolet [~var ~val] ~expr))]
	      (assert (= tag 'do))
	      new-expr))]
    (let [tmp-var (gensym (name var))]
      ;; tmp-var is needed because symbol-macro cannot contain self-reference
      (->> expr
	   (replacer var tmp-var)
	   (replacer tmp-var val)))))

(defn extract-test-from-repl []
  (let [test-inp (->> **1
		      (replace-var-in-expr ['*1 **2])
		      (replace-var-in-expr ['*2 **3])
		      ;; replacing could re-insert *1 which now refers to older form
		      (replace-var-in-expr ['*1 **3]))]
    `(~'is (~'= ~test-inp '~*1))))

;; (defn extract-test-from-repl []
;;   (let [test-inp (-> (str **1)
;; 		     (replace "*1" (str **2))
;; 		     (replace "*2" (str **3))
;; 		     ;; replacing could re-insert *1 which now refers to older form
;; 		     (replace "*1" (str **3))
;; 		     (read-string))]
;;     `(~'is (~'= ~test-inp '~*1))))

