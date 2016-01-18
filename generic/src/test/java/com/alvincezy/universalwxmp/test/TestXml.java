package com.alvincezy.universalwxmp.test;

import com.alvincezy.universalwxmp.generic.message.WxMsgWrapper;
import com.alvincezy.universalwxmp.generic.message.resp.NewsArticle;
import com.alvincezy.universalwxmp.generic.message.resp.NewsMsg;
import com.alvincezy.universalwxmp.generic.message.resp.RespMsg;
import com.alvincezy.universalwxmp.generic.message.resp.VideoMsg;
import org.junit.Test;

import java.io.*;

/**
 * Created by Administrator on 2016/1/13.
 *
 * @author alvince.zy@gmail.com
 */
public class TestXml {

    @Test
    public void testParseMsg() throws Exception {
        InputStream inputStream = new FileInputStream(getClass().getResource("/msg_exam/voicemsg.xml").getPath());
        WxMsgWrapper msgWrapper = WxMsgWrapper.parseXml(inputStream);
        System.out.println(msgWrapper.msg);
        inputStream.close();
    }

    @Test
    public void testParseEvent() throws Exception {
        InputStream inputStream = new FileInputStream(getClass().getResource("/msg_exam/event/action.xml").getPath());
        WxMsgWrapper msgWrapper = WxMsgWrapper.parseXml(inputStream);
        System.out.println(msgWrapper.msg);
        inputStream.close();
    }

    @Test
    public void testTransform() throws Exception {
        RespMsg msg = new VideoMsg("from", "to", "_media_id", "_标题", "_描述");
        WxMsgWrapper wrapper = new WxMsgWrapper(msg);
        String xmlStr = wrapper.transform2Xml();
        writeToFile(new File(getClass().getResource("/TestOut.xml").getPath()), xmlStr);
    }

    @Test
    public void testTransformComplex() throws Exception {
        NewsMsg msg = new NewsMsg("from", "to");
        msg.addArticle(new NewsArticle("article", "Article_1", "PicUrl1", "Url1"));
        msg.addArticle(new NewsArticle("article", "Article_2", "PicUrl2", "Url2"));
        WxMsgWrapper wrapper = new WxMsgWrapper(msg);
        String xmlStr = wrapper.transform2Xml();
        writeToFile(new File(getClass().getResource("/TestOut.xml").getPath()), xmlStr);
    }

    /*
     * Write xml content to file for test.
     */
    private void writeToFile(File output, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));
        writer.write(content);
        writer.close();
    }
}
