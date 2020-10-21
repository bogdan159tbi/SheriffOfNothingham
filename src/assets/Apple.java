package assets;
/**
 * Singleton ce reprezinta cartea Apple.
 * Fiecare carte de tip Apple va fi de fapt o referinta la obiectul APPLE din aceasta clasa.
 */
public final class Apple extends Card {
    private static final Apple APPLE = new Apple();

    private static final int APPLE_ID = 0;
    private  static final  boolean APPLE_LEGAL = true;
    private static final int APPLE_PROFIT = 2;
    private static final int APPLE_PENALTY = 2;
    private static final int APPLE_KING = 20;
    private static final int APPLE_QUEEN = 10;
    private static final int APPLE_NUM_BONUS = 0;

    private Apple(){
        super(APPLE_ID,APPLE_LEGAL,APPLE_PROFIT,APPLE_PENALTY,APPLE_KING,APPLE_QUEEN,null,APPLE_NUM_BONUS);
    }
    /**
     * Metoda -> allows access to unique object of type = apple which is APPLE
     * @return reference to APPLE
     */
    public static Apple getInstance(){
        return APPLE;
    }
}
