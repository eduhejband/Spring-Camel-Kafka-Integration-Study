package com.example.demo_camel.service;

import com.example.demo_camel.model.Pedido;
import com.example.demo_camel.model.StatusPedido;
import com.example.demo_camel.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public void processarPedido(Pedido pedido) {
        // 1. Salva no banco
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // 2. Aplica regra de negócio (ex: valida estoque)
        if (pedidoSalvo.getQuantidade() <= 0) {
            pedidoSalvo.setStatus(StatusPedido.CANCELADO);
            pedidoRepository.save(pedidoSalvo);
            throw new RuntimeException("Quantidade inválida!");
        }

        // 3. Simula processamento
        try {
            Thread.sleep(2000); // Simula tempo de processamento
            pedidoSalvo.setStatus(StatusPedido.PROCESSADO);
            pedidoRepository.save(pedidoSalvo);

            System.out.println("✅ Pedido processado: " + pedidoSalvo.getId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}