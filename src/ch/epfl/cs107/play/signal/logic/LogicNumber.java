package ch.epfl.cs107.play.signal.logic;

public class LogicNumber extends LogicSignal {
    private float nb;
    private Logic[] e;

    /**
     * Constructor for the class LogicNumber
     * @param nb (float) : Number required for isOn() to return Logic.TRUE
     * @param e (Logic) : List of signals
     */
    public LogicNumber(float nb, Logic... e) {
        this.nb = nb;
        this.e = e;
    }


    @Override
    public boolean isOn() {
    	
        float nbSignal = 0;

        if ((nb < 0) || (e.length > 12) || nb > Math.pow(2, e.length)) {
            return false;
        }
        
        for (int i = 0; i < e.length; ++i) {
            nbSignal += Math.pow(2, i) * (e[i].getIntensity());
        }

        return nbSignal == nb;

    }
}
