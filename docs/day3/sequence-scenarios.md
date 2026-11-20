# Диаграммы последовательности (день 3)

## Сценарий 1. Создание и сохранение заметки

```mermaid
sequenceDiagram
    actor U as Пользователь
    participant UI as Интерфейс
    participant S as NoteService
    participant R as NoteRepository
    U->>UI: Нажать «Новая заметка»
    UI-->>U: Показать редактор
    U->>UI: Ввести заголовок и текст
    U->>UI: Нажать «Сохранить»
    UI->>S: create_note(title, content)
    S->>S: Проверить, что заметка не пустая
    S->>R: add(note)
    R-->>S: id заметки
    S-->>UI: Созданная Note
    UI-->>U: Показать заметку в списке
```

## Сценарий 2. Редактирование и удаление заметки

```mermaid
sequenceDiagram
    actor U as Пользователь
    participant UI as Интерфейс
    participant S as NoteService
    participant R as NoteRepository
    U->>UI: Открыть заметку
    UI->>S: get_note(note_id)
    S->>R: get_by_id(note_id)
    R-->>UI: Данные заметки
    U->>UI: Изменить текст и сохранить
    UI->>S: update_note(note_id, title, content)
    S->>R: update(note)
    R-->>UI: Успешно
    U->>UI: Выбрать «Удалить»
    UI-->>U: Запрос подтверждения
    U->>UI: Подтвердить удаление
    UI->>S: delete_note(note_id)
    S->>R: delete(note_id)
    R-->>UI: Успешно
    UI-->>U: Обновлённый список заметок
```

## Сценарий 3. Поиск заметок

```mermaid
sequenceDiagram
    actor U as Пользователь
    participant UI as Интерфейс
    participant F as NoteSearch
    participant R as NoteRepository
    U->>UI: Ввести поисковый запрос
    UI->>R: get_all()
    R-->>UI: Список заметок
    UI->>F: search_notes(notes, query)
    F-->>UI: Отфильтрованный список
    UI-->>U: Показать результаты поиска
```

## Сценарий 4. Экспорт заметки в файл

```mermaid
sequenceDiagram
    actor U as Пользователь
    participant UI as Интерфейс
    participant E as NoteExporter
    participant FS as Файловая система
    U->>UI: Нажать «Экспорт»
    UI-->>U: Открыть выбор места сохранения
    U->>UI: Указать имя и путь файла
    UI->>E: export_txt(note, file_path)
    E->>FS: Записать данные в UTF-8
    FS-->>E: Результат записи
    E-->>UI: Успех или ошибка
    UI-->>U: Сообщение о результате
```
