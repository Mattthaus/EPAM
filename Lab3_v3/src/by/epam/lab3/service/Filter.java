package by.epam.lab3.service;

import by.epam.lab3.entity.Aircraft;
import by.epam.lab3.entity.Owner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class Filter {
    //Создаем объект для вывода логов
    private static final Logger log = LogManager.getLogger();

    //Функция для определения есть ли самолет с вместимостью больше переданной
    public static boolean isAircraftsWithCapacity(Optional<ArrayList<Aircraft>> aircrafts, int capacity) throws NullPointerException {
        //Проверяем что передали не null
        aircrafts.orElseThrow(NullPointerException::new);
        //Открываем стрим из самолетов
        return aircrafts.get().stream()
                //Выводим данные о самолетах в логи
                .peek(log::info)
                //Ищем что есть хотя бы один самолет с нужными нам параметрами
                .anyMatch(item -> item.getCapacity() > capacity);
    }

    //Функция для получения самолета с максимальной вместимостью
    public static Optional<Aircraft> getAircraftMaxCapacity(Optional<ArrayList<Aircraft>> aircrafts) throws NullPointerException {
        //Проверяем что передали не null
        aircrafts.orElseThrow(NullPointerException::new);
        //Открываем стрим из самолетов
        return aircrafts.get().stream()
                //Ищем самолет с максимальной вместимостью
                .max(Comparator.comparing(Aircraft::getCapacity));
    }

    //Функция для получения самолета с минимальной вместимостью
    public static Optional<Aircraft> getAircraftMinCapacity(Optional<ArrayList<Aircraft>> aircrafts) throws NullPointerException {
        //Проверяем что передали не null
        aircrafts.orElseThrow(NullPointerException::new);
        //Открываем стрим из самолетов
        return aircrafts.get().stream()
                //Ищем самолет с минимальной вместимостью
                .min(Comparator.comparing(Aircraft::getCapacity));
    }

    //Функция для получения списка самолетов, которые есть у единственной авиакомпании
    public static ArrayList<Aircraft> getAircraftsOneOwner(Optional<ArrayList<Aircraft>> aircrafts) throws NullPointerException {
        //Проверяем что передали не null
        aircrafts.orElseThrow(NullPointerException::new);
        //Открываем стрим из самолетов
        return aircrafts.get().stream()
                //Берем только те самолеты где есть только одна авиакомпания
                .filter(item -> item.getStores().size() == 1)
                //Возвращаем в виде массива
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //Функция что и выше но с параллельным вычислением
    public static ArrayList<Aircraft> getAircraftsOneOwnerParallel(Optional<ArrayList<Aircraft>> aircrafts) throws NullPointerException {
        //Проверяем что передали не null
        aircrafts.orElseThrow(NullPointerException::new);
        return aircrafts.get().parallelStream()
                .filter(item -> item.getStores().size() == 1)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //Функция сортирующая самолеты по цене
    public static ArrayList<Aircraft> getSortedByPrice(Optional<ArrayList<Aircraft>> aircrafts) throws NullPointerException {
        //Проверяем что передали не null
        aircrafts.orElseThrow(NullPointerException::new);
        //Открываем стрим из самолетов
        return aircrafts.get().stream()
                //Сортируем самолеты по цене
                .sorted(Comparator.comparing(Aircraft::getPrice))
                //Возвращаем в виде массива
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //Функция сортирующая самолеты по вместимости
    public static ArrayList<Aircraft> getSortedByCapacity(Optional<ArrayList<Aircraft>> aircrafts) throws NullPointerException {
        //Проверяем что передали не null
        aircrafts.orElseThrow(NullPointerException::new);
        //Открываем стрим из самолетов
        return aircrafts.get().stream()
                //Сортируем самолеты по вместимости
                .sorted(Comparator.comparing(Aircraft::getCapacity))
                //Возвращаем в виде массива
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //Функция для получения списка авиакомпаний у самолетов
    public static ArrayList<Owner> getOwners(Optional<ArrayList<Aircraft>> aircrafts) throws NullPointerException {
        //Проверяем что передали не null
        aircrafts.orElseThrow(NullPointerException::new);
        //Открываем стрим из самолетов
        return aircrafts.get().stream()
                //Выводим данные о самолете в логи
                .peek(log::info)
                //Объект самолета заменяем на стрим авиакомпаний самолета
                .flatMap(item -> item.getStores().stream())
                //Выводим данные о авиакомпании в логи
                .peek(log::info)
                //Возвращаем в виде массива
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //Функция для получения списка авиакомпаний у самолетов без повторений
    public static ArrayList<Owner> getOwnersDistinct(Optional<ArrayList<Aircraft>> aircrafts) throws NullPointerException {
        //Проверяем что передали не null
        //Открываем стрим из самолетов
        aircrafts.orElseThrow(NullPointerException::new);
        return aircrafts.get().stream()
                //Объект самолета заменяем на стрим авиакомпаний самолета
                .flatMap(item -> item.getStores().stream())
                //Удаляем повторения
                .distinct()
                //Возвращаем в виде массива
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //Функция для вывода данных о авикомпаниях
    public static void printOwners(Optional<ArrayList<Aircraft>> aircrafts) throws NullPointerException {
        //Проверяем что передали не null
        aircrafts.orElseThrow(NullPointerException::new);
        //Для авиакомпаний без повторений вызываем функцию вывода
        getOwnersDistinct(aircrafts).forEach(System.out::println);
    }
}