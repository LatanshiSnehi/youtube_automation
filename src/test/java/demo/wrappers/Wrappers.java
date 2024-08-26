package demo.wrappers;
import java.util.List;

import org.apache.commons.math3.analysis.function.Exp;
import org.checkerframework.checker.units.qual.t;
// import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
// import java.util.ArrayList;
import org.testng.asserts.SoftAssert;

// import dev.failsafe.internal.util.Assert;
import java.time.Duration;
import java.util.NoSuchElementException;

public class Wrappers {
   WebDriver driver;
   WebDriverWait wait;

   public SoftAssert s ;

   String[] expectedCatergories = {"Comedy" , "Animation", "Drama"};


   @FindBy(xpath = "//a[text() ='About']")
   private WebElement about;

   @FindBy(xpath = "//section[contains(@class, 'ytabout__content')]//h1")
   private WebElement aboutText;

   @FindBy(xpath = "//section[contains(@class, 'ytabout__content')]//p[1]")
   private WebElement aboutTextp;

   @FindBy(xpath = "//a[@id='endpoint' and @class='yt-simple-endpoint style-scope ytd-guide-entry-renderer']")
   private List<WebElement> tabOptions;

   @FindBy(xpath = "(//div[@id='dismissible' and @class='style-scope ytd-shelf-renderer'])[1]")
   private WebElement MusicFirstcategories;

   @FindBy(xpath = "//button[@aria-label='Next']")
   private WebElement nextButton;

   @FindBy(xpath = "(//div//p[contains (@class , 'style-scope')])[32]")
   private WebElement rating;

   @FindBy(xpath = "(//span[contains(@class, 'grid-movie-renderer-metadata')])[16]")
   private WebElement categoryWebelement;

   @FindBy(xpath = "(//div[contains (@class ,'flex-container style-scope ytd-compact-station-renderer')])[11]")
   private WebElement lastPlaylist;

//    @FindBy(xpath = "(//div[@id='dismissible' and @class ='style-scope ytd-rich-shelf-renderer'])[2]")
//    private WebElement latestNewSection;

   @FindBy(xpath = "(//div[@id='dismissible' and @class ='style-scope ytd-post-renderer'])[position()<=5]")
   private List<WebElement> allNewsPosts;

   @FindBy(xpath = "//input[@id='search']")
   private WebElement searchBox;

   @FindBy(xpath = "//div[@id= 'metadata-line']//span[1]")
   private List<WebElement> viewsWebElements;


   
  
   public Wrappers(WebDriver driver){
    this.driver = driver;
    this.wait = new WebDriverWait(driver,Duration.ofSeconds(60));
    this.s = new SoftAssert();
    PageFactory.initElements(driver, this);
   
   }

   public void navigateToYoutube(){
    String url = "https://www.youtube.com/";
    driver.get(url);
    wait.until(ExpectedConditions.urlToBe(url));
    String currentUrl = driver.getCurrentUrl();
    Assert.assertEquals(currentUrl, url,"Unable to navigate to youtube");
   }

   public void clickOnAbout(){
    try{
        wait.until(ExpectedConditions.elementToBeClickable(about)).click();
        wait.until(ExpectedConditions.urlContains("about.youtube"));
    }catch(NoSuchElementException e){
        System.out.println("Unable to click on about");
    }
   }

   public void printMessage(){
    try{
        wait.until(ExpectedConditions.visibilityOf(aboutText));
        System.out.println(aboutText.getText());
        wait.until(ExpectedConditions.visibilityOf(aboutTextp));
        System.out.println(aboutTextp.getText());
    }catch(Exception e){
        System.out.println("unablt to find the About text");
        e.printStackTrace();
    }
   }

   public void selectTab(String tabName){
    try{
        for(WebElement tabOption : tabOptions){
            wait.until(ExpectedConditions.visibilityOf(tabOption));
            String title = tabOption.getAttribute("title");
            if(title.equalsIgnoreCase(tabName)){
                System.out.println(tabOption);
                tabOption.click();
                Thread.sleep(3000);
                return;
            }
        }
       }catch(Exception e){
        System.out.println("Unable to find the tab name"+ tabName);
       }
    }

    public void clickOnNextBtn(){
        while(true){
            try{
                wait.until(ExpectedConditions.visibilityOf(nextButton));
                nextButton.click();
                Thread.sleep(2000);
            }
            catch(TimeoutException e){
                System.out.println("Next button is no longer visible. Exiting loop.");
                break;
            }catch(Exception e){
                System.out.println("An error occurred: " + e.getMessage());
                 break;
            }
        }      
    }

    public void verifyRatings(){
        wait.until(ExpectedConditions.visibilityOf(rating));
        Boolean var = rating.getText().equalsIgnoreCase("U");
        s.assertTrue(var, "Movie is not A rated");  
    }

    public void verifyCategoryExists(){
        Boolean categoryExists = false;
        String foundCategory ="";
        wait.until(ExpectedConditions.visibilityOf(categoryWebelement));
        String metadata = categoryWebelement.getText();

        String[] parts = metadata.split("•");
        if(parts.length>0){
            String categoryText =  parts[0].trim();
            for(String category : expectedCatergories){
                if(categoryText.contains(category)){
                    categoryExists = true;
                    foundCategory = category;
                    break;
                }
            }
        }
        if (categoryExists) {
            System.out.println("Category found: " + foundCategory);
        } else {
            System.out.println("No matching category found.");
        }
        s.assertTrue(categoryExists, "Category doesnt exist");
    }


