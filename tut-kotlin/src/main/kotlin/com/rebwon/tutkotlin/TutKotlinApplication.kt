package com.rebwon.tutkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class TutKotlinApplication

fun main(args: Array<String>) {
    runApplication<TutKotlinApplication>(*args)
}
