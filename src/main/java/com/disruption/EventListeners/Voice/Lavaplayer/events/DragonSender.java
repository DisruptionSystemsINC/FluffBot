package com.disruption.EventListeners.Voice.Lavaplayer.events;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.nio.ByteBuffer;

public class DragonSender implements AudioSendHandler {
        private  AudioPlayer audioPlayer;
        private AudioFrame lastFrame;

        public DragonSender(AudioPlayer audioPlayer) {
            this.audioPlayer = audioPlayer;
        }

        @Override
        public boolean canProvide() {
            lastFrame = audioPlayer.provide();
            return lastFrame != null;
        }

        @Override
        public ByteBuffer provide20MsAudio() {
            return ByteBuffer.wrap(lastFrame.getData());
        }

        @Override
        public boolean isOpus() {
            return true;
        }
    }

