package com.legacy.commerce.order.controller;

import org.springframework.web.servlet.ModelAndView;

public class AbstractBaseController {
    protected ModelAndView getModelAndView(String viewName) {
        return new ModelAndView(viewName);
    }
}
