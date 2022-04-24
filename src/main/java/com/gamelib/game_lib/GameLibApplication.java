package com.gamelib.game_lib;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@Theme(value = "flowcrmtutorial")
@PWA(name = "Game Library App", shortName = "Game Library", offlineResources = {"./images/offline.png"})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class GameLibApplication extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(GameLibApplication.class, args);
    }

}
