package ru.egorova.api.homework2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Program{
    
    static Scanner scanner = new Scanner(System.in);
    static File logFile = null;
    static FileWriter fileWriter = null;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        logFile = new File("log.txt");
        fileWriter = new FileWriter(logFile, true);

        boolean f = true;
        while(f){
            System.out.println("Введите номер задачи: ");
            System.out.println("1 - Задача № 1;");
            System.out.println("2 - Задача № 2;");
            System.out.println("3 - Задача № 3;");
            System.out.println("4 - Задача № 4;");
            System.out.println("0 - Выход из программы.");
            System.out.println("**************************************");

            int no = Integer.parseInt(scanner.nextLine());
            
            switch(no){
                case 1:
                    String [] list = readDataFromFile("Input.txt"); 
                    System.out.println(list[0]); 
                    System.out.println(task1(list[0]));
                    System.out.println();
                    break;
               case 2:
                    int[] array = {3, 2, 5, 1};
                    System.out.print("Исходный массив - ");
                    System.out.println(Arrays.toString(array));                             
                    task2(array);
                    System.out.print("Отсортированный массив - ");
                    System.out.println(Arrays.toString(array));  
                    System.out.println("Результат каждой итерации находится в файле log_task1.txt"); 
                    System.out.println();
                    break;
                case 3:
                    task3();
                    System.out.println();
                    break;
                case 4:
                
                    System.out.print("Введите первое число - ");
                    Double x = Double.parseDouble(scanner.nextLine());
                    System.out.print("Введите второе число - ");
                    Double y = Double.parseDouble(scanner.nextLine());
                    System.out.print("Введите необходимую операцию (+, -, *, /) = ");
                    String operation = scanner.nextLine();
                    System.out.printf("%.0f %s %.0f = %.2f\n",x,operation, y, task4(x, y, operation));
                    System.out.println("Логирование находится в файле - log.txt");
                    System.out.println();
                    break;
                case 0:
                    System.out.println("Завершение работы приложения");
                    f = false;
                    break;
                default:
                    System.out.println("Вы указали некорректный номер задачи.\nПовторите попытку ввода. ");
                    break;
            }
        }
    }


/*
 * 1) Дана строка sql-запроса "select * from students where ". Сформируйте часть WHERE этого запроса, используя StringBuilder. 
 * Данные для фильтрации приведены ниже в виде json-строки.
Если значение null, то параметр не должен попадать в запрос.

Параметры для фильтрации: {"name":"Ivanov", "country":"Russia", "city":"Moscow", "age":"null"}

*/

    static String task1(String str){
        String str1 = str.replace("{", "");
        String str2 = str1.replace("}", "");
        String str3 = str2.replaceAll("\"", "");

        StringBuilder stringBuilder = new StringBuilder("select * from students where ");

        String [] arrayData = str3.split(", ");
        for (int i =0; i < arrayData.length; i++) {
            String[] arrData = arrayData[i].split(":");

            if(arrData[1].equals("null") == false){
                if (i != 0) {
                    stringBuilder
                    .append(", ")
                    .append(arrData[0])
                    .append(" = ")
                    .append(arrData[1]);
                } 
                else {
                    stringBuilder
                    .append(arrData[0])
                    .append(" = ")
                    .append(arrData[1]);
                }
            }
        }                
        return stringBuilder.toString();
    }

public static String[] readDataFromFile(String path) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(path));
        String str;
        int size = 0;
        while ((str = br.readLine()) != null){
            size += 1;
        }
        br.close();
        String [] Data = new String [size];

        int i = 0;
        BufferedReader br1 = new BufferedReader(new FileReader(path));
        while ((str = br1.readLine()) != null) {
            Data[i] = str;
            i += 1;
        }
        br1.close();
        return Data;        
}


// 2) Реализуйте алгоритм сортировки пузырьком числового массива, результат после каждой итерации запишите в лог-файл.
    static void task2(int[] array){

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length - 1; i++){
            for (int j = 0; j< array.length -1; j++){
                if (array[j]> array[j+1]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
                sb.append(Arrays.toString(array)).append("\n");
                try {
                    FileWriter fw = new FileWriter("log_task1.txt", false);
                    fw.write(sb.toString());
                    fw.close();
                } 
                catch (Exception e){
                    System.out.println("ERROR!");
                }
            }
        }
    }

/*

3) Дана json-строка (можно сохранить в файл и читать из файла)
[{"фамилия":"Иванов","оценка":"5","предмет":"Математика"},{"фамилия":"Петрова","оценка":"4","предмет":"Информатика"},
{"фамилия":"Краснов","оценка":"5","предмет":"Физика"}]
Написать метод(ы), который распарсит json и, используя StringBuilder, создаст строки вида: Студент [фамилия] получил [оценка] 
по предмету [предмет].
Пример вывода:
Студент Иванов получил 5 по предмету Математика.
Студент Петрова получил 4 по предмету Информатика.
Студент Краснов получил 5 по предмету Физика.
*/
   
    static void task3(){
        String dataFile = "file.txt";
        String[] infoBase;
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(dataFile));
            String line; {
                while ((line = br.readLine()) != null) {
                    infoBase = line.replace("\"", "").split(":|,");
                    sb.append("Студент ").append(infoBase[1]).append(" получил ").append(infoBase[3])
                            .append(" по предмету ").append(infoBase[5]).append(".\n");
                }
                System.out.println(sb);
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
4) К калькулятору из предыдущего ДЗ добавить логирование.
 */
 static double task4(Double x, Double y, String operation) throws Exception{
        double result = 0;
        switch (operation) {
            case "+":
                result = x + y;
                fileWriter.append(x + " +  " + y + " = " + (x + y) + '\n' );
                fileWriter.flush();
                break;
            case "-":
                result = x - y;
                fileWriter.append(x + " -  " + y + " = " + (x - y) + '\n' );
                fileWriter.flush();
                break;
            case "/":
                result = x / y;
                fileWriter.append(x + " /  " + y + " = " + (x / y) + '\n' );
                fileWriter.flush();
                break;
            case "*":
                result = x * y;
                fileWriter.append(x + " *  " + y + " = " + (x * y) + '\n' );
                fileWriter.flush();
                break;
            default:
                System.out.println("Введена неправильная операция!");
                break;
            }
        fileWriter.close();
        return result;     
    }


}