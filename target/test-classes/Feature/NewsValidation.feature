@newsValidation
Feature: Validate the news site is posting the valid news or not

  Scenario Outline: Validation site to prevent fake news
    Given user launches browser
    Then navigates to news portal and verifies the "<verify_Title>"page title
    When user retrieves the first story from the news portal
    Then user navigates to the google page and enters the retrieved text
    And verifies the news is valid or not

    Examples: 
      |verify_Title|
      |News \| The Guardian|