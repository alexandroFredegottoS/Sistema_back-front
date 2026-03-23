package br.com.pedido.demo.service;

import br.com.pedido.demo.model.Pedido;
import br.com.pedido.demo.model.Item_Pedido;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

@Service
public class RelatorioService {

    public byte[] gerarRelatorio(Pedido pedido){
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        //  TÍTULO
        document.add(new Paragraph("RELATÓRIO DO PEDIDO").setBold().setFontSize(16));

        //  DADOS DO PEDIDO
        document.add(new Paragraph("Cliente: " + pedido.getCliente().getNome()));
        document.add(new Paragraph("Descrição: " + pedido.getDescricao()));
        document.add(new Paragraph("\n"));

        //  TABELA
        Table tabela = new Table(4);

        tabela.addHeaderCell("Produto");
        tabela.addHeaderCell("Preço");
        tabela.addHeaderCell("Quantidade");
        tabela.addHeaderCell("Subtotal");

        BigDecimal total = BigDecimal.ZERO;

        //  LOOP DOS ITENS
        for (Item_Pedido item : pedido.getItens()) {

            String nome = item.getProduto().getNome();
            BigDecimal preco = item.getVlUnitario();
            Long quantidade = item.getQuantidade();
            BigDecimal subtotal = item.getVlTotal();

            tabela.addCell(nome);
            tabela.addCell("R$ " + preco);
            tabela.addCell(String.valueOf(quantidade));
            tabela.addCell("R$ " + subtotal);

            total = total.add(subtotal);
        }

        document.add(tabela);

        //  TOTAL FINAL
        document.add(new Paragraph("\nTotal: R$ " + total).setBold());

        document.close();

        return out.toByteArray();
    }
}