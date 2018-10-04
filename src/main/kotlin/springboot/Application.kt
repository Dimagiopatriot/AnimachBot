package springboot

import com.google.gson.GsonBuilder
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@SpringBootApplication
class Application

@Configuration
class ObjectMapperConfiguration {
    @Bean
    @Primary
    fun objectMapper() = GsonBuilder().setPrettyPrinting()?.create()
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}