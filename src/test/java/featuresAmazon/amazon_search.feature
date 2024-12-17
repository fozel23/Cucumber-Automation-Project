
Feature: Ebay üzerinde ürün arama

  Scenario: Ebay'de Samsung Galaxy S23 araması yapmak
    Given I open Ebay
    When I search for "Samsung Galaxy S23"
    Then I should see products related to "Samsung Galaxy S23"
    And I close the browser
