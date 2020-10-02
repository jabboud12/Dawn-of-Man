package ch.epfl.cs107.play.signal.logic;

public class MultipleAnd extends LogicSignal{
    private Logic[] signals;

    /**
     * Constructor for the class MultipleAnd
     * @param signals (Logic) : List of the various signals
     */
    public MultipleAnd(Logic... signals){
        this.signals=signals;
    }

    @Override
    public boolean isOn() {
        for (Logic s : signals) {
            if (s == null || !s.isOn()) {
                return false;
            }
        }
        return true;
    }
}
