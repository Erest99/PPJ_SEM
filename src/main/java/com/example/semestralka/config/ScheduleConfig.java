package com.example.semestralka.config;

import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoService;
import com.example.semestralka.pocasi.Pocasi;
import com.example.semestralka.pocasi.PocasiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Profile("normal")
@Configuration
@EnableScheduling

public class ScheduleConfig implements SchedulingConfigurer {

    @Value("${refresh.rate}")
    private int refreshrate;

    @Value("${expiration.rate}")
    private int exprate;

    @Value("${api.code.part1}")
    private String code1;

    @Value("${api.code.part2}")
    private String code2;

    @Autowired
    private RestTemplate restTemplate;

    private final PocasiService pocasiService;
    private final MestoService mestoService;

    Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

    @Autowired
    public ScheduleConfig(PocasiService pocasiService,MestoService mestoService) {
        this.pocasiService = pocasiService;
        this.mestoService = mestoService;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                logger.info("Taking new data");
                String mesto;
                String stat;
                String url;
                List<Mesto> ms = mestoService.getCities();
                for(Mesto m : ms)
                {
                    mesto = m.getName();
                    stat = m.getState();


                    url = code1+mesto+","+stat+code2;
                    Pocasi pocasi = restTemplate.getForObject(url,Pocasi.class);
                    pocasiService.addNewPocasi(pocasi);

                }

                pocasiService.deleteExpired(LocalDateTime.now().minusDays(exprate));



            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                List<Mesto> ms = mestoService.getCities();
                int cityCount= ms.size();
                Calendar nextExecutionTime = new GregorianCalendar();
                Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
                nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                nextExecutionTime.add(Calendar.SECOND, getNewExecutionTime()*cityCount);
                return nextExecutionTime.getTime();
            }
        });
    }

    private int getNewExecutionTime() {
        return refreshrate;
    }

    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        scheduler.setPoolSize(1);
        scheduler.initialize();
        return scheduler;
    }

}