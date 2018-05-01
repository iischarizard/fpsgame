package game.data;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.util.ArrayList;

public abstract class AudioControls {
    public ArrayList<Audio> audioList = new ArrayList<>();
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
    public void playFile(){

    }
}
