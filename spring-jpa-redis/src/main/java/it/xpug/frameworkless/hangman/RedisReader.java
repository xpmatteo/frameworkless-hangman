package it.xpug.frameworkless.hangman;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
@Slf4j
public class RedisReader implements Runnable, ApplicationListener<ApplicationReadyEvent> {

    private static final String QUEUE = "events";
    private static final String QUEUE_WIP = "events:wip";
    private static final int TIMEOUT = 20;

    @Autowired
    TaskExecutor taskExecutor;

    @Override
    @SneakyThrows
    public void run() {
        // https://github.com/xetorthio/jedis
        Jedis jedis = new Jedis("localhost");
        while (true) {
            log.info("RedisReader here!");
            String message = jedis.brpoplpush(QUEUE, QUEUE_WIP, TIMEOUT);
            if (null == message)
                continue;

            log.info("Got message: " + message);
            // processing
            for (int i=0; i< 5; i++) {
                System.out.print(".");
                Thread.sleep(1000);
            }
            jedis.lrem(QUEUE_WIP, 1, message);
            log.info("Message " + message + " deleted");
        }
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Starting RedisReader thread");
        taskExecutor.execute(this);
    }
}
