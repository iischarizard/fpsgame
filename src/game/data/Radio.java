package game.data;

/**
 * Created by Katkr on 4/30/2018.
 */
public class Radio extends AudioControls {

    private int counter = 0;
    public Radio(){
        super();
    }

    public void playFile(int direction){
        if(audioList.get(counter).isPlaying())
            audioList.get(counter).stop();
        counter += direction;
        if(counter == audioList.size())
            counter = 0;
        if(counter < 0)
            counter = audioList.size()-1;
        audioList.get(counter).playAsMusic(1,1,false);
        System.out.println(audioList.get(counter));
    }
}
