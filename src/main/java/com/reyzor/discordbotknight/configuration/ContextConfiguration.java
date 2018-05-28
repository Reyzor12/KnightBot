package com.reyzor.discordbotknight.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for spring context
 * @author Reyzor
 * @version 1.0
 * @since 26.05.2018
 */

@Configuration
@ComponentScan(basePackages = {"com.reyzor.discordbotknight"})
public class ContextConfiguration {
}
