package com.petrov.dimitar.blockchainexplorer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import info.blockchain.api.blockexplorer.BlockExplorer;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public BlockExplorer blockExplorer() {
        return new BlockExplorer();
    }

}
