package assets;

public final class Bread extends Card {
    private static final Bread BREAD = new Bread();

    private static final int BREAD_ID = 2;
    private static final boolean BREAD_LEGAL = true;
    private static final int BREAD_PROFIT = 4;
    private static final int BREAD_PENALTY = 2;
    private static final int BREAD_KING = 15;
    private static final int BREAD_QUEEN = 10;
    private static final int BREAD_NUM_BONUS = 0;

    private Bread() {
        super(BREAD_ID,
                BREAD_LEGAL,
                BREAD_PROFIT,
                BREAD_PENALTY,
                BREAD_KING,
                BREAD_QUEEN,
                null,
                BREAD_NUM_BONUS);
    }

    public static Bread getInstance() {
        return BREAD;
    }
}
