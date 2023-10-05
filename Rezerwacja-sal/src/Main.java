import java.util.*;

public class Main {
    private static final List<Sala> listaSal = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Wybierz opcję:");
            System.out.println("1. Rezerwacja sali");
            System.out.println("2. Sprawdzenie dostępności sali");
            System.out.println("3. Dodanie sali");
            System.out.println("4. Edycja pojemności sali");
            System.out.println("5. Wyjście");

            int wybor = scanner.nextInt();
            scanner.nextLine();

            switch (wybor) {
                case 1 -> rezerwujSale();
                case 2 -> sprawdzDostepnoscSali();
                case 3 -> dodajSale();
                case 4 -> edytujPojemnoscSali();
                case 5 -> {
                    System.out.println("Dziękujemy za korzystanie z programu.");
                    System.exit(0);
                }
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    private static void rezerwujSale() {
        System.out.println("Podaj numer sali:");
        int numerSali = scanner.nextInt();
        scanner.nextLine();

        Sala sala = znajdzSale(numerSali);

        if (sala == null) {
            System.out.println("Sala o podanym numerze nie istnieje.");
            return;
        }

        System.out.println("Podaj datę i godzinę rezerwacji (w formacie 'dd-mm-yyyy hh:mm'):");
        String dataGodzina = scanner.nextLine();

        if (sala.czyDostepna(dataGodzina)) {
            if (czyMoznaZarezerwowac(sala)) {
                sala.zarezerwuj(dataGodzina);
                System.out.println("Sala została zarezerwowana na " + dataGodzina);
            } else {
                System.out.println("Sala ma za małą pojemność na tę rezerwację.");
            }
        } else {
            System.out.println("Sala jest już zajęta w podanym terminie.");
        }
    }

    static class Sala {
        private final int numerSali;
        private final int pojemnosc;
        private final Map<String, Boolean> terminyRezerwacji = new HashMap<>();

        public Sala(int numerSali, int pojemnosc) {
            this.numerSali = numerSali;
            this.pojemnosc = pojemnosc;
        }

        public int getNumerSali() {
            return numerSali;
        }

        public int getPojemnosc() {
            return pojemnosc;
        }

        public boolean czyDostepna(String dataGodzina) {
            return !terminyRezerwacji.containsKey(dataGodzina) || !terminyRezerwacji.get(dataGodzina);
        }

        public void zarezerwuj(String dataGodzina) {
            terminyRezerwacji.put(dataGodzina, true);
        }
    }
    private static void sprawdzDostepnoscSali() {
        System.out.println("Podaj numer sali:");
        int numerSali = scanner.nextInt();
        scanner.nextLine();

        Sala sala = znajdzSale(numerSali);

        if (sala == null) {
            System.out.println("Sala o podanym numerze nie istnieje.");
            return;
        }

        System.out.println("Podaj datę i godzinę do sprawdzenia (w formacie 'dd-mm-yyyy hh:mm'):");
        String dataGodzina = scanner.nextLine();

        if (sala.czyDostepna(dataGodzina)) {
            System.out.println("Sala jest dostępna w podanym terminie.");
        } else {
            System.out.println("Sala jest zajęta w podanym terminie.");
        }
    }

    private static void dodajSale() {
        System.out.println("Podaj numer sali:");
        int numerSali = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Podaj pojemność sali:");
        int pojemnosc = scanner.nextInt();
        scanner.nextLine();

        listaSal.add(new Sala(numerSali, pojemnosc));
        System.out.println("Sala została dodana.");
    }

    private static void edytujPojemnoscSali() {
        System.out.println("Podaj numer sali:");
        int numerSali = scanner.nextInt();
        scanner.nextLine();

        Sala sala = znajdzSale(numerSali);

        if (sala == null) {
            System.out.println("Sala o podanym numerze nie istnieje.");
            return;
        }

        System.out.println("Podaj nową pojemność sali:");
        int nowaPojemnosc = scanner.nextInt();
        scanner.nextLine();

        new Sala(numerSali, nowaPojemnosc);
        System.out.println("Pojemność sali została zaktualizowana.");
    }

    private static Sala znajdzSale(int numerSali) {
        return listaSal.stream().filter(sala -> sala.getNumerSali() == numerSali).findFirst().orElse(null);
    }

    private static boolean czyMoznaZarezerwowac(Sala sala) {
        System.out.println("Podaj ilość uczestników rezerwacji:");
        int liczbaUczestnikow = scanner.nextInt();
        scanner.nextLine();
        return sala.getPojemnosc() >= liczbaUczestnikow;
    }
}
