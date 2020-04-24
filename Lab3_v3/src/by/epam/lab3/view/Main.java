package by.epam.lab3.view;

import by.epam.lab3.dao.DAO;
import by.epam.lab3.entity.Aircraft;
import by.epam.lab3.entity.Owner;
import by.epam.lab3.service.Filter;

import java.util.ArrayList;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        //Создаем массива для хранения данных
        ArrayList<Owner> owners = new ArrayList<>();
        ArrayList<Aircraft> aircrafts = new ArrayList<>();
        //Заполняем массивы
        DAO.getData(owners, aircrafts);
        //Выводим данные
        System.out.println("Owners:");
        owners.forEach(System.out::println);
        System.out.println();
        System.out.println("Aircrafts:");
        aircrafts.forEach(System.out::println);
        System.out.println();
        //Вызываем функции для проверки работы программы
        try {
            if (Filter.isAircraftsWithCapacity(Optional.of(aircrafts), 200)) {
                System.out.println("Есть самолеты с вместимостью более 200");
            } else {
                System.out.println("Нет самолетов с вместимостью более 200");
            }
            System.out.println();
            Optional<Aircraft> aircraft = Filter.getAircraftMinCapacity(Optional.of(aircrafts));
            System.out.println("Самолет с минимальной вместимостью:");
            if (aircraft.isPresent()) {
                System.out.println(aircraft.get());
            } else {
                System.out.println("Самолетов нет");
            }
            System.out.println();
            aircraft = Filter.getAircraftMaxCapacity(Optional.of(aircrafts));
            System.out.println("Самолет с максимальной вместимостью:");
            if (aircraft.isPresent()) {
                System.out.println(aircraft.get());
            } else {
                System.out.println("Самолетов нет");
            }
            System.out.println();
            System.out.println("Самолеты, которые есть у единственной авиакомпании:");
            long start = System.currentTimeMillis();
            ArrayList<Aircraft> aircrafts1 = Filter.getAircraftsOneOwner(Optional.of(aircrafts));
            long finish = System.currentTimeMillis();
            long time1 = finish - start;
            aircrafts1.forEach(System.out::println);
            System.out.println();
            start = System.currentTimeMillis();
            aircrafts1 = Filter.getAircraftsOneOwnerParallel(Optional.of(aircrafts));
            finish = System.currentTimeMillis();
            long time2 = finish - start;
            System.out.println("Время выполнения без использования parallelStream: " + time1);
            System.out.println("Время выполнения с использованием parallelStream: " + time2);
            System.out.println();
            System.out.println("Сортировка по цене:");
            aircrafts1 = Filter.getSortedByPrice(Optional.of(aircrafts));
            aircrafts1.forEach(System.out::println);
            System.out.println();
            System.out.println("Сортировка по вместимости:");
            aircrafts1 = Filter.getSortedByCapacity(Optional.of(aircrafts));
            aircrafts1.forEach(System.out::println);
            System.out.println();
            System.out.println("Список авиакомпаний:");
            ArrayList<Owner> owners1 = Filter.getOwners(Optional.of(aircrafts));
            owners1.forEach(System.out::println);
            System.out.println();
            System.out.println("Список авиакомпаний без дубликатов:");
            owners1 = Filter.getOwnersDistinct(Optional.of(aircrafts));
            owners1.forEach(System.out::println);
            System.out.println();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
