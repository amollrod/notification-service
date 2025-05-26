package com.tfg.notification;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitectureTest {

    private static final JavaClasses classes = new ClassFileImporter().importPackages("com.tfg.notification");

    /**
     * Ensures that there are no cyclic dependencies between packages.
     */
    @Test
    void shouldNotHaveCyclicDependenciesBetweenPackages() {
        ArchRule rule = slices().matching("com.tfg.notification.(*)..")
                .should().beFreeOfCycles();
        rule.check(classes);
    }

    /**
     * Ensures that Kafka consumer classes are placed inside the 'kafka.consumer' package.
     */
    @Test
    void kafkaConsumersShouldResideInKafkaConsumerPackage() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("Consumer")
                .should().resideInAPackage("..kafka.consumer..")
                .allowEmptyShould(true);
        rule.check(classes);
    }

    /**
     * Ensures event classes are located in the common 'event' package. This is due to the fact that are used by both
     * Kafka consumers and producers, and potentially other components.
     */
    @Test
    void kafkaEventDtosShouldResideInKafkaDtoPackage() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("Event")
                .should().resideInAPackage("..event..")
                .allowEmptyShould(true);
        rule.check(classes);
    }

    /**
     * Ensures that REST controllers (if any) are placed in the 'controller' package.
     */
    @Test
    void restControllersShouldResideInControllerPackage() {
        ArchRule rule = classes()
                .that().areAnnotatedWith(org.springframework.web.bind.annotation.RestController.class)
                .should().resideInAPackage("..controller..")
                .allowEmptyShould(true);
        rule.check(classes);
    }

    /**
     * Prevents classes from being placed directly in the root package.
     */
    @Test
    void noClassesExceptMainAndTestShouldResideInRootPackage() {
        ArchRule rule = classes()
                .that().resideInAPackage("com.tfg.notification")
                .and().doNotHaveSimpleName("NotificationApplication")
                .and().doNotHaveSimpleName("ArchitectureTest")
                .should().resideOutsideOfPackage("com.tfg.notification")
                .allowEmptyShould(true);
        rule.check(classes);
    }
}
