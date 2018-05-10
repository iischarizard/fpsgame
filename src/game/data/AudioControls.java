package game.data;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.util.ArrayList;

public abstract class AudioControls {
    public ArrayList<Audio> audioList = new ArrayList<>();
    public ArrayList<String> songNames = new ArrayList<>();

    public AudioControls(){

    }
    public void loadSongs(String[] songList, String path){
        try {
            for (String s : songList) {
                String temp = s.split(path)[1];
                songNames.add(temp.substring(0,temp.indexOf(".wav")));
                audioList.add(AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(s)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void playFile(){

    }

}
