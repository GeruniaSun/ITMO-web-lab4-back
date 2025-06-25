package lt.shgg.weblab4

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.EnableMBeanExport

@EnableMBeanExport
@SpringBootApplication
open class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
        SpringApplication.run(Application::class.java, *args)
        }
    }
}