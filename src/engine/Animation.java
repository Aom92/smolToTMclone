package src.engine;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Animation {
    private ArrayList<AnimFrame> frames;
    public int currFrameIndex;
    private long animTime;
    public long totalDuration;

    public Animation(){
        frames = new ArrayList<>();
        totalDuration = 0;
        start();
    }
    
    public synchronized void addFrame(ImageIcon image, long duration){
        totalDuration += duration;
        frames.add(new AnimFrame(image,totalDuration));

    }

    public synchronized void start(){
        animTime = 0;
        currFrameIndex = 0;
    }

    public synchronized void update(long elapsedTime){
        if (frames.size() > 1){
            animTime += elapsedTime;

            if(animTime >= totalDuration){
                animTime = animTime % totalDuration;
                currFrameIndex = 0;
            }

            while(animTime > getFrame(currFrameIndex).endTime){
                currFrameIndex++;
            }
        }
    }

    public synchronized void noloopupdate(long elapsedTime){
        if (frames.size() > 1){
            animTime += elapsedTime;

            if(animTime >= totalDuration){
               
                currFrameIndex = frames.size() - 1;
            }

            while(animTime > getFrame(currFrameIndex).endTime && currFrameIndex < frames.size()- 1){
                currFrameIndex++;
            }
        }
    }

    public synchronized ImageIcon getImage(){
        if (frames.size() == 0){
            return null;
        } else {
            return getFrame(currFrameIndex).image;
        }
    }

    private AnimFrame getFrame(int i ){
        return (AnimFrame)frames.get(i);
    }

    private class AnimFrame {
        ImageIcon image;

        long endTime;

        public AnimFrame(ImageIcon image, long endTime){
            this.image = image;
            this.endTime = endTime;
        }
    }



}