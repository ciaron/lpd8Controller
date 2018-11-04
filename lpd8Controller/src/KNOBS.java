package lpd8Controller;

/**
 * The 16 knobs of the controller. Knobs from the upper row are named KNOB_x_HIGH, where x goes
 * from 1 to 8, as labelled on the controller.
 */
public enum KNOBS {
    KNOB_1(0),
    KNOB_2(1),
    KNOB_3(2),
    KNOB_4(3),
    KNOB_5(4),
    KNOB_6(5),
    KNOB_7(6),
    KNOB_8(7);

    private final byte code;
    public byte code() { return code; }
    KNOBS(int code)
    {
        this.code = (byte)code;
    }
}
