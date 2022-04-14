package org.koix.zephyr.system.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ui")
public class ListController {

    /**
     * 会员列表页面
     */
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("ui/list");
        return modelAndView;
    }
    
    @GetMapping("/info")
    @ResponseBody
    public Principal info(Principal principal) {
        System.out.println(principal);
        return principal;
    }

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/me")
    @ResponseBody
    public Authentication me(Authentication authentication) {
        OAuth2AuthenticationDetails details=(OAuth2AuthenticationDetails)authentication.getDetails();
        String jwtstr=details.getTokenValue();
        System.out.println(jwtstr);
        
        OAuth2Authentication oauth=(OAuth2Authentication)authentication;
        System.out.println(oauth.getUserAuthentication().getDetails());
        

        return authentication;
    }
}
