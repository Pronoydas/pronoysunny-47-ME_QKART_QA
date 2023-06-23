package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/";
    String parentWindow=null;
    Set<String> set = null;

    public Home(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            // Wait for Logout to Complete
            Thread.sleep(1000);

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any errors
     */
    public Boolean searchForProduct(String product) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Clear the contents of the search box and Enter the product name in the search
            // box
            WebElement searchBox = driver.findElement(By.xpath("//input[@name='search'][1]"));
            searchBox.clear();
            searchBox.sendKeys(product);
            Thread.sleep(1000);
           WebDriverWait wdw=new WebDriverWait(driver, 15);
           wdw.until(ExpectedConditions.or(ExpectedConditions.presenceOfElementLocated(By.className("css-s18byi")),
           ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[normalize-space()='No products found']"))));
           return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>();
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Find all webelements corresponding to the card content section of each of
            // search results
            searchResults = driver.findElements(By.className("css-1qw96cp"));
            // int size=searchResults.size();
            // System.out.println(size);
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;

        }
    }

    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        // Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Check the presence of "No products found" text in the web page. Assign status
            // = true if the element is *displayed* else set status = false
            String str = driver.findElement(By.xpath("//h4[normalize-space()='No products found']")).getText();     
            return str.equalsIgnoreCase("No products found") ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * Return Boolean if add product to cart is successful
     */
    public Boolean addProductToCart(String productName) {
        try {
            /*
             * Iterate through each product on the page to find the WebElement corresponding
             * to the matching productName
             * 
             * Click on the "ADD TO CART" button for that element
             * 
             * Return true if these operations succeeds
             */
            WebElement addtoCart = driver.findElement(By.className("css-54wre3"));
            if (addtoCart.isDisplayed() && addtoCart.isEnabled()) {
                addtoCart.click();
            } else {
                System.out.println("Unable to find the given product");
                return false;
            }

            return true;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of clicking on the checkout button
     */
    public Boolean clickCheckout() {
        Boolean status = true;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find and click on the the Checkout button
            driver.findElement(By.className("css-177pwqq")).click();
            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            status = false;
            return status;
        }
    }

    /*
     * Return Boolean denoting the status of change quantity of product in cart operation
     */
    public Boolean changeProductQuantityinCart(String productName, int quantity) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 06: MILESTONE 5
            // Find the item on the cart with the matching productName
            // Increment or decrement the quantity of the matching product until the current
            // quantity is reached (Note: Keep a look out when then input quantity is 0,
            // here we need to remove the item completely from the cart)
            List<WebElement> titleOfProducts =
                    driver.findElements(By.xpath("//div[@class='MuiBox-root css-1gjj37g']/div[1]"));
            List<WebElement> productQtyElements =
                    driver.findElements(By.xpath("//div[@data-testid='item-qty']"));
            List<WebElement> increseQtyElements =
                    driver.findElements(By.xpath("//*[@data-testid='AddOutlinedIcon']"));
            List<WebElement> descriseQtyElements =
                    driver.findElements(By.xpath("//*[@data-testid='RemoveOutlinedIcon']"));
            for (int i = 0; i < titleOfProducts.size(); i++) {
                WebElement titleOfProduct = titleOfProducts.get(i);
                String title = titleOfProduct.getText();
                if (title.equalsIgnoreCase(productName)) {
                    while (true) {
                        int actualQty = Integer.parseInt(productQtyElements.get(i).getText());
                        if (actualQty == quantity) {
                            break;
                        }
                        if (actualQty < quantity) {
                            increseQtyElements.get(i).click();
                            Thread.sleep(3000);
                        } else if (actualQty > quantity) {
                            descriseQtyElements.get(i).click();
                            Thread.sleep(3000);
                        }
                        if (actualQty == 1 && quantity == 0) {
                            break;
                        }

                    }


                }

            }

            return true;
        } catch (Exception e) {
            if (quantity == 0)
                return true;
            System.out.println("exception occurred when updating cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 07: MILESTONE 6

            // Get all the cart items as an array of webelements

            // Iterate through expectedCartContents and check if item with matching product
            // name is present in the cart
            WebElement parentNode =
                    driver.findElement(By.xpath("//div[@class='cart MuiBox-root css-0']"));
            for (int i = 0; i < expectedCartContents.size(); i++) {
                String text = parentNode.findElement(By
                        .xpath("(//div[@class='MuiBox-root css-1gjj37g'])[" + (i + 1) + "]/div[1]"))
                        .getText();
                if (!expectedCartContents.get(i).equalsIgnoreCase(text)) {
                    return false;
                }
            }
            return true;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }
    public boolean clickOnLinks(WebElement link){
       boolean isTrue = false;
       if(link.isDisplayed()&&link.isEnabled()){
        link.click();
        isTrue=true;
       }
        return isTrue;
    }
    public boolean switchToTab(WebDriver driver){
       
        boolean isTrue = false;
        try {
        parentWindow=driver.getWindowHandle();
        set=driver.getWindowHandles();
        for(String str :set){
            if (!str.equals(parentWindow)){
                driver.switchTo().window(str);
                isTrue=true;
            }
        }
      } catch (Exception e) {
        //TODO: handle exception
        System.out.println("Error At the time of Switching"+e.getMessage());
      }
      return isTrue;

    }

    public boolean verifyCurrentUrl(WebDriver driver){
        boolean isTrue= false;
        String urlCurrent = driver.getCurrentUrl();
        if(urlCurrent.equals(url)){
            isTrue=true;
        }

        return isTrue;
    }

    public boolean verifyTabContent(WebElement privacyLink, String str){
        boolean isTrue = false;
        try{
        // String xpath = String.format("//h2[normalize-space()='%s']",str);
        // System.out.println(xpath);
        Thread.sleep(3000);
        String content=privacyLink.getText();
        if(str.equalsIgnoreCase(content)){
            isTrue=true;
        }
    }catch(Exception e){
        System.out.println("Error while getting text from the page "+e.getMessage());
    }
        return isTrue;
    }

    public boolean switchToParentWindow(WebDriver driver){
        try{
         driver.close();
         driver.switchTo().window(parentWindow);
        }catch(Exception e){
            System.out.println("Error Getting while switch to ParentWindow "+e.getMessage());
            return false;
        }

       return true;
    }

    public boolean contractUs(WebDriver driver){
         try {
            WebDriverWait wdw= new WebDriverWait(driver, 30);
            wdw.until(ExpectedConditions.presenceOfElementLocated(By.className("card-block")));
            driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys("crio user");
            driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys("criouser@gmail.com");
            driver.findElement(By.xpath("//input[@placeholder='Message']")).sendKeys(" Testing the contact us page");
            driver.findElement(By.className("m-b-20")).click();
            if (driver.findElement(By.className("card-block")).isDisplayed()){
                return true;
            }
         } catch (Exception e) {
            System.out.println("Error at the time of contractUs "+e.getMessage());
         }

        return false;
    }
}
