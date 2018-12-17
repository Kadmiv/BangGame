По пунктам

Разработать приложение для Android 5+ работающее по следующему алгоритму:
1) При первом запуске приложения выполняется запрос на веб-сервер, который случайно отвечает {"allow":true} или {"allow":false};
	- Данный функционал был реализован на основе Retrofit - Рандомно выбираетя путь на false_response.json, либо на true_response.json - которые находятся в репозитории.
2) Полученный ответ сервера запоминается приложением навсегда, дальнейшие запросы к серверу не выполняются;
	- Реализовано на основе SharedPreferences - savedServerAnswer
3) В зависимости от полученного ответа, приложение после запуска:
    а) если allow = false: показывает webview с сайтом https://html5test.com/, JavaScript должен работать
    	- Готово - реализовано на андроиде
    б) если allow = true: показывает игру, работающую по принципу "Ready Steady Bang" (com.noodlecake.rsb). От вас требуется сделать минимально функциональный вариант игры. Приветствуется использование игровых движков, например Unity, Godot.
    	- Игровая часть программы была реализована на освнове LibGDX. Я немного работал с Unity, посмотрел пару туториалов по Godot. Но самым доступным и простым путем была эта библиотека.


Другие требования:

1) приложение не должно быть слишком требовательным к устройству, тесты будут проходить на Samsung SM-A300H/DS;
2) предусмотреть возможность сброса записанного в п.2 значения ответа сервера (для тестирования). Если записанное значение сбрасывается вместе с очисткой данных приложения в Android, дополнительно ничего делать не нужно;
	- SharedPreferences 
3) минимизировать размер APK. Идеальный конечный размер APK - до 16МБ, хотя жёсткого ограничения по размеру сверху нет;
4) можно использовать любую технологию разработки Android приложений, любые фреймворки и библиотеки;
5) количество игровых функций не имеет значения. Не нужно реализовывать дополнительных, кроме означенных в п. задания 3.б;
6) веб-сервер для приложения реализуется вами с использованием произвольной технологии.
	- П.2

Проблемы в реализации:
- проблемка со звуком - не совсем коректно производит воспроизведение звуком ( скорее всего это всязано с началом воспроизведения звуком самих файлов )
- сделано не все как в игре - думаю за денек работы доработал нормальный интерфей и было бы всё ок. (Много времени потерял на проблему с обработкой нажаити. Если добавлять актеров в группы, то в последующем невозможно обработать нажатие на них. Изначально в игре была неплохая декопозиция, не никак не мног решить эту проблему. В итоге все игровые обьекты решил накидать в GameScreen. Кода не много, но выглядит не так )
- не реализована одиночная игра - думаю в этом проблемы не много, так как реализация требует не очень много времени.