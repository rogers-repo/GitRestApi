package com.roger.git.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**Custom health configuration for actuator
 * build the health status details that can be shown in endpoint
 * */
@Component
public class CustomHealthIndicator extends AbstractHealthIndicator{

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.up()
                .withDetail("app", "Alive use me to get info from Git")
                .withDetail("info", "Rest microservices")
                .withDetail("error", "Errors !");
    }
}
