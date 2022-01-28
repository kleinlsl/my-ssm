package com.tujia.myssm.web.backdoor;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import com.tujia.myssm.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2022/01/25 16:46
 */
@Slf4j
public class HttpTest {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Test
    public void reqReceiveWelfare() {
        String body = "{\n" + "    \"sceneId\": \"CM0001\",\n" + "    \"platformParty\": 0,\n" +
                "    \"wrapperId\": \"waptujia004\"\n" + "}";

        try {
            for (int i = 0; i < 10; i++) {
                ContentType contentType = ContentType.APPLICATION_JSON;
                HttpPost httpPost = new HttpPost("http://up301.beta.tujia.com/activity/h5/welfare/receiveWelfare");
                HttpEntity entity = new StringEntity(body, contentType);
                httpPost.setEntity(entity);
                httpPost.addHeader("Cookie",
                                   "_fas_uuid=b13cb78c-ae93-47bb-9369-2fb8db737d0e-1624012586703; Hm_lvt_405c96e7f6bed44fb846abfe1f65c6f5=1624359354; gr_user_id=c684f016-5d86-4f5d-bad5-df2d4f9c7c71; _ga=GA1.2.587831369.1624359355; _RSG=z2K_rZ48wIBWHBPQ2DVCK8; _RDG=28dbfa959d01382a9815709c6783837a47; _RGUID=b4ba2048-f5fd-40bd-850c-7cd09a64b460; _RF1=183.84.2.208; fvt.tujia.com_PortalContext_UserToken=060f9f73-d0de-4a29-af7c-f87cad1ca449; fvt.tujia.com_PortalContext_UserId=30996100; dorae_username=songlinl; fvt.tujia.com_sso_user_name=songlinl; fvt.tujia.com_sso_user_real_name=%E5%8D%A2%E6%9D%BE%E6%9E%97; fvt.tujia.com_MWebHttpContext_UserName=%E5%8D%A2%E6%9D%BE%E6%9E%97; dorae_pmoid=TOB-1859; t2_sso_web_url=\"https://nedit2.fvt.tujia.com/\"; fvt.tujia.com_sso_user_token=f674defe-20d0-4d0c-b472-81b1673cfa5d; fvt.tujia.com_sso_user_id=100036; fvt.tujia.com_MWebHttpContext_UserToken=f674defe-20d0-4d0c-b472-81b1673cfa5d; fvt.tujia.com_MWebHttpContext_UserId=100036; meta_token=\"jsession:9dbd7c35875b40c1bfaf9f252adaa328\"; meta_acc=songlinl; _fas_session_id=6ZtJjCPsc9Kms6z1SrJHQrci1YRS1639551438512; tj_channel_id=tujia; clientName=T_h5; tj_abtest=%255B%257B%2522key%2522%253A%2522T_salogin_831%2522%252C%2522value%2522%253A%2522B%2522%257D%252C%257B%2522key%2522%253A%2522E_list_TOC_1218%2522%252C%2522value%2522%253A%2522B%2522%257D%252C%257B%2522key%2522%253A%2522TTJ-9989-H5%2522%252C%2522value%2522%253A%2522C%2522%257D%252C%257B%2522key%2522%253A%2522TTJ-9993-H5-SEARCHRECOMMENDHOUSE%2522%252C%2522value%2522%253A%2522C%2522%257D%252C%257B%2522key%2522%253A%2522TOC-1341-5KM%2522%252C%2522value%2522%253A%2522C%2522%257D%252C%257B%2522key%2522%253A%2522TTJ-9992-footmark%2522%252C%2522value%2522%253A%2522A%2522%257D%255D; fvt.tujia.com_MobileContext_StartDate=2021-12-15; fvt.tujia.com_MobileContext_EndDate=2021-12-16; gr_session_id_1fa38dc3b3e047ffa08b14193945e261=f20e5839-15b1-435d-99f2-8ffb5b731316; gr_cs1_f20e5839-15b1-435d-99f2-8ffb5b731316=user_id%3A30996100; gr_session_id_1fa38dc3b3e047ffa08b14193945e261_f20e5839-15b1-435d-99f2-8ffb5b731316=true; gr_flag=MC41MjgzNjA3NjgxNzU1ODNfMF9jaGlwc19haG95");
                CloseableHttpResponse response = httpClient.execute(httpPost);
                log.info("[TestController.testRedisGet] res：{}", JsonUtils.tryToJson(response.getStatusLine()));
            }
        } catch (Exception e) {
            log.error("e:", e);
        }
    }
}
