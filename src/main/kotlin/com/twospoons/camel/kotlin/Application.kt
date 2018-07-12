package com.twospoons.camel.kotlin

import com.mongodb.MongoClient
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Bean
    fun mongoLocal(): MongoClient {
        return MongoClient("127.0.0.1", 27017)
    }
}


fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}