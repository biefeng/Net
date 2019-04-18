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
        String value = "{\n" +
                "  \"orderDataMqDto\": {\n" +
                "    \"gatewayTradeNo\": \"4CECEP01163225172569\",\n" +
                "    \"lat\": 30.50517200,\n" +
                "    \"lng\": 114.40256200,\n" +
                "    \"orderCreateDate\": 1555291963061,\n" +
                "    \"orderId\": 1999110600000009915,\n" +
                "    \"orderSource\": 1,\n" +
                "    \"payTime\": 1555058340000,\n" +
                "    \"paymentChannel\": 1,\n" +
                "    \"paymentTypeId\": 1,\n" +
                "    \"paymentWayId\": 1,\n" +
                "    \"platform\": 1,\n" +
                "    \"sendPay\": 1,\n" +
                "    \"storeId\": 99911,\n" +
                "    \"storeName\": \"测试门店\",\n" +
                "    \"userId\": 166,\n" +
                "    \"userName\": \"徐\",\n" +
                "    \"uuid\": \"166_99911_0.3_0.000_1555058329555\",\n" +
                "    \"weight\": 0.000\n" +
                "  },\n" +
                "  \"orderDeliveryMqDto\": {\n" +
                "    \"address\": \"湖北省武汉市洪山区珞瑜路726号 光谷步行街 10号楼\",\n" +
                "    \"city\": \"武汉市\",\n" +
                "    \"district\": \"洪山区\",\n" +
                "    \"mobile\": \"17762386465\",\n" +
                "    \"previousTimeSlot\": 30,\n" +
                "    \"promiseEndTime\": 1555063200000,\n" +
                "    \"promiseStartTime\": 1555061400000,\n" +
                "    \"province\": \"湖北省\",\n" +
                "    \"receiver\": \"徐\",\n" +
                "    \"roadAreaId\": 128,\n" +
                "    \"roadAreaName\": \"路区2\",\n" +
                "    \"shipDate\": 1554998400000,\n" +
                "    \"shipEndTime\": \"18:00\",\n" +
                "    \"shipStartTime\": \"17:30\"\n" +
                "  },\n" +
                "  \"orderId\": 1999110600000009915,\n" +
                "  \"orderInvoiceMqDtoList\": [],\n" +
                "  \"orderItemMoneyPromtMqDtoList\": [\n" +
                "    {\n" +
                "      \"orderItemId\": 1116621685949423618,\n" +
                "      \"orderItemMoneyId\": 1116621685949423617,\n" +
                "      \"promotionDiscount\": 0.02,\n" +
                "      \"promotionName\": \"测试财务用\",\n" +
                "      \"promotionType\": 100,\n" +
                "      \"skuId\": 100005\n" +
                "    }\n" +
                "  ],\n" +
                "  \"orderItemMqDtoList\": [\n" +
                "    {\n" +
                "      \"buyUnit\": 1,\n" +
                "      \"categoryId\": 2360,\n" +
                "      \"dataType\": 1,\n" +
                "      \"fLossRate\": 0.0000,\n" +
                "      \"fStdInTaxRate\": 0.0600,\n" +
                "      \"fTaxControlCode\": \"8755456789876543238\",\n" +
                "      \"fTaxRate\": 0.0600,\n" +
                "      \"fTaxableWay\": 1,\n" +
                "      \"fUnitConversionFactory\": 1.0000,\n" +
                "      \"feature\": \"{\\\"serviceTag\\\": \\\"0\\\", \\\"cateringTag\\\": 1, \\\"foodProcessTime\\\": 0}\",\n" +
                "      \"financeTag\": \"3\",\n" +
                "      \"height\": 0,\n" +
                "      \"isShelfLifeProd\": 1,\n" +
                "      \"length\": 0,\n" +
                "      \"mark\": 1,\n" +
                "      \"orderItemId\": 1116621685945229312,\n" +
                "      \"orderItemMoneyMqDto\": {\n" +
                "        \"balancePay\": 0.00,\n" +
                "        \"couponDiscount\": 0.00,\n" +
                "        \"deliveryFee\": 0.00,\n" +
                "        \"deliveryFeeDiscount\": 0.00,\n" +
                "        \"erasePrice\": 0.00,\n" +
                "        \"giftCardPrice\": 0.00,\n" +
                "        \"itemType\": 1,\n" +
                "        \"oriPrice\": 0.23,\n" +
                "        \"payDiscount\": 0.00,\n" +
                "        \"payPrice\": 0.23,\n" +
                "        \"price\": 0.23,\n" +
                "        \"promotionDiscount\": 0.00,\n" +
                "        \"rePrice\": 0.00,\n" +
                "        \"totalPrice\": 0.23\n" +
                "      },\n" +
                "      \"price\": 0.2300,\n" +
                "      \"prodPlace\": \"北京\",\n" +
                "      \"productName\": \"巧克力慕斯\",\n" +
                "      \"returnPolicy\": 1,\n" +
                "      \"saleAmount\": 1.000,\n" +
                "      \"serviceTag\": 0,\n" +
                "      \"shelfLifeDay\": 2,\n" +
                "      \"skuId\": 100012,\n" +
                "      \"tempControl\": 4,\n" +
                "      \"weight\": 0.000,\n" +
                "      \"width\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"buyUnit\": 1,\n" +
                "      \"categoryId\": 2368,\n" +
                "      \"dataType\": 1,\n" +
                "      \"fLossRate\": 0.0000,\n" +
                "      \"fStdInTaxRate\": 0.0000,\n" +
                "      \"fTaxControlCode\": \"2423213213213213243\",\n" +
                "      \"fTaxRate\": 0.0000,\n" +
                "      \"fTaxableWay\": 1,\n" +
                "      \"fUnitConversionFactory\": 1.0000,\n" +
                "      \"feature\": \"{\\\"serviceTag\\\": \\\"0\\\", \\\"cateringTag\\\": 1, \\\"foodProcessTime\\\": 0}\",\n" +
                "      \"financeTag\": \"1\",\n" +
                "      \"height\": 0,\n" +
                "      \"isShelfLifeProd\": 1,\n" +
                "      \"length\": 0,\n" +
                "      \"mark\": 1,\n" +
                "      \"orderItemId\": 1116621685949423616,\n" +
                "      \"orderItemMoneyMqDto\": {\n" +
                "        \"balancePay\": 0.00,\n" +
                "        \"couponDiscount\": 0.00,\n" +
                "        \"deliveryFee\": 0.00,\n" +
                "        \"deliveryFeeDiscount\": 0.00,\n" +
                "        \"erasePrice\": 0.00,\n" +
                "        \"giftCardPrice\": 0.00,\n" +
                "        \"itemType\": 1,\n" +
                "        \"oriPrice\": 0.01,\n" +
                "        \"payDiscount\": 0.00,\n" +
                "        \"payPrice\": 0.01,\n" +
                "        \"price\": 0.01,\n" +
                "        \"promotionDiscount\": 0.00,\n" +
                "        \"rePrice\": 0.00,\n" +
                "        \"totalPrice\": 0.01\n" +
                "      },\n" +
                "      \"price\": 0.0100,\n" +
                "      \"prodPlace\": \"湖北武汉\",\n" +
                "      \"productName\": \"雀巢咖啡\",\n" +
                "      \"returnPolicy\": 1,\n" +
                "      \"saleAmount\": 1.000,\n" +
                "      \"serviceTag\": 0,\n" +
                "      \"shelfLifeDay\": 1,\n" +
                "      \"skuId\": 100064,\n" +
                "      \"tempControl\": 1,\n" +
                "      \"weight\": 0.000,\n" +
                "      \"width\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"buyUnit\": 1,\n" +
                "      \"categoryId\": 2547,\n" +
                "      \"dataType\": 1,\n" +
                "      \"fLossRate\": 1.0000,\n" +
                "      \"fStdInTaxRate\": 0.0000,\n" +
                "      \"fTaxControlCode\": \"8765456789876543237\",\n" +
                "      \"fTaxRate\": 0.0600,\n" +
                "      \"fTaxableWay\": 1,\n" +
                "      \"fUnitConversionFactory\": 1.0000,\n" +
                "      \"feature\": \"{\\\"serviceTag\\\": \\\"0\\\"}\",\n" +
                "      \"financeTag\": \"1\",\n" +
                "      \"height\": 5,\n" +
                "      \"isShelfLifeProd\": 0,\n" +
                "      \"length\": 5,\n" +
                "      \"mark\": 0,\n" +
                "      \"orderItemId\": 1116621685949423618,\n" +
                "      \"orderItemMoneyMqDto\": {\n" +
                "        \"balancePay\": 0.00,\n" +
                "        \"couponDiscount\": 0.00,\n" +
                "        \"deliveryFee\": 0.00,\n" +
                "        \"deliveryFeeDiscount\": 0.00,\n" +
                "        \"erasePrice\": 0.00,\n" +
                "        \"giftCardPrice\": 0.00,\n" +
                "        \"itemType\": 1,\n" +
                "        \"oriPrice\": 0.02,\n" +
                "        \"payDiscount\": 0.00,\n" +
                "        \"payPrice\": 0.02,\n" +
                "        \"price\": 0.01,\n" +
                "        \"promotionDiscount\": 0.00,\n" +
                "        \"rePrice\": 0.00,\n" +
                "        \"totalPrice\": 0.02\n" +
                "      },\n" +
                "      \"price\": 0.0200,\n" +
                "      \"prodPlace\": \"新加坡\",\n" +
                "      \"productName\": \"吹风筒-gb\",\n" +
                "      \"returnPolicy\": 2,\n" +
                "      \"saleAmount\": 2.000,\n" +
                "      \"serviceTag\": 0,\n" +
                "      \"shelfLifeDay\": 0,\n" +
                "      \"skuId\": 100005,\n" +
                "      \"tempControl\": 1,\n" +
                "      \"width\": 5\n" +
                "    },\n" +
                "    {\n" +
                "      \"buyUnit\": 1,\n" +
                "      \"categoryId\": 2060,\n" +
                "      \"dataType\": 1,\n" +
                "      \"fLossRate\": 0.0000,\n" +
                "      \"fStdInTaxRate\": 0.0600,\n" +
                "      \"fTaxControlCode\": \"9876543456787698767\",\n" +
                "      \"fTaxRate\": 0.0600,\n" +
                "      \"fTaxableWay\": 1,\n" +
                "      \"fUnitConversionFactory\": 1.0000,\n" +
                "      \"feature\": \"{\\\"serviceTag\\\": \\\"0\\\"}\",\n" +
                "      \"financeTag\": \"4\",\n" +
                "      \"height\": 0,\n" +
                "      \"isShelfLifeProd\": 0,\n" +
                "      \"length\": 0,\n" +
                "      \"mark\": 0,\n" +
                "      \"orderItemId\": 1116621685953617920,\n" +
                "      \"orderItemMoneyMqDto\": {\n" +
                "        \"balancePay\": 0.00,\n" +
                "        \"couponDiscount\": 0.00,\n" +
                "        \"deliveryFee\": 0.00,\n" +
                "        \"deliveryFeeDiscount\": 0.00,\n" +
                "        \"erasePrice\": 0.00,\n" +
                "        \"giftCardPrice\": 0.00,\n" +
                "        \"itemType\": 1,\n" +
                "        \"oriPrice\": 0.02,\n" +
                "        \"payDiscount\": 0.00,\n" +
                "        \"payPrice\": 0.04,\n" +
                "        \"price\": 0.02,\n" +
                "        \"promotionDiscount\": 0.00,\n" +
                "        \"rePrice\": 0.00,\n" +
                "        \"totalPrice\": 0.04\n" +
                "      },\n" +
                "      \"price\": 0.0200,\n" +
                "      \"prodPlace\": \"湖北武汉\",\n" +
                "      \"productName\": \"乐事薯片-gb\",\n" +
                "      \"returnPolicy\": 1,\n" +
                "      \"saleAmount\": 2.000,\n" +
                "      \"serviceTag\": 0,\n" +
                "      \"shelfLifeDay\": 1,\n" +
                "      \"skuId\": 100006,\n" +
                "      \"tempControl\": 1,\n" +
                "      \"weight\": 0.000,\n" +
                "      \"width\": 0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"orderMoneyMqDto\": {\n" +
                "    \"balancePay\": 0.00,\n" +
                "    \"couponDiscount\": 0.00,\n" +
                "    \"deliveryFee\": 0.00,\n" +
                "    \"deliveryFeeDiscount\": 0.00,\n" +
                "    \"erasePrice\": 0.00,\n" +
                "    \"giftCardPrice\": 0.00,\n" +
                "    \"oriPrice\": 0.32,\n" +
                "    \"payDiscount\": 0.00,\n" +
                "    \"payPrice\": 0.30,\n" +
                "    \"price\": 0.30,\n" +
                "    \"promotionDiscount\": 0.00,\n" +
                "    \"rePrice\": 0.00\n" +
                "  },\n" +
                "  \"storeId\": 99911,\n" +
                "  \"userId\": 166\n" +
                "}";
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
