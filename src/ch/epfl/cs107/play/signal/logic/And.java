package ch.epfl.cs107.play.signal.logic;

public class And extends LogicSignal {
    private Logic s1, s2;
    
    /**
     * Constructor for the class And
     * @param s1 (Logic) : First signal
     * @param s2 (Logic) : Second signal
     */
    public And(Logic s1, Logic s2){

        this.s1=s1;
        this.s2=s2;
    }


    @Override
    public boolean isOn() {
        return s1 != null && s2 != null && s1.isOn() && s2.isOn();
    }
}
