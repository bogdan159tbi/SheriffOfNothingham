package tools;

import assets.Card;
import assets.CardFactory;

import java.util.*;

/**
 *  * Singleton-ul Deck contine pachetul de carti sub forma unei cozi.
 *   * Deck va da carti fiecarui jucator
 *      si se va completa cu cartile scoase din sacii acestora.
 */
public final class Deck {
    private Queue<Card> deckContents;
    private static final Deck DECK = new Deck();
    /**
     *       Constructorul clasei, ce se va apela o singura data,
     *      creand obiectul DECK.
     */
    private Deck(){
        deckContents = new LinkedList<>();
    }
    /**
     * Metoda introduce in coada de carti
     * referinte la obiectele corespunzatoare ID-urilor
     * primite ca parametri si creeaza tabelul de carti cu bonus.
     *
     * @param cardIds   lista cu ID-urile cartilor de joc din pachet
     * @param cardTable HashSet-ul ce contine cartile ce pot primi bonus
     */
    void makeDeck(final List<Integer> cardIds, final HashSet<Card> cardTable){
        for(Integer card: cardIds){
            deckContents.add(CardFactory.getCard(card,cardTable));
        }
    }
    public LinkedList<Card> giveCards(final int numCardToDraw){
        LinkedList<Card> drawnCards = new LinkedList<>();

        for(int i = 0;i < numCardToDraw; ++i){
            drawnCards.add(deckContents.remove());
        }
        return  drawnCards;
    }
    public void addCard(final Card card){
        deckContents.add(card);
    }
    public static Deck getInstance(){
        return DECK;
    }
}
