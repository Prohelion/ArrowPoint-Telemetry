package au.com.teamarrow.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages={"au.com.teamarrow"})
@EnableScheduling
@EnableTransactionManagement
@ImportResource({	"classpath:/META-INF/spring/mvc/spring-context.xml",
					"classpath:/META-INF/spring/mvc/spring-data.xml",
					"classpath:/META-INF/spring/maps/ArrowPointMaps.xml",
					"classpath:/META-INF/spring/alerts/ArrowPointAlerts.xml",
					"classpath:/META-INF/spring/integration/spring-integration-context.xml"})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    public Application() {
        super();
        // Disable error logs for ajax responses that are interrupted
        setRegisterErrorPageFilter(false);
    }

}