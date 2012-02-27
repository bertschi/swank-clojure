(defvar testing-buffer "Testing"
  "The buffer were extracted tests will be appended to")

(defun slime-extract-test ()
  "Extract test from repl interaction and append it to the testing buffer."
  (interactive)
  (with-current-buffer (get-buffer-create testing-buffer)
    (save-excursion
      (goto-char (point-max))
      (let* ((extract-command "(clojure.pprint/pprint (swank.util.extract-test/extract-test-from-repl))")
	     (slime-current-thread :repl-thread)
	     (output
	      (first (slime-eval `(swank:eval-and-grab-output ,extract-command)))))
	(insert "\n" output "\n")))))

(provide 'slime-extract-test)
