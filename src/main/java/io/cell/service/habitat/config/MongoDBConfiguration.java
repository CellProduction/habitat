package io.cell.service.habitat.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import io.cell.service.habitat.utils.EntityConditionMapper;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static org.bson.codecs.configuration.CodecRegistries.fromCodecs;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Конфигуарция mongoDB.
 * Параметры храняться в aplication.yml.
 */
@Configuration
public class MongoDBConfiguration extends AbstractMongoConfiguration {

  private String host;
  private String port;
  private String user;
  private String password;
  private String databaseName;


  @Autowired
  public MongoDBConfiguration(Environment environment) {
    host = environment.getProperty("spring.data.mongodb.host");
    port = environment.getProperty("spring.data.mongodb.port");
    user = environment.getProperty("spring.data.mongodb.username");
    password = environment.getProperty("spring.data.mongodb.password");
    databaseName = environment.getProperty("spring.data.mongodb.database");
  }

  @Override
  public String getDatabaseName() {
    return databaseName;
  }

  @Override
  @Bean
  public MongoClient mongoClient() {
    ServerAddress serverAddress = new ServerAddress(host, Integer.valueOf(port));
    Optional<MongoCredential> credential = buildCredential();

    MongoClientOptions.Builder builder = MongoClientOptions.builder();
    builder.codecRegistry(fromRegistries(fromCodecs(new UuidCodec(UuidRepresentation.JAVA_LEGACY)), MongoClient.getDefaultCodecRegistry()));
    MongoClientOptions clientOptions = builder.build();

    MongoClient mongoClient = null;
    if (credential.isPresent()) {
      mongoClient = new MongoClient(serverAddress, credential.get(), clientOptions);
    } else {
      mongoClient = new MongoClient(serverAddress, clientOptions);
    }
    return mongoClient;
  }

  public Optional<MongoCredential> buildCredential() {
    MongoCredential credential = null;
    if (!StringUtils.isEmpty(user) && !StringUtils.isEmpty(password)) {
      credential = MongoCredential.createCredential(user, databaseName, password.toCharArray());
    }
    return Optional.ofNullable(credential);
  }

  @Bean
  public EntityConditionMapper entityConditionMapper() {
    return new EntityConditionMapper();
  }
}