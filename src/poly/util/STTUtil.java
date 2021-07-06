package poly.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v1.LongRunningRecognizeMetadata;
import com.google.cloud.speech.v1.LongRunningRecognizeResponse;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognizeRequest;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

public class STTUtil {

	public static void main(String[] args) throws Exception {
		//String filePath = "c:\\tts\\123\\0.ogg";
		
		//System.out.println("filePath: " + filePath);
		//sampleRecognize(filePath);
		//getRecognitionAudio(filePath);
		String localFilePath = "c:/tts/123/0.wav";
		sampleRecognize(localFilePath);
	}

		/**
		 * Transcribe a short audio file using synchronous speech recognition
		 *
		 * @param localFilePath Path to local audio file, e.g. /path/audio.wav
		 */
		public static void sampleRecognize(String localFilePath) {
		  try (SpeechClient speechClient = SpeechClient.create()) {

		    // The language of the supplied audio
		    String languageCode = "ko-KR";

		    // Sample rate in Hertz of the audio data sent
		    int sampleRateHertz = 24000;

		    // Encoding of audio data sent. This sample sets this explicitly.
		    // This field is optional for FLAC and WAV audio formats.
		    RecognitionConfig.AudioEncoding encoding = RecognitionConfig.AudioEncoding.LINEAR16;
		    RecognitionConfig config =
		        RecognitionConfig.newBuilder()
		            .setLanguageCode(languageCode)
		            .setSampleRateHertz(sampleRateHertz)
		            .setEncoding(encoding)
		            .setAudioChannelCount(1)
		            .build();
		    Path path = Paths.get(localFilePath);
		    byte[] data = Files.readAllBytes(path);
		    ByteString content = ByteString.copyFrom(data);
		    RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(content).build();
		    RecognizeRequest request =
		        RecognizeRequest.newBuilder().setConfig(config).setAudio(audio).build();
		    RecognizeResponse response = speechClient.recognize(request);
		    for (SpeechRecognitionResult result : response.getResultsList()) {
		      // First alternative is the most probable result
		      SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
		      System.out.printf("Transcript: %s\n", alternative.getTranscript());
		    }
		  } catch (Exception exception) {
		    System.err.println("Failed to create the client due to: " + exception);
		  }
		}

}
