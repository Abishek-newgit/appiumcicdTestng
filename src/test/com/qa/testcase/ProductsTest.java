package com.qa.testcase;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.MenuPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class ProductsTest extends BaseTest {

    LoginPage loginPageobj;
    ProductsPage productsPageobj;
    MenuPage menu;
    ProductDetailsPage prdDetails;
    TestUtils utils = new TestUtils();


    // to read json file creat obj for JSONObject clas (its predefined clasS)
    JSONObject jsonObj;

//to read the JSON file

    @BeforeClass
    public void readjson() throws IOException {
        InputStream datais = null;
        try{

            String dataFileName = "./resources/data/loginUsers.json";
            datais = new FileInputStream(dataFileName);
            JSONTokener tokener = new JSONTokener(datais);


            jsonObj= new JSONObject(tokener);
        }catch (Exception e)
        {
            e.printStackTrace();
            throw  e;
        }finally {

            if(datais != null)
            {
                datais.close();
            }

        }



    }


    @BeforeMethod

//Method is a predefined class to print the testcase that is currently executing
    public void beforeMethod(Method m)
    {
        loginPageobj = new LoginPage();

        //to print the current test case execution method name
        System.out.println("\n" + "**** Starting Testcase:" + m.getName() + "****" + "\n");
        /*LogoutApp();
        LaunchApp();*/

        //login to the app before every test case so
        //every test case is a method

        LaunchApp();

        productsPageobj = loginPageobj.properLogin(jsonObj.getJSONObject("validUserName").getString("userName"),
                jsonObj.getJSONObject("validUserName").getString("password"));

    }

    @AfterMethod

    public void afterMethod()
    {
        //after every test case we need to logout so add it under after method
        LogoutApp();
    }

    //valid login and get the product name and price and log out

    @Test
    public void validLogin() {

      //productsPageobj.tapByCoordinates();
       /* LogoutApp();
        LaunchApp();*/

        //we are using this because we are validating product price and product tittle
        SoftAssert sa = new SoftAssert();

        //passingh userName and password as argument to ProperLogin
        // create obj loginPage class and with that you can access the method from that class




        //get the actual prod name
        String actualproductsName = productsPageobj.getProductName();

        String productPrice = productsPageobj.getProductPrice();
        utils.log("Product price is: " + productPrice);

        //String actualProductTitle = productsPageobj.getTitle();
        String expectedProductName= "Sauce Labs Backpack";
        String expectedProductPrice = "$29.99";
        System.out.println("The actual product name is: " + actualproductsName+ "\n" +"expected prd name: " + expectedProductName);
        sa.assertEquals(actualproductsName,expectedProductName);
        sa.assertEquals(productPrice,expectedProductPrice);



       menu =  productsPageobj.clickHamburgerMenu();
         getDriver().navigate().back();
      // loginPageobj = menu.clickLogout();

        sa.assertAll();

        //error validation

    }

    // to navigate to product details and get the name and price

    @Test
    public void getProductDes() {

        //we are using this because we are validating product price and product tittle
        SoftAssert sa = new SoftAssert();

        //passingh userName and password as argument to ProperLogin
        // create obj loginPage class and with that you can access the method from that class




        prdDetails = productsPageobj.clickProduct();

        //get the prod name and descriptiom

        String productsName = prdDetails.getProductName();
        String ProdDesc = prdDetails.getProductDes();

        //String actualProductTitle = productsPageobj.getTitle();
        //expected result
        String expectedProductTitle = "Sauce Labs Backpack";
        String expectedProductDesc = "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.";

       //validate the actial nd the expected reults
        sa.assertEquals(productsName,expectedProductTitle);
        sa.assertEquals(expectedProductDesc,ProdDesc);
        System.out.println("actual desc is: " + ProdDesc +"\n" + "expected desc is: " + expectedProductDesc);

        //scroll and get the product price
         //calling scroll method from product details page and get the price

        //use Android scroll
      if(getPlatform().equalsIgnoreCase("Android")){
          String actualprdPrice = prdDetails.scrollToProductPriceAndGetPrice();
          String expectedPrdPrice = "$29.99";

          sa.assertEquals(actualprdPrice,expectedPrdPrice);
          System.out.println("actual prdPrice is: " + actualprdPrice + "\n" + "expected PrdPrice is: " + expectedPrdPrice);

      }

      if(getPlatform().equalsIgnoreCase("iOS"))
      {


         String actualprdPrice = prdDetails.scrolliOS();
         String expectedPrdPrice = "ADD TO CART";

          sa.assertEquals(actualprdPrice,expectedPrdPrice);
          System.out.println("actual prdPrice is: " + actualprdPrice + "\n" + "expected PrdPrice is: " + expectedPrdPrice);

          sa.assertTrue(prdDetails.isAddToCartDisaplyed());
      }






        productsPageobj = prdDetails.goBackToProductPage();

        //menu =  productsPageobj.clickHamburgerMenu();
      //  loginPageobj = menu.clickLogout();

        sa.assertAll();

        //error validation

    }




}




