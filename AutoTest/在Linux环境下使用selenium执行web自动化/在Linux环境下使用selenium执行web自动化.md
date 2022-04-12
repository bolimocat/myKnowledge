# 在Linux环境下使用selenium执行web自动化

### 一、执行测试的必要组件

对于selenium的执行方式有很多，包括支持不同语言，实际使用时肯定选择自己熟悉的架构，比如我选择使用webdriver驱动的方式。

需要的组件包括：驱动firefox的工具geckodriver，加载到eclipse里的jar包 selenium-server-standalone.jar。

这个组合就可以实现在linux环境下，顺利调起firefox进行web的功能自动化测试。



### 二、组件部署

把geckodriver放在任意一个可访问的位置，为把他放在 了/usr/bin/下，然后在/etc/profile的PATH中增加了一个引用。

selenium-server-standalone.jar更简单，只要在java的工程里添加jar即可。

--geckodriver下载位置
https://github.com/mozilla/geckodriver/releases
有linux和windows两种支持


### 三、基本语法

```
--引用基本的包
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
--此为当前可支持的浏览器

WebDriver driver = new ChromeDriver();    //Chrome浏览器
WebDriver driver = new FirefoxDriver();   //Firefox浏览器
WebDriver driver = new EdgeDriver();      //Edge浏览器
WebDriver driver = new InternetExplorerDriver();  // Internet Explorer浏览器
WebDriver driver = new OperaDriver();     //Opera浏览器
WebDriver driver = new PhantomJSDriver();   //PhantomJS
--同时使用不同浏览器的话，首先要注意实例化的名称要有区别，另外/usr/bin/下的webdriver工具要补全。
```



selenium有8种定位方式：

> - id
> - name
> - class name
> - tag name
> - link text
> - partial link text
> - xpath
> - css selector

可以根据页面种没种元素使用的属性，灵活定位元素。

这8种定位方式在使用时：

```
    driver.findElement(By.id())
    driver.findElement(By.name())
    driver.findElement(By.className())
    driver.findElement(By.tagName())
    driver.findElement(By.linkText())
    driver.findElement(By.partialLinkText())
    driver.findElement(By.xpath())
    driver.findElement(By.cssSelector())
```

通过xpath定位，xpath定位有N种写法，这里列几个常用写法：

```
driver.findElement(By.xpath("//*[@id='kw']"))
driver.findElement(By.xpath("//*[@name='wd']"))
driver.findElement(By.xpath("//input[@class='s_ipt']"))
driver.findElement(By.xpath("/html/body/form/span/input"))
driver.findElement(By.xpath("//span[@class='soutu-btn']/input"))
driver.findElement(By.xpath("//form[@id='form']/span/input"))
driver.findElement(By.xpath("//input[@id='kw' and @name='wd']"))
```

通过css定位，css定位有N种写法，这里列几个常用写法： 

```
driver.findElement(By.cssSelector("#kw")
driver.findElement(By.cssSelector("[name=wd]")
driver.findElement(By.cssSelector(".s_ipt")
driver.findElement(By.cssSelector("html > body > form > span > input")
driver.findElement(By.cssSelector("span.soutu-btn> input#kw")
driver.findElement(By.cssSelector("form#form > span > input")
```

直接使用文本连接定位：

> ```
> <a class="mnav" href="http://news.baidu.com" name="tj_trnews">新闻</a>
> ```

```
driver.findElement(By.linkText("新闻") --通过link text定位
```

```
driver.findElement(By.partialLinkText("新") --通过partialLink text定位
```

获得元素后，对元素进行操作：

```
    WebElement search_text = driver.findElement(By.id("kw"));
    WebElement search_button = driver.findElement(By.id("su"));
    search_text.sendKeys("Java");
    search_button.click();
    --更多可用方法，可以直接通过补全命令查看。
```



### 四、selenium驱动浏览器

```
        driver.get("http://www.itest.info"); --打开新窗口，地址为字符串内容。
        String title = driver.getTitle(); --获得浏览器标题
        driver.manage().window().maximize();	--浏览器窗口最大化
        driver.manage().window().setSize(new Dimension(480, 800));
        driver.getCurrentUrl()	--获得当前地址位置
        driver.navigate().back();	--后退
        driver.navigate().forward();	--前进
        driver.navigate().refresh();	--刷新页面
        search_text.submit();	--文本框控件的默认回车提交
        driver.quit();	--退出
```



