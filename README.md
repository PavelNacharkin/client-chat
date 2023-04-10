# Клиент 
---
## Эта часть отвечает за функционал со стороны клиента
#### В ней реализовано:
- _подключение к серверу_ 
``` Socket socket = new Socket(HOST, PORT);```
- _ввод данных для авторизации или регистрации_

```    

   System.out.println("1) Авторизиция ");
   System.out.println("2) Регистрация ");
        
        int menu = scanner.nextInt();
        String prefix = null;

        if (menu == 1) {
            prefix = "!autho!";
            log.info("Authorization request");
        } else if (menu == 2) {
            prefix = "!regis!";
            log.info("Registration request");
        }

    System.out.println("Введите свой логин");
    String login = messageInputService.getMessage();

    System.out.println("Введите свой пароль");
    String password = messageInputService.getMessage();

    serverWriter.println(prefix + login + ":" + password);
    serverWriter.flush(); 
   ```

   - _отправка сообщений_
   ```
       serverWriter = new PrintWriter(socket.getOutputStream());
       serverWriter.println(consoleMessage.trim());
       serverWriter.flush();
 ```                
- _прием сообщений_
```
    MessageInputService serverReader = new MessageInputServiceImpl(socket.getInputStream());
    String inputFromServer = serverReader.getMessage();
 ```
 
