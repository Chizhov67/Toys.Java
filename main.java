import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final static String FILE_NAME = "C://wining_toys.txt";

    public static void main(String[] args) throws IOException {
        // Объявляем список игрушек, которые участвуют в розыгрыше
        List<Toy> toys = new ArrayList<>();
        toys.add(new Toy(1, "Машинка", 5, 20));
        toys.add(new Toy(2, "Плюшевый медведь", 3, 10));
        toys.add(new Toy(3, "Мяч", 2, 10));
        toys.add(new Toy(4, "Настольная игра", 5, 20));
        toys.add(new Toy(5, "Смартфон", 1, 5));
        // Создаём рулетку
        Roulette roulette = new Roulette(toys);
        // Создаём файл для выигрыша
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        }
        // Создаём сканер
        Scanner in = new Scanner(System.in);
        int select;
        // Запускаем игру
        do {
            System.out.println("Выберите действие: ");
            System.out.println("1) Крутить рулетку");
            System.out.println("2) Забрать приз");
            System.out.println("3) Выйти");
            System.out.print(">>> ");
            select = in.nextInt();
            switch (select) {
                case 1:
                    Toy toy = roulette.spinRoulette();
                    System.out.println("Попытка " + roulette.getAttempt());
                    if (toy == null) {
                        System.out.println("Вы ничего не выиграли");
                    } else {
                        System.out.println("Выиграна игрушка " + toy);
                    }
                    break;
                case 2:
                    Toy winToy = roulette.getPrice();
                    if (winToy == null) {
                        System.out.println("У вас нет выигранных игрушек");
                    } else {
                        // Сохранение выигрыша в файл
                        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                            fileWriter.write(winToy.toString());
                            fileWriter.newLine();
                        }
                        System.out.println("Вы забрали игрушку " + winToy);
                    }
                    break;
                default:
            }
        } while (select != 3);
    }
}