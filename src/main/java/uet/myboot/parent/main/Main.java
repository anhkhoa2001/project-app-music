package uet.myboot.parent.main;

import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static final String URL_IP_TRO = "http://192.168.2.105:8080/";
    public static final String URL_IP = "http://192.168.1.70:8080/";

    static List<String> list = new ArrayList<>();
    public static void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                list.add(fileEntry.getName());
            }
        }
    }

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    public static LocalDateTime now = LocalDateTime.now();

    static final File folder = new File("C:\\Users\\ADMIN\\Desktop\\kho\\" +
            "Spring-Boot\\firstspringboot\\src\\main\\resources\\public\\sounds\\us-uk");

    public static List<String> getList() {
        listFilesForFolder(folder);
        return list;
    }

    public static int getDurationWithMp3Spi(File file) throws UnsupportedAudioFileException, IOException {
        int totalTimer = 0;
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
        if (fileFormat instanceof TAudioFileFormat) {
            Map<?, ?> properties = ((TAudioFileFormat) fileFormat).properties();
            String key = "duration";
            Long microseconds = (Long) properties.get(key);
            int mili = (int) (microseconds / 1000);
            totalTimer = mili/1000;
        } else {
            throw new UnsupportedAudioFileException();
        }
        return totalTimer;
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {

        System.out.println(String.format("%02d", 1));
    }
}
