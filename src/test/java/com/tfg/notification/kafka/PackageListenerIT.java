package com.tfg.notification.kafka;

import com.tfg.notification.event.PackageEvent;
import com.tfg.notification.services.NotificationService;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.ConfluentKafkaContainer;

import java.util.Map;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PackageListenerIT {

    static final ConfluentKafkaContainer kafkaContainer = new ConfluentKafkaContainer("confluentinc/cp-kafka:latest");

    @DynamicPropertySource
    static void registerKafkaProperties(DynamicPropertyRegistry registry) {
        kafkaContainer.start();
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.consumer.group-id", () -> "test-group");
        registry.add("spring.kafka.consumer.auto-offset-reset", () -> "earliest");
        registry.add("spring.kafka.consumer.properties.spring.json.trusted.packages", () -> "*");
        registry.add("spring.kafka.consumer.properties.spring.json.use.type.headers", () -> false);
        registry.add("spring.kafka.consumer.properties.spring.json.value.default.type", PackageEvent.class::getName);
    }

    @MockitoBean
    private NotificationService notificationService;

    @Autowired
    private KafkaTemplate<String, PackageEvent> kafkaTemplate;

    private KafkaTemplate<String, PackageEvent> createKafkaTemplate() {
        var producerFactory = new DefaultKafkaProducerFactory<String, PackageEvent>(
                Map.of(
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers(),
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                        JsonSerializer.ADD_TYPE_INFO_HEADERS, false
                )
        );
        var template = new KafkaTemplate<>(producerFactory);
        template.setObservationEnabled(false);
        return template;
    }

    @BeforeAll
    static void setup() {
        kafkaContainer.start();
    }

    @AfterAll
    static void tearDown() {
        kafkaContainer.stop();
    }

    @Test
    void shouldConsumePackageCreatedEvent() {
        KafkaTemplate<String, PackageEvent> template = createKafkaTemplate();
        PackageEvent event = PackageEvent.builder()
                .id("test-id")
                .origin("Madrid")
                .destination("Sevilla")
                .status("CREATED")
                .lastLocation("Madrid")
                .build();

        template.send("package-created", event);

        verify(notificationService, timeout(5000)).handlePackageCreated(event);
    }

    @Test
    void shouldConsumePackageUpdatedEvent() {
        KafkaTemplate<String, PackageEvent> template = createKafkaTemplate();
        PackageEvent event = PackageEvent.builder()
                .id("test-id-2")
                .origin("Madrid")
                .destination("Barcelona")
                .status("IN_TRANSIT")
                .lastLocation("Zaragoza")
                .build();

        template.send("package-updated", event);

        verify(notificationService, timeout(5000)).handlePackageUpdated(event);
    }
}
