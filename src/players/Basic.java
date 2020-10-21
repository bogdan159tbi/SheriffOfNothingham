package players;

import assets.Apple;
import assets.Card;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class Basic extends Player {
    /**
     * Metoda ilustreaza modul in care Basic se comporta ca serif: verifica tot.
     *
     * @param players  un ArrayList ce contine toti jucatorii
     */
    public void playSheriff(final ArrayList<Player> players){
        for (Player currPlayer : players){
            if(!currPlayer.equals(this)){
                checkPlayer(currPlayer);
                currPlayer.pushToStand();
            }
        }
    }
    public void playMerchant(){
        playLegal();

        if(super.getBag().isEmpty()){
            playIllegal();
            setDeclaredCard(Apple.getInstance());
        }
    }
    final void playLegal(){
        int[] freq = new int[super.getMaxCardTypes()];
        int maxFreq = 0,maxProfit = 0;
        LinkedList<Card> cardsInHand = super.getCardsInHand();

        for(Card currCard: cardsInHand){
            int currId = currCard.getId();

            if(currCard.isLegal()){
                ++freq[currId];
            }
        }
        for (Card currCard: cardsInHand){
            int currId = currCard.getId();
            int currProfit = currCard.getProfit();

            if(currCard.isLegal() &&
                    (maxFreq == freq[currId] && maxProfit < currProfit) ||
            maxFreq < freq[currId]){
                maxFreq = freq[currId];
                setDeclaredCard(currCard);
                maxProfit = currProfit;
            }
        }
        Card declaredCard = super.getDeclaredCard();

        for(int i = 0; i < maxFreq; ++i){
            addItemToBag(declaredCard);
            discardItem(declaredCard);
        }
    }

    final void playIllegal(){
        Card bestCard = null;

        for(Card currCard : super.getCardsInHand()){
            if(!currCard.isLegal()&&
                    (bestCard == null || bestCard.getProfit() < currCard.getProfit())){
                bestCard = currCard;
            }
        }
        if( bestCard != null){
            addItemToBag(bestCard);
            discardItem(bestCard);
        }
    }

}
