package DAO;

import service.Client;

//класс для хранения сущности клиента
public class ClientEntity {
    //переменная для хранения имени клиента сервиса(пользователя)
    private String username;
    //переменная для храненияя информации о состоянии клиента(пользователя), является ли он админом или нет
    private boolean isAdmin;

    //контруктор по умолчанию
    public ClientEntity(){
        //заполняем переменные "нулями"
        this.username = null;
        this.isAdmin = false;
    }

    //конструктор с параметрами: имя пользователя и состояние пользователя
    public ClientEntity(String username, boolean isAdmin){
        //заполняем переменные переданными значениями
        this.username = username;
        this.isAdmin = isAdmin;
    }

    //метод, возвращающий имя пользователя
    public String getUsername() {
        return username;
    }

    //метод, задающий новое имя пользователя
    public void setUsername(String username) {
        this.username = username;
    }

    //метод, возвращающий(true, false) состояние пользователя(является ли он админом)
    public boolean isAdmin() {
        return isAdmin;
    }

    //метод, задающий(true, false) состояние пользователя(является ли он админом)
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    //переопределение метода, возвращающего строку с названием переменных и их значениями
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append("\"username\" : \"").append(username).append("\",\n");
        sb.append("\"isAdmin\" : ").append(isAdmin);
        sb.append('\n');
        return sb.toString();
    }
}
