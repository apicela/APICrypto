package com.apicela.apicrypto;

import com.apicela.apicrypto.services.CoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CoinServiceTest {
    @Autowired
    private CoinService coinService;

    @Test
    public void testListAll() throws Exception {
        var response = coinService.listAllCoins()
                .collectList()  // Collect the items into a List
                .block();       // Block and wait for the result

        System.out.println(response);
    }
}