### 五、模拟鼠标操作

> - contextClick() 右击
> - clickAndHold() 鼠标点击并控制
> - doubleClick() 双击
> - dragAndDrop() 拖动
> - release() 释放鼠标
> - perform() 执行所有Actions中存储的行为

```
    WebDriver driver = new ChromeDriver();
    driver.get("https://www.baidu.com/");
    WebElement search_setting = driver.findElement(By.linkText("设置"));
    Actions action = new Actions(driver);
    action.clickAndHold(search_setting).perform();
    driver.quit();
    --百度首页设置悬停下拉菜单。
    
    Actions action = new Actions(driver);
 
// 鼠标右键点击指定的元素
action.contextClick(driver.findElement(By.id("element"))).perform();
 
// 鼠标右键点击指定的元素
action.doubleClick(driver.findElement(By.id("element"))).perform();
 
// 鼠标拖拽动作， 将 source 元素拖放到 target 元素的位置。
WebElement source = driver.findElement(By.name("element"));
WebElement target = driver.findElement(By.name("element"));
action.dragAndDrop(source,target).perform();
 
// 释放鼠标
action.release().perform();
```



### 六、模拟键盘操作

```
    WebDriver driver = new ChromeDriver();
    driver.get("https://www.baidu.com");
 
    WebElement input = driver.findElement(By.id("kw"));
 
    //输入框输入内容
    input.sendKeys("seleniumm");
    Thread.sleep(2000);
 
    //删除多输入的一个 m
    input.sendKeys(Keys.BACK_SPACE);
    Thread.sleep(2000);
 
    //输入空格键+“教程”
    input.sendKeys(Keys.SPACE);
    input.sendKeys("教程");
    Thread.sleep(2000);
 
    //ctrl+a 全选输入框内容
    input.sendKeys(Keys.CONTROL,"a");
    Thread.sleep(2000);
 
    //ctrl+x 剪切输入框内容
    input.sendKeys(Keys.CONTROL,"x");
    Thread.sleep(2000);
 
    //ctrl+v 粘贴内容到输入框
    input.sendKeys(Keys.CONTROL,"v");
    Thread.sleep(2000);
 
    //通过回车键盘来代替点击操作
    input.sendKeys(Keys.ENTER);
    Thread.sleep(2000);
 
    driver.quit();
```

> 以下为常用的键盘操作：
>  sendKeys(Keys.BACK_SPACE) 回格键（BackSpace）
>  sendKeys(Keys.SPACE) 空格键(Space)
>  sendKeys(Keys.TAB) 制表键(Tab)
>  sendKeys(Keys.ESCAPE) 回退键（Esc）
>  sendKeys(Keys.ENTER) 回车键（Enter）
>  sendKeys(Keys.CONTROL,‘a’) 全选（Ctrl+A）
>  sendKeys(Keys.CONTROL,‘c’) 复制（Ctrl+C）
>  sendKeys(Keys.CONTROL,‘x’) 剪切（Ctrl+X）
>  sendKeys(Keys.CONTROL,‘v’) 粘贴（Ctrl+V）
>  sendKeys(Keys.F1) 键盘 F1
>  ……
>  sendKeys(Keys.F12) 键盘 F12



### 七、获取断言

> - getTitle()： 用于获得当前页面的title。
> - getCurrentUrl() ： 用户获得当前页面的URL。
> - getText() 获取页面文本信息。



### 八、设置元素等待

WebDriver提供了两种类型的等待：显式等待和隐式等待。

显式等待

```
    WebDriver driver = new ChromeDriver();
    driver.get("https://www.baidu.com");
 
    //显式等待， 针对某个元素等待
    WebDriverWait wait = new WebDriverWait(driver,10,1);
 
    wait.until(new ExpectedCondition<WebElement>(){
      @Override
      public WebElement apply(WebDriver text) {
            return text.findElement(By.id("kw"));
          }
    }).sendKeys("selenium");
 
    driver.findElement(By.id("su")).click();
    Thread.sleep(2000);
 
    driver.quit();
```

WebDriverWait类是由WebDirver提供的等待方法。在设置时间内，默认每隔一段时间检测一次当前页面元素是否存在，如果超过设置时间检测不到则抛出异常。具体格式如下：
 WebDriverWait(driver, 10, 1)
 driver： 浏览器驱动。 10： 最长超时时间， 默认以秒为单位。 1： 检测的的间隔（步长） 时间， 默认为 0.5s。

