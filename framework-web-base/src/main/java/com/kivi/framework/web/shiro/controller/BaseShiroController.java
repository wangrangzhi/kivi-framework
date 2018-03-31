/**
 * 
 */
package com.kivi.framework.web.shiro.controller;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.kivi.framework.constant.GlobalErrorConst;
import com.kivi.framework.exception.AppException;
import com.kivi.framework.vo.web.LoginVO;
import com.kivi.framework.web.controller.BaseController;
import com.kivi.framework.web.shiro.token.StatelessToken;
import com.kivi.framework.web.shiro.token.manager.TokenManager;
import com.kivi.framework.web.shiro.util.ShiroKit;
import com.kivi.framework.web.shiro.vo.ShiroUserVO;

/**
 * 集成shiro相关操作的controller基类
 * 
 * @author Eric
 *
 */
public class BaseShiroController extends BaseController {

    @Autowired
    protected TokenManager tokenManager;

    protected ShiroUserVO shiroLogin( LoginVO vo ) {
        ShiroUserVO shiroUser = null;

        try {
            Subject currentUser = ShiroKit.getSubject();
            StatelessToken token = tokenManager.createToken(vo.getPrincipal(), vo.getPassword());
            token.setRememberMe(true);

            currentUser.login(token);

            shiroUser = (ShiroUserVO) currentUser.getPrincipal();
        }
        catch (AuthenticationException e) {
            throw new AppException(GlobalErrorConst.E_USER_NOT_AUTH, e);
        }
        catch (Exception e) {
            throw new AppException(GlobalErrorConst.E_UNDEFINED, e);
        }

        return shiroUser;
    }

    protected ShiroUserVO shiroLogout( String authToken ) {
        ShiroUserVO shiroUser = null;

        try {
            StatelessToken token = tokenManager.getToken(authToken);
            String userName = token.getUsername();
            if (token != null) {
                tokenManager.deleteToken(userName);
            }

            Subject currentUser = ShiroKit.getSubject();
            currentUser.logout();
        }
        catch (AuthenticationException e) {
            throw new AppException(GlobalErrorConst.E_USER_NOT_AUTH, e);
        }
        catch (Exception e) {
            throw new AppException(GlobalErrorConst.E_UNDEFINED, e);
        }

        return shiroUser;
    }
}
