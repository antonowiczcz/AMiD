import java.io.FileWriter;
import java.io.IOException;

class Termostat {
    private double aktualnaTemperatura = 20.0;
    private double ustawionaTemperatura = 22.0;
    private boolean ogrzewanieWlaczane = false;
    private boolean chlodzenieWlaczane = false;

    public void wlaczOgrzewanie(double docelowaTemperatura) {
        ustawionaTemperatura = docelowaTemperatura;
        ogrzewanieWlaczane = true;
        chlodzenieWlaczane = false;
        log("Ogrzewanie zostało włączone. Docelowa temperatura: " + docelowaTemperatura + " stopni Celsjusza");
    }

    public void wlaczChlodzenie(double docelowaTemperatura) {
        ustawionaTemperatura = docelowaTemperatura;
        chlodzenieWlaczane = true;
        ogrzewanieWlaczane = false;
        log("Chłodzenie zostało włączone. Docelowa temperatura: " + docelowaTemperatura + " stopni Celsjusza");
    }

    public void zatrzymajRegulacje() {
        ogrzewanieWlaczane = false;
        chlodzenieWlaczane = false;
        log("Zatrzymano regulację temperatury.");
    }

    public void symulujZmianeTemperatury() {
        while (true) {
            try {
                Thread.sleep(2000);
                symulujZmianeTemperatury(0.5);
                sprawdzTemperature();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void sprawdzTemperature() {
        if (aktualnaTemperatura < ustawionaTemperatura) {
            if (chlodzenieWlaczane) {
                chlodzenieWlaczane = false;
                log("Chłodzenie zostało wyłączone.");
            }
            if (!ogrzewanieWlaczane) {
                ogrzewanieWlaczane = true;
                log("Ogrzewanie zostało włączone.");
            }
        } else if (aktualnaTemperatura > ustawionaTemperatura) {
            if (ogrzewanieWlaczane) {
                ogrzewanieWlaczane = false;
                log("Ogrzewanie zostało wyłączone.");
            }
            if (!chlodzenieWlaczane) {
                chlodzenieWlaczane = true;
                log("Chłodzenie zostało włączone.");
            }
        } else {
            if (ogrzewanieWlaczane) {
                ogrzewanieWlaczane = false;
                log("Ogrzewanie zostało wyłączone.");
            }
            if (chlodzenieWlaczane) {
                chlodzenieWlaczane = false;
                log("Chłodzenie zostało wyłączone.");
            }
        }
    }

    public void symulujZmianeTemperatury(double zmianaTemperatury) {
        if (aktualnaTemperatura != ustawionaTemperatura) {
            if (chlodzenieWlaczane) {
                aktualnaTemperatura -= zmianaTemperatury;
            } else {
                aktualnaTemperatura += zmianaTemperatury;
            }
            if (aktualnaTemperatura > 29.0) {
                aktualnaTemperatura = 29.0;
            } else if (aktualnaTemperatura < 14.0) {
                aktualnaTemperatura = 14.0;
            }
            log("Aktualna temperatura: " + aktualnaTemperatura + " stopni Celsjusza");
        }
    }

    private void log(String komunikat) {
        System.out.println(komunikat);
        zapiszDoPliku(komunikat);
    }

    private void zapiszDoPliku(String komunikat) {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write(komunikat + "\n");
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu do pliku.");
        }
    }

    public double getAktualnaTemperatura() {
        return aktualnaTemperatura;
    }

    public double getUstawionaTemperatura() {
        return ustawionaTemperatura;
    }
}