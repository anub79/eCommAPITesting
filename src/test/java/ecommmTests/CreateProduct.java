package ecommmTests;

import POJO.Login;
import POJO.LoginResponse;
import POJO.Order;
import POJO.OrderDetails;
import io.restassured.builder.RequestSpecBuilder;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.*;
import org.testng.annotations.Test;
import io.restassured.RestAssured;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class CreateProduct {
    String token;
    String UserID;
    String ProductId;
    @Test
    public void createProd() {
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
        Login login=new Login();
        login.setUserEmail("rahulshetty@gmail.com");
        login.setUserPassword("Iamking@000");

        RequestSpecification reqLogin=given().log().all().spec(req).body(login);
       // LoginResponse loginResponse=reqLogin.when().post("api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
      // System.out.println("Token is:- "+loginResponse.getToken());
      //  System.out.println("UserId is:- "+loginResponse.getUserId());
        Response response=reqLogin.when().post("api/ecom/auth/login").then().log().all().extract().response();
        //String resp=response.getBody().asString();
        //JsonPath jsonPath=new JsonPath(resp);
        //String token= jsonPath.getString("token");
       //System.out.println(token);
        //token= response.jsonPath().getString("token");
        token=JsonPath.from(response.getBody().asString()).get("token");
        UserID=JsonPath.from(response.getBody().asString()).getString("userId");
        System.out.println(token);
        System.out.println(UserID);

       //Add Product-file upload as attachement
       Map<String,String> formParams=new HashMap<>();
        formParams.put("productName","qwertyoo");
        formParams.put("productAddedBy",UserID);formParams.put("productCategory","fashion");
        formParams.put("productSubCategory","shirts");
        formParams.put("productPrice","115002");formParams.put("productDescription","Addias Originacls");formParams.put("productFor","women");

        RequestSpecification addProductBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization",token).build();
        RequestSpecification addProductreq=given().log().all().spec(addProductBaseReq).params(formParams)
                .multiPart("spaceimg.jpg",new File("C:\\Users\\Dell\\Pictures\\Saved Pictures\\spaceimg.jpg"));
        Response addProductresponse=addProductreq.when().post("/api/ecom/product/add-product").then().log().all().extract().response();
        System.out.println("addProductresponse:==="+addProductresponse.getBody().asString());
         ProductId=addProductresponse.getBody().asString();

        //Add Order
        Order order=new Order();

        OrderDetails ordDetails=new OrderDetails();
        ordDetails.setCountry("India");
        ordDetails.setProductOrderId(ProductId);
        List<OrderDetails> orderDetailList=new ArrayList<>();
        orderDetailList.add(ordDetails);
        order.setOrders(orderDetailList);
        RequestSpecification addOrderSpecReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization",token)
                .setContentType(ContentType.JSON).build();
        RequestSpecification addProductReq=given().spec(addOrderSpecReq).body(order);
        String responseAddProduct=addProductReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
        System.out.println(responseAddProduct);

    }
}