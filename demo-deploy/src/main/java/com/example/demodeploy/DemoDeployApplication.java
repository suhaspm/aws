package com.example.demodeploy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoDeployApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoDeployApplication.class, args);
	}

//	@GetMapping("/deploy")
//	public String deploy() {
//		return "Hello World";
//	}

//	@Bean
//	public CommandLineRunner initDatabase(JdbcTemplate jdbcTemplate) {
//		return args -> {
//			try {
//				// Create a simple table if it doesn't exist
//				// Changed 'value' to 'data_value'
//				jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS my_test_data (id INT PRIMARY KEY, data_value VARCHAR(255))");
//				System.out.println(">>>>>>>>>> H2: Created 'my_test_data' table (if not exists) <<<<<<<<<<");
//
//				// Insert some data
//				// Changed 'value' to 'data_value'
//				jdbcTemplate.update("INSERT INTO my_test_data (id, data_value) VALUES (1, 'Hello from H2!')");
//				System.out.println(">>>>>>>>>> H2: Inserted a row into 'my_test_data' <<<<<<<<<<");
//
//				// Query the data
//				// Changed 'value' to 'data_value'
//				String storedValue = jdbcTemplate.queryForObject("SELECT data_value FROM my_test_data WHERE id = 1", String.class);
//				System.out.println(">>>>>>>>>> H2: Successfully retrieved from DB: " + storedValue + " <<<<<<<<<<");
//
//			} catch (Exception e) {
//				System.err.println(">>>>>>>>>> H2 Database connection / operation FAILED during startup: " + e.getMessage() + " <<<<<<<<<<");
//				e.printStackTrace();
//			}
//		};

}