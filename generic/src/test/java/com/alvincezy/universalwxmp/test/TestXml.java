package com.alvincezy.universalwxmp.test;

import com.alvincezy.universalwxmp.generic.message.WxMsgWrapper;
import com.alvincezy.universalwxmp.generic.message.resp.*;
import com.alvincezy.universalwxmp.util.common.ClassUtils;
import com.alvincezy.universalwxmp.util.xml.XmlTransformer;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 *
 * @author alvince.zy@gmail.com
 */
public class TestXml {

    @Test
    public void testParse() throws Exception {
        InputStream inputStream = new FileInputStream("E:/JProjects/UniversalWeixinMp/generic/src/test/resources/voicemsg.xml");
        WxMsgWrapper msgWrapper = WxMsgWrapper.parseXml(inputStream);
        System.out.println(msgWrapper.msg);
        inputStream.close();
    }

    @Test
    public void testTransform() throws Exception {
        RespMsg msg = new VideoMsg("from", "to", "_media_id", "_标题", "_描述");
        String xmlStr = XmlTransformer.transform(msg);

        File out = new File(getClass().getResource("/").getPath(), "TestOut.xml");
        BufferedWriter writer = new BufferedWriter(new FileWriter(out));
        writer.write(xmlStr);
        writer.close();
    }

    @Test
    public void testTransformComplex() throws Exception {
        NewsMsg msg = new NewsMsg("from", "to");
        msg.addArticle(new NewsArticle("article", "Article_1", StringUtils.EMPTY, StringUtils.EMPTY));
        msg.addArticle(new NewsArticle("article", "Article_2", StringUtils.EMPTY, StringUtils.EMPTY));
        String xmlStr = XmlTransformer.transform(msg);

        File out = new File(getClass().getResource("/").getPath(), "TestOut.xml");
        BufferedWriter writer = new BufferedWriter(new FileWriter(out));
        writer.write(xmlStr);
        writer.close();
    }
}
