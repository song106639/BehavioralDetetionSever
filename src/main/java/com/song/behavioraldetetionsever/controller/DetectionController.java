package com.song.behavioraldetetionsever.controller;

import com.song.behavioraldetetionsever.vo.R;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
@RestController()
@Api(value = "物体识别接口，选择文件上传返回识别接口")
@RequestMapping("/detection")
public class DetectionController {
    @PostMapping("/upload")
    @ApiOperation("上传文件，返回识别结果")
    public R<String> getDetection(MultipartFile file) throws IOException, URISyntaxException {
        String apiUrl = "http://localhost:8081/upload"; // 替换成实际的接口URL

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        RequestEntity<MultiValueMap<String, Object>> requestEntity = new RequestEntity<>(body, headers, HttpMethod.POST, new URI(apiUrl));

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        return new R<String>(200,true,response.getBody());
    }
}
