package spring.retry.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyServiceClassTest {
    @Autowired
    private MyServiceClass myServiceClass;

    @Test
    void testDoSomething() {
        // when doSomething is called TimeOutException is thrown and the retryTemplate is called 4 times
        Assertions.assertThrows(TimeoutException.class, () -> myServiceClass.doSomething());
    }

    @Test
    void testDoSomethingSafely() {
        // when doSomethingSafe is called TimeOutException is thrown and the retryTemplate is called 4 times and then callback logic is executed
        Assertions.assertDoesNotThrow(() -> myServiceClass.doSomethingSafely());
    }
}