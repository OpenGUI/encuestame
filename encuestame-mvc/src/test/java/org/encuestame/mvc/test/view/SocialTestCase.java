package org.encuestame.mvc.test.view;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;


public class SocialTestCase extends TestCase{


    public void teeeevfst() throws IOException{
//         HttpTransport transport = GoogleTransport.create();
//            transport.addParser(new JsonCParser());
//            try {
//              // authenticate with ClientLogin
//              ClientLogin authenticator = new ClientLogin();
//              authenticator.authTokenType = "ndev";
//              authenticator.username = "juanpicado19@gmail.com";
//              authenticator.password = "panasonic23.";
//              authenticator.authenticate().setAuthorizationHeader(transport);
//              // make query request
//              HttpRequest request = transport.buildGetRequest();
//              request.setUrl("https://www.googleapis.com/bigquery/v1/query");
//              request.url.put(
//                  "q", "select count(*) from [bigquery/samples/shakespeare];");
//            } catch (HttpResponseException e) {
//              System.err.println(e.response.parseAsString());
//              throw e;
//            }
    }
    @Test
    public void test2() throws IOException{
         // authorize using OAuth
//        HttpTransport transport = GoogleTransport.create();
//        transport.addParser(new JsonCParser());
//        OAuthParameters parameters = new OAuthParameters();
//        parameters.consumerKey = "287300901667.apps.googleusercontent.com";
//        parameters.token = "oIJZgke5nPDSEEeNOy0lBrxv";
//        OAuthHmacSigner signer = new OAuthHmacSigner();
//        signer.clientSharedSecret = "1/ncHgRP2vuLdrQTgxDhGxX_VK-yFnAl-cvFnOVM6H1h8";
//        signer.tokenSharedSecret = "1/u95Of9IpCB2TU5MRHXf4LnPKA32XeWakRd1q1PONq2g";
//        parameters.signer = signer;
//        parameters.signRequestsUsingAuthorizationHeader(transport);
//        // prepare JSON content
//        BuzzActivity activity = new BuzzActivity();
//        activity.object = new BuzzObject();
//        activity.object.content = "Posted via google-api-java-client";
//        //JsonCContent content = new JsonCContent();
//        //content.data = activity;
//        // post Buzz activity
//        HttpRequest request = transport.buildPostRequest();
//        request.setUrl("https://www.googleapis.com/buzz/v1/activities/@me/@self");
//        //request.content = content;
//        try {
//          BuzzActivity postedActivity =
//              request.execute().parseAs(BuzzActivity.class);
//        } catch (HttpResponseException e) {
//          //System.err.println(e.getMessage());
//          //System.err.println(e.response.parseAsString());
//        }

    }
}
