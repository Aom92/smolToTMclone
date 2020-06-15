package src.engine;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;



public enum SoundEffect{
    COLLIDE("./assets/collide.wav"),
    MOVE("./assets/move.wav"),
    BG("./assets/fun.wav"),
    DBG("./assets/sad.wav");

    public static enum Volume{
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;
    private AudioInputStream audioInputStream;

    //Cada efecto tiene su propio clip, cargado con su propio archivo
    private Clip clip;

    /**
     * Constructor para construir cada elemento de la enumeracion con su unico sonido
     * @param soundFileName Nombre Interno del archivo de sonido, definido previamente en la enumeración
     */
    SoundEffect(String soundFileName){
        try {
            
            File audiofile = new File(soundFileName);
            audioInputStream = AudioSystem.getAudioInputStream(audiofile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (LineUnavailableException e){
            e.printStackTrace();
        }
    }

    
    public void play(){
        if(volume != Volume.MUTE){
            if(clip.isRunning()){
                clip.stop();
            }
                 
            clip.setFramePosition(0);
            clip.start();
            
        }
    }

    /**
     * Reproduce el Efecto de sonido y se loopeara indefinidamente.
     * @param anyint cualquier entero hará valida la condición.
     */
    public void play(int anyint){
        if(anyint == -1){
            if(clip.isRunning())
                clip.stop();
            clip.loop(0);
            clip.setFramePosition(0);
            clip.start();
        }
        else if(anyint == 0){
                if(volume != Volume.MUTE){
                    if(clip.isRunning())
                        clip.stop();
                    clip.loop(-1);
                    clip.setFramePosition(0);
                    clip.start();
                    
                }
            } else 
                if(volume != Volume.MUTE){
                    if(clip.isRunning())
                        clip.stop();
                    clip.loop(-1);
                    clip.setFramePosition(anyint);
                    clip.start();
                }   
            
        
    
    }

    public void stop(){
        clip.stop();
    }

    public int getPos(){
        return clip.getFramePosition();
    }
    
    public static void init(){
        values();
    }

}