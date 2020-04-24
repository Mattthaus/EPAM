package by.epam.lab3.dao;

import by.epam.lab3.entity.Aircraft;
import by.epam.lab3.entity.Owner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

public class DAO {
    //Создаем объект для вывода логов
    private static final Logger log = LogManager.getLogger();
    //Путь к папке для хранения файлов
    private static final String PATH_DIR = "data";
    //Путь к фалу с данными о самолетах
    private static final String PATH_AIRCRAFTS = PATH_DIR + File.separator + "aircrafts.txt";
    //Путь к файлу с данными о авиакомпаниях
    private static final String PATH_OWNERS = PATH_DIR + File.separator + "owners.txt";

    //Функция возвращающая массив данных о самолетах из файла
    private static ArrayList<Aircraft> getAircrafts() {
        //Создаем пустой массив
        ArrayList<Aircraft> aircrafts = new ArrayList<>();
        //Открываем файл для чтения
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_AIRCRAFTS))) {
            //Количество самолетов
            int n = Integer.parseInt(bufferedReader.readLine());
            String str;
            for (int i = 0; i < n; ++i) {
                //Создаем переменнуя для хранения данных самолета
                Aircraft aircraft = new Aircraft();
                //Читаем и заполняем название
                str = bufferedReader.readLine();
                aircraft.setName(str);
                //Читаем и заполняем цену
                str = bufferedReader.readLine();
                aircraft.setPrice(Integer.parseInt(str));
                //Читаем и заполняем вместимость
                str = bufferedReader.readLine();
                aircraft.setCapacity(Integer.parseInt(str));
                //Добавляем самолет в массив
                aircrafts.add(aircraft);
            }
        } catch (IOException e) {
            log.error(e);
        }
        return aircrafts;
    }

    //Функция возвращающая массив данных о авиакомпаниях из файла
    private static ArrayList<Owner> getOwners(ArrayList<Aircraft> aircrafts) {
        //Создаем пустой массив
        ArrayList<Owner> owners = new ArrayList<>();
        //Открываем файл для чтения
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_OWNERS))) {
            //Количество авиакомпаний
            int n = Integer.parseInt(bufferedReader.readLine());
            String str;
            for (int i = 0; i < n; ++i) {
                //Создаем переменнуя для хранения данных авиакомпаний
                Owner owner = new Owner();
                //Читаем и заполняем название
                str = bufferedReader.readLine();
                owner.setName(str);
                //Читаем и заполняем местоположение
                str = bufferedReader.readLine();
                owner.setLocation(str);
                //Количество самолетов авиакомпании
                int m = Integer.parseInt(bufferedReader.readLine());
                for (int j = 0; j < m; ++j) {
                    //Читаем название самолета
                    str = bufferedReader.readLine();
                    //В массиве самолетов ищем самолет с таким же нахванием
                    for (Aircraft aircraft : aircrafts) {
                        //Если нашли
                        if (aircraft.getName().equals(str)) {
                            //Добавляем самолет к авиакомпании
                            owner.addAircraft(aircraft);
                            //Добавляем авиакоманию к самолету
                            aircraft.addOwner(owner);
                        }
                    }
                }
                //Добавляем авиакоманию в массив
                owners.add(owner);
            }
        } catch (IOException e) {
            log.error(e);
        }
        return owners;
    }

    //Метод для получения данных из файла
    public static void getData(ArrayList<Owner> owners, ArrayList<Aircraft> aircrafts) {
        //Получаем данные самолетов
        aircrafts.addAll(getAircrafts());
        //Получаем данные авиакомпаний
        owners.addAll(getOwners(aircrafts));
    }
}
