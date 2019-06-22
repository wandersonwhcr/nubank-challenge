(ns balance.service.users-test
  (:refer-clojure :exclude [find])
  (:require [clojure.test :refer :all]
            [balance.util :refer [uuid]]
            [balance.service.users :refer :all]))

(deftest test-users
  (testing "User Record"
    (let [id (uuid)]
      (let [user (->User id "John Doe")]
        (is (= id (:id user)))
        (is (= "John Doe" (:name user))))))

  (testing "set-bucket"
    (let [bucket (atom {})]
      (set-bucket bucket)
      (is (= bucket (get-bucket)))))

  (testing "fetch"
    (let [bucket (atom {})]
      (set-bucket bucket)
      (is (= 0 (count (fetch))))))

  (testing "fetch and save"
    (let [bucket (atom {}) user (->User (uuid) "John Doe")]
      (set-bucket bucket)
      (is (= 0 (count (fetch))))
      (is (= user (save user)))
      (is (= 1 (count (fetch))))
      (is (= user (find (:id user))))))

  (testing "fetch and save multiple"
    (let [bucket (atom {}) userA (->User (uuid) "John Doe") userB (->User (uuid) "João da Silva")]
      (set-bucket bucket)
      (is (= 0 (count (fetch))))
      (save userA)
      (save userB)
      (is (= 2 (count (fetch))))
      (is (= userA (find (:id userA))))
      (is (= userB (find (:id userB))))))

  (testing "save and delete"
    (let [bucket (atom {}) id (uuid)]
      (set-bucket bucket)
      (save (->User id "John Doe"))
      (is (= 1 (count (fetch))))
      (is (= id (delete id)))
      (is (= 0 (count (fetch))))))

  (testing "validate"
    (is (validate (->User (uuid) "John Doe")))
    (is (thrown? Exception (validate (->User "" "John Doe"))))
    (is (thrown? Exception (validate (->User (uuid) ""))))
    (is (thrown-with-msg? Exception #"^Invalid Data$" (validate (->User "" "")))))

  (testing "save with invalid data"
    (let [bucket (atom {}) user (->User "" "")]
      (set-bucket bucket)
      (is (thrown-with-msg? Exception #"^Invalid Data$" (save user)))))

  (testing "find with unknown identifier"
    (let [bucket (atom {}) user (->User (uuid) "John Doe")]
      (set-bucket bucket)
      (save user)
      (is (find (:id user)))
      (is (thrown-with-msg? Exception #"^Unknown Identifier$" (find (uuid))))))

  (testing "delete with unknown identifier"
    (let [bucket (atom {}) id (uuid)]
      (set-bucket bucket)
      (save (->User id "John Doe"))
      (is (delete id))
      (is (thrown-with-msg? Exception #"^Unknown Identifier$" (delete (uuid)))))))
