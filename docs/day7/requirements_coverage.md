# Матрица покрытия требований (Requirements Coverage)

**Проект:** Мобильное приложение «Заметки»  
**Составила:** Малкова Диана Сергеевна  

| ID требования | Описание требования | Тест-кейсы | Unit-тесты | Статус |
| :--- | :--- | :--- | :--- | :--- |
| REQ-01 | Создание новой заметки (заголовок + текст) | TC-01, TC-02, TC-08 | `validate_validNote_returnsTrue` | Покрыто (Passed) |
| REQ-02 | Валидация пустого ввода и длины заголовка | TC-02, TC-09 | `validate_emptyTitleAndContent_returnsFalse`, `validate_tooLongTitle_returnsFalse` | Покрыто (Passed) |
| REQ-03 | Редактирование существующей заметки | TC-03 | — | Покрыто (Passed) |
| REQ-04 | Удаление заметки с подтверждением | TC-04 | — | Покрыто (Passed) |
| REQ-05 | Локальное хранение данных (Room/SQLite) | TC-05 | — | Покрыто (Passed) |
| REQ-06 | Поиск заметок по заголовку и тексту | TC-06, TC-10 | `filterNotes_caseInsensitiveSearch_findsMatches` | Покрыто (Passed) |
| REQ-07 | Экспорт заметки в текстовый файл (.txt) | TC-07 | `sanitizeFileName_removesIllegalChars`, `toPlainText_blankTitle_usesDefaultTitle` | Покрыто (Passed) |
