package pl.mikbac.dependencystatusscanner.scanner;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by MikBac on 15.11.2025
 */

@AutoConfiguration
@ComponentScan("pl.mikbac.dependencystatusscanner.scanner")
@EnableScheduling
public class ScannerAutoConfiguration {
}
