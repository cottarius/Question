INSERT INTO questions (question, answer, theme, is_impotent)
VALUES ('Что такое DDL? Какие операции в него входят? Рассказать про них.', 'DDL (Data Definition Language) -  операторы определения данных (Data Definition Language, DDL):\n\n
- CREATE создает объект БД (базу, таблицу, представление, пользователя и т. д.),\n
- ALTER изменяет объект,\n
- DROP удаляет объект;\n
- TRUNCATE удаляет таблицу и создает её пустую заново, но если в таблице были foreigh key, то создать таблицу не получится. Rollback после TRUNCATE невозможен', 'SQL_DATABASE', false),
       ('Что такое DML? Какие операции в него входят? Рассказать про них.', 'операторы манипуляции данными (Data Manipulation Language, DML):\n\n
- SELECT выбирает данные, удовлетворяющие заданным условиям,\n
- INSERT добавляет новые данные,\n
- UPDATE изменяет существующие данные,\n
DELETE удаляет данные при выполнении условия WHERE;', 'SQL_DATABASE', false),
       ('Что такое TCL? Какие операции в него входят? Рассказать про них.', 'операторы управления транзакциями (Transaction Control Language, TCL):\n\n
- BEGIN служит для определения начала транзакции\n
- COMMIT применяет транзакцию,\n
- ROLLBACK откатывает все изменения, сделанные в контексте текущей транзакции,\n
- SAVEPOINT разбивает транзакцию на более мелкие.', 'SQL_DATABASE', false),
       ('Что такое DCL? Какие операции в него входят? Рассказать про них.', 'операторы определения доступа к данным (Data Control Language, DCL):\n\n
- GRANT предоставляет пользователю (группе) разрешения на определенные операции с объектом,\n
- REVOKE отзывает ранее выданные разрешения,\n
- DENY задает запрет, имеющий приоритет над разрешением;', 'SQL_DATABASE', false),
       ('Нюансы работы с NULL в SQL. Как проверить поле на NULL?', 'NULL - специальное значение (псевдозначение), которое может быть записано в поле таблицы базы данных.\n NULL соответствует понятию «пустое поле», то есть «поле, не содержащее никакого значения».\n\n
NULL означает отсутствие, неизвестность информации.\n Значение NULL не является значением в полном смысле слова: по определению оно означает отсутствие значения и не принадлежит ни одному типу данных. Поэтому NULL не равно ни логическому значению FALSE, ни пустой строке, ни 0. При сравнении NULL с любым значением будет получен результат NULL, а не FALSE и не 0. Более того, NULL не равно NULL!\n\n
команды: IS NULL, IS NOT NULL', 'SQL_DATABASE', false),
       ('Виды Join’ов?', '- INNER JOIN (Простой JOIN): Возвращает строки, которые имеют соответствующие значения в обеих таблицах. Если нет совпадения, строка не возвращается.\n
SELECT *FROM table1 INNER JOIN table2 ON table1.column = table2.column;\n\n
- LEFT (OUTER) JOIN: Возвращает все строки из левой таблицы и соответствующие строки из правой таблицы. Если нет совпадения, для правой таблицы возвращаются NULL значения.\n
SELECT *FROM table1 JOIN table2 ON table1.column = table2.column;\n\n
- RIGHT (OUTER) JOIN: Возвращает все строки из правой таблицы и соответствующие строки из левой таблицы. Если нет совпадения, для левой таблицы возвращаются NULL значения.\n
SELECT *FROM table1 RIGHT JOIN table2 ON table1.column = table2.column;\n\n
- FULL (OUTER) JOIN: Возвращает строки, если они имеют соответствие в одной из таблиц. Если нет совпадения, для недостающих значений возвращаются NULL.\n\n
- CROSS JOIN - перекрестное (или декартово) произведение. Каждая строка одной таблицы соединяется с каждой строкой второй таблицы, давая тем самым в результате все возможные сочетания строк двух таблиц. Аналогичного результата можно достичь просто перечислив таблицы в FROM через запятую.', 'SQL_DATABASE', false),
       ('Что лучше использовать join или подзапросы? Почему?', 'Обычно лучше использовать JOIN, поскольку в большинстве случаев он более понятен и лучше оптимизируется СУБД (но 100% этого гарантировать нельзя). Так же JOIN имеет заметное преимущество над подзапросами в случае, когда список выбора SELECT содержит столбцы более чем из одной таблицы.\n\n
Подзапросы лучше использовать в случаях, когда нужно вычислять агрегатные значения и использовать их для сравнений во внешних запросах.', 'SQL_DATABASE', false),
       ('Что делает UNION?', 'В языке SQL ключевое слово UNION применяется для объединения результатов двух SQL-запросов в единую таблицу, состоящую из схожих записей. Оба запроса должны возвращать одинаковое число столбцов и совместимые типы данных в соответствующих столбцах. Необходимо отметить, что UNION сам по себе не гарантирует порядок записей. Записи из второго запроса могут оказаться в начале, в конце или вообще перемешаться с записями из первого запроса. В случаях, когда требуется определенный порядок, необходимо использовать ORDER BY.\n\n
Разница между UNION и UNION ALL заключается в том, что UNION будет пропускать дубликаты записей, тогда как UNION ALL будет включать дубликаты записей.', 'SQL_DATABASE', false),
       ('Чем WHERE отличается от HAVING ( ответа про то что используются в разных частях запроса - недостаточно)?', 'WHERE нельзя использовать с агрегатными функциями, HAVING можно (предикаты тоже).\n\n
В HAVING можно использовать псевдонимы только если они используются для наименования результата агрегатной функции, в WHERE можно всегда.\n\n
HAVING стоит после GROUP BY, но может использоваться и без него. При отсутствии предложения GROUP BY агрегатные функции применяются ко всему выходному набору строк запроса, т.е. в результате мы получим всего одну строку, если выходной набор не пуст.
', 'SQL_DATABASE', false),
       ('Что такое ORDER BY?', 'ORDER BY (сортировка по выбранному столбцу)  упорядочивает вывод запроса согласно значениям в том или ином количестве выбранных столбцов. Многочисленные столбцы упорядочиваются один внутри другого. Возможно определять возрастание ASC или убывание DESC для каждого столбца. По умолчанию установлено - возрастание.', 'SQL_DATABASE', false),
       ('Что такое DISTINCT?', 'DISTINCT (исключает дублирование строк)  указывает, что для вычислений используются только уникальные значения столбца. После SELECT и перед FROM. ', 'SQL_DATABASE', false),
       ('Что такое GROUP BY?', 'GROUP BY (группировка данных) используется для агрегации записей результата по заданным атрибутам.\n
Cоздает отдельную группу для всех возможных значений (включая значение NULL)\n
При использовании GROUP BY все значения NULL считаются равными.', 'SQL_DATABASE', false),
       ('Что быстрее убирает дубликаты distinct или group by?', 'Быстрее убирает дубликаты оператор DISTINCT.\n\n
DISTINCT просто фильтрует дубликаты, не группируя данные. Это более эффективная операция, особенно для больших наборов данных.\n\n
GROUP BY, с другой стороны, сначала группирует данные по указанным столбцам, а затем возвращает одну строку для каждой группы. Это более дорогостоящая операция, поскольку она требует дополнительной обработки для группировки данных.', 'SQL_DATABASE', false),
       ('Что такое LIMIT?', 'Позволяет ограничить количество выводимых записей. После FROM', 'SQL_DATABASE', false),
       ('Что такое EXISTS?', 'EXISTS берет подзапрос, как аргумент, и оценивает его как TRUE, если подзапрос возвращает какие-либо записи и FALSE, если нет.', 'SQL_DATABASE', false),
       ('Расскажите про операторы IN, BETWEEN, LIKE.', '• IN - определяет наличие данных в масиве.\n
SELECT * FROM Persons WHERE name IN (''Ivan'',''Petr'',''Pavel'');\n\n
• BETWEEN определяет диапазон значений. В отличие от IN, BETWEEN чувствителен к порядку, и первое значение в предложении должно быть первым по алфавитному или числовому порядку.\n
SELECT * FROM Persons WHERE age BETWEEN 20 AND 25;\n\n
• LIKE применим только к полям типа CHAR или VARCHAR, с которыми он используется чтобы находить подстроки. В качестве условия используются символы шаблонизации (wildkards) - специальные символы, которые могут соответствовать чему-нибудь: % Любая строка, содержащая ноль или более символов _ (подчеркивание) Любой одиночный символ\n\n\n
_ замещает любой одиночный символ. Например, ''b_t'' будет соответствовать словам ''bat'' или ''bit'', но не будет соответствовать ''brat''.\n
% замещает последовательность любого числа символов. Например ''%p%t'' будет соответствовать словам ''put'', ''posit'', или ''opt'', но не ''spite''.\n
SELECT * FROM UNIVERSITY WHERE NAME LIKE ''%o'';', 'SQL_DATABASE', false),
       ('Что делает оператор MERGE? Какие у него есть ограничения?', 'MERGE позволяет осуществить слияние данных одной таблицы с данными другой таблицы. При слиянии таблиц проверяется условие, и если оно истинно, то выполняется UPDATE, а если нет - INSERT. При этом изменять поля таблицы в секции UPDATE, по которым идет связывание двух таблиц, нельзя.\n\n
MERGE Ships AS t  -- таблица, которая будет меняться\n
USING (SELECT запрос ) AS s ON (t.name = s.ship)  -- условие слияния\n
    THEN UPDATE SET t.launched = s.year -- обновление\n
WHEN NOT MATCHED -- если условие не выполняется\n
    THEN INSERT VALUES(s.ship, s.year) -- вставка', 'SQL_DATABASE', false),
       ('Какие агрегатные функции вы знаете?', 'Агрегатных функции - функции, которые берут группы значений и сводят их к одиночному значению.\n\n
Несколько агрегатных функций:\n
COUNT - производит подсчет записей, удовлетворяющих условию запроса;\n
CONCAT - соединяет строки;\n
SUM - вычисляет арифметическую сумму всех значений колонки;\n
AVG - вычисляет среднее арифметическое всех значений;\n
MAX - определяет наибольшее из всех выбранных значений;\n
MIN - определяет наименьшее из всех выбранных значений.', 'SQL_DATABASE', false),
       ('Что такое ограничения (constraints)? Какие вы знаете?', 'Ограничения - это ключевае слова, которые помогают установить правила размещения данных в базе. Используются при создании БД.\n\n
- NOT NULL указывает, что значение не может быть пустым.\n
- UNIQUE обеспечивает отсутствие дубликатов.\n
- PRIMARY KEY - комбинация NOT NULL и UNIQUE. Помечает каждую запись в базе данных уникальным значением.\n
- CHECK проверяет вписывается ли значение в заданный диапазон ( s_id int CHECK(s_id > 0) )\n
- FOREIGN KEY создает связь между двумя таблицами и защищает от действий, которые могут нарушить связи между таблицами. FOREIGN KEY в одной таблице указывает на PRIMARY KEY в другой.\n
- DEFAULT устанавливает значение по умолчанию, если значения не предоствлено (name VARCHAR(20) DEFAULT "noname").\n\n
Какие отличия между PRIMARY и UNIQUE?\n
По умолчанию PRIMARY создает кластерный индекс на столбце, а UNIQUE - некластерный. PRIMARY не разрешает NULL записей, в то время как UNIQUE разрешает одну (а в некоторых СУБД несколько) NULL запись.\n
Таблица может иметь один PRIMARY KEY и много UNIQUE.\n\n
Может ли значение в столбце, на который наложено ограничение FOREIGN KEY, равняться NULL?\n
Может, если на данный столбец не наложено ограничение NOT NULL.', 'SQL_DATABASE', false),
       ('Что такое суррогатные ключи?', 'Суррога́тный ключ — это дополнительное служебное поле, автоматически добавленное к уже имеющимся информационным полям таблицы, предназначение которого — служить первичным ключом.', 'SQL_DATABASE', true),
       ('Что такое индексы? Какие они бывают?', 'Индексы относятся к методу настройки производительности, позволяющему быстрее извлекать записи из таблицы. Индекс создает структуру для индексируемого поля. Необходимо просто добавить указатель индекса в таблицу.\n\n
Содержит структуры бинарных деревьев\n\n
Есть три типа индексов, а именно:\n
1. Уникальный индекс (Unique Index): этот индекс не позволяет полю иметь повторяющиеся значения. Если первичный ключ определен, уникальный индекс применен автоматически.\n\n
2. Кластеризованный индекс (Clustered Index): Кластеризованный индекс хранит реальные строки данных в листьях индекса. Возвращаясь к предыдущему примеру, это означает что строка данных, связанная со значение ключа, равного 123 будет храниться в самом индексе. Важной характеристикой кластеризованного индекса является то, что все значения отсортированы в определенном порядке либо возрастания, либо убывания. Таким образом, таблица или представление может иметь только один кластеризованный индекс. В дополнение следует отметить, что данные в таблице хранятся в отсортированном виде только в случае если создан кластеризованный индекс у этой таблицы.\n
Таблица не имеющая кластеризованного индекса называется кучей.\n\n
3. Некластеризованный индекс (Non-Clustered Index): Создаются только после клатерного индекса, создаются автоматически при объявлении столбца UNIQUE.В отличие от кластеризованного индекса, листья некластеризованного индекса содержат только те столбцы (ключевые), по которым определен данный индекс, а также содержит указатель на строки с реальными данными в таблице. Это означает, что системе подзапросов необходима дополнительная операция для обнаружения и получения требуемых данных. Содержание указателя на данные зависит от способа хранения данных: кластеризованная таблица или куча. Если указатель ссылается на кластеризованную таблицу, то он ведет к кластеризованному индексу, используя который можно найти реальные данные.', 'SQL_DATABASE', true),
       ('Чем TRUNCATE отличается от DELETE?', 'DELETE - оператор DML, удаляет записи из таблицы, которые удовлетворяют условиям WHERE. Медленнее, чем TRUNCATE. Есть возможность восстановить данные.\n\n
TRUNCATE - DDL оператор, удаляет все строки из таблицы. Нет возможность восстановить данные - сделать ROLLBACK.
', 'SQL_DATABASE', false),
       ('Что такое хранимые процедуры? Для чего они нужны?', 'Хранимая процедура — объект базы данных, представляющий собой набор SQL-инструкций, который хранится на сервере. Плюс в том что компилируются только 1 раз что увеличивает производительность.\n\n Хранимые процедуры очень похожи на обыкновенные методы языков высокого уровня, у них могут быть входные и выходные параметры и локальные переменные, в них могут производиться числовые вычисления и операции над символьными данными, результаты которых могут присваиваться переменным и параметрам. В хранимых процедурах могут выполняться стандартные операции с базами данных (как DDL, так и DML). Кроме того, в хранимых процедурах возможны циклы и ветвления, то есть в них могут использоваться инструкции управления процессом исполнения.\n\n
Хранимые процедуры позволяют повысить производительность, расширяют возможности программирования и поддерживают функции безопасности данных. В большинстве СУБД при первом запуске хранимой процедуры она компилируется (выполняется синтаксический анализ и генерируется план доступа к данным) и в дальнейшем её обработка осуществляется быстрее.', 'SQL_DATABASE', true),
       ('Что такое представления (VIEW)? Для чего они нужны?', 'View - (Псевдонимы для запросов SELECT) виртуальная таблица, представляющая данные одной или более таблиц альтернативным образом. Нужны для ограничения доступа к данным и также для сокрытия реализации\n\n
В действительности представление – всего лишь результат выполнения оператора SELECT, который хранится в структуре памяти, напоминающей SQL таблицу. Они работают в запросах и операторах DML точно также как и основные таблицы, но не содержат никаких собственных данных. Представления значительно расширяют возможности управления данными. Это способ дать публичный доступ к некоторой (но не всей) информации в таблице.\n\n
Представления могут основываться как на таблицах, так и на других представлениях, т.е. могут быть вложенными (до 32 уровней вложенности).', 'SQL_DATABASE', false),
       ('Что такое временные таблицы? Для чего они нужны?', 'Подобные таблицы удобны для каких-то временных промежуточных выборок из нескольких таблиц.\n\n
Создание временной таблицы начинается со знака решетки #. Если используется один знак #, то создается локальная таблица, которая доступна в течение текущей сессии. Ели используются два знака ##, то создается глобальная временная таблица(живет пока открыт SQL Management ). В отличие от локальной глобальная временная таблица доступна всем открытым сессиям базы данных.\n\n
    CREATE TABLE #ProductSummary\n
    (ProdId INT IDENTITY,\n
    ProdName NVARCHAR(20),\n
    Price MONEY)', 'SQL_DATABASE', false),
       ('Что такое транзакции? Расскажите про принципы ACID.', 'Транзакция - (совокупность операций над данными как чтения так и записи) это воздействие на базу данных, переводящее её из одного целостного состояния в другое и выражаемое в изменении данных, хранящихся в базе данных.\n\n
ACID-принципы транзакций:\n
    • Атомарность (atomicity) гарантирует, что транзакция будет полностью выполнена или потерпит неудачу, где транзакция представляет одну логическую операцию данных. Это означает, что при сбое одной части любой транзакции происходит сбой всей транзакции и состояние базы данных остается неизменным.\n\n
    • Согласованность (consistency). Транзакция, достигающая своего завершения и фиксирующая свои результаты, сохраняет согласованность базы данных\n\n
    • Изолированность (isolation). Во время выполнения транзакции параллельные транзакции не должны оказывать влияние на ее результат.\n\n
    • Долговечность (durability). Независимо от проблем (к примеру, потеря питания, сбой или ошибки любого рода) изменения, сделанные успешно завершённой транзакцией, должны остаться сохраненными после возвращения системы в работу.', 'SQL_DATABASE', true),
       ('Расскажите про уровни изолированности транзакций.', 'При параллельных транзакциях могут возникать некоторые проблемы:\n\n
1. Lost Update - потерянное обновление. Происходит, когда обе транзакции одновременно обновляют данные и затем вторая транзакция откатывает изменения, вследствие чего изменения обеих транзакций теряются. Данная проблема решена во всех современных СУБД\n\n
2. Dirty Read - “грязное” чтение. Транзакция читает данные, измененные параллельной транзакцией, которая еще не завершилась. Если эта параллельная транзакция в итоге будет отменена, тогда окажется, что первая транзакция прочитала данные, которых нет в системе.\n\n
3. Non-Repeatable Read - неповторяющееся чтение. Происходит, когда первая транзакция читает одни и те же данные дважды, но после первого прочтения вторая транзакция изменяет (update) эти же данные и делает коммит, вследствие чего вторая выборка в первой транзакции вернет другой результат.\n\n
4. Phantom Read - фантомное чтение. Происходит, когда первая транзакция читает одни и те же данные дважды, но после первого прочтения вторая транзакция добавляет новые строки или удаляет старые и делает коммит, вследствие чего вторая выборка в первой транзакции вернет другой результат (разное количество записей).\n\n\n
    **Уровни изоляции в SQL**\n\n
1. Read Uncommitted\n
Это самый низкий уровень изоляции. Согласно стандарту SQL на этом уровне допускается чтение «грязных» (незафиксированных) данных.\n\n
Однако в PostgreSQL требования, предъявляемые к этому уровню, более строгие, чем в стандарте: чтение «грязных» данных на этом уровне не допускается.\n\n
2. Read Committed\n
Не допускается чтение «грязных» (незафиксированных) данных. Транзакция может видеть только те незафиксированные изменения данных, которые произведены в ходе выполнения ее самой.\n\n
3. Repeatable Read\n
Не допускается чтение «грязных» (незафиксированных) данных и неповторяющееся чтение. В PostgreSQL на этом уровне не допускается также фантомное чтение.\n\n
4. Serializable\n
Не допускается ни один из феноменов, перечисленных выше, в том числе и аномалии сериализации.', 'SQL_DATABASE', true),
       ('Что такое нормализация и денормализация? Расскажите про 3 нормальные формы?', '1. Нормализация - это процесс организации, структуризации данных в базе, который обеспечивает большую гибкость базы данных за счет исключения избыточности и несогласованности зависимостей.\n
Целью является уменьшение потенциальной противоречивости хранимой в базе данных информации.\n\n
2. Денормализация базы данных — намеренное снижение или нарушение форм  нормализации базы данных, обычно — чтобы ускорить чтение из базы за счет добавления избыточных данных. В общем, это процесс, обратный к нормализации.\n
Так происходит потому, что теория нормальных форм не всегда применима на практике.\n\n
Каждая нормальная форма включает в себя предыдущую. 3 Типа форм:\n
- Первая нормальная форма (1NF) - значения всех полей атомарны (неделимы), нет множества значений в одном поле.\n
- Вторая нормальная форма (2NF) - все неключевые поля зависят только от ключа целиком, а не от какой-то его части.\n
- Третья нормальная форма (3NF) - все неключевые поля не зависят друг от друга.', 'SQL_DATABASE', true),
       ('Что такое TIMESTAMP?', 'DATETIME предназначен для хранения целого числа: YYYYMMDDHHMMSS. И это время не зависит от временной зоны настроенной на сервере. Размер: 8 байт\n\n
TIMESTAMP хранит значение равное количеству секунд, прошедших с полуночи 1 января 1970 года по усреднённому времени Гринвича. Тогда была создана Unix. При получении из базы отображается с учётом часового пояса. Размер: 4 байта', 'SQL_DATABASE', false),
       ('Шардирование БД', 'При большом количестве данных запросы начинают долго выполняться, и сервер начинает не справляться с нагрузкой.\n    Одно из решений, что с этими данными делать — это масштабирование базы данных. Например, шардинг или репликация.    Суть Шардинга заключается в разделении БД на отедльные части так чтобы каждую из них можно было вынести на отдельный сервер\n\n
Шардинг бывает вертикальным(партицирование) и горизонтальным.
    1. Вертикальный шардинг - это выделение таблицы или группы таблиц на отдельный сервер.\n
    2. Горизонтальный - это разделение одной таблицы на разные сервера.\n
Единственное отличие горизонтального масштабирования от вертикального в том, что горизонтальное будет разносить данные по разным инстансам в других базах.', 'SQL_DATABASE', false),
       ('EXPLAIN', 'Оператор EXPLAIN в SQL используется для анализа и оценки запросов SELECT в базе данных. Он выводит информацию о том, как база данных будет выполнять запрос, например, какие индексы будут использоваться, в каком порядке будут объединяться таблицы и т. д. Анализ плана выполнения запроса с помощью оператора EXPLAIN помогает оптимизировать запросы и улучшить производительность базы данных.\n\n
      Рассчитывает стоимость на основании двух операций:\n
- page_cost (input-output) - количество считанных сегментов (страниц) с жесткого диска (минимальная единица стоимости - 1.0 )\n
- cpu_cost - количество операций процессора (минимальная единица стоимости - 0.01)', 'SQL_DATABASE', true),
       ('Как сделать запрос из двух баз?', 'Если в запросе таблица указывается с именем базы данных database1.table1, то таблица выбирается из database1, если просто table1, то - из активной базы данных.\n\n
Общий принцип перекрестного запроса к двум базам в пределах одного MySQL-сервера:\n\n
    SELECT t1.*, t2.*\n
    FROM database1.table1 AS t1\n
    INNER JOIN database2.table2 AS t2 ON t2.field1 = t1.field1\n\n
Алиасы t1 и t2 использовать не обязательно. Но они уменьшают размер запроса, и улучшают читабельность.', 'SQL_DATABASE', false),
       ('Механизмы оптимизации запросов в БД', '- изменение исходного кода запроса\n
- обновление статистики, на основе которой планировщик строит планы\n
- денормализация: создание временных таблиц или создание индексов\n
- изменение параметров планировщика, управляющих выбором порядка соединения наборов строк\n
- изменение параметров планировщика, управляющих выбором метода доступа к данным (enable_seqscan, enable_indexscan, enable_indexonlyscan, enable_bitmapscan)
- изменение параметров планировщика, управляющих способом соединения наборов строк (enable_nestloop, enable_hashjoin, enable_mergejoin);
- изменение параметров планировщика, управляющих использованием ряда операций: агрегирование на основе хеширования, материализация временных наборов строк, выполнение явной сортировки при наличии других возможностей.', 'SQL_DATABASE', false),
       ('Что такое «триггер»?', 'Триггер (trigger) — это хранимая процедура, которая не вызывается непосредственно, а исполняется при наступлении определенного события ( вставка, удаление, обновление строки / это хранимая процедура особого типа, исполнение которой обусловлено действием по модификации данных: добавлением, удалением или изменением данных в заданной таблице реляционной базы данных.\n    Триггер запускается сервером автоматически и все производимые им модификации данных рассматриваются как выполняемые в транзакции, в которой выполнено действие, вызвавшее срабатывание триггера.\n\n
Момент запуска триггера определяется с помощью ключевых слов BEFORE (триггер запускается до выполнения связанного с ним события) или AFTER (после события).', 'SQL_DATABASE', false);