package com.company.hoanganh_thuctap_project.view.login;

import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import io.jmix.core.CoreProperties;
import io.jmix.core.MessageTools;
import io.jmix.flowui.component.loginform.JmixLoginForm;
import io.jmix.flowui.kit.component.ComponentUtils;
import io.jmix.flowui.view.*;
import io.jmix.securityflowui.authentication.AuthDetails;
import io.jmix.securityflowui.authentication.LoginViewSupport;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

@Route(value = "login") // Ensure the route is correctly defined
@ViewController(id = "LoginView")
@ViewDescriptor(path = "login-view.xml") // Pointing to the correct XML view descriptor
public class LoginView extends StandardView {

    private static final Logger log = LoggerFactory.getLogger(LoginView.class);

    @Autowired
    private CoreProperties coreProperties;

    @Autowired
    private LoginViewSupport loginViewSupport;

    @Autowired
    private MessageTools messageTools;

    @ViewComponent
    private JmixLoginForm login;

    @ViewComponent
    private MessageBundle messageBundle;

    @Value("${ui.login.defaultUsername:}")
    private String defaultUsername;

    @Value("${ui.login.defaultPassword:}")
    private String defaultPassword;

    @Subscribe
    public void onInit(final InitEvent event) {
        initLocales();
        initDefaultCredentials();
    }

    private void initLocales() {
        LinkedHashMap<Locale, String> locales = coreProperties.getAvailableLocales().stream()
                .collect(Collectors.toMap(Function.identity(), messageTools::getLocaleDisplayName, (s1, s2) -> s1,
                        LinkedHashMap::new));

        ComponentUtils.setItemsMap(login, locales);

        login.setSelectedLocale(VaadinSession.getCurrent().getLocale());
    }

    private void initDefaultCredentials() {
        if (StringUtils.isNotBlank(defaultUsername)) {
            login.setUsername(defaultUsername);
        }

        if (StringUtils.isNotBlank(defaultPassword)) {
            login.setPassword(defaultPassword);
        }
    }

    @Subscribe("login")
    public void onLogin(final LoginEvent event) {
        try {
            loginViewSupport.authenticate(
                    AuthDetails.of(event.getUsername(), event.getPassword())
                            .withLocale(login.getSelectedLocale())
                            .withRememberMe(login.isRememberMe())
            );
        } catch (final BadCredentialsException | DisabledException | LockedException e) {
            log.warn("Login failed for user '{}': {}", event.getUsername(), e.toString());
            event.getSource().setError(true);
        }
    }

}
