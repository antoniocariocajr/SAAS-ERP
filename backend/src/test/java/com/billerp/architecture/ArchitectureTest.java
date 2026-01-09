package com.billerp.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.billerp", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

        @ArchTest
        static final ArchRule controllers_should_only_call_services = classes()
                        .that().resideInAPackage("..controller..")
                        .should().onlyAccessClassesThat()
                        .resideInAnyPackage("..controller..", "..service..", "..dto..", "..domain..", "java..",
                                        "org.springframework..",
                                        "..infrastructure..", "com.nimbusds..", "jakarta..",
                                        "io.swagger.v3.oas.annotations..");

        @ArchTest
        static final ArchRule services_should_not_access_controllers = noClasses()
                        .that().resideInAPackage("..service..")
                        .should().accessClassesThat()
                        .resideInAPackage("..controller..");

        @ArchTest
        static final ArchRule domain_should_not_access_other_services_or_controllers = noClasses()
                        .that().resideInAPackage("..domain..")
                        .and().resideOutsideOfPackage("..domain.service..")
                        .should().accessClassesThat()
                        .resideInAnyPackage("..com.billerp.service..", "..controller..");

        @ArchTest
        static final ArchRule layers_should_be_respected = classes()
                        .that().resideInAPackage("..service.impl..")
                        .should().onlyHaveDependentClassesThat()
                        .resideInAnyPackage("..controller..", "..service..", "..infrastructure..");
}
