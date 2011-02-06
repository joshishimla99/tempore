package com.gs.spring4.gwt.ex.client.annotations;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

import com.gs.spring4.gwt.ex.client.GreetingService;

@Aspect
public class Introductions {
@SuppressWarnings("unused")
@DeclareParents("GreetingServiceImpl")
private GreetingService greetingService;
}