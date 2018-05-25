package game.data;

/**
 * Created by Katkr on 4/30/2018.
 */
public class SoundEffects extends AudioControls {

    public SoundEffects(){
        super();
    }

    public void playFile(int fileNumber){
        audioList.get(fileNumber).playAsSoundEffect(1,1,false);
    }
}
