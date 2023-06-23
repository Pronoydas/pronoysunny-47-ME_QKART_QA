package QKART_SANITY_LOGIN.Module1;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/checkout";

    public Checkout(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToCheckout() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    /*
     * Return Boolean denoting the status of adding a new address
     */
    public Boolean addNewAddress(String addresString) {
        try {
            /*
             * Click on the "Add new address" button, enter the addressString in the address
             * text box and click on the "ADD" button to save the address
             */
            WebElement addNewAddressBtn = driver.findElement(By.xpath("//button[@id='add-new-btn']"));
            addNewAddressBtn.click();
            WebElement addressSection = driver.findElement(By.xpath("//textarea[contains(@placeholder,'Enter your complete address')]"));
            addressSection.sendKeys(addresString);
            WebElement addBtn= driver.findElement(By.xpath("//button[normalize-space()='Add']"));
            addBtn.click();
            return true;
        } catch (Exception e) {
            System.out.println("Exception occurred while entering address: " + e.getMessage());
            return false;

        }
    }

    /*
     * Return Boolean denoting the status of selecting an available address
     */
    public Boolean selectAddress(String addressToSelect) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Iterate through all the address boxes to find the address box with matching
             * text, addressToSelect and click on it
             * 
             */
            WebElement address =driver.findElement(By.name("address"));
            if(!address.isSelected()){
                address.click();
            }

           // System.out.println("Unable to find the given address");
            return true;
        } catch (Exception e) {
            System.out.println("Exception Occurred while selecting the given address: " + e.getMessage());
            return false;
        }

    }

    /*
     * Return Boolean denoting the status of place order action
     */
    public Boolean placeOrder() {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find the "PLACE ORDER" button and click on it
            driver.findElement(By.className("css-177pwqq")).click();
            return true;

        } catch (Exception e) {
            System.out.println("Exception while clicking on PLACE ORDER: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the insufficient balance message is displayed
     */
    public Boolean verifyInsufficientBalanceMessage() {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 08: MILESTONE 7
            WebElement e = driver.findElement(By.xpath("//div[@class='SnackbarItem-message']"));
            String text = "You do not have enough balance in your wallet for this purchase";
            if (!text.equalsIgnoreCase(e.getText())){
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("Exception while verifying insufficient balance message: " + e.getMessage());
            return false;
        }
    }


    public boolean verifyCountOfAdvertisements(WebDriver driver){
        List<WebElement> list=driver.findElements(By.tagName("iframe"));
        if (list.size()==3){
            return true;
        }
        return false;
    }


    public boolean verifyContentOfAdvertisements(WebDriver driver){
      boolean isTrue= false ;
      try{
        WebElement parentTagFrame = driver.findElement(By.className("css-92t6i8"));
        List<WebElement> list = parentTagFrame.findElements(By.tagName("iframe"));
        for(int i=0 ; i<list.size();i++){
            driver.switchTo().frame(i);
        WebElement viewCartBtw = driver.findElement(By.xpath("//button[1]"));
        WebElement buyNowBtw = driver.findElement(By.xpath("//button[2]"));
        if ((viewCartBtw.isDisplayed()&&viewCartBtw.isEnabled())){
           buyNowBtw.click();
           WebDriverWait wdw = new WebDriverWait(driver,30);
           wdw.until(ExpectedConditions.urlContains("/checkout"));
           driver.navigate().back();
           wdw.until(ExpectedConditions.urlContains("/thanks"));
           driver.switchTo().parentFrame();

        }
        else{
            return false;
        }
        isTrue=true;
        }
      }catch(Exception e){
        System.out.println("Error while Perfoming operetion on Frame " +e.getMessage());
        return false;
      }

      return isTrue;
    }

}