package com.petrov.dimitar.blockchainexplorer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressControllerE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getUnspentTransactions_ok() throws Exception {
        UnspentOutput expected = new UnspentOutput(1,
                "223536de38ba2f70a0d210e451d53b376b62195834af02d64eb771e95bb21e7f", 2730);

        UnspentOutputs outputs = restTemplate.getForObject("/address/16bUGjvunVp7LqygLHrTvHyvbvfeuRCWAh", UnspentOutputs.class);

        assertThat(outputs.getOutputs(), hasSize(44));
        assertThat(outputs.getOutputs().get(0), equalTo(expected));
    }


    @Test
    public void getUnspentTransactions_noTransactions_ok() throws Exception {
        UnspentOutputs outputs = restTemplate.getForObject("/address/1Aff4FgrtA1dZDwajmknWTwU2WtwUvfiXa", UnspentOutputs.class);

        assertThat(outputs.getOutputs(), hasSize(0));
    }

    @Test
    public void getUnspentTransactions_incorrectAddress() throws Exception {
        ResponseEntity<UnspentOutputs> entity = restTemplate.getForEntity("/address/wrongAddress", UnspentOutputs.class);

        assertThat(entity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }


}