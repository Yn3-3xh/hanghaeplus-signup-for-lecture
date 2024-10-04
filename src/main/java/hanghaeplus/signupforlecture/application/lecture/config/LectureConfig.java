package hanghaeplus.signupforlecture.application.lecture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class LectureConfig {

    @Bean
    public ReentrantLock reentrantLock() {
        return new ReentrantLock();
    }
}
