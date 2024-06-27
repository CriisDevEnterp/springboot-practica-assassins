package com.cristiand.practica.springboot.app.springboot_practica_assassins.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración principal de la aplicación Spring MVC.
 */
@Configuration // Indica que esta clase proporciona configuración de beans para la aplicación.
@EnableWebMvc // Habilita la configuración de Spring MVC en la aplicación.
@ComponentScan(basePackageClasses = AppConfig.class) // Escanea y registra los componentes dentro del paquete donde se
                                                     // encuentra AppConfig.
public class AppConfig implements WebMvcConfigurer {

    /**
     * Configura y devuelve un bean de RestTemplate para realizar peticiones HTTP.
     *
     * @return RestTemplate configurado para peticiones HTTP.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Configura el manejo de recursos estáticos en la aplicación.
     *
     * @param registry el registro de manejadores de recursos estáticos.
     */
    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // Configuración para manejar recursos estáticos en la carpeta 'uploads'.
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/")
                .setCachePeriod(0);

        // Configuración para manejar favicon.ico en la raíz del contexto.
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/favicon.ico")
                .setCachePeriod(0);

        // Configuración para manejar recursos estáticos en la carpeta 'static'.
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
    }

    /**
     * Configura y devuelve un bean de LocalValidatorFactoryBean para la validación
     * de datos.
     *
     * @return el bean de validación LocalValidatorFactoryBean.
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

}
