;; Был вдохновлен решением этой задачи в книге Clojure Programming 
;;
;; На всякий случай, (f 1 "hello") - вызов функции f с параметрами 1 и "hello", 
;; в Java это было бы ка то так: Utils.f(1 "hello") 
;; [1 "hello"] - вектор c элементами 1 и "hello"
;;
;; Начнем...
;;
;; Создадим неймспейс в котором будет находится все наши обьявленые функции.
;; Загрузим все из midje.sweet.
;; midje - супер няшная библеотека для тестирования https://github.com/marick/Midje 
;; midje.sweet - неймспейс в котором хранятся вещи нужные для тестов
(ns kata-3.core
  (:require [midje.sweet :refer :all]))

;; Обьявим что у нас есть empty-field. В Clojure это необходимо если мы хотим
;; где то использовать empty-filed до того как явно его опишем, 
;; см. http://clojuredocs.org/clojure_core/clojure.core/declare
;; На сколько я понимаю это все из за ограничени JVM, в ClojureScript можно и без declare
;; компилятор только warning напишет
(declare empty-field)
;; Создадим тест для первой функции
(fact "empty-field должен создать пустое поле заданого размера. Пустое - заплненое nil"
;;     функция         ожидаемый результат
   (empty-field 2 2) => [[nil nil]
                         [nil nil]]

    (empty-field 2 1) => [[nil nil]]

    (empty-field 1 2) => [[nil]
                          [nil]])

;; Создадим функцию которая сгенерирует поле (вектор векторов)
;; (repeat w nil) вернет последовательность из nil длинной w
;; (repeat 2 nil) => (nil nil)
;; А (vec ..) создаст вектор из последовательности те, (vec (repeat 2 nil)) => [nil nil]
;; Соответственно (repeat h (vec (repeat w nil))) будет (repeat h [nil nil)) а это последовательность 
;; из [nil nil] повторяющееся h раз, те (repeat 2 [nil nil]) => ([nil nil] [nil nil])
(defn empty-field [w h] (vec (repeat h (vec (repeat w nil)))))

;; Пусть символ живой клетки будет возвращатся функцией l. Это для того что 
;; бы не привязыватся к конкретному значению дальше в коде.
;; (l) => :live
(defn l [] :on)

;; В общем, ситуация такая же как и с функцией empty-field
;; Мы должны сделать declare для того что бы сначала написать тест
;; а потом реализацию
(declare make-alive)

;; Также нам необходима функция для оживления клеток
;; Напишем тест для нее
(fact "make-alive должна оживить указаные клетки"
;; Создадим пустую доску размером 2 Х 2. 
;; и скажем что живой должна стать клетка с координатами [0 0]
   (make-alive (empty-field 2 2) [[0 0]]) => [[(l) nil]
                                              [nil nil]]
;; Для двух живих клеток                                              
    (make-alive (empty-field 2 2) [[0 0] [1 1]]) => [[(l) nil] 
                                                     [nil (l)]])

