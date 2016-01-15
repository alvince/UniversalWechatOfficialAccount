package com.alvincezy.universalwxmp.web;

import com.alvincezy.universalwxmp.generic.utils.SecurityHelper;
import com.alvincezy.universalwxmp.web.controller.CoreController;
import com.alvincezy.universalwxmp.web.util.ReqHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class ServiceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = "";
        boolean validate = SecurityHelper.validate(ReqHelper.getParam(req, "signature"),
                token, ReqHelper.getParam(req, "timestamp"), ReqHelper.getParam(req, "nonce"));
        if (validate) {

        } else {

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CoreController controller = new CoreController();
        controller.handleWxMessage(req, resp);
    }
}
