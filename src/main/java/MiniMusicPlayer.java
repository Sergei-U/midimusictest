import javax.sound.midi.*;
import javax.swing.*;

/**
 * @author Sergei Usov
 * @version 1.0.0
 */
public class MiniMusicPlayer {

    static JFrame jf = new JFrame();
    static MyDrawPanel ml;

    public static void main(String[] args) {
        MiniMusicPlayer miniMusicPlayer = new MiniMusicPlayer();
        miniMusicPlayer.go();
    }

    public void setUpGui() {
        ml = new MyDrawPanel();
        jf.setContentPane(ml);
        jf.setBounds(30, 30, 300, 300);
        jf.setVisible(true);
    }

    public void go() {
        setUpGui();

        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addControllerEventListener(ml, new int[]{127});
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();
            int r = 0;

            for (int i = 0; i < 60; i += 4) {
                r = (int) ((Math.random() * 50) + 1);
                track.add(makeEvent(144, 1, r, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, r, 100, i + 2));
            }

            sequencer.setSequence(seq);
            sequencer.start();
            sequencer.setTempoInBPM(120);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent midiEvent = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            midiEvent = new MidiEvent(a, tick);
        } catch (Exception e) {
        }
        return midiEvent;
    }


}