;; make-alive принимает первым аргументом доску, вторым колекцию координат живых клеток
;; и воскрешает клетки по заданым координатам!
;; сердце этой функции - ф-я reduce, 
;;
;; Чуть-чуть про reduce:
;; reduce - очень полезная в жизни программиста штука,
;; reduce принемает первым агрументом функцию, назовем ее f, f должна принемать 2 параметра 
;; и что то возвращать,  а вторым - колекцию, на первом шаге reduce применяет f к первым двум аргументам колекции,
;; полечает результат, на втором шаге reduce применяет к f предыдущему результу и третему элементу
;; начальной колекции, таким образом перебирается вся колекция, и в конце мы получаем результат вычеслений.
;; Проще на примере (reduce + [1 2 3 4 5] ) => 15 если разложить на шаги то получим:
;; 1-й шаг       2-й шаг           3-й шаг            4-й шаг             результат
;; (+ 1 2)--(+ (+ 1 2) 3)--(+ (+ (+ 1 2) 3) 4)--(+ (+ (+ (+ 1 2) 3) 4) 5) => 15
;; Кроме функции и колекции reduce может принемать еще и элемент для первого шага,
;; предыдущий пример эквивалентен (reduce + 0 [1 2 3 4 5]), в таком случае первый шаг
;; будет состоять из указаного элемента и первого элемента колекции , в нашем случае 
;; первым шагом будет (+ 0 1) 
;;
;; У нас в качестве функции для reduce мы передаем анонимную ф-ю которая принемает доску и координату, анонимная функция
;; меняет нужный элемент на живой, это делается с помощью ф-ции assoc-in. 
;; assoc-in принемает колекцию которая подвержит изминению, колекцию ключей (в нашем случае эти ключи как рза и будут
;; координатами), и элемент который будет установлен 
;; (assoc-in [[1 2] [3 4]] [1 0] 9) возьмет элемент с индексом 1 из изначальной колекции, это будет [3 4]
;; и у этой колекции установит элемент по индексу 0 в 9, те:  
;; (assoc-in [[1 2] [3 4]] [1 0] 9) => [[1 2] [9 4]]
;; 
;; Для первого шага у нас будет использоватся доска и reduce будет делать по координатам
;; те. (make-alive [[nil nil] [nil nil]] [[0 0] [1 0]]) можно разложить в
;; (assoc-in 
;;      (assoc-in [[nil nil] [nil nil]] [0 0] (l))
;;      [1 0] (l)) => [[(l) nil] [(l) nil]]
(defn make-alive [board coords] 
  (reduce 
    (fn [board coord] (assoc-in board coord (l))) 
    board coords))

;; Следующая функция для получения соседей клетки 
(declare neighbours-for)
;; Соседи это (Moore neighborhood), у каждой клетки на поле без ограничений всегда будет 8 соседей
;; http://en.wikipedia.org/wiki/Moore_neighborhood
;; Например для клетки "К", соседи - "C":
;; [nil nil nil  nil]
;; [C    C   C   nil]
;; [C    K   C   nil]
;; [C    C   C   nil]
;;
;; Напишем тест
(fact "neighbours-for должна возвращать соседей клетки"
  (neighbours-for [2 2]) => [[1 1] [1 2] [1 3] [2 1] [2 3] [3 1] [3 2] [3 3]] 
  (neighbours-for [0 0]) => [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]])
;;
;; Наша функция будет генерировать все возможные варианты изминения координат (кроме [0 0]) 
;; и применит эти варианты изминения к начальным координатам
;; все возможные варианты будут такими:
;;          (for [dx [-1 0 1]
;;                dy [-1 0 1]]
;;                :when (not= 0 dx dy) [dx dy]) => '([-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1])
;; теперь каждый вариант нужно применить к начальным координатам [(+ x dx) (+ y dy)] 
;; получим:
(defn neighbours-for [[x y]] 
  (for [dx [-1 0 1]
        dy [-1 0 1]
        :when (not= 0 dx dy)]
        [(+ x dx) (+ y dy)] ))

