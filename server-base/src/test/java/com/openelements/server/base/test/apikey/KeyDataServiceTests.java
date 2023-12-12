package com.openelements.server.base.test.apikey;

import com.openelements.server.base.apikey.ApiKeyConfig;
import com.openelements.server.base.apikey.ApiKeyData;
import com.openelements.server.base.apikey.KeyDataService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ApiKeyConfig.class, ApiKeyTestConfig.class})
public class KeyDataServiceTests {

    @Autowired
    private KeyDataService keyDataService;

    @Test
    void testInjectable() {
        Assertions.assertNotNull(keyDataService);
    }

    @Test
    void testKeyCreation() {
        //given
        final String name = "open-elements-key";

        //when
        final ApiKeyData key = keyDataService.createNewKey(name);

        //then
        Assertions.assertNotNull(key);
        Assertions.assertNotNull(key.id());
        Assertions.assertNotNull(key.key());
        Assertions.assertEquals(name, key.name());
        Assertions.assertEquals(ApiKeyTestConfig.TEST_USER, key.user());
        Assertions.assertTrue(key.validUntil().isAfter(LocalDateTime.now().plusYears(10)));
    }

    @Test
    void testKeyCreationWithTime() {
        //given
        final String name = "open-elements-key";

        //when
        final ApiKeyData key = keyDataService.createNewKey(name, LocalDateTime.now().plusDays(1));

        //then
        Assertions.assertNotNull(key);
        Assertions.assertNotNull(key.id());
        Assertions.assertNotNull(key.key());
        Assertions.assertEquals(name, key.name());
        Assertions.assertEquals(ApiKeyTestConfig.TEST_USER, key.user());
        Assertions.assertTrue(key.validUntil().isAfter(LocalDateTime.now().plusHours(23)));
        Assertions.assertTrue(key.validUntil().isBefore(LocalDateTime.now().plusHours(25)));
    }

    @Test
    void testKeyCreationWithInvalidValues() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            keyDataService.createNewKey(null);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            keyDataService.createNewKey("");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            keyDataService.createNewKey(" ");
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            keyDataService.createNewKey("open-elements-key", null);
        });
    }
}
