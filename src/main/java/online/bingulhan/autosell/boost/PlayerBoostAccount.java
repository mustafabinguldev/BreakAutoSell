package online.bingulhan.autosell.boost;

import java.util.ArrayList;

public class PlayerBoostAccount {

    public String ownerName;

    public ArrayList<Boost> boosts;

    public PlayerBoostAccount(String ownerName) {
        boosts=new ArrayList<>();
        this.ownerName=ownerName;

    }

    public PlayerBoostAccount(ArrayList<Boost> boosts,String ownerName) {
        this.boosts=boosts;
        this.ownerName=ownerName;

    }
}