隐式等待

```
    WebDriver driver = new ChromeDriver();
 
    //页面加载超时时间设置为 5s
    driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    driver.get("https://www.baidu.com/");
 
    //定位对象时给 10s 的时间, 如果 10s 内还定位不到则抛出异常
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.findElement(By.id("kw")).sendKeys("selenium");
 
    //异步脚本的超时时间设置成 3s
    driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
 
    driver.quit();
```



### 九、获得一组元素

定位一组元素的方法与定位单个元素的方法类似，唯一的区别是在单词 findElement 后面多了一个 s 表示复数。

之后可以用List<WebElement> 方式进行访问。



### 十、多表单切换

在 Web 应用中经常会遇到 frame/iframe 表单嵌套页面的应用， WebDriver 只能在一个页面上对元素识别与 定位， 对于 frame/iframe 表单内嵌页面上的元素无法直接定位。 这时就需要通过 switchTo().frame()方法将当前定 位的主体切换为 frame/iframe 表单的内嵌页面中。

> ```
> <html>
>   <body>
>     ...
>     <iframe id="x-URS-iframe" ...>
>       <html>
>          <body>
>            ...
>            <input name="email" >
> ```

```
    WebDriver driver = new ChromeDriver();
    driver.get("http://www.126.com");
    WebElement xf = driver.findElement(By.xpath("//*[@id='loginDiv']/iframe"));
    driver.switchTo().frame(xf);
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys("username");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("password");
    driver.findElement(By.id("dologin")).click();
    driver.switchTo().defaultContent();
    //……
```

如果完成了在当前表单上的操作，则可以通过switchTo().defaultContent()方法跳出表单。



### 十一、多窗口切换

在页面操作过程中有时候点击某个链接会弹出新的窗口， 这时就需要主机切换到新打开的窗口上进行操作。WebDriver提供了switchTo().window()方法可以实现在不同的窗口之间切换。 以百度首页和百度注册页为例，在两个窗口之间的切换如下图。

```
    WebDriver driver = new ChromeDriver();
    driver.get("https://www.baidu.com");
 
    //获得当前窗口句柄
    String search_handle = driver.getWindowHandle();
 
    //打开百度注册窗口
    driver.findElement(By.linkText("登录")).click();
    Thread.sleep(3000);
    driver.findElement(By.linkText("立即注册")).click();
 
    //获得所有窗口句柄
    Set<String> handles = driver.getWindowHandles();
 
    //判断是否为注册窗口， 并操作注册窗口上的元素
    for(String handle : handles){
      if (handle.equals(search_handle)==false){
        //切换到注册页面
        driver.switchTo().window(handle);
        System.out.println("now register window!");
        Thread.sleep(2000);
        driver.findElement(By.name("userName")).clear();
        driver.findElement(By.name("userName")).sendKeys("user name");
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("phone number");
        //......
        Thread.sleep(2000);
        //关闭当前窗口
        driver.close();
      }
    }
    Thread.sleep(2000);
 
    driver.quit();
```

- getWindowHandle()： 获得当前窗口句柄。
- getWindowHandles()： 返回的所有窗口的句柄到当前会话。
- switchTo().window()： 用于切换到相应的窗口，与上一节的switchTo().frame()类似，前者用于不同窗口的切换， 后者用于不同表单之间的切换。



### 十二、下拉框选择

> ```
> <select id="nr" name="NR">
>  <option value="10" selected>每页显示 10 条</option>
>  <option value="20">每页显示 20 条</option>
>  <option value="50">每页显示 50 条</option>
> <select>
> ```

```
ebDriver driver = new ChromeDriver();
    driver.get("https://www.baidu.com");
 
    driver.findElement(By.linkText("设置")).click();
    driver.findElement(By.linkText("搜索设置")).click();
    Thread.sleep(2000);
 
    //<select>标签的下拉框选择
    WebElement el = driver.findElement(By.xpath("//select"));
    Select sel = new Select(el);
    sel.selectByValue("20");
    Thread.sleep(2000);
 
    driver.quit();
```



### 十三、警告框处理

在 WebDriver中处理JavaScript所生成的alert、confirm以及prompt十分简单，具体做法是使用switch_to_alert()方法定位到alert/confirm/prompt，然后使用text/accept/dismiss/sendKeys等方法进行操作。

