package players;

import java.io.CharArrayReader;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import assets.Card;
import tools.Deck;


public abstract class Player {
    private static final int MAX_BAG = 5;
    private static final int MAX_CARDS = 6;
    private static final int MAX_CARD_TYPES = 13;
    private static final int START_COINS = 50;

    private HashMap<Card, Integer> merchantStand;
    private LinkedList<Card> cardsInHand;
    private ArrayList<Card> bag;
    private int totalCoins, bribe;
    private Card declaredCard;
    /*
     * Se initializeaza campurile pe care le foloseste
     * un jucator si se trag primele carti din mana.     */
    Player(){
        totalCoins = START_COINS;
        bribe = 0; // mita
        declaredCard = null;

        merchantStand = new HashMap<Card, Integer>();
        cardsInHand = new LinkedList<Card>();
        bag = new ArrayList<Card>();

        drawCards();
    }
    final boolean hasBribe(){
        return bribe > 0;
    }
    final int getBribe(){
        totalCoins -= bribe;
        return bribe;
    }
    final void setBribe(final int bribeValue){
        bribe = bribeValue;
    }
    final LinkedList<Card> getCardsInHand(){
        return cardsInHand;
    }
    final void discardItem(final Card card){
        cardsInHand.remove(card);
    }
    final ArrayList<Card> getBag(){
        return bag;
    }
    final void addItemToBag(final Card card){
        bag.add(card);
    }

    final Card getDeclaredCard(){
        return declaredCard;
    }
    final int getMaxBag(){
        return  MAX_BAG;
    }
    final int getMaxCardTypes() {
        return MAX_CARD_TYPES;
    }
    final void checkPlayer(final Player player){
        int exchangedSum = player.calculatePenalty();

        totalCoins += exchangedSum;
        player.exchangeCoins(exchangedSum);
        player.exchangeCoins(exchangedSum);
    }
    private int calculatePenalty(){
        int exchangedSum = 0;
        boolean liar = false;
        ArrayList<Card> removedCards = new ArrayList<>();
        // se pun inapoi in pachet cartile ilegale si se va adauga penalizarea corespunzatoare lor
        // la exchangedSum

        for (Card currCard : bag){
            if(!currCard.isLegal() || currCard != declaredCard){
                liar = true;
                exchangedSum += currCard.getPenalty();
                removedCards.add(currCard);
                Deck.getInstance().addCard(currCard);
            }
        }
        // daca nu au fost gasite carti ilegale, la exchangedSum se va adauga penalizarea
        // corespunzatoare cartilor legale
        if(!liar){
            for(Card currCard: bag){
                exchangedSum -= currCard.getPenalty();
            }
        }
        bag.removeAll(removedCards);
        return exchangedSum;
    }
    private void exchangeCoins(final int numCoins){
        totalCoins -= numCoins;
    }
    public final void drawCards(){
        cardsInHand.addAll(Deck.getInstance().giveCards(MAX_CARDS - cardsInHand.size()));
    }
    final void pushToStand(){
        for(Card currCard: bag){
            pushItemToStand(currCard);
            if(!currCard.isLegal()){
                Card currBonus = currCard.getBonus();

                for(int i = 0;i < currCard.getNumBonusItems(); ++i){
                    pushItemToStand(currBonus);
                }
            }
        }
        bag.clear();
    }
    private void pushItemToStand(final Card card){
        try{
            int numItemsOnStand = merchantStand.get(card);
            merchantStand.put(card,numItemsOnStand + 1);
        }catch(NullPointerException nullPointerException){
            merchantStand.put(card,1);
        }
    }
    final int getScore(){
        int score = totalCoins;
        for(Map.Entry<Card,Integer> currItem: merchantStand.entrySet()){
            score += currItem.getKey().getProfit()* currItem.getValue();
        }
        return score;
    }

    final void setDeclaredCard(final Card newDeclaredCard){
        declaredCard = newDeclaredCard;
    }
    //fara bonus
    /**
     * Fiecare jucator ce extinde aceasta clasa va implementa propria sa strategia sa de comerciant.
     */
    public abstract void playMerchant();

    /**
     * Fiecare jucator ce extinde aceasta clasa va implementa propria sa strategia sa de serif.
     * @param players  un ArrayList ce contine toti jucatorii
     */
    public abstract void playSheriff(ArrayList<Player> players);

}
