package com.heredi.nowait.application.model.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class EmailDTO {
    private String addressee;
    private String affair;
    private String message;
    private String nickName;
    private String attachmentFileName;
    private File fileImage;
}
