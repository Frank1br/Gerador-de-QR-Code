package com.frank1br.qrcode.generator.controller;

import com.frank1br.qrcode.generator.dto.qrcode.QrCodeGenerateRequest;
import com.frank1br.qrcode.generator.dto.qrcode.QrCodeGenerateResponse;
import com.frank1br.qrcode.generator.service.QrCodeGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {



    private final QrCodeGeneratorService QrCodeGeneratorService;


    public QrCodeController(QrCodeGeneratorService qrCodeService) {
        this.QrCodeGeneratorService = qrCodeService;
    }

    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGenerateRequest request) {
        try {
            QrCodeGenerateResponse response = this.QrCodeGeneratorService.generateAndUploadQrCode(request.text());
            return ResponseEntity.ok(response);
        }
        catch (Exception e ) {
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
