package io.cell.service.habitat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Точка входа/старта/конфигурирвания spring-boot прилолжения
public class HabitatServiceApplication {

	public static void main(String[] args) {
		// Запуск spring-boot приложения
		SpringApplication.run(HabitatServiceApplication.class, args);
	}
}
