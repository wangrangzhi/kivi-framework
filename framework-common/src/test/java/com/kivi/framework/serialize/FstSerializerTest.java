package com.kivi.framework.serialize;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kivi.framework.serialize.FstSerializer;
import com.kivi.framework.util.kit.HexKit;

public class FstSerializerTest {

    @Test
    public void testByte() {
        TestBean bean = new TestBean();

        // byte[] bytes = FstSerializer.serialize(bean);

        String data = "0003FC0A32303030303037323137FC1430303732434E5930303030303130303036323337FC03434E59FC0C434633303030303030313030FFFC0142FC03303037FC06FFD84EFF3E6BFF268DFF37625F38FC023130FC0132FD00FC04FFD84EFF3E6B5F38FC095041594D454E545F38FC014EFDFD00FB00025B490000000000000000F971F971FFFFFD00FFF971F971FFFFFFFC0143FC0130FFFC0159FFFF00";

        Object bean1 = FstSerializer.deserialize(HexKit.hex2Byte(data));

        assertEquals(bean.getCode(), bean.getCode());
    }

    @Test
    public void testJSON() {
        try {
            TestBean bean = new TestBean();

            String fastJson = JSON.toJSONString(bean, SerializerFeature.WriteClassName);

            System.out.println(fastJson);

            String json = new String(FstSerializer.serializeJson(bean));

            System.out.println(json);

            TestBean bean1 = FstSerializer.deserializeJson(json.getBytes("UTF-8"), TestBean.class);

            assertEquals(JSON.toJSONString(bean), FstSerializer.serializeJson(bean));
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
