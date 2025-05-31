  Feature: Object API

 Scenario: 
    Given the base url is "https://whitesmokehouse.com"

  Scenario:
    When Send a http "POST" request to "/webhook/api/login" with body:
      """
      {
      "email": "w1ndtes206@yopmail.com",
      "password": "@dmin123"
    }
      """
  Then The response status code should be 200
  And Save the token from the response to local storage

 
Scenario:
    When Send a http "POST" request to "/webhook/api/objects" with body:
    """
    {
      "name": "Ipad Winda",
      "data": {
        "year": 2022,
        "price": 599,
        "cpu_model": "Chipset A13",
        "hard_disk_size": "64GB",
        "capacity": "1",
        "screen_size": "10.9 Inch",
        "color": "Silver"
      }
    }
    """
    Then The response status code should be 200
    And Save the object id from the response to local storage

Scenario: 
Given Make sure token in local storage not empty
When I send a http "PUT" request to "/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/" with body:
"""
{
  "name": "Ipad Winda di-Update",
  "data": {
    "year": 2022,
    "price": 599,
    "cpu_model": "Chipset A13",
    "hard_disk_size": "64GB",
    "capacity": "1",
    "screen_size": "10.9 Inch",
    "color": "Silver"
  }
}
"""
Then The response status code should be 200
And Name in the response must be "Ipad Winda di-Update"
And CPU Model in the response must be "Chipset A13"
And Screen size in the response must be "10.9 Inch"    

Scenario:     
Given Make sure token in local storage not empty 
When User Send a http "GET" request to "/webhook/api/objectslistId?id=" with body:
"""
{}
"""
Then The response status code should be 200
When Send a http "GET" request to "/webhook/api/objectslistId?id=" with body:
"""
{}
"""
Then The response status code should be 200

Scenario:     
Given Make sure token in local storage not empty 
When User Send a http "DELETE" request to "/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" with body:
"""
{}
"""
Then The response status code should be 200
