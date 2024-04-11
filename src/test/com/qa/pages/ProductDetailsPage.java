package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductDetailsPage extends BaseTest  {



    //create constructor to initalize pagefactory
    public ProductDetailsPage(){

        PageFactory.initElements(new AppiumFieldDecorator(getDriver()),this  ); // passing this(LoginPage) class

    }



    //ProductNames

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
    @iOSXCUITFindBy(accessibility = "Sauce Labs Backpack")
    private WebElement productName;

    //product description
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[2]")

    private WebElement productDescription;

    // BAck to products button

    @AndroidFindBy(accessibility = "test-BACK TO PRODUCTS")
    @iOSXCUITFindBy(accessibility = "test-BACK TO PRODUCTS")

    private WebElement backTOProductBttn;


    //product Price
    // not required as its hardcoded in scrollIntoElement Method

    @AndroidFindBy (accessibility = "test-Price")
    private WebElement prdPrice;

    //add to cart

    @iOSXCUITFindBy(accessibility = "test-ADD TO CART")
    private WebElement addtoCartCTA;


    //in page factory, its must to add return type
    //in below method we are returning the product header (String)


    public String getProductName()
    {
        return getText(productName);
    }

    public String getProductDes()
    {
        return getText(productDescription);
    }

    //navigate back to products page

    public ProductsPage goBackToProductPage()
    {
        click(backTOProductBttn);
        return new ProductsPage();
    }

    //to get price
    //this is optimized in scrollToProductPriceAndGetPrice()

   /* public String getProductPrice()
    {
        String price = getText(prdPrice);
        System.out.println("price is: " + price);
        return price;

        //if you dont want print the price on console

        *//*
        return getText(prdPrice);
        *//*
    }*/

    //to scroll to product price and get the price

    public String scrollToProductPriceAndGetPrice()
    {
        //returns the product price by calling scrollToElement method
        return getText(scrollToElement());
    }

    //to scroll for iOS

    public String scrolliOS()
    {
      return getText(iOSScrollToElement());
    }

    // to get addTocart button

    public boolean isAddToCartDisaplyed()
    {
        return addtoCartCTA.isDisplayed();
    }


}


