package com.indas.portal.configure;

//@EnableWebMvc
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//
//
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/jsp/");
//        resolver.setSuffix(".jsp");
//        resolver.setViewClass(JstlView.class);
//        registry.viewResolver(resolver);
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
//    }
//}

//@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages = {"com.indas.portal"})
//public class WebConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//
//        var templateResolver = new SpringResourceTemplateResolver();
//
//        templateResolver.setApplicationContext(applicationContext);
//        templateResolver.setPrefix("classpath:/templates/");
//        templateResolver.setSuffix(".html");
//
//        return templateResolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//
//        var templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        templateEngine.setEnableSpringELCompiler(true);
//
//        return templateEngine;
//    }
//
//    @Bean
//    public ViewResolver viewResolver() {
//
//        var resolver = new ThymeleafViewResolver();
//        var registry = new ViewResolverRegistry(null, applicationContext);
//
//        resolver.setTemplateEngine(templateEngine());
//        registry.viewResolver(resolver);
//
//        return resolver;
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
//        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
//
////        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
////        registry.addResourceHandler("/vendor/**").addResourceLocations("classpath:/static/vendor/");
////        registry.addResourceHandler("/favicons/**").addResourceLocations("classpath:/static/favicons/");
//    }
//}