;; Теперь функция которая будет считать следующее поколение живых клеток, на основе существующих живых клеток
;; Как всегда...
(declare next-gen)
;; next-gen принемает множество живый клеток и возвращает множество живых клеток следующего поколения
;; #{ 1 2 3} - множество из 1 2 и 3
;; Тест для функции next-gen
(fact "next-gen должна возвращать следющее поколение живых клеток, согласно правилам игры жизнь"
  (fact "в пустой (мёртвой) клетке, рядом с которой ровно три живые клетки, зарождается жизнь"
;; [(l) nil nil]    [nil nil nil]
;; [nil nil nil] => [nil (l) nil] 
;; [(l) nil (l)]    [nil nil nil] 
     (next-gen #{[0 0] [2 0] [2 2]}) => #{[1 1]})

  (fact "если у живой клетки есть две или три живые соседки, то эта клетка продолжает жить; 
        в противном случае (если соседей меньше двух или больше трёх) клетка умирает 
        («от одиночества» или «от перенаселённости»)."
;; 3 живых соседки, клетка живет, меньше двух - клетка умирает, 3 живых соседа - мертвая оживает!
;; [(l) nil nil]    [nil nil nil]
;; [nil (l) nil] => [(l) (l) nil] 
;; [(l) nil (l)]    [nil (l) nil] 
    (next-gen #{[0 0] [1 1] [2 0] [2 2]}) => #{[1 0] [1 1] [2 1]}

;; 2 живых соседки, клетка продолжает жить, меньше двух - клетка умирает
;; [(l) nil nil]    [nil nil nil]
;; [nil (l) nil] => [nil (l) nil] 
;; [nil nil (l)]    [nil nil nil] 
     (next-gen #{[0 0] [1 1] [2 2]}) => #{[1 1]}

;; больше трех живы соседей - клетка умирает, 3 живых соседа - мертвая оживает!
;; [(l) nil (l)]    [nil (l) nil]
;; [nil (l) nil] => [(l) nil (l)] 
;; [(l) nil (l)]    [nil (l) nil] 
     (next-gen #{[0 0] [0 2] [1 1] [2 0] [2 2]}) => #{[0 1] [1 0] [1 2] [2 1]}))
 

(defn next-gen [live-cells] 
  ;; для каждой живой клетки считаем соседей и все это дело сплющиваем.
  ;; mapcat это как map 
  ;; map принемпет функцию и колекцию и возвращает колекцию к каждому 
  ;; елементу которой применена функция (map f [n1 n2 n3]) => [(f n1) (f n2) (f n3)]
  ;; mapcat работает так же, только кроме этого сплючивает результат, это занчит что
  ;; если f возвращает список, например (f n1) => [q w e]; (f n2) => [r t y]; (f n3) => [u i o]
  ;; то (map f [n1 n2 n3])    => [[q w e] [r t y] [u i o]]
  ;; а  (mapcat f [n1 n2 n3]) => [q w e r t y u i o]
  ;; http://clojuredocs.org/clojure_core/clojure.core/mapcat
  ;;
  ;; В общем мы получаем последовательность соседей для всех живых клеток
  (let [neighbours (mapcat neighbours-for live-cells)
  ;; Теперь сгрупируем соседей ко личеству повторений
  ;; frequencies принемает последовательность элементво 
  ;; и возвращает карту в которой ключ - элемент колекции
  ;; а значение - количство повторений
  ;; (frequencies ['a 'b 'a 'a]) => {a 3, b 1}
  ;; Кстати {a 3, b 1} - Карта в которой ключи "a" и "b" а значени 3 и 1
        grouped-neighbours (frequencies neighbours)
        
        next-generation (for [[cell n] grouped-neighbours
          ;;                 Когда 3 живых клетки клетка оживает, или продолжает жить
                              :when (or (= n 3)
          ;;                  Когда 2 живых соседа и клетка жива, она продолжает жить          
                                      (and (= n 2) 
                                           (live-cells cell)))] 
          ;;                  Возвращаем живую клетку 
                         cell)]
  ;; Делаем множество из следующего поколения и возвращаем его 
    (set next-generation)))

;; Собственно все, можно рисовать glider 

; [[nil nil nil nil nil]
 ; [nil nil :on nil nil]
 ; [:on nil :on nil nil]
 ; [nil :on :on nil nil]
 ; [nil nil nil nil nil]]

(let [w 5
      h 5] 
  (->> 
    (iterate next-gen #{[2 0] [2 1] [2 2] [1 2] [0 1]})
    (drop 3)
    first 
    ; Отфильтруем клетки которые не входят в доску
    (filter (fn [[x y]] (and (<= 0 x w) 
                           (<= 0 y w))))
    (make-alive (empty-field 5 5))
    (clojure.pprint/pprint)))










