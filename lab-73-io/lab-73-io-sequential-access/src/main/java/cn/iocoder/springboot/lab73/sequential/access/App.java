package cn.iocoder.springboot.lab73.sequential.access;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

/**
 * Hello world!
 */
@Slf4j
@SpringBootApplication
public class App {
    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
        Long position = 0L;
        AtomicLong positionAtomic = new AtomicLong();
        positionAtomic.set(0L);

        // LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS"));

        log.info("position {}", position);
        log.info("positionAtomic start with {}", position);
        final int loopTimes = 1024 * 10;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 2048; i++) {
            stringBuilder.append(0);
        }
//        for (int i = 0; i < loopTimes; i++) {
//            position = fileWrite("./test.log", String.valueOf(stringBuilder), position);
//        }

        IntStream.range(1, 10).parallel().forEach(i -> {
                    positionAtomic.set(fileWriteAtom("./test.log", i + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + System.getProperty("line.separator")));
                }
        );

        sleep(20);


        log.info("position {}", position);
        log.info("positionAtomic end with {}", positionAtomic);
//        position = fileWrite("./test.log", "34", position + 1);
//        log.info("position {}", position);
    }

    public static long fileWrite(String filePath, String content, Long position) {
        //  log.info("write position {}", position);
        File file = new File(filePath);

        try {
            //map.position(index);
            map.put(content.getBytes("utf-8"));
            //log.info("map position {}", map.position());
            return map.position() + 1;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return 0L;
    }

    public synchronized static Long fileWriteAtom(String filePath, String content) {
        //  log.info("write position {}", position);
        File file = new File(filePath);

        try {

            //map.position(index);
            map.put(content.getBytes("utf-8"));
            //log.info("map position {}", map.position());
            return Long.valueOf(map.position() + 1);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return 0L;
    }

    static MappedByteBuffer map;
    static RandomAccessFile randomAccessTargetFile;

    static {

        try {
            randomAccessTargetFile = new RandomAccessFile("./test.log", "rw");

            FileChannel targetFileChannel = randomAccessTargetFile.getChannel();

            map = targetFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, (long) 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
