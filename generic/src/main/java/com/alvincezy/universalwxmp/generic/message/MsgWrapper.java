package com.alvincezy.universalwxmp.generic.message;

import com.alvincezy.universalwxmp.util.xml.TransformException;
import com.alvincezy.universalwxmp.util.xml.XmlTransformer;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerConfigurationException;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class MsgWrapper {

    /**
     * Type of WeChat-mp message received.
     */
    public enum Type {
        EVENT, IMAGE, LINK, LOCATION, TEXT, SHORT_VIDEO, VIDEO, VOICE
    }

    public WXMsg msg;
    public Type type;

    public MsgWrapper() {
    }

    public MsgWrapper(WXMsg msg) {
        this.msg = msg;
    }

    /**
     * Transform message to xml-text.
     * <br/>
     * Will throw {@link IllegalStateException} when the msg is not set.
     *
     * @return xml text
     * @throws TransformException when transform exception happened.
     */
    public String transform2Xml() throws TransformException {
        if (msg == null) {
            throw new IllegalStateException("No message to transform: no msg set.");
        }

        try {
            return XmlTransformer.transform(msg);
        } catch (IllegalAccessException e) {
            throw new TransformException(e.getMessage(), e);
        } catch (TransformerConfigurationException e) {
            throw new TransformException(e.getMessage(), e);
        } catch (SAXException e) {
            throw new TransformException(e.getMessage(), e);
        }
    }
}
