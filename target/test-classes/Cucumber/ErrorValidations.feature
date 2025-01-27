#199
@tag
Feature: Error Validation
  I want to use this template for my feature file

  @ErrorValidation
  Scenario Outline: Doing ErrorValidations
    Given I landed on Ecommerce Page
    When logged in with username <name> and password <password>
    Then "Incorrect email or password." message is displayed

    Examples: 
      | name 									 | password 				| 
      | rahulshetty@gmail.com  | IamKing@000 			| 
