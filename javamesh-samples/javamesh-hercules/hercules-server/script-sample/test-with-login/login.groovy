import static net.grinder.script.Grinder.grinder
import static org.junit.Assert.*
import net.grinder.plugin.http.HTTPPluginControl
import net.grinder.plugin.http.HTTPRequest
import net.grinder.script.GTest
import net.grinder.scriptengine.groovy.junit.GrinderRunner
import net.grinder.scriptengine.groovy.junit.annotation.BeforeProcess
import net.grinder.scriptengine.groovy.junit.annotation.BeforeThread
 
import org.junit.After
import org.junit.Before;
import org.junit.Test
import org.junit.runner.RunWith
 
import HTTPClient.CookieModule
import HTTPClient.HTTPResponse
import HTTPClient.NVPair
 
/**
 * A simple example using the HTTP plugin that shows the retrieval of a
 * single page via HTTP. 
 * 
 * This script is automatically generated by ngrinder.
 * 
 * @author Gisoo Gwon
 */
@RunWith(GrinderRunner)
class Login {
    public static GTest test
    public static HTTPRequest request
    public Object cookies = []
 
    @BeforeProcess
    public static void beforeProcess() {
        HTTPPluginControl.getConnectionDefaults().timeout = 6000
        test = new GTest(1, "login test")
        request = new HTTPRequest()
        test.record(request);
    }
 
    @BeforeThread
    public void beforeThread() {
        grinder.statistics.delayReports=true;
         
        // reset to the all cookies
        def threadContext = HTTPPluginControl.getThreadHTTPClientContext()
        cookies = CookieModule.listAllCookies(threadContext)
        cookies.each {
            CookieModule.removeCookie(it, threadContext)
        }
         
        // do login & save to the login info in cookies 
        NVPair[] params = [new NVPair("id", "MY_ID"), new NVPair("pw", "MY_PASSWORD")];
        HTTPResponse res = request.POST("https://login.site.com/login/do", params);
        cookies = CookieModule.listAllCookies(threadContext)
    }
 
    @Before
    public void before() {
        // set cookies for login state
        def threadContext = HTTPPluginControl.getThreadHTTPClientContext()
        cookies.each {
            CookieModule.addCookie(it ,threadContext)
            grinder.logger.info("{}", it)
        }
    }
 
    @Test
    public void test(){
        HTTPResponse result = request.GET("http://my.site.com")
 
        if (result.statusCode == 301 || result.statusCode == 302) {
            grinder.logger.warn("Warning. The response may not be correct. The response code was {}.", result.statusCode);
        } else {
            assertThat(result.statusCode, is(200));
        }
    }
}
