
@tag #lesson #195
Feature: Purchase the order from ecom website
    
 Background:  
 	Given I landed on Ecommerce page
 

  @Regression
  Scenario Outline: Postive Test of Submitting the order.
    Given Logged in with username <name> and password <password> 
    When I add the <productName> from  Cart
    And Checkout <productName> and submit the Order
    Then  verify the "THANK YOU FOR YOUR ORDER." is displayed on Confirmation page 
       
    Examples: 
      | name 									 | password 				| productName | status  |
      | rahulshetty@gmail.com  | IamKing@000 			| ZARA COAT 3  | success |
       
