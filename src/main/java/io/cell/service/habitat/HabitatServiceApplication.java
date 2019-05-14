package io.cell.service.habitat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication // Точка входа/старта/конфигурирвания spring-boot прилолжения
public class HabitatServiceApplication {

	public static void main(String[] args) {
		// Запуск spring-boot приложения
		ApplicationContext context = SpringApplication.run(HabitatServiceApplication.class, args);
	}
}
