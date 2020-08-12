package PaooGame.Sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.Arrays;

public class Sound {
    public static File stab ;

    public static File death_big_demon;
    public static File death_big_zombie;
    public static File death_little_skeleton;


    public static File sword_dash;


    public static File mouse_over;

    public static void Init(){
        stab = new File("res/Sounds/stab.wav");
        death_big_demon = new File("res/Sounds/death_big_demon.wav");
        death_big_zombie = new File("res/Sounds/death_big_zombie.wav");
        death_little_skeleton = new File("res/Sounds/death_little_skeleton.wav");
        mouse_over = new File("res/Sounds/mouse_over.wav");
        sword_dash = new File("res/Sounds/sword_dash.wav");


    }

    public static void playSound(File sound)  {

        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));

            clip.start();

        }
        catch (Exception e){
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
        }
    }

}
