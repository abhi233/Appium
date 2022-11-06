package utils;

import BaseClass.AndroidBaseClass;
import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.*;
import io.appium.java_client.android.connection.ConnectionState;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

public class AndroidActions extends AndroidBaseClass {

    public static AndroidDriver driver;
    private static String datetimeabc = null;
    private static int counter = 0;
    private final Map<Object, Object> dicttoread = new HashMap<>();

    public AndroidActions(AndroidDriver driver) {
        this.driver = driver;
    }

    public static void longPressAction(WebElement ele) {
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(),
                        "duration", 2000));
    }


    public void scrollToEndAction() {
        boolean canScrollMore;
        do {
            canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 200,
                    "direction", "down",
                    "percent", 3.0

            ));
        } while (canScrollMore);
    }

    public void scrollToText(String text) {

        driver.findElement(MobileBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));"));
    }


    public void swipeAction(WebElement ele, String direction) {
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) ele).getId(),

                "direction", direction,
                "percent", 0.75
        ));


    }

}
