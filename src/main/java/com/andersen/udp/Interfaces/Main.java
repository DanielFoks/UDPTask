package com.andersen.udp.Interfaces;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//from  w w  w  .  ja v  a  2s .  co  m
public class Main {
  public static void main(String[] args) throws Exception{

    Thread thread = new Thread(() -> {
      final String sUrl = "https://egb.com/play/simple_bets";
      File file = new File("D:/chromedriver.exe");
      System.setProperty(/*"webdriver.ie.driver"*/ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, file.getAbsolutePath());
      WebDriver oWebDriver = new ChromeDriver();
      oWebDriver.get(sUrl);

/*      try {
        System.out.println("START");
        Thread.sleep(3000);
        System.out.println("END");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }*/


      List<WebElement> oSearchInputElem = oWebDriver.findElements(By.className("bet-rate")); // Use name locator to identify the search input field.
      List<String> coefs = new ArrayList<>();

      System.out.println("************");
      for(WebElement f: oSearchInputElem){
        System.out.println(f.getText());
        coefs.add(f.getText());
      }
      System.out.println("************");



      for (String s:coefs){
        System.out.println("--COEF: " + s);
      }

      /*oWebDriver.close();*/
    });
    thread.start();



/*    oSearchInputElem.sendKeys("Selenium 2");
    WebElement oGoogleSearchBtn = oWebDriver.findElement(By.xpath("//input[@name='btnG']"));
    oGoogleSearchBtn.click();*/

/*    try {
      Thread.sleep(5000);
    } catch(InterruptedException ex) {
      System.out.println(ex.getMessage());
    }
    oWebDriver.close();
  }*/

}
}
