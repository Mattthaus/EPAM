package DAO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Hashtable;
import java.util.Optional;

//класс для работы с объектами сущности клиентов, наследуемый от интерфейса DAO
//ключ - имя клиента(пользователя), значение - объект класса клиент
public class ClientRepository implements DAO<String, ClientEntity> {

    //переменная для хранения объекта таблицы(ключ, значение), где ключ это строка с именем пользователя(username), а значение это объект клиента
    //то есть это объект, в котором мы храним всех наших клиентов
    //мы можем работать c информацией о любом клиенте, зная только его имя(username)
    Hashtable<String, ClientEntity> clientTable = new Hashtable<String, ClientEntity>();


    //констуктор по умолчанию, может выбросить исключение ввода,вывода и преобразования типов данных
    public ClientRepository() throws IOException, ParseException {
        //создаем объект для работы с ресурсами класса ClientRepository
        ClassLoader classLoader = ClientRepository.class.getClassLoader();
        //создаем объект для работы с файлом, который хранит информацию о клиентах
        File file = new File(classLoader.getResource("Clients.json").getFile());
        //записываем путь к файлу
        String path = file.getAbsolutePath();
        //создаем объект для парсирования данных из json
        JSONParser jsonParser = new JSONParser();
        //открываем json файл с информацией о клиентах и записываем ее в json объект
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(path));
        //записываем в json массив информацию о клиентах
        JSONArray jsonArray = (JSONArray) jsonObject.get("clients");

        //идем по json массиву клиентов
        for (Object o : jsonArray) {
            //записываем информацию о клиенте в json объект
            JSONObject object = (JSONObject) jsonParser.parse(o.toString());

            //записываем значения из json объекта в переменные
            String username = object.get("username").toString();
            boolean isAdmin = Boolean.valueOf(object.get("isAdmin").toString());

            //добавляем в таблицу новый объект клиента
            //ключ - username, значение - объект класса ClientEntity
            clientTable.put(username, new ClientEntity(username, isAdmin));
        }
    }

    //реализация метода, возвращающего переменную, хранящую объект класса клиента, по имени пользователя
    //возвращаем клиента по его имени из таблицы
    @Override
    public Optional<ClientEntity> get(String username) {
        return Optional.of(clientTable.get(username));
    }

    //реализация метода, возвращающего объект, в котором мы храним всех наших клиентов
    @Override
    public Hashtable<String, ClientEntity> getAll() {
        return clientTable;
    }

    //реализация метода, сохраняющего таблицу клиентов в файл, может выбросить исключение ввода,вывода
    @Override
    public void save() throws IOException {
        //создаем объект для работы с ресурсами класса ClientRepository
        ClassLoader classLoader = ClientRepository.class.getClassLoader();
        //создаем объект для работы с файлом, который хранит информацию о клиентах
        File file = new File(classLoader.getResource("Clients.json").getFile());
        //записываем путь к файлу
        String fileName = file.getAbsolutePath();
        //записываем в строку все данные объекта ClientRepository, таблицу клиентов
        String str = this.toString();
        //создаем объект для вывода данных в файл
        FileOutputStream outputStream = new FileOutputStream(fileName);
        //записываем нашу строку в массив байт
        byte[] strToBytes = str.getBytes();
        //сохраняем данные в файл
        outputStream.write(strToBytes);
        //закрываем файл
        outputStream.close();
    }

    //реализация метода, добавляющего нового клиента в объект, хранящий всех наших клиентов, может выбросить исключение
    @Override
    public void add(ClientEntity clientEntity) throws IOException {
        //добавляем в таблицу нового клиента
        clientTable.put(clientEntity.getUsername(), clientEntity);
        this.save();
    }

    //реализация метода, удаляющего клиента по переданному параметру(имя пользователя), может выбросить исключение
    @Override
    public void delete(String username) throws IOException {
        //удаляем из таблицы клиента по его имени
        clientTable.remove(username);
        this.save();
    }

    //реализация метода, заменяющего объект на новый переданный объект по параметру(имя пользователя), может выбросить исключение
    @Override
    public void update(ClientEntity clientEntity, String username) throws IOException {
        //удаляем из таблицы клиента по имени
        clientTable.remove(username);
        //добавляем в таблицу нового клиента
        clientTable.put(username, clientEntity);
        this.save();
    }

    //переопределение метода, возвращающего строку со всеми клиентами
    @Override
    public String toString() {
        //создание переменной
        final StringBuilder sb = new StringBuilder("{\n");
        sb.append("\"clients\" : [\n");
        //проходимся циклом по всем ключам клиентов и записываем в переменную информацию о них
        for (String key: clientTable.keySet()){
            sb.append("{\n");
            sb.append(clientTable.get(key).toString());
            sb.append("},\n");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("]\n}");
        //преобразуем переменную в строку и возвращаем
        return sb.toString();
    }


}
