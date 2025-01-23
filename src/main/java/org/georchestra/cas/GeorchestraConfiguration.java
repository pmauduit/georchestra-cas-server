package org.georchestra.cas;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("georchestraConfiguration")
@EnableConfigurationProperties(GeorchestraConfiguration.class)
@ConfigurationProperties("georchestra.cas.config")
@PropertySource(value= { "file://${georchestra.datadir}/default.properties",
                         "file://${georchestra.datadir}/cas/cas.properties"},
        ignoreResourceNotFound=true)
@Data
public class GeorchestraConfiguration {

    @Value( "${useLegacyHeader:false}" )
    boolean useLegacyHeader = false;

    @Value( "${headerConfigFile:}" )
    String headerConfigFile;

    @Value( "${headerUrl:/header/}" )
    String headerUrl;

    @Value("${headerHeight:80}")
    String headerHeight;

    @Value("${headerScript:https://cdn.jsdelivr.net/gh/georchestra/header@dist/header.js}")
    String headerScript;

    @Value("${logoUrl:https://www.georchestra.org/public/georchestra-logo.svg}")
    String logoUrl;

    @Value("${georchestraStylesheet:}")
    String georchestraStylesheet;

}
