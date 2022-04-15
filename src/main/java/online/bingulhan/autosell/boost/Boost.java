package online.bingulhan.autosell.boost;

public class Boost {

    public int value;
    public boolean isOfflineActive;
    public int second;


    public Boost(int value, boolean isOfflineActive, int second) {

        this.value=value;
        this.isOfflineActive=isOfflineActive;
        this.second=second;


    }


    public boolean control() {

        if (second<1) {

            return false;

        }

        return true;
    }


}
