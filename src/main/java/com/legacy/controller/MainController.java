package com.legacy.controller;


import com.legacy.controller.logging.OrderLogging;
import com.legacy.controller.service.BuyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping({"/checkout", "/m/checkout"})
public class MainController extends BaseUIController {
    private final static Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private BuyService service;

    @OrderLogging
    @RequestMapping(value = {"/", ""})
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView modelAndView = getModelAndView(request.getRequestURI()+"/main");
        System.out.println("main");
        LOGGER.error("yjk");
        service.getTest();
        return modelAndView;
    }

    @OrderLogging
    protected ModelAndView getModelAndView(String viewName) {
        LOGGER.error("viewName ==> {}", viewName);

        if ( isMobile(viewName)) {
            viewName = convertToPcUrl(viewName);
        }
        ModelAndView modelAndView = super.getModelAndView(viewName);
        modelAndView.addObject("_viewName", viewName);

        initMetaInfo(modelAndView);

        return modelAndView;
    }

    @OrderLogging
    private boolean isMobile(String viewName) {
        return viewName.startsWith("/m");
    }

    @OrderLogging
    private String convertToPcUrl(String viewName) {
        return viewName = "/p"+viewName;
    }

    @OrderLogging
    private void initMetaInfo(ModelAndView modelAndView) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        try {
            String encode = URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");

//            modelAndView.addObject("CURRENT_URL", request.getRequestURL());
//            modelAndView.addObject("LOGIN_URL", urlManager.getUrl("loginPage") + encode);
//            modelAndView.addObject("LOGIN_URL_M", urlManager.getUrl("loginPage_m") + encode);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("#initMetaInfo error=> modelAndView = [{}]", modelAndView, e);
        }

    }

}
