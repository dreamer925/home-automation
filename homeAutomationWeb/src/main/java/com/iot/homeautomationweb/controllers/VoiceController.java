package com.iot.homeautomationweb.controllers;

import ai.picovoice.rhino.Rhino;
import ai.picovoice.rhino.RhinoException;
import ai.picovoice.rhino.RhinoInference;
import com.iot.homeautomationweb.services.VoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/voice")
@RequiredArgsConstructor
public class VoiceController {
    private static final String WAKE_WORD_KEYWORD = "D:\\Mary\\Code\\Diploma\\homeAutomationWeb\\homeAutomationWeb\\src\\main\\resources\\picovoice\\Hey-Vidi_en_windows_v2_2_0.ppn";
    private static final String accessKey = "4YL2dnF/U/pEUAXnJTayPNHlNaSnCq8UCqVNBYwUCietG/bDRdzMZQ=="; // Picovoice AccessKey
    private static final String contextPath = "D:\\Mary\\Code\\Diploma\\homeAutomationWeb\\homeAutomationWeb\\src\\main\\resources\\picovoice\\Smart-home_en_windows_v2_2_0.rhn";
    private static Process process;
    private final VoiceService voiceService;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = "multipart/form-data")
    public ResponseEntity<?> handleVoiceRequest(@RequestParam("audio") MultipartFile audioFile) throws RhinoException, IOException {
        // Check audio format and create audio input stream
        AudioInputStream audioInputStream = null;
        try {
            System.out.println("Uploaded file name: " + audioFile.getOriginalFilename() + " size: " + audioFile.getSize());
            System.out.println("Uploaded file getContentType: " + audioFile.getContentType() );
            String filePath = "D:\\Mary\\Code\\Diploma\\homeAutomationWeb\\homeAutomationWeb\\src\\main\\resources\\picovoice\\";

            if(audioFile.getOriginalFilename() != null) {
                // Save the uploaded file to disk
                File file = new File(filePath + audioFile.getOriginalFilename());
                if (file.exists()) {
                    file.delete();
                }
                audioFile.transferTo(file);

                // Convert the file to a supported audio format using FFmpeg
                String outputFileName = filePath + "output.wav";
                File outputFile = new File(outputFileName);
                if (outputFile.exists()) {
                    outputFile.delete();
                }
                ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", file.getAbsolutePath(), "-acodec", "pcm_s16le", "-ar", "16000", "-ac", "1", outputFileName);
                pb.redirectErrorStream(true);
                process = pb.start();

                // Wait for FFmpeg to finish
                int exitCode = -1;
                try {
                    if (process.waitFor(30, TimeUnit.SECONDS)) {
                        exitCode = process.exitValue();
                        System.out.println("Exit code: " + exitCode);
                    } else {
                        // The process did not finish within the timeout
                        InputStream errorStream = process.getErrorStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.err.println(line);
                        }
                        throw new IOException("FFmpeg timedout. Process exited with error code " + exitCode);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    System.err.println("InterruptedException: " + e);
                    return ResponseEntity.internalServerError()
                            .body("InterruptedException");
                }

                // Create an AudioInputStream from the converted file
                audioInputStream = AudioSystem.getAudioInputStream(outputFile);

                // Get the audio format information
                AudioFormat format = audioInputStream.getFormat();
                int sampleRate = (int)format.getSampleRate();
                int sampleSizeInBits = format.getSampleSizeInBits();

                if (file.exists()) {
                    file.delete();
                }
                if (outputFile.exists()) {
                    outputFile.delete();
                }

                // Extract sample rate and sample size
                System.out.println(format.getSampleRate());
                System.out.println(format.getSampleSizeInBits());
            }

        } catch (UnsupportedAudioFileException e) {
            System.err.println("Audio format not supported. Please provide an input file of .au, .aiff or .wav format");
            return ResponseEntity.badRequest()
                    .body("Audio format not supported. Please provide an input file of .au, .aiff or .wav format");
        } catch (IOException e) {
            System.err.println("Could not get input audio file: " + e);
            return ResponseEntity.badRequest()
                    .body("Could not get input audio file");
        }

        Rhino rhino = null;
        try {
            rhino = new Rhino.Builder()
                    .setAccessKey(accessKey)
                    .setContextPath(contextPath)
                    .build();

            AudioFormat audioFormat = audioInputStream.getFormat();

            if (audioFormat.getSampleRate() != 16000.0f || audioFormat.getSampleSizeInBits() != 16) {
                throw new IllegalArgumentException(String.format("Invalid input audio file format. " +
                        "Input file must be a %dkHz, 16-bit audio file.", rhino.getSampleRate()));
            }

            if (audioFormat.getChannels() > 1) {
                System.out.println("Picovoice processes single-channel audio, but a multi-channel file was provided. " +
                        "Processing leftmost channel only.");
            }

            int frameIndex = 0;
            short[] rhinoFrame = new short[rhino.getFrameLength()];

            ByteBuffer sampleBuffer = ByteBuffer.allocate(audioFormat.getFrameSize());
            sampleBuffer.order(ByteOrder.LITTLE_ENDIAN);
            while (audioInputStream.available() != 0) {

                int numBytesRead = audioInputStream.read(sampleBuffer.array());
                if (numBytesRead < 2) {
                    break;
                }

                rhinoFrame[frameIndex++] = sampleBuffer.getShort(0);

                if (frameIndex == rhinoFrame.length) {

                    boolean isFinalized = rhino.process(rhinoFrame);
                    if (isFinalized) {
                        RhinoInference inference = rhino.getInference();
                        if (inference.getIsUnderstood()) {

                            System.out.println("{");
                            System.out.printf("  intent : '%s'%n", inference.getIntent());
                            System.out.println("  slots : {");
                            for (Map.Entry<String, String> slot : inference.getSlots().entrySet()) {
                                System.out.printf("    %s : '%s'%n", slot.getKey(), slot.getValue());
                            }
                            System.out.println("  }");
                            System.out.println("}");

//                            Map<String, String> response = voiceService.getCommand(inference);
                            String response = voiceService.getCommand(inference);

                            System.out.println(response);

                            return ResponseEntity.ok(response);
                        } else {
                            System.out.println("Didn't understand the command.");
                            return ResponseEntity.badRequest().body("Didn't understand the command.");
                        }
                    }
                    frameIndex = 0;
                }
            }
            System.out.println("Reached end of audio file before Rhino returned an inference.");
            return ResponseEntity.internalServerError().body("Reached end of audio file before Rhino returned an inference.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().body(e.toString());
        } finally {
            if (process != null) {
                process.destroy();
            }
            audioInputStream.close();
            if (rhino != null) {
                rhino.delete();
            }
        }
    }
}