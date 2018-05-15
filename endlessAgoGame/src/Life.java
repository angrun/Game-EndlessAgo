public class Life {


    private int livesAmount;
    private int price = 1;

    Life(int livesAmount) {
        this.livesAmount = livesAmount;
    }

    public void addLife() {
        setLivesAmount(getLivesAmount() + 1);
    }

    public boolean canRemoveLife() {
        return getLivesAmount() > 1;
    }

    private void setPrice(int price) {
        this.price = price;
    }

    public void setLivesAmount(int livesAmount) {
        this.livesAmount = livesAmount;
    }

    public int getLivesAmount() {
        return livesAmount;
    }

    public int getPriceAndIncrease() {
        setPrice(price * 2);
        return price / 2;
    }

    public int getPrice() {
        return price;
    }

}
