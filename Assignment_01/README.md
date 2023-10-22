## Zadanie 01

### Idea zadania
Zadanie polega na napisaniu kodu programu (klasy), który będzie prostym kalkulatorem wzbogaconym o pamięć i stos.

### Niepoprawne operacje
Zakładamy, że użytkownik będzie używać kalkulatora zgodnie z przeznaczeniem poszczególnych metod. Użytkownik nie będzie próbował wykonywać operacji np. z użyciem ujemnych indeksów dla pamięci, czy przepełniających stos.

### Indeksowanie pamięci
Pamięć kalkulatora indeksowana jest w sposób naturalny (tak jak tablice w Java). Jeśli pamięć ma N pozycji, to pierwszą poprawną jest 0, ostatnią N-1.

### Stan początkowy
Stan początkowy obiektu kalkulatora to:

    akumulator - ustawiony na 0
    pamięć - wypełniona zerami
    stos - nieużywany 

### Konstruktor
Klasę będącą rozwiązaniem można wyposażyć w konstruktor. Ja będę używać jej (tworzyć jej obiekty) wyłącznie poprzez konstruktor bezparametrowy.

### Wprowadzanie danych
Państwa kod będzie używany poprzez mój program. Nie ma potrzeby wprowadzania do niego jakiegokolwiek interfejsu użytkownika wczytującego dane np. z terminala. Użycie będzie opierać się wyłącznie o wywoływanie poszczególnych metod.

### Elementy statyczne
Należy mieć na względzie, że w trakcie testu utworzonych zostanie wiele obiektów. Operacje zlecone jednemu z nich nie mogą wpływać na pozostałe.

### Dostarczanie rozwiązania
Proszę o dostarczenie kodu źródłowego klasy ```Calculator```. W klasie można umieścić własne metody i pola. Klasa ```Calculator``` ma dziedziczyć po klasie ```CalculatorOperations```. 
