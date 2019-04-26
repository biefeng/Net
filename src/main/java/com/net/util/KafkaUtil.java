package com.net.util;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class KafkaUtil {

    public static void main(String[] args) {
        String value = "{\"orderDataMqDto\":{\"gatewayTradeNo\":\"4200000267201904150167456318\",\"lat\":30.48088100,\"lng\":114.40234400,\"orderCreateDate\":1555294408000,\"orderId\":1999000100000181512,\"orderSource\":1,\"payTime\":1555294411000,\"paymentChannel\":2,\"paymentTypeId\":1,\"paymentWayId\":1,\"platform\":1,\"sendPay\":1,\"storeId\":99900,\"storeName\":\"S11-TEST\",\"userId\":169,\"userName\":\"Bag\",\"uuid\":\"169_99900_0.01_0.350_1555294406957\",\"weight\":0.350},\"orderDeliveryMqDto\":{\"address\":\"湖北省武汉市洪山区 光谷APP广场 1\",\"city\":\"武汉市\",\"district\":\"洪山区\",\"mobile\":\"13297036046\",\"previousTimeSlot\":30,\"promiseEndTime\":1555299000000,\"promiseStartTime\":1555297200000,\"province\":\"湖北省\",\"receiver\":\"1\",\"roadAreaId\":84,\"roadAreaName\":\"路区B\",\"shipDate\":1555257600000,\"shipEndTime\":\"11:30\",\"shipStartTime\":\"11:00\"},\"orderId\":1999000100000181512,\"orderInvoiceMqDtoList\":[],\"orderItemMoneyPromtMqDtoList\":[],\"orderItemMqDtoList\":[{\"buyUnit\":8,\"categoryId\":688,\"dataType\":1,\"fStdInTaxRate\":0.1300,\"fTaxControlCode\":\"3070401000000000000\",\"fTaxRate\":0.0600,\"fTaxableWay\":2,\"fUnitConversionFactory\":1.0000,\"feature\":\"{\\\"serviceTag\\\": \\\"0\\\", \\\"cateringTag\\\": 2, \\\"foodProcessTime\\\": 2}\",\"financeTag\":\"4,3\",\"height\":101,\"ingredientListId\":92,\"isShelfLifeProd\":1,\"length\":90,\"mark\":1,\"orderItemId\":1117611865171431424,\"orderItemMoneyMqDto\":{\"balancePay\":0.00,\"couponDiscount\":0.00,\"deliveryFee\":0.00,\"deliveryFeeDiscount\":0.00,\"erasePrice\":0.00,\"giftCardPrice\":0.00,\"itemType\":1,\"oriPrice\":0.01,\"payDiscount\":0.00,\"payPrice\":0.01,\"price\":0.01,\"promotionDiscount\":0.00,\"rePrice\":0.00,\"totalPrice\":0.01},\"price\":0.0100,\"prodPlace\":\"北京\",\"productName\":\"香草拿铁\",\"returnPolicy\":1,\"saleAmount\":1.000,\"serviceTag\":0,\"shelfLifeDay\":1,\"skuId\":101458,\"tempControl\":1,\"weight\":0.350,\"width\":58}],\"orderMoneyMqDto\":{\"balancePay\":0.00,\"couponDiscount\":0.00,\"deliveryFee\":0.00,\"deliveryFeeDiscount\":0.00,\"erasePrice\":0.00,\"giftCardPrice\":0.00,\"oriPrice\":0.01,\"payDiscount\":0.00,\"payPrice\":0.01,\"price\":0.01,\"promotionDiscount\":0.00,\"rePrice\":0.00},\"storeId\":99900,\"userId\":169}";
        String jsonStr = value.trim().replaceAll("[\\r\\n\\t\\s]+", " ");

        boolean sendMessage = sendMessage("oms_order_transfer", "2100051100000113465", jsonStr);
        //boolean sendMessage = sendMessage("pmc_prod_add_store_sku_topic", "store_sku_01", jsonStr);
        if (sendMessage) {
            System.out.println("消息发送成功");
        } else {
            System.out.println("消息发送失败");
        }
    }

    public static boolean sendMessage(String topic, String key, String value) {
        boolean flag = false;
        try {
            InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("kafka.properties");
            Properties props = new Properties();
            if (resourceAsStream != null) {
                props.load(resourceAsStream);
            }
            KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
            producer.send(new ProducerRecord<>(topic, key, value));
            producer.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
