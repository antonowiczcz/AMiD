import java.util.Scanner;
public class Main {

    public static void Termostat(String[] args) {

        float aktualnaTemperatura = 20;
        float ustawionaTemperatura = 22;


        System.out.println("Wybierz akcję termostatu:" +
                "(0) Sprawdz Aktualna Temperature" +
                "(1) Wlacz Ogrzewanie" +
                "(2) Wylacz Ogrzewanie" +
                "(3) Wlacz Chlodzenie" +
                "(4) Wylacz Chlodzenie");

        Scanner scan = new Scanner(System.in);
        Float opcja = Float.valueOf(scan.nextLine());
        void Switch(opcja) {
        case 0:
        sprawdzTemperature();
        break;
        case 1:


    }
        public static void sprawdzTemperature(float aktualnaTemperatura) {
            System.out.println("Aktualna temperatura to: " + aktualnaTemperatura);
        }

    }

}