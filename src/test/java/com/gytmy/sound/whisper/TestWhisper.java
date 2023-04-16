package com.gytmy.sound.whisper;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

public class TestWhisper {

    private static final String AUDIO_DIRECTORY_PATH = "src/test/resources/audio";
    private static final String JSON_DIRECTORY_PATH = "src/test/resources/json";
    private static final String AUDIO_FILE_NAME = "yesmymaster";

    @Ignore // This test is ignored because it takes to long on the CI server
    @Test
    public void testWhisper() {
        Whisper whisper = new Whisper(false, Whisper.Model.TINY_EN);
        // Assert that the following code does not throw an exception
        whisper.run(AUDIO_DIRECTORY_PATH, AUDIO_FILE_NAME, JSON_DIRECTORY_PATH);
        assertTrue(Files.exists(Paths.get(JSON_DIRECTORY_PATH, AUDIO_FILE_NAME + ".json")));
    }
}
