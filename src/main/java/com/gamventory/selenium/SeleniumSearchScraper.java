// package com.gamventory.selenium;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;

// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.chrome.ChromeDriver;

// import com.gamventory.constant.Category;
// import com.gamventory.constant.ItemSellStatus;
// import com.gamventory.constant.Platform;
// import com.gamventory.entity.Item;


// public class SeleniumSearchScraper {
    
//     public static void main(String[] args) {
//         // ChromeDriver의 위치를 지정
//         System.setProperty("webdriver.chrome.driver", "C:\\team\\gamventory\\drivers\\chrome-win64\\chromedriver.exe");
        
//         // WebDriver 인스턴스 생성
//         WebDriver driver = new ChromeDriver();
        
//         // 웹페이지에 접속
//         driver.get("https://store.steampowered.com/search/?term=");

//         // // 스크롤을 몇 번 할지 결정. 여기서는 예시로 5번 스크롤하도록 설정
//         // int numberOfScrolls = 1;
//         // JavascriptExecutor js = (JavascriptExecutor) driver;
//         // for (int i = 0; i < numberOfScrolls; i++) {
//         //     js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
//         //     try {
//         //         Thread.sleep(3000);  // 로딩 대기를 위해 3초간 대기
//         //     } catch (InterruptedException e) {
//         //         e.printStackTrace();
//         //     }
//         // }

//         List<WebElement> gameLinks = driver.findElements(By.cssSelector("a.search_result_row"));
//         List<String> gameAppIds = new ArrayList<>();
//         for (WebElement link : gameLinks) {
//             gameAppIds.add(link.getAttribute("data-ds-appid"));
//         }



//         List<String> gameImages = new ArrayList<>();
//         List<String> itemDetails = new ArrayList<>();
//         List<WebElement> gameNames = driver.findElements(By.cssSelector("span.title"));
//         List<WebElement> gamePrices = driver.findElements(By.cssSelector("div.discount_final_price"));

        

//         // 각 게임의 세부 페이지에 접속하여 필요한 정보를 가져옵니다.
//         for (int i = 0; i < 5; i++) {
//             String appId = gameAppIds.get(i);
//             driver.get("https://store.steampowered.com/app/" + appId + "/");
            
//             // 이미지 가져오기 시도
//             List<WebElement> imageElements = driver.findElements(By.cssSelector(".game_header_image_full"));
//             if (imageElements.size() > 0) {
//                 gameImages.add(imageElements.get(0).getAttribute("src"));
//             } else {
//                 gameImages.add("X data");
//                 System.out.println("Image not found for app ID: " + appId);
//             }
            
//             // 상세내역 가져오기시도
//             List<WebElement> detailElements = driver.findElements(By.cssSelector("#game_area_description"));
//             if (detailElements.size() > 0) {
//                 itemDetails.add(detailElements.get(0).getText());
//             } else {
//                 itemDetails.add("상세보기를 할 수 없는 게임입니다");
//                 System.out.println("Details not found for app ID: " + appId);
//             }

//             Item item = new Item();
        
//             // 게임 이름 및 가격 설정
//             item.setItemNm(gameNames.get(i).getText());
//             String priceStr = gamePrices.get(i).getText().strip().replace("₩", "").replace(",", "").trim();
            
//             int price;
//             if ("Free".equals(priceStr)) {
//                 price = 0;
//             } else {
//                 price = Integer.parseInt(priceStr);
//             }
//             item.setPrice(price);

//             item.setItemNm(gameNames.get(i).getText());
//             item.setPrice(price);
//             item.setPlatform(Platform.STEAM);
//             item.setStockNumber(50);
//             item.setItemDetail("테스트용 더미 데이터의 설명입니다.");
//             item.setItemSellStatus(ItemSellStatus.SELL);
//             // 장르는 랜덤 값으로 설정
//             Category[] categories = Category.values();
//             Category randomCategory = categories[new Random().nextInt(categories.length)];
//             item.setCategory(randomCategory);
//         }

//         for (int i = 0; i < 5; i++) {
//             System.out.println("Game Name: " + gameNames.get(i).getText());
//             System.out.println("Game Price: " + gamePrices.get(i).getText().strip());
//             System.out.println("Game Image: " + gameImages.get(i));
//             System.out.println("Item Detail: " + itemDetails.get(i));
//             System.out.println("-------------------------------------------------");
//         }
        
//         // 크롤링된 게임 이름, 가격 및 이미지 URL을 DB에 저장하는 코드
//         for (int i = 0; i < gameNames.size() && i < gamePrices.size() && i < gameImages.size(); i++) {
//             Item item = new Item();
        
//             // 게임 이름 및 가격 설정
//             item.setItemNm(gameNames.get(i).getText());
//             String priceStr = gamePrices.get(i).getText().strip().replace("₩", "").replace(",", "").trim();
            
//             int price;
//             if ("Free".equals(priceStr)) {
//                 price = 0;
//             } else {
//                 price = Integer.parseInt(priceStr);
//             }
//             item.setPrice(price);
            
//             // 기본 값 설정
//             item.setPlatform(Platform.STEAM);
//             item.setStockNumber(50);
//             item.setItemDetail("테스트용 더미 데이터의 설명입니다.");
//             item.setItemSellStatus(ItemSellStatus.SELL);
            
//             // 장르는 랜덤 값으로 설정
//             Category[] categories = Category.values();
//             Category randomCategory = categories[new Random().nextInt(categories.length)];
//             item.setCategory(randomCategory);
            
//             // 데이터베이스에 저장하는 코드는 여기에 추가됩니다.
//             // itemRepository.save(item);
//         }

        
//         // 브라우저 종료
//         driver.quit();
//     }
// }

