package com.gamventory.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamventory.constant.Category;
import com.gamventory.constant.GameKind;
import com.gamventory.constant.ItemSellStatus;
import com.gamventory.constant.Platform;
import com.gamventory.dto.ItemFormDto;
import com.gamventory.dto.ItemImgDto;

@Service
public class EpicGamesScraper {

    @Autowired
    private ItemService itemService;

    private final String DRIVER_PATH = "C:\\team\\gamventory\\drivers\\chrome-win64\\chromedriver.exe";

    public EpicGamesScraper() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
    }

    private Path downloadImage(String imageUrl, Path targetPath) {
        try {
            if (imageUrl.startsWith("data:")) {
                saveDataUriAsImage(imageUrl, targetPath);
            } else {
                try (InputStream in = new URL(imageUrl).openStream()) {
                    Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetPath;
    }

    private void saveDataUriAsImage(String dataUri, Path outputPath) throws Exception {
        String base64Image = dataUri.split(",")[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            Files.copy(bis, outputPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    // Platform enum 랜덤값 가져오는 메서드
    public Platform getRandomPlatform() {
        Random random = new Random();
        Platform[] selectedPlatforms = new Platform[] {
            Platform.EPIC_GAMES,
            Platform.ROCKSTAR_GAMES,
            Platform.STEAM,
            Platform.UBISOFT_CONNECT,
            Platform.X_BOX,
            Platform.OTHER
        };
        int randomIndex = random.nextInt(selectedPlatforms.length);
        return selectedPlatforms[randomIndex];
    }

    // Category enum에서 랜덤한 값을 가져오는 메서드
    public Category getRandomCategory() {
        Random random = new Random();
        Category[] categories = Category.values();
        int randomIndex = random.nextInt(categories.length);
        return categories[randomIndex];
    }


    public List<ItemFormDto> scrapeEpicGames() {
        List<ItemFormDto> itemList = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-agent=Your_Desired_User_Agent_String_Here");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://store.epicgames.com/ko/collection/top-sellers");
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.css-rgqwpc")));

            List<WebElement> gameNames = driver.findElements(By.cssSelector("div.css-rgqwpc"));
            List<WebElement> gamePrices = driver.findElements(By.cssSelector("span.css-119zqif"));
            List<WebElement> gameImages = driver.findElements(By.cssSelector("div.css-uwwqev img"));

            

            for (int i = 0; i < gameNames.size(); i++) {

                String originalGameName = gameNames.get(i).getText();
                String formattedGameName = originalGameName.replace(" ", "-").replaceAll("[^a-zA-Z0-9-]", "");
                String rawPrice = gamePrices.get(i).getText().strip();
                String cleanedPrice = rawPrice.replace("₩", "").replace(",", "").trim();

                String imageUrl = gameImages.get(i).getAttribute("src");
                Path downloadedImagePath = downloadImage(imageUrl, Paths.get("C:/team/gamventory/upload/images/item", formattedGameName + ".jpg"));

                //제목 길이설정
                int maxItemNameLength = 50; // 데이터베이스 컬럼의 실제 최대 길이로 설정
                if (formattedGameName.length() > maxItemNameLength) {
                    formattedGameName = formattedGameName.substring(0, maxItemNameLength);
                    // 로그를 남기거나, 잘린 이름에 대한 처리를 할 수 있습니다.
                    // 예: log.warn("Game name truncated: " + originalGameName + " to " + formattedGameName);
                }

                ItemFormDto itemFormDto = new ItemFormDto();
                itemFormDto.setItemNm(originalGameName);
                if (!cleanedPrice.isEmpty() && cleanedPrice.matches("\\d+")) {
                    itemFormDto.setPrice(Integer.parseInt(cleanedPrice));
                } else {
                    itemFormDto.setPrice(50000); // 기본값 설정
                }
                itemFormDto.setCategory(getRandomCategory());
                itemFormDto.setItemDetail("X");
                itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
                itemFormDto.setPlatform(getRandomPlatform());
                itemFormDto.setGameKind(GameKind.PC);
                itemFormDto.setStockNumber(500);

                ItemImgDto itemImgDto = new ItemImgDto();
                itemImgDto.setImgName(formattedGameName + ".jpg");
                itemImgDto.setOriImgName(downloadedImagePath.toString());
                itemImgDto.setImgUrl(imageUrl);

                List<ItemImgDto> itemImgDtoList = new ArrayList<>();
                itemImgDtoList.add(itemImgDto);
                itemFormDto.setItemImgDtoList(itemImgDtoList);

                itemList.add(itemFormDto);
            }

            for (ItemFormDto itemFormDto : itemList) {
                List<Path> itemImagePaths = new ArrayList<>();
                for (ItemImgDto imgDto : itemFormDto.getItemImgDtoList()) {
                    Path imagePath = Paths.get(imgDto.getOriImgName());
                    itemImagePaths.add(imagePath);
                }
                // TODO: 테스트 코드에서 이 경로를 사용하여 MockMultipartFile 객체를 만들어 저장하세요
            }
            return itemList;

        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
