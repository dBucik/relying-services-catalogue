package cz.muni.ics.serviceslist.web.configuration;

import cz.muni.ics.serviceslist.ApplicationProperties;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebMvc
@Slf4j
public class WebConfiguration implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/",
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/public/"
    };
    private static final String MESSAGES_FILE = "messages";
    public static final String PARAM_LOCALE = "locale";
    private final ApplicationProperties applicationProperties;

    @Autowired
    public WebConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("/webjars/").resourceChain(false);
        if (StringUtils.hasText(applicationProperties.getStaticResourcesDirectory())) {
            registry.addResourceHandler("/local/**")
                .addResourceLocations("file:" + applicationProperties.getStaticResourcesDirectory());
        }
        registry.addResourceHandler("/**")
            .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseTrailingSlashMatch(true);
    }

    @Bean
    @Primary
    @Autowired
    public MessageSource messageSource(
        @Qualifier("defaultMessageSource") MessageSource defaultMessageSource
    ) {
        if (!StringUtils.hasText(applicationProperties.getLocalizationFilesDirectory())) {
            log.info("No path to filesystem i18n files provided, using only default messages configured in source...");
            return defaultMessageSource;
        } else {
            ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
            String path = "file:" + applicationProperties.getLocalizationFilesDirectory();
            if (!path.endsWith("/")) {
                path += '/';
            }
            path += MESSAGES_FILE;
            messageSource.setBasename(path);

            messageSource.setDefaultLocale(new Locale(applicationProperties.getDefaultLocale()));
            messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
            messageSource.setParentMessageSource(defaultMessageSource);
            messageSource.setUseCodeAsDefaultMessage(false);

            log.info("I18n files from path '{}'. Using default files as fallback...", path);
            return messageSource;
        }
    }

    @Bean("defaultMessageSource")
    public MessageSource defaultMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(MESSAGES_FILE);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultLocale(new Locale(applicationProperties.getDefaultLocale()));
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());

        messageSource.setParentMessageSource(null);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        slr.setDefaultLocale(new Locale(applicationProperties.getDefaultLocale()));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName(PARAM_LOCALE);
        return lci;
    }

    @Bean
    @Primary
    @Autowired
    public ITemplateResolver templateResolver(
        @Qualifier("defaultTemplateResolver") SpringResourceTemplateResolver defaultTemplateResolver
    ) {
        if (!StringUtils.hasText(applicationProperties.getTemplateFilesDirectory())) {
            log.info("No path to filesystem template files provided, using only default messages configured in source...");
            return defaultTemplateResolver;
        } else {
            FileTemplateResolver filesystemTemplateResolver = new FileTemplateResolver();
            filesystemTemplateResolver.setPrefix(applicationProperties.getTemplateFilesDirectory());
            filesystemTemplateResolver.setSuffix(".html");
            filesystemTemplateResolver.setTemplateMode(TemplateMode.HTML);
            filesystemTemplateResolver.setCharacterEncoding("UTF-8");
            filesystemTemplateResolver.setOrder(0);
            filesystemTemplateResolver.setCheckExistence(true);

            defaultTemplateResolver.setOrder(1);

            return filesystemTemplateResolver;
        }
    }

}
