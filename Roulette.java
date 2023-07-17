import java.util.*;

public class Roulette {
    private Random random;
    private List<Toy> raffleToys;       // разыгрываемые игрушки
    private Queue<Toy> winingToys;       // выигранные игрушки
    private int attempt;                // номер попытки

    public Roulette(List<Toy> raffleToys) {
        checkWeights(raffleToys);
        this.raffleToys = raffleToys;
        this.winingToys = new LinkedList<>();
        this.attempt = 0;
        this.random = new Random(System.currentTimeMillis());
    }

    /**
     * Проверка, что сумма весов всех игрушек равно 100
     */
    private void checkWeights(List<Toy> toys) {
        int countWeight = 0;
        for (Toy toy : toys) {
            countWeight += toy.getWeight();
        }
        if (countWeight > 100) {
            throw new RuntimeException("Сумма весов в массиве не должна быть больше 100");
        }
    }

    /**
     * Возвращает номер попытки
     */
    public int getAttempt() {
        return attempt;
    }

    /**
     * Метод по кручению рулетки. Выигранная игрушка добавляется в winingToys.
     * @return выигранная игрушка или null
     */
    public Toy spinRoulette() {
        attempt++;
        int rnd = random.nextInt(100);
        int sum = 0;
        for (int i=0; i<raffleToys.size(); i++) {
            sum += raffleToys.get(i).getWeight();
            // Если выиграли игрушку, записываем её в winingToys и удаляем 1 штуку из raffleToys
            if (rnd < sum) {
                final Toy winToy = raffleToys.get(i);
                if (winingToys.stream().noneMatch(toy -> toy.getId() == winToy.getId())) {
                    // Если такой игрушки нет - добавляем её
                    winingToys.add(new Toy(winToy.getId(), winToy.getName(), 1, winToy.getWeight()));
                } else {
                    // Иначе - увеличиваем количество на 1
                    for (Toy toy : winingToys) {
                        if (toy.getId() == winToy.getId()) {
                            toy.setCount(toy.getCount() + 1);
                        }
                    }
                }
                // Уменьшаем количество игрушек в рулетке или убираем игрушку из разыгрываемых
                if (winToy.getCount() == 1) {
                    raffleToys.remove(winToy);
                } else {
                    winToy.setCount(winToy.getCount() - 1);
                }
                // Возвращаем информацию о выигранной игрушке
                return new Toy(winToy.getId(), winToy.getName(), 1, winToy.getWeight());
            }
        }
        return null;
    }

    /**
     * Метод по получению выигранного приза
     * @return первая с начала выигранная игрушка или null
     */
    public Toy getPrice() {
        return winingToys.poll();
    }
}
