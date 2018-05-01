package game.data;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.util.ArrayList;

public class AudioControls {
    private ArrayList<Audio> audioList = new ArrayList<>();
    private int counter = 0;
    public AudioControls(){

    }
    public void loadSongs(String[] songList){
        try {
            for (String s : songList) {
                audioList.add(AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(s)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void playSong(int direction){
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
