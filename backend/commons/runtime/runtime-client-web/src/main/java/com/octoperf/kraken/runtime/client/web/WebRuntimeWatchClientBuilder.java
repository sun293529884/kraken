package com.octoperf.kraken.runtime.client.web;

import com.octoperf.kraken.config.backend.client.api.BackendClientProperties;
import com.octoperf.kraken.runtime.client.api.RuntimeWatchClient;
import com.octoperf.kraken.runtime.client.api.RuntimeWatchClientBuilder;
import com.octoperf.kraken.security.authentication.api.ExchangeFilterFactory;
import com.octoperf.kraken.security.authentication.client.spring.WebAuthenticatedClientBuilder;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class WebRuntimeWatchClientBuilder extends WebAuthenticatedClientBuilder<RuntimeWatchClient, BackendClientProperties> implements RuntimeWatchClientBuilder {

  public WebRuntimeWatchClientBuilder(final List<ExchangeFilterFactory> exchangeFilterFactories,
                                      final BackendClientProperties properties) {
    super(exchangeFilterFactories, properties);
  }

  @Override
  public Mono<RuntimeWatchClient> build() {
    return Mono.just(new WebRuntimeWatchClient(webClientBuilder.build()));
  }

}
