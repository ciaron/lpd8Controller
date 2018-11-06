package lpd8Controller;

/**
 * The 8 pads of the controller.
 */
public enum PADS {
    /**
     * The first pad from left to right, bottom row
     */
    PAD_1(0),
    /**
     * The second pad from left to right, bottom row
     */
    PAD_2(1),
    /**
     * The third pad from left to right, bottom row
     */
    PAD_3(2),
    /**
     * The fourth pad from left to right, bottom row
     */
    PAD_4(3),
    /**
     * The first pad from left to right, top row
     */
    PAD_5(4),
    /**
     * The second pad from left to right, top row
     */
    PAD_6(5),
    /**
     * The third pad from left to right, top row
     */
    PAD_7(6),
    /**
     * The fourth pad from left to right, top row
     */
    PAD_8(7);

    private final byte code;
    protected byte code() { return code; }

    PADS(int code)
    {
        this.code = (byte)code;
    }
}

