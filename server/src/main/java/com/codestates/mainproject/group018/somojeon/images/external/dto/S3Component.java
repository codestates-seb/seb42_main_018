package com.codestates.mainproject.group018.somojeon.images.external.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class S3Component {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

}
