import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Wybierz figurę do obliczeń:");
            System.out.println("1. Kwadrat");
            System.out.println("2. Prostokąt");
            System.out.println("3. Trójkąt");
            System.out.println("4. Wyjście");

            int wybor = scanner.nextInt();

            if (wybor == 4) {
                System.out.println("Wyjście z programu.");
                break;
            }

            double pole = 0.0;

            switch (wybor) {
                case 1 -> {
                    System.out.print("Podaj długość boku kwadratu: ");
                    double bokKwadratu = scanner.nextDouble();
                    Kwadrat kwadrat = new Kwadrat(bokKwadratu);
                    pole = kwadrat.obliczPole();
                }
                case 2 -> {
                    System.out.print("Podaj długość prostokąta: ");
                    double dlugoscProstokata = scanner.nextDouble();
                    System.out.print("Podaj szerokość prostokąta: ");
                    double szerokoscProstokata = scanner.nextDouble();
                    Prostokat prostokat = new Prostokat(dlugoscProstokata, szerokoscProstokata);
                    pole = prostokat.obliczPole();
                }
                case 3 -> {
                    System.out.print("Podaj długość podstawy trójkąta: ");
                    double podstawaTrojkata = scanner.nextDouble();
                    System.out.print("Podaj wysokość trójkąta: ");
                    double wysokoscTrojkata = scanner.nextDouble();
                    Trojkat trojkat = new Trojkat(podstawaTrojkata, wysokoscTrojkata);
                    pole = trojkat.obliczPole();
                }
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }

            System.out.println("Pole powierzchni wynosi: " + pole);
        }
    }
}
