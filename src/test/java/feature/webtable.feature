Feature: Webtable
  This feature shows user details
  Scenario: Add user and validate if it is added successfully
    Given I navigate to the webtable page
    When I click Add User link
    Then I should see Add User tab
    When I add user data and click Save button
    Then User should be added in the webtable
    When I delete the novak user
    Then User should get deleted from the webtable