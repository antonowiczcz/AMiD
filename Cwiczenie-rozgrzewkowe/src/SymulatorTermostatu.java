import java.util.Scanner;

public class SymulatorTermostatu {
    public static void main(String[] args) {
        Termostat termostat = new Termostat();
        Scanner scanner = new Scanner(System.in);

        Thread temperaturaThread = new Thread(termostat::symulujZmianeTemperatury);
        temperaturaThread.start();

        while (true) {
            System.out.println("Aktualna temperatura: " + termostat.getAktualnaTemperatura() + " stopni Celsjusza");
            System.out.println("Ustawiona temperatura: " + termostat.getUstawionaTemperatura() + " stopni Celsjusza");
            System.out.println("1. Włącz ogrzewanie");
            System.out.println("2. Włącz chłodzenie");
            System.out.println("3. Zatrzymaj regulację temperatury");
            System.out.println("4. Zakończ program");
            System.out.print("Wybierz opcję: ");

            int wybor = scanner.nextInt();

            switch (wybor) {
                case 1 -> {
                    System.out.print("Podaj docelową temperaturę dla ogrzewania: ");
                    double temperaturaOgrzewanie = scanner.nextDouble();
                    termostat.wlaczOgrzewanie(temperaturaOgrzewanie);
                }
                case 2 -> {
                    System.out.print("Podaj docelową temperaturę dla chłodzenia: ");
                    double temperaturaChlodzenie = scanner.nextDouble();
                    termostat.wlaczChlodzenie(temperaturaChlodzenie);
                }
                case 3 -> termostat.zatrzymajRegulacje();
                case 4 -> {
                    System.out.println("Zakończono program.");
                    temperaturaThread.interrupt();
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Nieprawidłowy wybór.");
            }
        }
    }
}
