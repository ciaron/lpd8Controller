package lpd8Controller;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.SysexMessage;


/**
 * An implementation of @see javax.sound.midi.Receiver
 */
class LPD8ControllerReceiver implements Receiver {

    private LPD8Controller parent;
    private MidiDevice device;

    LPD8ControllerReceiver(LPD8Controller parent, MidiDevice device) {
        this.parent = parent;
        this.device = device;
    }

    byte[] lastMessage;


    /**
     * Handles new messages coming from the Midi controller.
     * @param message
     * @param timeStamp
     */
    @Override
    public void send(MidiMessage message, long timeStamp) {
        lastMessage = message.getMessage();
        System.out.println(lastMessage[2]);
        //System.out.println(message.getMessage());
        if(lastMessage[0] == -80) { //KNOB
            switch(lastMessage[1]) {
                case 11:
                    parent.setKnobPosition(KNOBS.KNOB_1,lastMessage[2]);
                    break;
                case 12:
                    parent.setKnobPosition(KNOBS.KNOB_2,lastMessage[2]);
                    break;
                case 13:
                    parent.setKnobPosition(KNOBS.KNOB_3,lastMessage[2]);
                    break;
                case 14:
                    parent.setKnobPosition(KNOBS.KNOB_4,lastMessage[2]);
                    break;
                case 15:
                    parent.setKnobPosition(KNOBS.KNOB_5,lastMessage[2]);
                    break;
                case 16:
                    parent.setKnobPosition(KNOBS.KNOB_6,lastMessage[2]);
                    break;
                case 17:
                    parent.setKnobPosition(KNOBS.KNOB_7,lastMessage[2]);
                    break;
                case 18:
                    parent.setKnobPosition(KNOBS.KNOB_8,lastMessage[2]);
                    break;
            }
            return;
        }
        else if (lastMessage[0] == -104 ||lastMessage[0] == -120 ) { //PAD
            PADS padToChange = null;
            switch(lastMessage[1]){
                case 9:
                    if(lastMessage[2]==127)
                        padToChange = PADS.PAD_1;
                    break;
                case 10:
                    if(lastMessage[2]==127) {
                        padToChange = PADS.PAD_2;
                    }
                    break;
                case 11:
                    if(lastMessage[2]==127) {
                        padToChange = PADS.PAD_3;
                    }
                    break;
                case 12:
                    if(lastMessage[2]==127) {
                        padToChange = PADS.PAD_4;
                    }
                    break;
                case 25:
                    if(lastMessage[2]==127) {
                        padToChange = PADS.PAD_5;
                    }
                    break;
                case 26:
                    if(lastMessage[2]==127) {
                        padToChange = PADS.PAD_6;
                    }
                    break;
                case 27:
                    if(lastMessage[2]==127) {
                        padToChange = PADS.PAD_7;
                    }
                    break;
                case 28:
                    if(lastMessage[2]==127) {
                        padToChange = PADS.PAD_8;
                    }
                    break;
            }


            if(padToChange != null) {
                parent.invertPad(padToChange);
                if (parent.getPadMode() == PADMODE.RADIO) {
                    //switch off all other pads
                    for (PADS pad : PADS.values()) {
                        if(pad.equals(padToChange))
                            sendLedOnOff(true, padToChange);
                        else
                            sendLedOnOff(false,pad);
                    }
                }
                else if ( (parent.getPadMode() == PADMODE.TOGGLE))
                    sendLedOnOff(parent.getPad(padToChange), padToChange);


            }
        }
    }



    protected void sendLedOnOff(boolean onOff, PADS pad) {
        try {
            //Hex version F0h 00h 20h 29h 02h 0Ah 78h [Template] [LED] Value F7h
            //Where Template is 00h-07h (0-7) for the 8 user templates, and 08h-0Fh (8-15) for the 8 factory
            //templates; LED is the index of the pad/button (00h-07h (0-7) for pads, 08h-0Bh (8-11) for buttons);
            //and Value is the velocity byte that defines the brightness values of both the red and green LEDs.
            byte template = 0x08;
            byte color = onOff ? LedConstants.RED_FULL : LedConstants.OFF;
            byte[] ledOn = new byte[] { (byte)0xF0,0x00,0x20,0x29,0x02,0x0A,0x78, template,pad.code(),color, (byte)0xF7};
            SysexMessage ledOnMsg = new SysexMessage(ledOn, ledOn.length);

            device.getReceiver().send(ledOnMsg, 0);
        }
        catch(Exception e){
            System.out.println("Error sending Midi message: " + e);
        }
    }

    @Override
    public void close() {

    }
}