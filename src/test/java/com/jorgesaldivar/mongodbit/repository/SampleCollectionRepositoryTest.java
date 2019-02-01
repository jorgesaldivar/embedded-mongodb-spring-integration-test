package com.jorgesaldivar.mongodbit.repository;

import com.jorgesaldivar.mongodbit.config.MongoConfigTest;
import com.jorgesaldivar.mongodbit.model.SampleCollection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Embedded Mongo DB with Spring (not boot)
 * Mongo configuration at {@link MongoConfigTest}
 *
 * @see <a href="https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo">Embedded MongoDB using flapdoodle</a>
 * @see <a href="https://springframework.guru/spring-boot-with-embedded-mongodb/">Embedded MongoDB using jirutka code</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoConfigTest.class})
public class SampleCollectionRepositoryTest {

    @Autowired
    private SampleCollectionRepository sampleCollectionRepository;

    @Test
    public void test() {
        sampleCollectionRepository.save(new SampleCollection("one", "two"));
        Assert.assertTrue("two".equals(sampleCollectionRepository.findByKey("one").getValue()));
    }

}