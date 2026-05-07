class StandardRoom extends Guest {

    private final int COST_PER_DAY = 2000;

    public StandardRoom(int guestId, String guestName, int daysStayed) {
        super(guestId, guestName, daysStayed);
    }

    @Override
    public double calculateBill() {
        return COST_PER_DAY * daysStayed;
    }

    @Override
    public String getRoomType() {
        return "STANDARD";
    }
}