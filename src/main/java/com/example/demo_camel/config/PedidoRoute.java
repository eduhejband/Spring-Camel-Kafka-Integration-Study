package com.example.demo_camel.config;

import com.example.demo_camel.service.PedidoService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoRoute extends RouteBuilder {

    @Autowired
    private PedidoService pedidoService;

    @Override
    public void configure() throws Exception {
        // Rota principal: Recebe pedidos e envia para Kafka
        from("direct:novoPedido")
                .log("📤 Enviando pedido para Kafka: ${body}")
                .to("kafka:pedidos-topic?brokers=localhost:9092");

        // Rota de consumo: Processa pedidos do Kafka
        from("kafka:pedidos-topic?brokers=localhost:9092")
                .log("📥 Pedido recebido do Kafka: ${body}")
                .bean(pedidoService, "processarPedido")
                .onException(Exception.class)
                .log("❌ Erro ao processar pedido: ${exception.message}")
                .handled(true);
    }
}