package com.tgk.springmvc.handler;

import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

public interface HandlerMapping extends InstantiationAwareBeanPostProcessor {

    Object getHandler(String url);
}
