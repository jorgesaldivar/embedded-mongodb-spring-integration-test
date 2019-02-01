package com.jorgesaldivar.mongodbit.config;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.SocketUtils;

import java.io.IOException;

@Configuration
@EnableMongoRepositories(basePackages = "com.jorgesaldivar.mongodbit.repository")
public class MongoConfigTest {

    @Bean
    public MongoTemplate mongoTemplate(@Value("${enable.flapdoodle:true}") Boolean flapdoodle) throws IOException {
        return flapdoodle ? flapdoodle() : jirutka();
    }

    @Bean
    public MongoTemplate flapdoodle() throws IOException {

        String bindIp = "localhost";
        int port = SocketUtils.findAvailableTcpPort();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                .build();

        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
        MongodProcess mongod = mongodExecutable.start();

        MongoClient mongo = new MongoClient(bindIp, port);
        MongoTemplate mongoTemplate = new MongoTemplate(mongo, "embedded_database");
        return mongoTemplate;

    }

    private MongoTemplate jirutka() throws IOException {

        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp("localhost");
        MongoClient mongoClient = mongo.getObject();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "embedded_database");
        return mongoTemplate;
    }

}