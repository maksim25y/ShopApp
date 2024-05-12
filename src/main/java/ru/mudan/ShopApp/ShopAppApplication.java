package ru.mudan.ShopApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringBootApplication
@Configuration
@EnableConfigurationProperties
@EnableScheduling
public class ShopAppApplication {
	@PostConstruct
	public void init() {


		String url = "jdbc:postgresql://db:5432/postgres";
		String user = "admin";
		String password = "root";

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String passwordAdmin = "admin";
		String insertAdmin = "INSERT INTO person(username, year_of_birth, email,password, role)" +
				"VALUES('admin',2000,'myemail@gmail.com',?,'ROLE_ADMIN');";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		try (Connection connection = DriverManager.getConnection(url, user, password);
			 PreparedStatement statement = connection.prepareStatement(insertAdmin)) {
			statement.setString(1,encoder.encode(passwordAdmin));
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(ShopAppApplication.class, args);
	}
}
