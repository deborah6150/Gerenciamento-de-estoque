package com.produto.crudprodutothymeleaf.controller;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.commons.text.StringSubstitutor;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import com.produto.crudprodutothymeleaf.entitys.Produto;

import com.produto.crudprodutothymeleaf.service.ProdutoService;

@RestController
@RequestMapping("/generate-pdf")
public class PdfController {

    
    @Autowired
    private ProdutoService produtoService;
    

    @GetMapping
    public void generatePdf(HttpServletResponse response) throws IOException {

        ByteArrayOutputStream baosDest = new ByteArrayOutputStream();
        PDFMergerUtility merger = new PDFMergerUtility();
        merger.setDestinationStream(baosDest);
        
        Map<String, Object> param = new HashMap<>();

        String imagePath = ResourceUtils.getFile("classpath:img/logoprod.jpg").getPath();
        byte[] image = Files.readAllBytes(Paths.get(imagePath));
        
        Produto produto = produtoService.buscarPorId(1L);

        param.put("LOGO", "data:image/jpg;base64," + Base64.getEncoder().encodeToString(image));
        param.put("COD", produto.getId());
        param.put("NOME", produto.getNome());
        param.put("PRECO", produto.getPreco());
        

        String templatePath = ResourceUtils.getURL("classpath:templates/").getPath();
        
        merger.addSource(getTemplate(templatePath, "Produto.html", param));
        merger.addSource(getTemplate(templatePath, "Page2.html", new HashMap<>()));
        merger.addSource(getTemplate(templatePath, "Page3.html", new HashMap<>()));

        MemoryUsageSetting memUsageSetting = MemoryUsageSetting.setupMainMemoryOnly();
        merger.mergeDocuments(memUsageSetting);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=HelloWord.pdf");

        OutputStream os = response.getOutputStream();
        os.write(baosDest.toByteArray());
        os.flush();
    }

    private ByteArrayInputStream getTemplate(final String templatePath, String templateName, final Map<String, Object> param) throws IOException {
        FileInputStream fis = new FileInputStream(templatePath + templateName);
        String data = org.apache.commons.io.IOUtils.toString(fis, StandardCharsets.UTF_8);
        StringSubstitutor stringSubstitutor = new StringSubstitutor(param);
        String html = stringSubstitutor.replace(data);

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.testMode(true);
        builder.withHtmlContent(html, templatePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        builder.toStream(baos);
        builder.run();

        byte[] pdfBytes = baos.toByteArray();

        return new ByteArrayInputStream(pdfBytes);
    }
}