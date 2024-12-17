
Feature: Trendyol üzerinde arama yapma

  Scenario: Google üzerinden Trendyol araması yapmak ve ürünleri görmek
    Given I open Trendyol
    When I search for "iPhone 15" on Trendyol
    Then I should see results related to "iPhone 15"
    Then I close the browser