> - getText()： 返回 alert/confirm/prompt 中的文字信息。
> - accept()： 接受现有警告框。
> - dismiss()： 解散现有警告框。
> - sendKeys(keysToSend)： 发送文本至警告框。
> - keysToSend： 将文本发送至警告框。

```
    WebDriver driver = new ChromeDriver();
    driver.get("https://www.baidu.com");
 
    driver.findElement(By.linkText("设置")).click();
    driver.findElement(By.linkText("搜索设置")).click();
    Thread.sleep(2000);
 
    //保存设置
    driver.findElement(By.className("prefpanelgo")).click();
 
    //接收弹窗
    driver.switchTo().alert().accept();
    Thread.sleep(2000);
 
    driver.quit();
```



### 十四、文件上传

> ```
> <body>
>  <div class="row-fluid">
> 	<div class="span6 well">
> 	<h3>upload_file</h3>
> 	  <input type="file" name="file" />
> 	</div>
>   </div>
> </body>
> ```

```
    WebDriver driver = new ChromeDriver();
    File file = new File("./HTMLFile/upfile.html");
    String filePath = file.getAbsolutePath();
    driver.get(filePath);
 
    //定位上传按钮， 添加本地文件
    driver.findElement(By.name("file")).sendKeys("D:\\upload_file.txt");
    Thread.sleep(5000);
 
    driver.quit();
```



### 十五、浏览器cookie操作

有时候我们需要验证浏览器中Cookie是否正确， 因为基于真实Cookie的测试是无法通过白盒测试和集成测试进行的。WebDriver提供了操作Cookie的相关方法可以读取、 添加和删除Cookie信息。

> WebDriver 操作Cookie的方法：
>
> - getCookies() 获得所有 cookie 信息。
> - getCookieNamed(String name) 返回字典的key为“name”的Cookie信息。
> - addCookie(cookie dict) 添加Cookie。“cookie_dict”指字典对象，必须有 name和value值。
> - deleteCookieNamed(String name) 删除Cookie 信息。 “name”是要删除的 cookie的名称； “optionsString” 是该Cookie的选项，目前支持的选项包括“路径” ， “域” 。
> - deleteAllCookies() 删除所有 cookie 信息。

```
    WebDriver driver = new ChromeDriver();
    driver.get("https://www.baidu.com");
 
    Cookie c1 = new Cookie("name", "key-aaaaaaa");
    Cookie c2 = new Cookie("value", "value-bbbbbb");
    driver.manage().addCookie(c1);
    driver.manage().addCookie(c2);
 
    //获得 cookie
    Set<Cookie> coo = driver.manage().getCookies();
    System.out.println(coo);
 
    //删除所有 cookie
    //driver.manage().deleteAllCookies();
 
    driver.quit();
```



### 十六、调用JavaScript代码

虽然WebDriver提供了操作浏览器的前进和后退方法，但对于浏览器滚动条并没有提供相应的操作方法。在这种情况下，就可以借助JavaScript来控制浏览器的滚动条。WebDriver提供了executeScript()方法来执行JavaScript代码。

> 用于调整浏览器滚动条位置的JavaScript代码如下：
>
> ```
> <!-- window.scrollTo(左边距,上边距); -->
> window.scrollTo(0,450);
> ```

window.scrollTo()方法用于设置浏览器窗口滚动条的水平和垂直位置。方法的第一个参数表示水平的左间距，第二个参数表示垂直的上边距。其代码如下：

```
    WebDriver driver = new ChromeDriver();
 
 
    //设置浏览器窗口大小
    driver.manage().window().setSize(new Dimension(700, 600));
    driver.get("https://www.baidu.com");
 
 
    //进行百度搜索
    driver.findElement(By.id("kw")).sendKeys("webdriver api");
    driver.findElement(By.id("su")).click();
    Thread.sleep(2000);
 
 
    //将页面滚动条拖到底部
    ((JavascriptExecutor)driver).executeScript("window.scrollTo(100,450);");
    Thread.sleep(3000);
 
 
    driver.quit();
```



### 十七、获取窗口截图

```
    WebDriver driver = new ChromeDriver();
    driver.get("https://www.baidu.com");
 
    File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    try {
      FileUtils.copyFile(srcFile,new File("d:\\screenshot.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
 
    driver.quit();
```

