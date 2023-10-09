class Trojkat {
    private double podstawa;
    private double wysokosc;

    public Trojkat(double podstawa, double wysokosc) {
        this.podstawa = podstawa;
        this.wysokosc = wysokosc;
    }

    public double obliczPole() {
        return 0.5 * podstawa * wysokosc;
    }
}