    public void clickNextBtnInMusic() throws InterruptedException{
        //  wait.until(ExpectedConditions.visibilityOf(MusicFirstcategories));  
        WebElement nextBtn2 = MusicFirstcategories.findElement(By.xpath(".//button[@aria-label='Next']"));
        Thread.sleep(2000);
        while(true){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(nextBtn2));
                nextBtn2.click();
                Thread.sleep(2000);
            } catch(TimeoutException e){
                System.out.println("Next button is no longer visible. Exiting loop.");
                break;
            }catch(Exception e){
                System.out.println("An error occurred: " + e.getMessage());
                 break;
            }
        }

    }

    public void printPlaylistName(){
        try{          
            wait.until(ExpectedConditions.visibilityOf(lastPlaylist));
            WebElement playListName = lastPlaylist.findElement(By.xpath(".//a//h3"));
            wait.until(ExpectedConditions.visibilityOf(playListName));
            System.out.println("PlayList name is :  "+ playListName.getText());

            WebElement songsCount = lastPlaylist.findElement(By.xpath(".//a//p[@id='video-count-text']"));
            wait.until(ExpectedConditions.visibilityOf(songsCount));
            String songsCountinString = songsCount.getText().replaceAll("[^\\d]", "");
            //converting into int
            int songsCountInNum = Integer.parseInt(songsCountinString);
            System.out.println("Songs count is :: "+songsCountInNum);
            s.assertTrue(songsCountInNum <= 50, "The number of tracks listed is greater than 50. Found: " + songsCountInNum);
             
        }catch(Exception e){
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public int printNews(){
        int likeCount =0;
        wait.until(ExpectedConditions.urlToBe("https://www.youtube.com/channel/UCYfdidRxbB8Qhf0Nx7ioOYw"));
        try{
            List<WebElement> newsPosts = allNewsPosts;
            Thread.sleep(3000);
            
            for(int i =0; i<Math.min(newsPosts.size(),3);i++){
                    WebElement post = newsPosts.get(i);
                    Thread.sleep(2000);
                    WebElement title = post.findElement(By.xpath(".//*[@id='home-content-text']"));
                    wait.until(ExpectedConditions.visibilityOf(title));
                    System.out.println("Title of the post is "+title.getText());

                    WebElement like = post.findElement(By.xpath(".//div//span[@id='vote-count-middle']"));
                    Thread.sleep(3000);
                    String likeText = like.getText().trim();
                    if(!likeText.isEmpty()){
                        int likeNum = Integer.parseInt(likeText);
                        likeCount += likeNum;
                    }
                    else{
                        System.out.println("Likes are not available on this post :: ");
                    }                  
            }
            System.out.println("Total numbers of likes :: "+likeCount);
        }catch(Exception e){
            System.out.println("an error occured");
            e.printStackTrace();
        }
        return likeCount; 
    }

    //for searching on youtube
    public void searchItem(String item){
        try{ 
            wait.until(ExpectedConditions.visibilityOf(searchBox));
            searchBox.sendKeys(item);
            searchBox.sendKeys(Keys.ENTER);
        }catch(Exception e){
            System.out.println("Unable to search the item");
            e.printStackTrace();
        }
    }

    //for scrolling the page manually by javascript executer
    public void scrollToLoadMore() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    //for getting the count of views 
    public int getViewCount() throws InterruptedException{
        int totalViewCount = 0;
        int viewCount =0;
        for(WebElement viewsWebElement : viewsWebElements){
            Thread.sleep(1000);
            String viewsText = viewsWebElement.getText().replace("views", "").trim();
            if (viewsText.isEmpty() || viewsText.equalsIgnoreCase("No views") || viewsText.equalsIgnoreCase("Updated today")) {
                continue;
            }
            try{
            if(viewsText.endsWith("K")) {
                viewCount = (int) (Double.parseDouble(viewsText.replace("K",""))*1000);
            }
            else if(viewsText.endsWith("M")){
                viewCount = (int) (Double.parseDouble(viewsText.replace("M",""))*1000000);
            }
           else{
                viewCount = Integer.parseInt(viewsText.replaceAll("[^0-9]", ""));
           }
        }catch(NumberFormatException e){
            System.out.println("Error while parsing views count"+viewsText);
            e.printStackTrace();
            continue;
        }
           totalViewCount+=viewCount;

    }
    return totalViewCount;
    }

    public int verifyTotalViews() throws InterruptedException{
        int totalViews = 0;
        while(totalViews<1000000000){
            scrollToLoadMore();
            Thread.sleep(2000);
            try{
               int currentViews = getViewCount();
               totalViews += currentViews; 
               if (totalViews >= 1000000000) {
                   System.out.println("Views are more than 10 crores, Stopping scrolling.....");
                   break; 
               }
              
            }catch(Exception e){
                System.out.println("Error while getting the views count");
                e.printStackTrace();
                break;
            } 
        }
        System.out.println("total views are :: "+totalViews);
        return totalViews;
    }
}
