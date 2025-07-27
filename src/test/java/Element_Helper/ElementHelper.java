package Element_Helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.By;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ElementHelper {

    private ConcurrentMap<String, ElementInfo> elementMapList = new ConcurrentHashMap<>();
    private Gson gson = new Gson();

    public ElementHelper() {
        initElementInfo();
    }

    private void initElementInfo() {
        try {
            // Örnek: resources/locators klasöründeki JSON dosyalarını tek tek belirt
            String[] jsonFiles = {
                    "jsons/locators/homePage.json",
            };

            Type elementType = new TypeToken<List<ElementInfo>>() {}.getType();

            for (String resourcePath : jsonFiles) {
                InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
                if (is == null) {
                    System.err.println("Resource not found: " + resourcePath);
                    continue;
                }
                List<ElementInfo> elementInfoList = gson.fromJson(new InputStreamReader(is), elementType);
                checkDuplicateAndPut(elementInfoList);
                is.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error initializing element info", e);
        }
    }

    private void checkDuplicateAndPut(List<ElementInfo> elementInfoList) {
        for (ElementInfo elementInfo : elementInfoList) {
            if (elementMapList.containsKey(elementInfo.getKey())) {
                throw new RuntimeException("Duplicate key found! key = " + elementInfo.getKey());
            }
            elementMapList.put(elementInfo.getKey(), elementInfo);
        }
    }

    public ElementInfo getElementInfo(String key) {
        return elementMapList.get(key);
    }

    public By getElementInfoToBy(String key) {
        ElementInfo elementInfo = getElementInfo(key);
        if (elementInfo == null) {
            throw new RuntimeException("Element key not found: " + key);
        }
        switch (elementInfo.getType()) {
            case CSS: return By.cssSelector(elementInfo.getValue());
            case ID: return By.id(elementInfo.getValue());
            case XPATH: return By.xpath(elementInfo.getValue());
            case TAG: return By.tagName(elementInfo.getValue());
            case CLASS: return By.className(elementInfo.getValue());
            case NAME: return By.name(elementInfo.getValue());
            default: throw new RuntimeException("Unknown ByType: " + elementInfo.getType());
        }
    }

    // Örnek ElementInfo sınıfı
    public static class ElementInfo {
        private String key;
        private ByType type;
        private String value;

        public String getKey() { return key; }
        public ByType getType() { return type; }
        public String getValue() { return value; }
    }

    // Örnek ByType enum'u
    public enum ByType {
        CSS, ID, XPATH, TAG, CLASS, NAME
    }

    // Test için main metodu
    public static void main(String[] args) {
        ElementHelper helper = new ElementHelper();

        By by = helper.getElementInfoToBy("loginButton");
        System.out.println(by);  // örn: By.xpath: //button[text()='Login']
    }
}

