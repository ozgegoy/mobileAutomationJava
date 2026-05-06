package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.AkakcePage;

import java.net.MalformedURLException;

public class AkakceSteps {
    public AkakcePage akakcePage = new AkakcePage();

    @Given("the user opens the Akakce mobile application")
    public void theUserOpensTheAkakceMobileApplication() throws MalformedURLException {
        Assert.assertTrue(akakcePage.isHomePageDisplayed());
    }

    @When("the user enters {string} in the search field and submits the query")
    public void theUserEntersInTheSearchFieldAndSubmitsTheQuery(String searchText) {
        akakcePage.clickSearchButton();
        akakcePage.setSearchButton(searchText);
    }

    @And("the user taps the Filter button")
    public void theUserTapsTheFilterButton() {
        akakcePage.clickFilterButton();
    }

    @And("the user selects {string} as a filter option")
    public void theUserSelectsAsAFilterOption(String filterText) {
        akakcePage.clickFilterOption(filterText);
    }

    @And("the user taps the show products button")
    public void theUserTapsTheButton() {
        akakcePage.clickShowProductsButton();
    }

    @And("the user selects {string} as the sort order")
    public void theUserSelectsAsTheSortOrder(String sortText) {
        akakcePage.clickSortingOptions();
        akakcePage.clickSortName(sortText);
    }

    @And("the user taps on the {string}th product in the results list")
    public void theUserTapsOnTheThProductInTheResultsList(String productId) {
        akakcePage.clickProduct(productId);
    }

    @And("the user taps the Go to Product button")
    public void theUserTapsTheGoToProductButton() {
        akakcePage.clickDetailButton();
    }

    @Then("the {string} button should be visible on the product detail screen")
    public void theButtonShouldBeVisibleOnTheProductDetailScreen(String text) {
        Assert.assertTrue(akakcePage.isProductDisplayed(text));
    }
}
