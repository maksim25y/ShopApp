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
		String sql = "create table if not exists person\n" +
				"(\n" +
				"    id serial primary key,\n" +
				"    username  varchar(100) not null,\n" +
				"    year_of_birth integer      not null,\n" +
				"    email varchar," +
				"	email_active  boolean default false," +
				"    password  varchar not null,\n" +
				"    role varchar(100) not null\n" +
				");";
		String sql2 = "create table if not exists item(\n" +
				"    id serial primary key,\n" +
				"    name varchar unique,\n" +
				"    description varchar not null,\n" +
				"    photo varchar not null,\n" +
				"    user_id int default null references person(id) on delete set default\n" +
				");";
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
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(url, user, password);
			 PreparedStatement statement = connection.prepareStatement(sql2)) {
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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
