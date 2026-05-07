abstract class Guest {
    protected int guestId;
    protected String guestName;
    protected int daysStayed;

    public Guest(int guestId, String guestName, int daysStayed) {
        this.guestId = guestId;
        this.guestName = guestName;
        this.daysStayed = daysStayed;
    }

    public int getGuestId() {
        return guestId;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getDaysStayed() {
        return daysStayed;
    }

    public abstract double calculateBill();

    public abstract String getRoomType();
}