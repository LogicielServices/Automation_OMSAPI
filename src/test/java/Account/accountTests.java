package Account;

import APIHelper.Global;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static APIHelper.LoggingManager.logger;
import static io.restassured.RestAssured.given;

public class accountTests {
    @Test(priority = 1, dataProvider = "CreateAccountsData", dataProviderClass = ExcelDataProvider.class, groups = {"CreateAccountsData"})
    public void createAccount(String accountTestCaseID, String accountBasePath, String accountBody, String accountContentType, String accountStatusCode, String accountResponse) {
        try {
            logger.info("TestCase : {}", accountTestCaseID);
            RestAssured.baseURI = Global.ApiBaseURL;
            Response response =
                    given()
                            .header("Content-Type", accountContentType)
                            .body(accountBody)
                            .when()
                            .post(accountBasePath)
                            .then().log().all()
                            .extract().response();
            Assert.assertEquals(response.statusCode(), Integer.parseInt(accountStatusCode));
            Assert.assertEquals(response.getBody().asString(), accountResponse);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Test(priority = 2, dataProvider = "GetAccountsData", dataProviderClass = ExcelDataProvider.class, groups = {"GetAccountsData"})
    public void getAccount(String accountTestCaseID, String accountBasePath, String accountContentType, String accountStatusCode) {
        try {
            logger.info("TestCase : {}", accountTestCaseID);
            RestAssured.baseURI = Global.ApiBaseURL;
            Response response =
                    given()
                            .header("Content-Type", accountContentType)
                            .when()
                            .get(accountBasePath + "?pageNumber=" + Global.pageNumber + "&pageSize=" + Global.pageSize)
                            .then().log().all()
                            .extract().response();
            Assert.assertEquals(response.statusCode(), Integer.parseInt(accountStatusCode));
            Global.pageSize = response.jsonPath().get("pagination.totalCount");
            System.out.println(Global.pageSize);

            RestAssured.baseURI = Global.ApiBaseURL;
            Response newResponse =
                    given()
                            .header("Content-Type", accountContentType)
                            .when()
                            .get(accountBasePath + "?pageNumber=" + Global.pageNumber + "&pageSize=" + Global.pageSize)
                            .then().log().all()
                            .extract().response();

            Assert.assertEquals(newResponse.statusCode(), Integer.parseInt(accountStatusCode));
            List<Map<String, Object>> data = newResponse.jsonPath().getList("data");

            for (Map<String, Object> obj : data) {
                if (obj.get("booth").equals("TEST") && obj.get("name").equals("testMuzammil001") && obj.get("value").equals("testMuzammil001")) {
                    System.out.println(obj);
                    Global.accountId = (int) obj.get("id");
                    break;
                }
            }

        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Test(priority = 3, dataProvider = "DeleteAccountsData", dataProviderClass = ExcelDataProvider.class, groups = {"DeleteAccountsData"})
    public void deleteAccount(String accountTestCaseID, String accountBasePath, String accountContentType, String accountStatusCode, String accountResponse) {
        try {
            logger.info("TestCase : {}", accountTestCaseID);
            RestAssured.baseURI = Global.ApiBaseURL;
            Response response =
                    given()
                            .header("Content-Type", accountContentType)
                            .when()
                            .delete(accountBasePath + "?id=" + Global.accountId)
                            .then().log().all()
                            .extract().response();
            Assert.assertEquals(response.statusCode(), Integer.parseInt(accountStatusCode));
            Assert.assertEquals(response.getBody().asString(), accountResponse);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
