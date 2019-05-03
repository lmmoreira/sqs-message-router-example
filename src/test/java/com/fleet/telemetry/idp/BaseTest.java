package com.fleet.telemetry.idp;

import com.fleet.telemetry.idp.config.TestConfig;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public abstract class BaseTest {
}
