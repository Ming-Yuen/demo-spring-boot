package com.demo.common.bean;

import com.demo.common.controller.bo.DefaultResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class CommonBean {

    @Bean
    @Scope("prototype")
    private DefaultResponse getDefaultResponse(){
        return new DefaultResponse();
    }
}
