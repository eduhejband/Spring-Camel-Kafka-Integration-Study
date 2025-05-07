package com.example.demo_camel.controller;

import com.example.demo_camel.model.Pedido;
import com.example.demo_camel.model.StatusPedido;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final ProducerTemplate producerTemplate;

    public PedidoController(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    @PostMapping
    public ResponseEntity<String> criarPedido(@RequestBody Pedido pedido) {
        pedido.setStatus(StatusPedido.RECEBIDO);
        producerTemplate.sendBody("direct:novoPedido", pedido);
        return ResponseEntity.ok("Pedido recebido e em processamento!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> consultarPedido(@PathVariable Long id) {
        // Implemente consulta ao banco (opcional)
        return null;
    }
}