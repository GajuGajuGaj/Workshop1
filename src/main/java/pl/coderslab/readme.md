Program do zarządzania listą zadań.

1. Program najpierw wczytuje plik tasks.csv, robi jego kopię tasksTemp.csv.
2. Po przejściu całego procesu dodania lub usunięcia zadania zapisuje zmiany w pliku tasksTemp.csv, tak więc można dokonywać wielu edycji na jednej sesji programu.
3. Po wpisaniu nieistniejącego indeksu lub znaku innego niż cyfra podczas odejmowania, program wyświetla odpowiedni komunikat i ponownie pyta o indeks pozycji do usunięcia.
4. Przy użyciu komenty "list" program automatycznie cofa do menu głównego.
4. Komenda "exit" wpisana podczas dodawania lub usuwania wraca program do menu głównego.
5. Wyjście z programu komendą "exit", kopiuje plik tasksTemp.csv do pliku tasks.csv, nadpisuje go i usuwa plik tasksTemp.csv