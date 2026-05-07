Feature: Laptop Search on Akakce

  @test
  Scenario: Filter and sort laptop results, then verify seller navigation button
    Given the user opens the Akakce mobile application
    When the user enters "Laptop" in the search field and submits the query
    And the user taps the Filter button
    And the user selects "4K" as a filter option
    And the user taps the show products button
    And the user selects "En Yüksek Fiyat" as the sort order
    And the user taps on the 10th product in the results list
    And the user taps the Go to Product button
    Then the "Satıcıya Git" button should be visible on the product detail screen