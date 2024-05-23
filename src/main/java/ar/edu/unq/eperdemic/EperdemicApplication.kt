package ar.edu.unq.eperdemic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
class EperdemicApplication

fun main(args: Array<String>) {
    runApplication<EperdemicApplication>(*args)
}
