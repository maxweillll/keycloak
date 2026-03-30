package com.vcreators.controller;

import com.vcreators.pojo.Result;
import com.vcreators.pojo.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/public")
    public Result<String> getPublicUser() {
        return Result.success("这是公开访问的用户信息接口，无需登录即可访问");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('admin')")
    public Result<String> adminInfo(@AuthenticationPrincipal Jwt jwt) {
        return Result.success("这是管理员信息接口");
    }

    @GetMapping("/userInfo")
    @PreAuthorize("hasRole('user')")
    public Result<User> userInfo(@AuthenticationPrincipal Jwt jwt) {
        // 从 JWT 中提取 claims
        String username = jwt.getClaimAsString("preferred_username");
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        List<String> roles = (List<String>) realmAccess.get("roles");

        // 封装到 User 对象
        User user = new User();
        user.setUsername(username);
        user.setRoles(roles.stream().collect(Collectors.toSet()));

        return Result.success(user);
    }
}
