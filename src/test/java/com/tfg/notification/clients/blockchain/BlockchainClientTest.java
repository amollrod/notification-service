package com.tfg.notification.clients.blockchain;

import com.tfg.notification.clients.blockchain.dto.CreatePackageRequest;
import com.tfg.notification.clients.blockchain.dto.UpdatePackageStatusRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BlockchainClientTest {

    private ExchangeFunction exchangeFunction;
    private BlockchainClient client;

    @BeforeEach
    void setUp() {
        exchangeFunction = mock(ExchangeFunction.class);
        WebClient webClient = WebClient.builder()
                .exchangeFunction(exchangeFunction)
                .exchangeStrategies(ExchangeStrategies.withDefaults())
                .build();

        client = new BlockchainClient(webClient);
    }

    @Test
    void shouldSendCreatePackageRequest() {
        when(exchangeFunction.exchange(any()))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.OK).body("").build()));

        CreatePackageRequest dto = new CreatePackageRequest("pkg-1", "Madrid", "Valencia");

        client.createPackage(dto);

        ArgumentCaptor<ClientRequest> captor = ArgumentCaptor.forClass(ClientRequest.class);
        verify(exchangeFunction).exchange(captor.capture());

        ClientRequest request = captor.getValue();
        assertThat(request.method().name()).isEqualTo("POST");
        assertThat(request.url().toString()).endsWith("/package");
        assertThat(request.body()).isNotNull();
    }

    @Test
    void shouldSendUpdatePackageStatusRequest() {
        when(exchangeFunction.exchange(any()))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.OK).body("").build()));

        UpdatePackageStatusRequest dto = new UpdatePackageStatusRequest("IN_TRANSIT", "Zaragoza");

        client.updatePackageStatus("pkg-2", dto);

        ArgumentCaptor<ClientRequest> captor = ArgumentCaptor.forClass(ClientRequest.class);
        verify(exchangeFunction).exchange(captor.capture());

        ClientRequest request = captor.getValue();
        assertThat(request.method().name()).isEqualTo("PUT");
        assertThat(request.url().toString()).endsWith("/package/pkg-2/status");
        assertThat(request.body()).isNotNull();
    }
}
